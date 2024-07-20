package klaxon.klaxon.goverlays.mixins.late.journeymap;

import static journeymap.client.Constants.isPressed;
import static klaxon.klaxon.goverlays.events.ClientProxy.toggleLabels;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import journeymap.client.ui.fullscreen.Fullscreen;
import klaxon.klaxon.goverlays.config.GOConfig;

@SuppressWarnings("UnusedMixin")
@Mixin(Fullscreen.class)
public class FullscreenMixin {

    @Inject(method = "keyTyped", at = @At("TAIL"))
    private void gtorioo$injectKeybind(char c, int i, CallbackInfo ci) {
        if (isPressed(toggleLabels)) {
            GOConfig.alwaysShowAmt = !GOConfig.alwaysShowAmt;
        }
    }
}
