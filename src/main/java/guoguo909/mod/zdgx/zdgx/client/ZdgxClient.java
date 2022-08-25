package guoguo909.mod.zdgx.zdgx.client;

import com.alibaba.fastjson.JSONObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.CompletableFuture;

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
                    String[] baq={"是","否"};
                    int x= JOptionPane.showOptionDialog(null, "" +"是否设置为服务器提供的地址", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,baq, baq[0]);
                    if (x==0) {
                        String bufs = buf.readString();
                        logger.info("收到服务器发送的链接：" + bufs);
                        String wjml = System.getProperty("user.dir") + "\\zdgx";
                        logger.info("目录：" + wjml);
                        File json = new File(wjml + "/json.json");
                        FileReader jsonsx = new FileReader(json);
                        char jsonr[] = new char[1024];
                        int jsonlen = jsonsx.read(jsonr);
                        String jsonn = new String(jsonr, 0, jsonlen);
                        JSONObject jsonx = JSONObject.parseObject(jsonn);
                        jsonx.put("address", bufs);
                        FileWriter jsons = new FileWriter(json);
                        jsons.write(JSONObject.toJSONString(jsonx, true));
                        jsons.flush();
                        jsons.close();
                    }
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
