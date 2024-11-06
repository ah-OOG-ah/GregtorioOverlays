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

package klaxon.klaxon.goverlays.network.pollution;

import static klaxon.klaxon.goverlays.GregtorioOverlays.LOGGER;
import static klaxon.klaxon.goverlays.GregtorioOverlays.proxy;
import static klaxon.klaxon.goverlays.network.pollution.PollutionMessage.Error.BUFFER_OVERFLOW;
import static klaxon.klaxon.goverlays.network.pollution.PollutionMessage.Error.fromInt;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import klaxon.klaxon.goverlays.events.ClientProxy;
import org.jetbrains.annotations.NotNull;

/**
 * Sent from the server, giving clients a list of pollution chunks.
 */
public class PollutionMessage implements IMessage {

    // Store a set of pollution chunks
    private final Long2IntOpenHashMap chunks;
    public int dimID;

    @NotNull
    public Long2IntOpenHashMap getChunks() {
        return chunks;
    }

    // Constructor from nothing, because it's needed for registration or smth idk
    @SuppressWarnings("unused")
    public PollutionMessage() {
        this(0, new Long2IntOpenHashMap());
    }

    public PollutionMessage(int dimID, @NotNull Long2IntOpenHashMap chunks) {
        this.chunks = chunks;
        this.dimID = dimID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (!(proxy instanceof ClientProxy)) {
            LOGGER.warn("Received packet on server! Ignoring...");
            return;
        }

        // Read the header
        final int numChunks = buf.readInt();
        final Error err = fromInt(numChunks);

        switch (err) {
            case NONE -> {
                LOGGER.debug("No polluted chunks need updating, skipping rest of packet");
                return;
            }
            case BUFFER_OVERFLOW -> {
                LOGGER.warn("Server has too many polluted chunks to send a PollutionMessage!");
                LOGGER.warn("Updating client pollution chunks failed!");
                return;
            }
            case UNKNOWN -> {
                LOGGER.warn("Invalid packet received! The server encountered an unknown error when sending chunks.");
                return;
            }
        }

        // There are chunks, validate message. Should be 12 * number of chunks + 4, as header has already been read
        if (buf.readableBytes() != numChunks * 12 + 4) {

            LOGGER.error(buf.readableBytes() + " B in PollutionMessage, expected " + numChunks + "B!");
            LOGGER.error("This is probably a server or network problem.");
            LOGGER.error("Discarding PollutionMessage, client polluted chunks will not be updated.");
            return;
        }

        // Message valid, read and add chunks
        dimID = buf.readInt();
        final Long2IntOpenHashMap cache = proxy.pollution.getCache(dimID);
        cache.ensureCapacity(numChunks);
        for (int i = 0; i < numChunks; i++) {
            cache.put(buf.readLong(), buf.readInt());
        }
    }

    /**
     * This converts the HashSet of PollutionChunkPositions to a stream of bytes. It must:
     * <ul>
     * <li>Add a header showing how many chunks are in the message
     * <li>Validate this, making sure that enough space is available for the message
     * <li>Write each position as raw bytes, with no separator; this can be done because each chunk is the same size.
     * </ul>
     * While this is slightly less efficient, it's easier, plus Netty compresses packets anyways.
     *
     * @param buf Buffer to write to
     */
    @Override
    public void toBytes(ByteBuf buf) {
        // Validate and write the header
        // There are four ints in a PCP, each of which take 4 bytes; 16 bytes per PCP
        // Add the space taken by the header and dimension and log if there's an error
        int num = chunks.size();
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
            buf.writeInt(BUFFER_OVERFLOW.value);
            return;
        }

        // Write the header + dim
        buf.writeInt(num);
        buf.writeInt(dimID);

        // Iterate over HashSet
        Long2IntMaps.fastForEach(chunks, le -> {
            buf.writeLong(le.getLongKey());
            buf.writeInt(le.getIntValue());
        });
    }

    /**
     * Errors are all negative, as negative numbers of chunks to update do not make sense.
     */
    protected enum Error {

        NONE(0),
        BUFFER_OVERFLOW(-1),
        UNKNOWN(Integer.MIN_VALUE);

        final int value;

        Error(int value) {
            this.value = value;
        }

        static Error fromInt(int i) {
            if (i >= 0) return NONE;
            return switch (i) {
                case -1 -> BUFFER_OVERFLOW;
                default -> UNKNOWN;
            };
        }
    }
}
