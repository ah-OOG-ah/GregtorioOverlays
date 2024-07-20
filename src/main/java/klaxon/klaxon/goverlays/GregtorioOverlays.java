package klaxon.klaxon.goverlays;

import static klaxon.klaxon.goverlays.GregtorioOverlays.MODID;
import static klaxon.klaxon.goverlays.GregtorioOverlays.MODNAME;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import klaxon.klaxon.goverlays.events.CommonProxy;

@Mod(
    modid = MODID,
    version = Tags.VERSION,
    name = MODNAME,
    acceptedMinecraftVersions = "[1.7.10]",
    dependencies = "required-after:navigator;after:gregtech;after:hbm;",
    guiFactory = "klaxon.klaxon.goverlays.config.GOGuiFactory")
public class GregtorioOverlays {

    public static final String MODNAME = "GregtorioOverlays";
    public static final String MODID = "goverlays";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    // Network things
    public static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    public static final int pollutionMessageId = 1;

    // How often to update the pollution cache
    public static int ticksPerUpdate = 1200;

    @SidedProxy(
        clientSide = "klaxon.klaxon.goverlays.events.ClientProxy",
        serverSide = "klaxon.klaxon.goverlays.events.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
