package guoguo909.mod.zdgx.zdgx.client;

import guoguo909.mod.zdgx.zdgx.zhixing;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class ZdgxClient implements ClientModInitializer {
    protected static final Logger logger = LogManager.getLogger(ZdgxClient.class);
    @Override
    public void onInitializeClient() {
        try {
            Identifier itf=new Identifier("zdgx");
            ClientLoginNetworking.registerGlobalReceiver(itf, (client, handler, buf, listenerAdder) ->
            {
                try {
                    String bufs=buf.readString();
                    logger.info("收到服务器发送的链接："+bufs);
                    String wjml=System.getProperty("user.dir")+"\\zdgx";
                    logger.info("目录："+wjml);
                    File json=new File(wjml+"/json.txt");
                    FileWriter jsons=new FileWriter(json);
                    jsons.write(bufs);
                    jsons.flush();
                    jsons.close();
                }
                catch (Exception e) {
                    System.out.println("错误报告");
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    System.out.println("错误报告尾");
                }
                return CompletableFuture.completedFuture(PacketByteBufs.empty());
            });
        }
        catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }

    }
}
