package klaxon.klaxon.gregtoriooverlays.utils.network.pollution;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;

import java.util.*;
import javax.annotation.Nullable;
import klaxon.klaxon.gregtoriooverlays.GregtorioOverlays;
import klaxon.klaxon.gregtoriooverlays.visualprospecting.model.PollutionChunkPosition;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;

/**
 * Sent from the server, giving clients a list of pollution chunks
 * This will send as many packets as needed to send the data.
 */
public class PollutionMessage implements IMessage {

    // Store a hashset of pollution chunks
    private HashSet<PollutionChunkPosition> chunks;

    @Nullable
    public HashSet<PollutionChunkPosition> getChunks() {

        return chunks;
    }

    // Constructor from nothing, because it's needed for registration or smth idk
    public PollutionMessage() {}

    // Constructor from data
    public PollutionMessage(HashSet<PollutionChunkPosition> sendChunks) {

        chunks = sendChunks;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        // Read the header
        int header = buf.readInt();

        if (header == -1) {

            GregtorioOverlays.warn("Server has too many polluted chunks to send a PollutionMessage!");
            GregtorioOverlays.warn("Updating client pollution chunks failed!");
            chunks = null;
            return;
        } else if (header == 0) {

            GregtorioOverlays.debug("No polluted chunks need updating... how?");
            chunks = null;
            return;
        }

        // There are chunks, validate message. Should be 16 * number of chunks, as header has already been read
        if (buf.readableBytes() != header * 16) {

            GregtorioOverlays.error(buf.readableBytes() + " B in PollutionMessage, expected " + header + "B!");
            GregtorioOverlays.error("This is probably a server or network problem. Discarding PollutionMessage,");
            GregtorioOverlays.error("Discarding PollutionMessage, client polluted chunks will not be updated.");
            chunks = null;
            return;
        }

        // Message valid, read and add chunks
        chunks = new HashSet<>();
        for (int i = 0; i < header; i++) {

            chunks.add(new PollutionChunkPosition(
                buf.readInt(), // dimensionId
                buf.readInt(), // chunkX
                buf.readInt(), // chunkZ
                buf.readInt()  // pollution
            ));
        }

        // done!
    }

    /**
     * This converts the HashSet of PollutionChunkPositions to a stream of bytes. It must:
     * <li>Add a header showing how many chunks are in the message
     * <li>Validate this, making sure that enough space is available for the message
     * <li>Write each position as raw bytes, with no separator; this can be done because each chunk is the same size.
     * While this is slightly less efficient, it's easier, plus Netty compresses packets anyways.
     * @param buf Buffer to write to
     */
    @Override
    public void toBytes(ByteBuf buf) {

        // Count the chunks
        int num = chunks.size();

        // Validate and write the header
        // There are four ints in a PCP, each of which take 4 bytes; 16 bytes per PCP
        // Add the space taken by the header (another 4-byte int) and log if there's an error
        int msgSize = 4 + num * 16;
        try {

            buf.ensureWritable(msgSize);
        } catch (IndexOutOfBoundsException e) {

            // Log and send a -1 to let the client know
            GregtorioOverlays.error(e.toString());
            GregtorioOverlays.error("Too many chunks to send in one packet! Trying to send " + num +
                " chunks through a " + buf.maxCapacity() + "B buffer.");
            buf.writeInt(-1);
            return;
        }

        // Write the header
        buf.writeInt(num);

        // Iterate over HashSet
        for (PollutionChunkPosition chunk:chunks) {

            buf.writeInt(chunk.dimensionId);
            buf.writeInt(chunk.chunkX);
            buf.writeInt(chunk.chunkZ);
            buf.writeInt(chunk.pollution);
        }

        GregtorioOverlays.debug("Printing buffer:");
        GregtorioOverlays.debug(buf.copy().toString());
    }
}
