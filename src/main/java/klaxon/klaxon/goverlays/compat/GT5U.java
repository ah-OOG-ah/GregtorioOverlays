package klaxon.klaxon.goverlays.compat;

import static klaxon.klaxon.goverlays.GregtorioOverlays.ticksPerUpdate;
import static klaxon.klaxon.goverlays.mixins.late.gregtech.GT_PollutionAccessor.getCycleLen;

import gregtech.GT_Mod;

/**
 * This class should only be loaded when GT5u is loaded!
 */
public class GT5U {

    public static boolean isPollutionOn() {
        return GT_Mod.gregtechproxy.mPollution;
    }

    public static void preInit() {
        ticksPerUpdate = getCycleLen();
    }
}
