package klaxon.klaxon.goverlays.navigator;

import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.navigator.api.model.SupportedMods;
import com.gtnewhorizons.navigator.api.model.buttons.ButtonManager;

import klaxon.klaxon.goverlays.GregtorioOverlays;

public class PollutionButtonManager extends ButtonManager {

    public static final PollutionButtonManager INSTANCE = new PollutionButtonManager();

    @Override
    public ResourceLocation getIcon(SupportedMods mod, String theme) {
        return new ResourceLocation(GregtorioOverlays.MODID, "textures/icons/pollution.png");
    }

    @Override
    public String getButtonText() {
        return "goverlays.button.pollution";
    }
}
