package guoguo909.mod.zdgx.zdgx;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.alibaba.fastjson.JSONObject;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class zhixing extends Thread
{
    protected static final Logger logger = LogManager.getLogger(zhixing.class);
    public void run() {
        try
        {
           logger.info("开始执行");
            int banben=5;
            jiemian j=new jiemian();
            logger.info("初始化完毕");
            int gitbanban=Integer.parseInt(j.getgitbanben("z2bguoguo/zdgxmc"));
            logger.info("gitbanban："+String.valueOf(gitbanban));
            String wjml=System.getProperty("user.dir")+"\\zdgx";
            logger.info("目录："+wjml);
            File wjmlcz=new File(wjml);
            if (!wjmlcz .exists())
            {
                logger.info("目录不存在，正在创建目录");
                wjmlcz.mkdir();
            }
            System.out.println(wjml);
            File modmlf=new File(wjml);
            String modml= modmlf.getParent()+"/mods";
            logger.info("mod目录："+modml);
            File json=new File(wjml+"/json.json");
            if (!json.exists())
            {
                logger.info("json.json不存在");
                json.createNewFile();
                j.jsoncj(json);
                JOptionPane.showMessageDialog(null, "请输入地址", "错误", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            FileReader jsons=new FileReader(json);
            char jsonr[]=new char[1024];
            int jsonlen= jsons.read(jsonr);
            String jsonn=new String(jsonr,0,jsonlen);
            JSONObject jsonx=JSONObject.parseObject(jsonn);
            String dizhi=jsonx.getString("address");
            jsons.close();
            logger.info("json地址："+dizhi);
            if (jsonx.getBoolean("enable"))
            {
                String[] b=j.getmod(j.geturl(dizhi));//云端文件列表
                logger.info("云端文件列表："+ Arrays.toString(b));
                String[] c=j.getwenjian(modml);//本地文件列表
                logger.info("本地文件列表："+ Arrays.toString(c));
                String[] duo=j.bendiduo(c,b,modml);//本地文件多出的
                logger.info("本地文件多出的："+ Arrays.toString(duo));
                String[] shao=j.bendiduo(b,c,modml);//本地文件少的
                logger.info("本地文件少的："+ Arrays.toString(shao));
                File yunxing=new File(wjml+"/yunxing.jar");
                if(gitbanban>banben)
                {
                    logger.info("更新zdgx");
                    String gxdizhi=j.getgitxiazai("z2bguoguo/zdgxmc","zdgx"+String.valueOf(gitbanban)+".jar");
                    logger.info("gxdizhi："+gxdizhi);
                    jiemian.down downs=j.new down(gxdizhi, modml+"/"+"zdgx"+String.valueOf(gitbanban)+".jar", 1,1);
                    downs.run();
                    File ziji = new File(modml+"/"+"zdgx"+String.valueOf(banben)+".jar");
                    ziji.renameTo(new File(modml+"/"+"zdgx"+String.valueOf(banben)+".jar.duo"));
                }
                if (!yunxing.exists() || gitbanban>banben)
                {
                    logger.info("下载yunxing.jar");
                    String gxdizhi=j.getgitxiazai("z2bguoguo/zdgxmc","yunxing.jar");//下载yunxing.jar的地址
                    logger.info("下载yunxing.jar的地址："+gxdizhi);
                    jiemian.down downs=j.new down(gxdizhi, yunxing.getPath(), 1,1);
                    downs.run();
                }
                if (duo.length!=0||shao.length!=0)
                {
                    logger.info("更新mod");
                    Runtime.getRuntime().exec("java -jar "+wjml+"/yunxing.jar");
                    System.out.println("java -jar "+wjml+"/yunxing.jar");
                    System.exit(0);
                }
            }

        }
        catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }

    }

}
