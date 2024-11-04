/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2022, 2024 ah-OOG-ah
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

package klaxon.klaxon.goverlays.events;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import com.gtnewhorizons.navigator.api.NavigatorApi;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import klaxon.klaxon.goverlays.navigator.PollutionLayerManager;

public class ClientProxy extends CommonProxy {

    public static KeyBinding toggleLabels;
    private static final String KEYBIND_CATEGORY = "goverlays.key.category";

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        // Register a keybind for labels on the overlay
        toggleLabels = new KeyBinding("Toggle labels on the pollution overlay", Keyboard.KEY_P, KEYBIND_CATEGORY);
        ClientRegistry.registerKeyBinding(toggleLabels);

        FMLCommonHandler.instance()
            .bus()
            .register(new ClientEvents());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        // Register the pollution overlay
        NavigatorApi.registerLayerManager(PollutionLayerManager.INSTANCE);
    }
}
