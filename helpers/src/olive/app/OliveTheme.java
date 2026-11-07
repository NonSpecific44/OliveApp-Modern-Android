package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

/**
 * Appearance settings. Colours: wallpaper colours (Material You) or Olive green, applied to each screen as it
 * is created by overlaying the theme's colour-role attributes. Theme: system default, light or dark, set as the
 * app's own night mode (Android 12+, which remembers it). Both options need Android 12: before that the app
 * already uses Olive green, and apps can't override light/dark on their own.
 */
public final class OliveTheme {
    static final String PREFS = "Olive_Settings";
    static final String COLOURS = "appearance_colours";
    static final String COLOURS_OLIVE = "olive";
    static final String THEME = "appearance_theme";
    private static final String[] THEME_LABELS = {"System default", "Light", "Dark"};
    private static final String[] COLOUR_LABELS = {"Wallpaper colours", "Olive green"};

    /** The theme of the screen most recently shown, for the original code's untheme'd drawable loads. */
    static volatile Resources.Theme current;

    private OliveTheme() {
    }

    static boolean supported() {
        return Build.VERSION.SDK_INT >= 31;
    }

    static boolean olive(Context c) {
        return supported() && COLOURS_OLIVE.equals(c.getSharedPreferences(PREFS, 0).getString(COLOURS, "wallpaper"));
    }

    /** Called as each screen is created, before its layout is inflated. */
    public static void apply(Activity activity) {
        if (olive(activity)) {
            int style = activity.getResources().getIdentifier("OliveColors.Olive", "style", activity.getPackageName());
            if (style != 0) {
                activity.getTheme().applyStyle(style, true);
            }
        }
        current = activity.getTheme();
    }

    /** Called as each screen comes to the front. */
    public static void resumed(Activity activity) {
        current = activity.getTheme();
    }

    /**
     * Replacement for Resources.getDrawable(id) in the original code: loads with the current screen's theme so
     * colours follow the chosen palette.
     */
    @SuppressWarnings("deprecation")
    public static Drawable drawable(Resources resources, int id) {
        Resources.Theme theme = current;
        if (theme != null) {
            try {
                return resources.getDrawable(id, theme);
            } catch (RuntimeException ignored) {
            }
        }
        return resources.getDrawable(id);
    }

    /** 0 = system default, 1 = light, 2 = dark (as last chosen here; Android keeps the mode itself). */
    static int themeMode(Context c) {
        return supported() ? c.getSharedPreferences(PREFS, 0).getInt(THEME, 0) : 0;
    }

    static void setThemeMode(Context c, int choice) {
        c.getSharedPreferences(PREFS, 0).edit().putInt(THEME, choice).commit();
        int mode = choice == 1 ? UiModeManager.MODE_NIGHT_NO : choice == 2 ? UiModeManager.MODE_NIGHT_YES : UiModeManager.MODE_NIGHT_AUTO;
        // Android applies this to the app's screens itself, recreating them in the new theme.
        ((UiModeManager) c.getSystemService(Context.UI_MODE_SERVICE)).setApplicationNightMode(mode);
    }

    static void setOlive(Activity activity, boolean olive) {
        activity.getSharedPreferences(PREFS, 0).edit().putString(COLOURS, olive ? COLOURS_OLIVE : "wallpaper").commit();
        OliveWidget.render(activity.getApplicationContext());
        // Rebuild the screens in the new colours (the tabs, which host Settings, rebuild their content too).
        Activity root = activity.getParent() != null ? activity.getParent() : activity;
        root.recreate();
    }

    /** Wires up Settings → Appearance; hides it before Android 12. */
    public static void attachSettings(final Activity activity) {
        View themeRow = activity.findViewById(OliveUi.id(activity, "appearanceThemeRow"));
        View coloursRow = activity.findViewById(OliveUi.id(activity, "appearanceColoursRow"));
        if (themeRow == null || coloursRow == null) {
            return;
        }
        if (!supported()) {
            themeRow.setVisibility(View.GONE);
            coloursRow.setVisibility(View.GONE);
            View header = activity.findViewById(OliveUi.id(activity, "appearanceHeader"));
            if (header != null) {
                header.setVisibility(View.GONE);
            }
            return;
        }
        final TextView themeValue = (TextView) activity.findViewById(OliveUi.id(activity, "appearanceThemeValue"));
        final TextView coloursValue = (TextView) activity.findViewById(OliveUi.id(activity, "appearanceColoursValue"));
        themeValue.setText(THEME_LABELS[themeMode(activity)]);
        coloursValue.setText(COLOUR_LABELS[olive(activity) ? 1 : 0]);
        themeRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        .setTitle("Theme")
                        .setSingleChoiceItems(THEME_LABELS, themeMode(activity), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int which) {
                                d.dismiss();
                                themeValue.setText(THEME_LABELS[which]);
                                setThemeMode(activity, which);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        coloursRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int was = olive(activity) ? 1 : 0;
                new AlertDialog.Builder(activity)
                        .setTitle("Colours")
                        .setSingleChoiceItems(COLOUR_LABELS, was, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int which) {
                                d.dismiss();
                                if (which != was) {
                                    setOlive(activity, which == 1);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }
}
