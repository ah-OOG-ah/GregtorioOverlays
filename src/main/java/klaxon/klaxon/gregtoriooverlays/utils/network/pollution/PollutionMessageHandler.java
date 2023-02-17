package klaxon.klaxon.gregtoriooverlays.utils.network.pollution;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashSet;
import klaxon.klaxon.gregtoriooverlays.GregtorioOverlays;
import klaxon.klaxon.gregtoriooverlays.gregtech.PollutionFetcher;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionChunkPosition;

@SideOnly(Side.CLIENT)
public class PollutionMessageHandler implements IMessageHandler<PollutionMessage, IMessage> {

    /**
     * Recieves pollution chunks and loads them into cache on the client.
     * @param message The message
     * @param ctx Not used, can be literally anything javac lets you pass
     */
    public IMessage onMessage(PollutionMessage message, MessageContext ctx) {

        // In 1.8+ the network thread is separate, but this is 1.7.10 and I'm in the main thread rn
        // I have UNLIMITED POWER!

        // Get chunks
        HashSet<PollutionChunkPosition> chunks = message.getChunks();

        // Use them!
        if (chunks != null) {

            // Future reference because I will forget what this means:
            // 'PollutionFetcher.PollutionCache::setChunk' executes
            // PollutionFetcher.PollutionCache.setChunk(varFromForeach)
            chunks.forEach(PollutionFetcher.PollutionCache::setChunk);

        } else {

            GregtorioOverlays.error("Pollution message had no chunks in it!");
        }

        return null;
    }
}
