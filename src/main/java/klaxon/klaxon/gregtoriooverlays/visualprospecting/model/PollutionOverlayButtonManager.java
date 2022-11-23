package klaxon.klaxon.gregtoriooverlays.visualprospecting.model;

import com.sinthoras.visualprospecting.integration.model.buttons.ButtonManager;

public class PollutionOverlayButtonManager extends ButtonManager {

    public static final PollutionOverlayButtonManager instance = new PollutionOverlayButtonManager();

    public PollutionOverlayButtonManager() {

        super("gregtoriooverlays.button.pollution", "pollution");
    }
}
