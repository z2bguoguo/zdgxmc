package yunxing;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import z2bguoguo.zhixing;

public class yunxing {
    protected static final Logger logger = LogManager.getLogger(yunxing.class);
    public static void main(String[] args)
    {
        if(args.length>=1 && args[0].equals("zx"))
        {
            yx();

        }
        else
        {
            try
            {
                zhixing zx=new zhixing();
                zx.start();
            }
            catch (Exception e) {
                System.out.println("错误报告");
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                System.out.println("错误报告尾");
            }
        }
    }
    public static void yx()
    {
        try
        {
            logger.info("开始执行");
            jiemian j=new jiemian();
            File wjml=new File(System.getProperty("user.dir"));
            String cjml=wjml+"\\zdgx\\plugins";
            logger.info("目录："+wjml);
            logger.info("初始化完毕");
            chajian chaj=new chajian(cjml);
            logger.info("插件初始化完毕");
            logger.info("执行插件初始化命令");
            chaj.zhixing("Init","yunxing");
            logger.info("插件初始化命令执行完毕");
            File json=new File(wjml+"/zdgx/json.json");
            FileReader jsons=new FileReader(json);
            char jsonr[]=new char[1024];
            int jsonlen= jsons.read(jsonr);
            String jsonn=new String(jsonr,0,jsonlen);
            JSONObject jsonx=JSONObject.parseObject(jsonn);
            String dizhi=jsonx.getString("address");
            logger.info("json:"+dizhi);
            chaj.zhixing("Address",dizhi);
            logger.info("插件获取地址命令执行完毕");
            System.setProperty("java.awt.headless", "false");
            j.chuangjian();
            JSONArray modary=j.getmodary(j.geturl(dizhi));
            String[] b= j.getmodfilenames(modary);//云端文件列表
            logger.info("云端文件列表："+ Arrays.toString(b));
            String[] c=j.getwenjian(wjml+"/mods");//本地文件列表
            logger.info("本地文件列表："+ Arrays.toString(c));
            //String[] c=j.getwenjian("D:\\mc\\.minecraft\\versions\\mod测试\\mods");
            String[] duo=j.bendiduo(c,b,wjml+"/mods/");//本地文件多出的
            logger.info("本地文件多出的："+ Arrays.toString(duo));
            String[] shao=j.bendiduo(b,c,"");//本地文件少的
            logger.info("本地文件少的："+ Arrays.toString(shao));
            if (duo.length!=0)
            {
                j.chuliduo(duo);
                logger.info("处理多");
            }
            if(shao.length!=0)
            {
                logger.info("处理少");
                String downdz;
                for(int i=0;i< shao.length;i++)
                {
                    downdz=j.getdowndz(modary,shao[i],jsonx);
                    if(!downdz.equals(""))
                    {
                        jiemian.down d=j.new down(downdz,wjml+"/mods/"+shao[i],i+1,shao.length);
                        d.start();
                    }
                    else
                    {
                        logger.info("用户不同意下载不安全的链接："+shao[i]);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "更新完毕");
            System.exit(0);
            //
//
        } catch (Exception e) {
            System.out.println("错误报告");
            logger.error(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }
    }
}
