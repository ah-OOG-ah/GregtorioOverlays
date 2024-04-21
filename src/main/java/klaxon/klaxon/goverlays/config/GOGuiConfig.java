package klaxon.klaxon.goverlays.config;

import static klaxon.klaxon.goverlays.GregtorioOverlays.MODID;
import static klaxon.klaxon.goverlays.GregtorioOverlays.MODNAME;

import net.minecraft.client.gui.GuiScreen;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.SimpleGuiConfig;

public class GOGuiConfig extends SimpleGuiConfig {

    public GOGuiConfig(GuiScreen parent) throws ConfigException {
        super(parent, GOConfig.class, MODID, MODNAME);
    }
}
