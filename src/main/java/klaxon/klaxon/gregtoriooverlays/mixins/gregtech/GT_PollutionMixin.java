package klaxon.klaxon.gregtoriooverlays.mixins.gregtech;

import gregtech.common.GT_Pollution;
import java.util.HashSet;
import java.util.Set;
import klaxon.klaxon.gregtoriooverlays.GregtorioOverlays;
import klaxon.klaxon.gregtoriooverlays.gregtech.PollutionFetcher;
import klaxon.klaxon.gregtoriooverlays.utils.network.pollution.PollutionMessage;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionChunkPosition;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    // Dimension for convienence
    private int dimensionId;

    // Inject into the pollution tick manager. It gets called every 1200 ticks (60 secs); update the client too.
    // Going to be injected at RETURN so it fires after pollution is updated, currently injected at HEAD for testing
    @Inject(method = "tickPollutionInWorld", at = @At("HEAD"), require = 1)
    private void onTickPollutionInWorld(int aTickID, CallbackInfo ci) {

        // Only run every 1200 ticks, as set by onWorldTick
        if (aTickID == 0) {

            dimensionId = world.provider.dimensionId;
            HashSet<PollutionChunkPosition> mixinPollutedPositions = new HashSet<>();

            ChunkCoordIntPair[] testChunks = pollutedChunks.toArray(new ChunkCoordIntPair[0]);
            PollutionChunkPosition test = PollutionFetcher.getByChunkAndDimCommon(
                    dimensionId, testChunks[0].chunkXPos, testChunks[1].chunkZPos);

            pollutedChunks.forEach(intPair -> {

                // Convert a pair to a position and add it
                PollutionChunkPosition position =
                        PollutionFetcher.getByChunkAndDimCommon(dimensionId, intPair.chunkXPos, intPair.chunkZPos);
                mixinPollutedPositions.add(position);
            });

            GregtorioOverlays.dispatcher.sendToDimension(
                    new PollutionMessage(mixinPollutedPositions), dimensionId);
        }
    }
}
