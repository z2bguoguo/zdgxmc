package z2bguoguo;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class zhixing extends Thread
{
    protected static final Logger logger = LogManager.getLogger(zhixing.class);
    public String GameVersionName="";
    public String loder="";
    public int banben=12;
    public void run() {
        try
        {
            logger.info("开始执行");
            jiemian j=new jiemian();
            logger.info("初始化完毕");
            String wjml=System.getProperty("user.dir")+"\\zdgx";
            logger.info("目录："+wjml);
            File wjmlcz=new File(wjml);
            String cjml=wjml+"\\plugins";
            if (!wjmlcz .exists())
            {
                logger.info("目录不存在，正在创建目录");
                wjmlcz.mkdir();
            }
            File cjmlxx=new File(cjml);
            if (!cjmlxx .exists()) {
                logger.info("插件目录不存在，正在创建目录");
                cjmlxx.mkdir();
            }
            logger.info("初始化插件");
            chajian chaj=new chajian(cjml);
            logger.info("插件初始化完毕");
            logger.info("执行插件初始化命令");
            chaj.zhixing("Init","zhixing");
            logger.info("插件初始化命令执行完毕");
            System.out.println(wjml);
            File modmlf=new File(wjml);
            String modml= modmlf.getParent()+"/mods";
            logger.info("mod目录："+modml);
            File json=new File(wjml+"/json.json");
            if (!json.exists())
            {
                logger.info("json.json不存在");
                json.createNewFile();
                j.jsoncj(json,loder,GameVersionName);
                chaj.zhixing("JsonInit");
                logger.info("Json初始化命令执行完毕");
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
            chaj.zhixing("Address",dizhi);
            logger.info("插件获取地址命令执行完毕");
            logger.info("启用:"+jsonx.getBoolean("enable"));
            File yunxing=new File(wjml+"/yunxing.jar");
            if (jsonx.getBoolean("AutoUpdate"))
            {
                int gitbanban=Integer.parseInt(j.getgitbanben("z2bguoguo/zdgxmc"));
                logger.info("gitbanban："+String.valueOf(gitbanban));
                if(gitbanban>banben && tishi("检测到本mod有新版本，是否进行更新并关闭mc"))
                {
                    logger.info("更新zdgx");
                    String gxdizhi=j.getgitxiazai("z2bguoguo/zdgxmc","zdgxzhixing.jar");
                    logger.info("gxdizhi："+gxdizhi);
                    jiemian.down downs=j.new down(gxdizhi, wjml+"\\zdgxzhixing.jar", 1,1);
                    downs.run();
                    yunxing.delete();
                    System.exit(0);
                }
            }
            if (jsonx.getBoolean("enable"))
            {
                jiemian.curseforge cu=new jiemian.curseforge();
                String GameVersionTypeId=cu.GetGameVersionTypeId(jsonx.getString("GameVersionName"));
                logger.info("GameVersionTypeId："+GameVersionTypeId);
                JSONArray modary= j.getmodary(j.geturl(dizhi));
                String[] b=j.getmodfilenames(modary);//云端文件列表
                logger.info("云端文件列表："+ Arrays.toString(b));
                HashMap<String,String> yuns=j.getmodmd5(modary);
                logger.info("云端文件md5列表："+ yuns);
                String[] c=j.getwenjian(modml);//本地文件列表
                logger.info("本地文件列表："+ Arrays.toString(c));
                HashMap<String,String> bendis=j.getwenjianmd5(modml);
                logger.info("本地文件md5列表："+ bendis);
                //String[] duo=j.bendiduo(c,b,modml);//本地文件多出的
                jiemian.cha chas=j.bendicha_md5(bendis,yuns);
                logger.info("本地文件多出的："+ chas.bendiduo.toString());
                logger.info("本地文件少的："+ chas.bendishao.toString());
                //String[] shao=j.bendiduo(b,c,modml);//本地文件少的

                if (!yunxing.exists())
                {
                    InputStream is = getClass().getResourceAsStream("/yunxing.jars");
                    FileOutputStream fos = new FileOutputStream(yunxing);
                    byte[] bx = new byte[1024];
                    int len;
                    while ((len=is.read(bx)) != -1) {
                        fos.write(bx,0,len);// 写入数据
                    }
                    is.close();
                    fos.close();// 保存数据
                }
                if (chas.bendishao.size()!=0||chas.bendiduo.size()!=0)
                {
                    if(tishi("检测mod有更新，是否进行更新并关闭mc"))
                    {
                        logger.info("更新mod");
                        chaj.zhixing("ReadyUpdate");
                        logger.info("插件准备更新命令执行完毕");
                        String downjson= j.getdownjson(chas,modary,jsonx);
                        logger.info("下载json："+downjson);
                        Runtime.getRuntime().exec("java -jar "+wjml+"/yunxing.jar zx");
                        System.out.println("java -jar "+wjml+"/yunxing.jar zx");
                        jiemian.tcptx t=j.new tcptx();
                        t.servers(downjson);
                        System.exit(0);
                    }
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
    public void zx(String GameVersionNames,String loders)
    {
        GameVersionName=GameVersionNames;
        loder=loders;
        super.start();
    }

    public boolean tishi(String name)
    {
        String[] baq={"是","否"};
        return JOptionPane.showOptionDialog(null, name, "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,baq, baq[0])==0;

    }
}
