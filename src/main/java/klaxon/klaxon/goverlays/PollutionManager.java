package klaxon.klaxon.goverlays;

import javax.annotation.Nullable;

import cpw.mods.fml.relauncher.Side;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import klaxon.klaxon.goverlays.network.pollution.PollutionMessage;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import gregtech.common.GT_Pollution;
import gregtech.common.render.GT_PollutionRenderer;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionChunkPosition;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;
import static klaxon.klaxon.goverlays.GregtorioOverlays.SIDE;

public class PollutionManager {

    // The master cache of all pollution everywhere
    private final Int2ObjectOpenHashMap<Long2IntOpenHashMap> pollutionCache = new Int2ObjectOpenHashMap<>();

    public Long2IntOpenHashMap getCache(int dim) {
        return pollutionCache.computeIfAbsent(dim, i -> new Long2IntOpenHashMap());
    }

    public void updateCache(int dim, Long2IntOpenHashMap chunks) {
        if (!chunks.isEmpty())
            getCache(dim).putAll(chunks);
    }

    public void updateCache(int dim, long cpos, int pollution) {
        getCache(dim).put(cpos, pollution);
    }

    /**
     * Sends the whole pollution cache in a packet for the client. Should only be called on the server side. While the
     * current implementation includes every chunk, this is not a guarantee! The packet is only guaranteed to hold
     * modified chunks since the last update.
     */
    public void updateDim(int dimID) {
        GregtorioOverlays.dispatcher.sendToDimension(
            new PollutionMessage(dimID, getCache(dimID), false), dimID);
    }

}
