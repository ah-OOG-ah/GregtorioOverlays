package klaxon.klaxon.goverlays.mixins.late.gregtech;

import static klaxon.klaxon.goverlays.utils.ChunkPos.pack;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gregtech.common.GT_Pollution;
import klaxon.klaxon.goverlays.GregtorioOverlays;

@SuppressWarnings("UnusedMixin")
@Mixin(value = GT_Pollution.class, remap = false)
public abstract class GT_PollutionMixin {

    @Final
    @Shadow
    private World world;

    @Inject(method = "setChunkPollution", at = @At(value = "TAIL"))
    public void gtorioo$updateFromGT(ChunkCoordIntPair coord, int pollution, CallbackInfo ci) {
        GregtorioOverlays.proxy.pollution
            .updateCache(world.provider.dimensionId, pack(coord.chunkXPos, coord.chunkZPos), pollution);
    }
}
