package olive.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.reflect.Method;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.GraphicsMode;

/**
 * The player re-sets the play/pause icon on every metadata poll with a freshly loaded drawable.
 * Only a change in what the icon looks like may trigger the swap animation.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class PlayIconTest {
    private int key(String drawableName) throws Exception {
        Context c = RuntimeEnvironment.getApplication();
        Resources res = c.getResources();
        Drawable d = res.getDrawable(res.getIdentifier(drawableName, "drawable", "olive.app"), c.getTheme());
        Method m = Class.forName("olive.app.Compat").getMethod("playIconKey", Drawable.class);
        return (Integer) m.invoke(null, d);
    }

    @Test
    public void sameIconReloadedHasSameKey() throws Exception {
        // Regression: keys were per-object, so every poll looked like a change and the button pulsed.
        assertEquals(key("playpause"), key("playpause"));
        assertEquals(key("pause"), key("pause"));
    }

    @Test
    public void differentIconsHaveDifferentKeys() throws Exception {
        assertNotEquals(key("playpause"), key("pause"));
    }

    @Test
    public void pausedAndStoppedLookTheSame() throws Exception {
        assertEquals(key("pause"), key("stop"));
    }
}
