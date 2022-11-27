package klaxon.klaxon.gregtoriooverlays.utils.network.pollution;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import klaxon.klaxon.gregtoriooverlays.GregtorioOverlays;

public class DummyPollutionMessageHandler implements IMessageHandler<PollutionMessage, IMessage> {

    /**
     * Does nothing, just here so I can register the packet.
     * @param message
     * @param ctx
     */
    public IMessage onMessage(PollutionMessage message, MessageContext ctx) {

        GregtorioOverlays.error("Client tried to send polluted chunks list to server; ignoring.");
        GregtorioOverlays.error("This won't harm anything, but a mod on that client is likely acting up.");
        return null;
    }
}
