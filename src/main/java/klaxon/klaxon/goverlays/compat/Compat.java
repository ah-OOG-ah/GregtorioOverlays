package klaxon.klaxon.goverlays.compat;

import cpw.mods.fml.common.Loader;
import klaxon.klaxon.goverlays.Backend;

public class Compat {

    public static final boolean GT5U = Loader.isModLoaded("gregtech");
    public static final boolean HBM_NTM = Loader.isModLoaded("hbm");

    public static Backend BACKEND = GT5U ? Backend.GT5U : HBM_NTM ? Backend.HBM_NTM : Backend.NONE;
    // This only reflects the client's state. We should still be able to connect to enabled servers and working
    public static boolean ENABLED = true;
}
