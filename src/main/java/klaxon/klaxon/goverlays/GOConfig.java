package klaxon.klaxon.goverlays;

import static klaxon.klaxon.goverlays.GregtorioOverlays.MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = MODID)
public class GOConfig {

    @Config.Comment("Always show pollution amount")
    @Config.DefaultBoolean(false)
    public static boolean alwaysShowAmt;

    @Config.Comment("Use gibbl as the base unit instead of bbl")
    @Config.DefaultBoolean(true)
    public static boolean useGibblBase;

    @Config.Comment("Use binary prefixes instead of decimal")
    @Config.DefaultBoolean(false)
    public static boolean useBinaryPrefixes;

    @Config.Comment("The maximum alpha value for pollution")
    @Config.DefaultFloat(200F)
    @Config.RangeFloat(min = 0F, max = 255F)
    public static float maxAlpha;

    @Config.Comment("The number of different steps to display.")
    @Config.DefaultInt(8)
    @Config.RangeInt(min = 1, max = 255)
    public static int alphaSteps;

    @Config.Comment("The hex color to display for pollution")
    @Config.DefaultString("0xFF0000")
    public static String color;
}
