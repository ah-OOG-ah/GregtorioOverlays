package klaxon.klaxon.goverlays.utils.network.pollution;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

import javax.annotation.Nullable;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import klaxon.klaxon.goverlays.GregtorioOverlays;
import klaxon.klaxon.goverlays.visualprospecting.model.PollutionChunkPosition;

/**
 * Sent from the server, giving clients a list of pollution chunks
 * This will send as many packets as needed to send the data.
 */
public class PollutionMessage implements IMessage {

    // Store a hashset of pollution chunks
    private HashSet<PollutionChunkPosition> chunks;

    // Flags
    private boolean concurrentException;

    @Nullable
    public HashSet<PollutionChunkPosition> getChunks() {

        return chunks;
    }

    // Constructor from nothing, because it's needed for registration or smth idk
    public PollutionMessage() {}

    // Constructors from data
    public PollutionMessage(HashSet<PollutionChunkPosition> sendChunks) {

        chunks = sendChunks;
        concurrentException = false;
    }

    public PollutionMessage(ConcurrentModificationException e) {

        chunks = new HashSet<>();
        concurrentException = true;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        // Read the header
        int header = buf.readInt();

        if (header == -2) {

            GregtorioOverlays.warn("Server had a ConcurrentModificationException!");
            GregtorioOverlays.warn("Updating client pollution chunks failed!");
            chunks = null;
            return;
        } else if (header == -1) {

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
            GregtorioOverlays.error("This is probably a server or network problem.");
            GregtorioOverlays.error("Discarding PollutionMessage, client polluted chunks will not be updated.");
            chunks = null;
            return;
        }

        // Message valid, read and add chunks
        chunks = new HashSet<>();
        for (int i = 0; i < header; i++) {

            chunks.add(
                new PollutionChunkPosition(
                    buf.readInt(), // dimensionId
                    buf.readInt(), // chunkX
                    buf.readInt(), // chunkZ
                    buf.readInt() // pollution
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
     *
     * @param buf Buffer to write to
     */
    @Override
    public void toBytes(ByteBuf buf) {

        // Check for errors
        if (concurrentException) {

            // Write the header
            buf.writeInt(-2);
        } else {

            // No errors yet, proceed
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
                GregtorioOverlays.error(
                    "Too many chunks to send in one packet! Trying to send " + num
                        + " chunks through a "
                        + buf.maxCapacity()
                        + "B buffer.");
                buf.writeInt(-1);
                return;
            }

            // Write the header
            buf.writeInt(num);

            // Iterate over HashSet
            for (PollutionChunkPosition chunk : chunks) {

                buf.writeInt(chunk.dimensionId);
                buf.writeInt(chunk.chunkX);
                buf.writeInt(chunk.chunkZ);
                buf.writeInt(chunk.pollution);
            }
        }
    }
}
