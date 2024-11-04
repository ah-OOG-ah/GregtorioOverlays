package klaxon.klaxon.goverlays.mixins.late.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import gregtech.common.pollution.Pollution;

@SuppressWarnings("UnusedMixin")
@Mixin(Pollution.class)
public interface GT_PollutionAccessor {

    @Accessor(value = "cycleLen", remap = false)
    static short getCycleLen() {
        return 0; // Mixin will replace this at runtime, don't worry
    }
}
