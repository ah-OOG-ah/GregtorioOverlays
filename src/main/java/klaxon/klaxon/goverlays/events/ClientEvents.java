package klaxon.klaxon.goverlays.events;

import static klaxon.klaxon.goverlays.events.ClientProxy.toggleLabels;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import klaxon.klaxon.goverlays.config.GOConfig;

public class ClientEvents {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onKeyInput(KeyInputEvent event) {
        if (toggleLabels.isPressed()) {
            GOConfig.alwaysShowAmt = !GOConfig.alwaysShowAmt;
        }
    }
}
