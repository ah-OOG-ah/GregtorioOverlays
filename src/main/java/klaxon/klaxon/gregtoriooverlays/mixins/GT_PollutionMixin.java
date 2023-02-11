package klaxon.klaxon.gregtoriooverlays.mixins;

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

import static org.objectweb.asm.Opcodes.PUTFIELD;

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
        at =
        @At(
            value = "FIELD",
            opcode = PUTFIELD,
            target =
                "gregtech/common/GT_Pollution.blank",
            ordinal = 1
        ),
        remap = false,
        require = 1)
    private void onTickPollutionInWorld() {

        // Get dimension
        dimensionId = world.provider.dimensionId;

        // Convert coords to PCPs
        HashSet<PollutionChunkPosition> mixinPollutedPositions = new HashSet<>();
        pollutedChunks.forEach(intPair -> {

            // Convert a pair to a position and add it
            PollutionChunkPosition position =
                    PollutionFetcher.getByChunkAndDimCommon(dimensionId, intPair.chunkXPos, intPair.chunkZPos);
            mixinPollutedPositions.add(position);
        });

        // Dispatch!
        GregtorioOverlays.dispatcher.sendToDimension(new PollutionMessage(mixinPollutedPositions), dimensionId);
    }
}

