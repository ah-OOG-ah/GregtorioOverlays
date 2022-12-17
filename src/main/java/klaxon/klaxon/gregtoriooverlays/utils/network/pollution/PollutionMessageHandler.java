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
     * @param message
     * @param ctx
     */
    public IMessage onMessage(PollutionMessage message, MessageContext ctx) {

        // In 1.8+ the network thread is separate, but this is 1.7.10 and I'm in the main thread rn
        // I have UNLIMITED POWER!

        HashSet<PollutionChunkPosition> chonky = message.getChunks();
        // HashSet<PollutionChunkPosition> chunks = new HashSet<>();
        if (chonky != null) {

            /*for (PollutionChunkPosition chonk:chonky) {
                PollutionChunkPosition newChonk = (PollutionChunkPosition) chonk;
                chunks.add(newChonk);
            }*/

            /*chonky.forEach(chonk -> {
                PollutionChunkPosition chunk =
                        new PollutionChunkPosition(chonk.dimensionId, chonk.chunkX, chonk.chunkZ, chonk.pollution);
                PollutionFetcher.PollutionCache.setChunk(chunk);
            });*/

            PollutionChunkPosition[] chunks =  chonky.toArray(new PollutionChunkPosition[0]);
            for (int i = 0; i < chunks.length; i++) {

                PollutionFetcher.PollutionCache.setChunk(chunks[i]);
            }

        } else {

            GregtorioOverlays.error("Pollution message had no chunks in it!");
        }

        return null;
    }
}
