package z2bguoguo.zdgx;

/*
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class kehuduanwangluo {
    private static final String PROTOCOL_VERSION = "1";
    public static int id=0;
    public static SimpleChannel INSTANCE;
    private static final Logger LOGGER = LogManager.getLogger();
    public void RegisteringPackets()
    {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("zdgx", "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
        INSTANCE.registerMessage(id,String.class,this::BiConsumer,this::Function,this::messageConsumer);
        id++;

    }
    //编码
    public void BiConsumer(String a, FriendlyByteBuf b)
    {
        b.writeBytes(a.getBytes(StandardCharsets.UTF_8));
    }
    //解码
    public String Function(FriendlyByteBuf a)
    {
        return a.toString(StandardCharsets.UTF_8);
    }
    //客户端收到信息
    public void messageConsumer(String a, Supplier<NetworkEvent.Context> b)
    {
        LOGGER.info("收到信息："+a);
        NetworkEvent.Context context = b.get();
        context.setPacketHandled(true);
    }
}
*/