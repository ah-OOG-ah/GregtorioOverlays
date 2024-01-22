package klaxon.klaxon.goverlays.journeymap;

import com.sinthoras.visualprospecting.integration.journeymap.buttons.LayerButton;

import klaxon.klaxon.goverlays.visualprospecting.model.PollutionOverlayButtonManager;

public class PollutionOverlayButton extends LayerButton {

    public static final PollutionOverlayButton instance = new PollutionOverlayButton();

    public PollutionOverlayButton() {
        super(PollutionOverlayButtonManager.instance);
    }
}
