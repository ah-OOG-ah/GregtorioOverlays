package klaxon.klaxon.goverlays.mixins.late.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import gregtech.common.GT_Pollution;

@Mixin(GT_Pollution.class)
public interface GT_PollutionAccessor {

    @Accessor("cycleLen")
    static short getCycleLen() {
        return 0; // Mixin will replace this at runtime, don't worry
    }
}
