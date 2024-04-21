package klaxon.klaxon.goverlays.config;

import com.gtnewhorizon.gtnhlib.config.SimpleGuiFactory;
import net.minecraft.client.gui.GuiScreen;

@SuppressWarnings("unused")
public class GOGuiFactory implements SimpleGuiFactory {
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return GOGuiConfig.class;
    }
}
