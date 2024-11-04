/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2024 ah-OOG-ah
 *
 * GregtorioOverlays is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GregtorioOverlays is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package klaxon.klaxon.goverlays.navigator.journeymap;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.navigator.api.journeymap.render.JMLayerRenderer;
import com.gtnewhorizons.navigator.api.model.locations.ILocationProvider;
import com.gtnewhorizons.navigator.api.model.steps.RenderStep;

import klaxon.klaxon.goverlays.navigator.PollutionLayerManager;

public class PollutionLayerRenderer extends JMLayerRenderer {

    public static final PollutionLayerRenderer INSTANCE = new PollutionLayerRenderer();

    public PollutionLayerRenderer() {
        super(PollutionLayerManager.INSTANCE);
    }

    @Override
    @Nullable
    protected RenderStep generateRenderStep(ILocationProvider element) {
        if (!(element instanceof JMPollutionLocation location)) return null;
        return location;
    }
}
