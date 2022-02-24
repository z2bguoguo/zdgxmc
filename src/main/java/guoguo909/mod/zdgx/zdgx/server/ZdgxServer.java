package guoguo909.mod.zdgx.zdgx.server;

import com.alibaba.fastjson.JSONObject;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;

@Environment(EnvType.SERVER)
public class ZdgxServer implements DedicatedServerModInitializer
{
    protected static final Logger logger = LogManager.getLogger(ZdgxServer.class);
    String dizhi;
    @Override
    public void onInitializeServer()
    {
        try
        {
            String wjml=System.getProperty("user.dir")+"\\zdgx";
            logger.info("目录："+wjml);
            File json=new File(wjml+"/json.json");
            FileReader jsons=new FileReader(json);
            char jsonr[]=new char[1024];
            int jsonlen= jsons.read(jsonr);
            String dizhix=new String(jsonr,0,jsonlen);
            JSONObject jsonx=JSONObject.parseObject(dizhix);
            dizhi=jsonx.getString("address");
            logger.info("服务器json地址："+dizhi);
            jsons.close();
            Identifier itf=new Identifier("zdgx");
            ServerLoginConnectionEvents.QUERY_START.register(this::onLoginStart);
            ServerLoginNetworking.registerGlobalReceiver(itf, (server, handler, understood, buf, synchronizer, sender) ->
            {
                if (understood)
                {
                    logger.info("客户端理解对地址的查询");
                }
                else
                {
                    logger.warn("客户端不理解对地址的查询");
                }
            });
        }
        catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }
    }
    public void onLoginStart(ServerLoginNetworkHandler handler, MinecraftServer server, PacketSender sender, ServerLoginNetworking.LoginSynchronizer synchronizer)
    {
        logger.info("检测到客户端查询，正在反馈更新链接");
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(dizhi);
        Identifier itf=new Identifier("zdgx");
        sender.sendPacket(itf,buf);
    }
}
