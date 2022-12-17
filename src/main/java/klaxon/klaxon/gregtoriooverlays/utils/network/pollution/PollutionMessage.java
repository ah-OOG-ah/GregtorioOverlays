package klaxon.klaxon.gregtoriooverlays.utils.network.pollution;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

        // If there are bytes behind it...
        if (buf.getClass() != EmptyByteBuf.class) {

            // Copy buffer
            List<Byte> listData = new ArrayList<>();
            while (true) {

                try {

                    listData.add(buf.readByte());
                } catch (IndexOutOfBoundsException e) {

                    break;
                }
            }

            byte[] rawData = ArrayUtils.toPrimitive(listData.toArray(new Byte[0]));
            GregtorioOverlays.info(rawData.toString());

            // Deserialize to set
            chunks = SerializationUtils.deserialize(rawData);
        } else {

            // Houston, we have a problem
            GregtorioOverlays.error("Pollution message has no byte array!");
            GregtorioOverlays.error("Setting chunks to null");
            chunks = null;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {

        // Serialize to buffer
        byte[] rawData = SerializationUtils.serialize(chunks);

        // Expand buffer to match
        buf.ensureWritable(rawData.length);

        // Write to the buffer
        buf.writeBytes(rawData);

        GregtorioOverlays.info("Printing buffer:");
        GregtorioOverlays.info(buf.copy().toString());
    }
}
