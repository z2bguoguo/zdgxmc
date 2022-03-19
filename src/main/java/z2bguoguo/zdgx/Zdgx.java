package z2bguoguo.zdgx;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.versions.mcp.MCPVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import z2bguoguo.*;
import javax.swing.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("zdgx")
public class Zdgx {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    //private kehuduanwangluo w=new kehuduanwangluo();
   // private fuwuqiwangluo f=new fuwuqiwangluo();
    public Zdgx() {
        // Register the setup method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
       // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        //MinecraftForge.EVENT_BUS.register(this);
        //MinecraftForge.EVENT_BUS.register(f);
        try
        {
            System.setProperty("java.awt.headless", "false");
            zhixing zx=new zhixing();
            zx.GameVersionName=MCPVersion.getMCVersion();
            zx.loder="Forge";
            zx.start();
            //MinecraftForge.EVENT_BUS.register(f.getClass());
        }
        catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }
    }
    /*private void setup(final FMLCommonSetupEvent event) {

    }*/
/*
    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("zdgx", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
    */
}
