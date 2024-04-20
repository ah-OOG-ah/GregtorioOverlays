package klaxon.klaxon.goverlays.utils.network.pollution;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import klaxon.klaxon.goverlays.PollutionManager;

@SideOnly(Side.CLIENT)
public class PollutionMessageHandler implements IMessageHandler<PollutionMessage, IMessage> {

    /**
     * Recieves pollution chunks and loads them into cache on the client.
     */
    public IMessage onMessage(PollutionMessage message, MessageContext ctx) {

        if (ctx.side == Side.SERVER) {
            LOGGER.error("Client tried to send polluted chunks list to server; ignoring.");
            LOGGER.error("This won't harm anything, but a mod on that client is likely acting up.");
            return null;
        }

        // In 1.8+ the network thread is separate, but this is 1.7.10 and I'm in the main thread rn
        // I have UNLIMITED POWER!

        // Get chunks
        Long2LongOpenHashMap chunks = message.getChunks();

        // Use them!
        if (!chunks.isEmpty()) {

            // The reference served its purpose :)
            PollutionManager.updateCache(message.dimension, chunks);
        }

        return null;
    }
}
