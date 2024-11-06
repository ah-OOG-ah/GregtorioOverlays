/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2022-2024 ah-OOG-ah
 *
 * GregtorioOverlays is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GregtorioOverlays is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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

import gregtech.common.pollution.Pollution;
import klaxon.klaxon.goverlays.GregtorioOverlays;

@SuppressWarnings("UnusedMixin")
@Mixin(value = Pollution.class, remap = false)
public abstract class PollutionMixin {

    @Final
    @Shadow
    private World world;

    @Inject(method = "setChunkPollution", at = @At(value = "TAIL"))
    private void gtorioo$updateFromGT(ChunkCoordIntPair coord, int pollution, CallbackInfo ci) {
        GregtorioOverlays.proxy.pollution
            .updateCache(world.provider.dimensionId, pack(coord.chunkXPos, coord.chunkZPos), pollution);
    }
}
