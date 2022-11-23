package klaxon.klaxon.gregtoriooverlays.journeymap;

import com.sinthoras.visualprospecting.integration.journeymap.buttons.LayerButton;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionOverlayButtonManager;

public class PollutionOverlayButton extends LayerButton {

    public static final PollutionOverlayButton instance = new PollutionOverlayButton();

    public PollutionOverlayButton() {
        super(PollutionOverlayButtonManager.instance);
    }
}
