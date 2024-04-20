package klaxon.klaxon.goverlays.network.pollution;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;

import java.util.ConcurrentModificationException;

import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import org.jetbrains.annotations.NotNull;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Sent from the server, giving clients a list of pollution chunks
 * This will send as many packets as needed to send the data.
 */
public class PollutionMessage implements IMessage {

    // Store a set of pollution chunks
    private Long2IntOpenHashMap chunks;
    public int dimension;

    // Flags
    private final boolean concurrentException;

    @NotNull
    public Long2IntOpenHashMap getChunks() {
        return chunks;
    }

    // Constructor from nothing, because it's needed for registration or smth idk
    public PollutionMessage() {
        this(0, null, false);
    }

    public PollutionMessage(ConcurrentModificationException e) {
        this(0, null, true);
    }

    // Constructors from data
    public PollutionMessage(int dimID, Long2IntOpenHashMap sendChunks, boolean isExcept) {
        chunks = (sendChunks == null) ? new Long2IntOpenHashMap() : sendChunks;
        dimension = dimID;
        concurrentException = isExcept;
    }

    public PollutionMessage addAll(Long2IntOpenHashMap pollutionMap) {
        chunks.putAll(pollutionMap);
        return this;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        // Read the header
        int numChunks = buf.readInt();

        if (numChunks == -2) {

            LOGGER.warn("Server had a ConcurrentModificationException!");
            LOGGER.warn("Updating client pollution chunks failed!");
            return;
        } else if (numChunks == -1) {

            LOGGER.warn("Server has too many polluted chunks to send a PollutionMessage!");
            LOGGER.warn("Updating client pollution chunks failed!");
            return;
        } else if (numChunks == 0) {

            LOGGER.debug("No polluted chunks need updating, skipping rest of packet");
            return;
        }

        // There are chunks, validate message. Should be 12 * number of chunks + 4, as header has already been read
        if (buf.readableBytes() != numChunks * 12 + 4) {

            LOGGER.error(buf.readableBytes() + " B in PollutionMessage, expected " + numChunks + "B!");
            LOGGER.error("This is probably a server or network problem.");
            LOGGER.error("Discarding PollutionMessage, client polluted chunks will not be updated.");
            return;
        }

        // Message valid, read and add chunks
        dimension = buf.readInt();
        for (int i = 0; i < numChunks; i++) {
            chunks.put(buf.readLong(), buf.readInt());
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
            // Add the space taken by the header and dimension and log if there's an error
            int msgSize = 8 + num * 12;
            try {
                buf.ensureWritable(msgSize);
            } catch (IndexOutOfBoundsException e) {

                // Log and send a -1 to let the client know
                LOGGER.error(e.toString());
                LOGGER.error(
                    "Too many chunks to send in one packet! Trying to send " + num
                        + " chunks through a "
                        + buf.maxCapacity()
                        + "B buffer.");
                buf.writeInt(-1);
                return;
            }

            // Write the header + dim
            buf.writeInt(num);
            buf.writeInt(dimension);

            // Iterate over HashSet
            Long2IntMaps.fastForEach(chunks, le -> {
                buf.writeLong(le.getLongKey());
                buf.writeInt(le.getIntValue());
            });
        }
    }
}
