/**
 * This file is part of GregtorioOverlays - a mod to put pollution on the map.
 * Copyright (C) 2022-2024 ah-OOG-ah
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

package klaxon.klaxon.goverlays;

import static klaxon.klaxon.goverlays.GregtorioOverlays.MODID;
import static klaxon.klaxon.goverlays.GregtorioOverlays.MODNAME;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import klaxon.klaxon.goverlays.events.CommonProxy;

@Mod(
    modid = MODID,
    version = Tags.VERSION,
    name = MODNAME,
    acceptedMinecraftVersions = "[1.7.10]",
    dependencies = "required-after:navigator;after:gregtech;after:hbm;",
    guiFactory = "klaxon.klaxon.goverlays.config.GOGuiFactory")
public class GregtorioOverlays {

    public static final String MODNAME = "GregtorioOverlays";
    public static final String MODID = "goverlays";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    // Network things
    public static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    public static final int pollutionMessageId = 1;

    // How often to update the pollution cache. Some backends change this.
    public static int ticksPerUpdate = 1200;

    @SidedProxy(
        clientSide = "klaxon.klaxon.goverlays.events.ClientProxy",
        serverSide = "klaxon.klaxon.goverlays.events.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
