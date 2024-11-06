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

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;
import static klaxon.klaxon.goverlays.GregtorioOverlays.proxy;
import static klaxon.klaxon.goverlays.GregtorioOverlays.ticksPerUpdate;
import static klaxon.klaxon.goverlays.compat.Compat.BACKEND;
import static klaxon.klaxon.goverlays.compat.Compat.ENABLED;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import klaxon.klaxon.goverlays.Backend;
import klaxon.klaxon.goverlays.GregtorioOverlays;
import klaxon.klaxon.goverlays.PollutionManager;
import klaxon.klaxon.goverlays.compat.GT5U;
import klaxon.klaxon.goverlays.config.GOConfig;
import klaxon.klaxon.goverlays.network.pollution.PollutionMessage;
import klaxon.klaxon.goverlays.network.pollution.PollutionMessageHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public final PollutionManager pollution = new PollutionManager();

    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Come with me/and you can see/the results of your industrialization!");

        try {
            ConfigurationManager.registerConfig(GOConfig.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }

        // Hello? Is this thing on?
        if (BACKEND == Backend.GT5U && !GT5U.isPollutionOn()) {
            ENABLED = false;

            LOGGER.warn("Pollution is disabled!");
            LOGGER.warn("Not sure why I'm here...");
            // TODO: add non-pollution overlays
        }

        if (ENABLED) {
            switch (BACKEND) {
                case GT5U -> GT5U.preInit();
                case HBM_NTM, NONE -> {}
            }
        }

        LOGGER.info("Registering packets...");
        GregtorioOverlays.dispatcher.registerMessage(
            PollutionMessageHandler.class,
            PollutionMessage.class,
            GregtorioOverlays.pollutionMessageId,
            event.getSide());

        FMLCommonHandler.instance()
            .bus()
            .register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void postInit(FMLPostInitializationEvent event) {}

    @SubscribeEvent
    public void onServerTick(TickEvent.WorldTickEvent tickEvent) {
        if (tickEvent.side != Side.SERVER || !ENABLED) return;
        if (tickEvent.world.getTotalWorldTime() % ticksPerUpdate != 1) return;

        // Dispatch! Chunks in the cache should be updated on change by the update backend
        proxy.pollution.updateDim(tickEvent.world.provider.dimensionId);
    }
}
