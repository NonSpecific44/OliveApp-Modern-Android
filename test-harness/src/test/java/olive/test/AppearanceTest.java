package olive.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.GraphicsMode;
import org.robolectric.annotation.LooperMode;
import org.robolectric.shadows.ShadowAppWidgetManager;
import org.robolectric.shadows.ShadowLooper;

/** Settings → Appearance: wallpaper colours or Olive green, and the light/dark choice. */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34, qualifiers = "w411dp-h891dp-xhdpi")
@LooperMode(LooperMode.Mode.PAUSED)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class AppearanceTest {
    static final String SERVER = "127.0.0.1:8765";
    static final File OUT = new File(System.getProperty("shots.dir", "shots"));

    static Context app() {
        return RuntimeEnvironment.getApplication();
    }

    static Class<? extends Activity> activity(String n) throws Exception {
        return Class.forName("olive.app." + n).asSubclass(Activity.class);
    }

    static int res(String type, String name) {
        return app().getResources().getIdentifier(name, type, "olive.app");
    }

    static int color(String name) {
        return app().getColor(res("color", name));
    }

    /** The colour a screen's theme gives a colour role. */
    static int role(Activity a, String role) {
        TypedValue v = new TypedValue();
        assertTrue(a.getTheme().resolveAttribute(res("attr", "olive_m3_" + role), v, true));
        return a.getColor(v.resourceId);
    }

    static void settle() throws Exception {
        for (int i = 0; i < 12; i++) {
            ShadowLooper.idleMainLooper(100, java.util.concurrent.TimeUnit.MILLISECONDS);
            Thread.sleep(100);
        }
    }

    static void olive(boolean on) {
        app().getSharedPreferences("Olive_Settings", 0).edit().putString("appearance_colours", on ? "olive" : "wallpaper").commit();
    }

    static int centrePixel(Drawable d) {
        Bitmap b = Bitmap.createBitmap(96, 96, Bitmap.Config.ARGB_8888);
        d.setBounds(0, 0, 96, 96);
        d.draw(new Canvas(b));
        return b.getPixel(48, 52);
    }

    static void shot(Activity a, String name) throws Exception {
        OUT.mkdirs();
        DisplayMetrics dm = app().getResources().getDisplayMetrics();
        View v = a.getWindow().getDecorView();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, dm.widthPixels, dm.heightPixels);
        Bitmap b = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        v.draw(new Canvas(b));
        FileOutputStream out = new FileOutputStream(new File(OUT, name + ".png"));
        b.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.close();
    }

    @Before
    public void device() {
        app().getSharedPreferences("Olive_Settings", 0).edit().putString("devIP", SERVER).commit();
    }

    @Test
    public void wallpaperColoursByDefaultAndOliveGreenWhenChosen() throws Exception {
        assertNotEquals("the two palettes differ", color("m3dyn_primary"), color("m3olive_primary"));
        Activity normal = Robolectric.buildActivity(activity("Playscreen")).setup().get();
        assertEquals(color("m3dyn_primary"), role(normal, "primary"));
        assertEquals(color("m3dyn_surface"), role(normal, "surface"));

        olive(true);
        Activity green = Robolectric.buildActivity(activity("Playscreen")).setup().get();
        assertEquals(color("m3olive_primary"), role(green, "primary"));
        assertEquals(color("m3olive_surface"), role(green, "surface"));
    }

    @Test
    public void originalCodesDrawablesFollowTheChosenPalette() throws Exception {
        olive(true);
        Activity green = Robolectric.buildActivity(activity("Playscreen")).setup().get();
        Method drawable = Class.forName("olive.app.OliveTheme").getMethod("drawable", Resources.class, int.class);
        // The player loads its rating stars with getResources().getDrawable(id); that now goes through the theme.
        Drawable star = (Drawable) drawable.invoke(null, green.getResources(), res("drawable", "star_set"));
        assertEquals("filled star in Olive green", color("m3olive_primary"), centrePixel(star));
    }

    @Test
    public void settingsShowsAppearanceAndSwitchingRecreatesTheScreen() throws Exception {
        ActivityController<? extends Activity> settings = Robolectric.buildActivity(activity("SettingsTab")).setup();
        Activity a = settings.get();
        Class.forName("olive.app.OliveTheme").getMethod("attachSettings", Activity.class).invoke(null, a);
        assertEquals(View.VISIBLE, a.findViewById(res("id", "appearanceThemeRow")).getVisibility());
        assertEquals("System default", ((TextView) a.findViewById(res("id", "appearanceThemeValue"))).getText().toString());
        assertEquals("Wallpaper colours", ((TextView) a.findViewById(res("id", "appearanceColoursValue"))).getText().toString());

        Method setOlive = Class.forName("olive.app.OliveTheme").getDeclaredMethod("setOlive", Activity.class, boolean.class);
        setOlive.setAccessible(true);
        setOlive.invoke(null, a, true);
        assertEquals("olive", app().getSharedPreferences("Olive_Settings", 0).getString("appearance_colours", ""));

        Method setTheme = Class.forName("olive.app.OliveTheme").getDeclaredMethod("setThemeMode", Context.class, int.class);
        setTheme.setAccessible(true);
        setTheme.invoke(null, app(), 2);
        assertEquals(2, app().getSharedPreferences("Olive_Settings", 0).getInt("appearance_theme", -1));
    }

    @Test
    public void widgetUsesTheChosenPalette() throws Exception {
        ShadowAppWidgetManager widgets = Shadows.shadowOf(AppWidgetManager.getInstance(app()));
        int id = widgets.createWidget(Class.forName("olive.app.OliveWidget").asSubclass(android.appwidget.AppWidgetProvider.class),
                res("layout", "olive_widget_dyn"));
        olive(true);
        Method render = Class.forName("olive.app.OliveWidget").getDeclaredMethod("render", Context.class);
        render.setAccessible(true);
        render.invoke(null, app());
        TextView title = (TextView) widgets.getViewFor(id).findViewById(res("id", "widgetTitle"));
        assertEquals(color("m3olive_on_surface"), title.getCurrentTextColor());
    }

    @Test
    public void screenshotsLight() throws Exception {
        olive(true);
        Activity play = Robolectric.buildActivity(activity("Playscreen")).setup().get();
        settle();
        shot(play, "40-olive-light-player");
        Activity albums = Robolectric.buildActivity(activity("AlbumTab")).setup().get();
        settle();
        shot(albums, "41-olive-light-albums");
    }

    @Test
    @Config(qualifiers = "w411dp-h891dp-night-xhdpi")
    public void screenshotsDark() throws Exception {
        olive(true);
        Activity play = Robolectric.buildActivity(activity("Playscreen")).setup().get();
        settle();
        shot(play, "42-olive-dark-player");
        Activity settings = Robolectric.buildActivity(activity("SettingsTab")).setup().get();
        Class.forName("olive.app.OliveTheme").getMethod("attachSettings", Activity.class).invoke(null, settings);
        settle();
        shot(settings, "43-olive-dark-settings");
    }
}
