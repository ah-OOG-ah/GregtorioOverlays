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

package klaxon.klaxon.goverlays.network.pollution;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import klaxon.klaxon.goverlays.GregtorioOverlays;

public class PollutionMessageHandler implements IMessageHandler<PollutionMessage, IMessage> {

    /**
     * Recieves pollution chunks and loads them into cache on the client.
     */
    public IMessage onMessage(PollutionMessage message, MessageContext ctx) {

        if (ctx.side == Side.SERVER) {
            LOGGER.error("A client tried to send polluted chunks list to server; ignoring.");
            LOGGER.error("This won't harm anything, but a mod on that client is likely acting up.");
            return null;
        }

        // In 1.8+ the network thread is separate, but this is 1.7.10 and I'm in the main thread rn
        // I have UNLIMITED POWER!

        // The reference served its purpose ::)
        GregtorioOverlays.proxy.pollution.updateCache(message.dimID, message.getChunks());
        return null;
    }
}
