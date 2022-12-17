package klaxon.klaxon.gregtoriooverlays;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import java.text.NumberFormat;
import java.util.Locale;
import klaxon.klaxon.gregtoriooverlays.utils.FancyText.PrefixType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MODID,
    version = Tags.VERSION,
    name = Tags.MODNAME,
    acceptedMinecraftVersions = "[1.7.10]",
    dependencies = "required-after:gregtech;")
public class GregtorioOverlays {

    // LOG
    private static Logger LOG = LogManager.getLogger(Tags.MODID);

    // Network things
    public static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MODID);
    public static int pollutionMessageId = 1;
    public static final int FIRST_NONHEADER_BYTE = 1;
    // this is probably uniquie, if it needs changing the code will throw an error
    public static final byte[] MARK_DATA_END = {0x01, 0x03, 0x03, 0x07};

    // Set constants
    // Side (can't be bothered to find to forge method)
    public static Side side = Side.SERVER;

    // Pollution display
    public static int CHUNK_SIZE = 16;
    public static float POLLUTION_MAX_ALPHA = 200;
    // must be in [1, POLLUTION_MAX_ALPHA]. actual number of steps is this + 1 (including 0 case)
    public static float POLLUTION_ALPHA_STEPS = 8;
    public static int POLLUTION_COLOR = 0xFF0000;

    // Text format constants
    public static boolean USE_DOUBLE_PREFIXES = false;
    public static PrefixType prefixes = PrefixType.BINARY;
    public static boolean USE_OFFSET = false;

    // Text font constants
    public static int TEXT_OFFSET = 1;
    public static int TEXT_BG_COLOR = 0x000000;
    public static int TEXT_BG_ALPHA = 180;
    public static int TEXT_COLOR = 0xFFFFFF;
    public static int TEXT_ALPHA = 255;

    // This will probably need a rework later, but for now I'm handling locales like this
    // It's written out explicitly so I don't forget what these do
    // TODO: integrate this with Minecraft lang settings
    public static Locale defaultLocale = Locale.getDefault(Locale.Category.FORMAT);
    public static NumberFormat numFormat = NumberFormat.getNumberInstance(defaultLocale);

    // These values are based on the pollution effects in GT5U-GTNH
    // They are undergoing a major rework and still subject to change
    // This is current as of 5.09.41.132-dev
    public static int POLLUTION_1 = 500000; // Smog
    public static int POLLUTION_2 = 750000; // Poison
    public static int POLLUTION_3 = 1000000; // Dying Plants
    public static float POLLUTION_MAX = 1500000; // Sour Rain

    // Not sure what the proxies are
    @SidedProxy(clientSide = Tags.GROUPNAME + ".ClientProxy", serverSide = Tags.GROUPNAME + ".CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this."
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        proxy.serverAboutToStart(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.serverStarted(event);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        proxy.serverStopping(event);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        proxy.serverStopped(event);
    }

    public static void debug(String message) {
        LOG.debug(message);
    }

    public static void info(String message) {
        LOG.info(message);
    }

    public static void warn(String message) {
        LOG.warn(message);
    }

    public static void error(String message) {
        LOG.error(message);
    }
}
