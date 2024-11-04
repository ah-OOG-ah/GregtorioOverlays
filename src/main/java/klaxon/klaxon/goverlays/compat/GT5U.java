package klaxon.klaxon.goverlays.compat;

import static klaxon.klaxon.goverlays.GregtorioOverlays.ticksPerUpdate;
import static klaxon.klaxon.goverlays.mixins.late.gregtech.GT_PollutionAccessor.getCycleLen;

import gregtech.GTMod;

/**
 * This class should only be loaded when GT5u is loaded!
 */
public class GT5U {

    public static boolean isPollutionOn() {
        return GTMod.gregtechproxy.mPollution;
    }

    public static void preInit() {
        ticksPerUpdate = getCycleLen();
    }
}
