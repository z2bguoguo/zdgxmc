package z2bguoguo.zdgx;
/*
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.targets.ForgeServerLaunchHandler;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.*;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class fuwuqiwangluo {
    public static SimpleChannel INSTANCE;
    private static final String PROTOCOL_VERSION = "1";
    public static int id=0;
    @SubscribeEvent

    public void pickupItem(NetworkEvent.LoginPayloadEvent event) {
        System.out.println("登录事件");
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("zdgx", "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
        INSTANCE.registerMessage(id,String.class,this::BiConsumer,this::Function,this::messageConsumer);
        Supplier<ServerPlayer> ss=new Supplier<ServerPlayer>() {
            @Override
            public ServerPlayer get() {
                return event.getSource().get().getSender();
            }
        };
        INSTANCE.send(PacketDistributor.PLAYER.with(ss), this);
    }
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
        NetworkEvent.Context context = b.get();
        context.setPacketHandled(true);
    }
}
*/