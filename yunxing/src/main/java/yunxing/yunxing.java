package yunxing;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class yunxing {
    protected static final Logger logger = LogManager.getLogger(yunxing.class);
    public static void main(String[] args)
    {
        try
        {
            logger.info("开始执行");
            jiemian j=new jiemian();
            File wjml=new File(System.getProperty("user.dir"));
            logger.info("目录："+wjml);
            logger.info("初始化完毕");
            File json=new File(wjml+"/zdgx/json.txt");
            FileReader jsons=new FileReader(json);
            char jsonr[]=new char[1024];
            int jsonlen= jsons.read(jsonr);
            String dizhi=new String(jsonr,0,jsonlen);
            logger.info("json:"+dizhi);
            System.setProperty("java.awt.headless", "false");
            j.chuangjian();
            String[] b=j.getmod(j.geturl(dizhi));//云端文件列表
            logger.info("云端文件列表："+ Arrays.toString(b));
            String[] c=j.getwenjian(wjml+"/mods");//本地文件列表
            logger.info("本地文件列表："+ Arrays.toString(c));
            //String[] c=j.getwenjian("D:\\mc\\.minecraft\\versions\\mod测试\\mods");
            String[] duo=j.bendiduo(c,b,wjml+"/mods/");//本地文件多出的
            logger.info("本地文件多出的："+ Arrays.toString(duo));
            String[] shao=j.bendiduo(b,c,"/");//本地文件少的
            logger.info("本地文件少的："+ Arrays.toString(shao));
            if (duo.length!=0)
            {
                j.chuliduo(duo);
                logger.info("处理多");
            }
            if(shao.length!=0)
            {
                logger.info("处理少");
                for(int i=0;i< shao.length;i++)
                {
                    jiemian.down d=j.new down(dizhi+"/raw/main"+shao[i],wjml+"/mods/"+shao[i],i+1,shao.length);
                    d.start();
                }
            }
            JOptionPane.showMessageDialog(null, "更新完毕");
            System.exit(0);
            //
//
        } catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }
    }
}
