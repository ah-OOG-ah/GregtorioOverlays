package klaxon.klaxon.goverlays.mixins.late.gregtech;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;
import static org.objectweb.asm.Opcodes.PUTFIELD;

import java.util.ConcurrentModificationException;
import java.util.Set;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gregtech.common.GT_Pollution;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import klaxon.klaxon.goverlays.GregtorioOverlays;
import klaxon.klaxon.goverlays.PollutionManager;
import klaxon.klaxon.goverlays.utils.network.pollution.PollutionMessage;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionChunkPosition;

/**
 * Modifies GT_Pollution, adding a call to send data to the client.
 */
@Mixin(value = GT_Pollution.class, remap = false)
public abstract class GT_PollutionMixin {

    // List of all polluted chunks in a given dimension/world
    @Final
    @Shadow
    private Set<ChunkCoordIntPair> pollutedChunks;

    // World attached to GT_Pollution instance
    @Final
    @Shadow
    private World world;

    // Dimension for convenience
    private int dimensionId;

    // Inject into the pollution tick manager. It gets called every 1200 ticks (60 secs); update the client too.
    // Only gets called on the server side, due to how tickPollutionInWorld is called
    // Injected right at the start of every pollution loop, every 1200t/60s.
    @Inject(
        method = "tickPollutionInWorld",
        at = @At(
            value = "FIELD",
            target = "gregtech/common/GT_Pollution.blank:Z",
            opcode = PUTFIELD,
            remap = false,
            ordinal = 0),
        require = 1)
    private void gtorioo$onTickPollutionInWorld(CallbackInfo ci) {

        // Get dimension
        dimensionId = world.provider.dimensionId;

        // Convert coords to PCPs
        Long2LongOpenHashMap mixinPollutedPositions = new Long2LongOpenHashMap();

        try {

            pollutedChunks.forEach(intPair -> {

                // Convert a pair to a position and add it
                PollutionChunkPosition position = PollutionManager
                    .getByChunkAndDimCommon(dimensionId, intPair.chunkXPos, intPair.chunkZPos);
                mixinPollutedPositions.put(
                    ChunkCoordIntPair.chunkXZ2Int(intPair.chunkXPos, intPair.chunkZPos),
                    position != null ? position.pollution : 0);
            });

            // Dispatch!
            GregtorioOverlays.dispatcher
                .sendToDimension(new PollutionMessage(dimensionId, mixinPollutedPositions, false), dimensionId);
        } catch (ConcurrentModificationException e) {

            LOGGER.error("Warning, caught ConcurrentModificationException!");
            LOGGER.error("Sending no chunks to client.");
        }
    }
}
