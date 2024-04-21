package klaxon.klaxon.goverlays.events;

import static klaxon.klaxon.goverlays.GregtorioOverlays.*;
import static klaxon.klaxon.goverlays.compat.Compat.ENABLED;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class CommonEvents {

    @SubscribeEvent
    public void onServerTick(TickEvent.WorldTickEvent tickEvent) {
        if (tickEvent.side != Side.SERVER || !ENABLED) return;
        if (tickEvent.world.getTotalWorldTime() % ticksPerUpdate != ticksPerUpdate / 2) return;

        // Dispatch! Chunks in the cache should be updated on change by the update backend
        proxy.pollution.updateDim(tickEvent.world.provider.dimensionId);
    }
}
