package guoguo909.mod.zdgx.zdgx;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import javax.swing.*;
import java.io.File;
import java.io.FileReader;
public class zhixing extends Thread
{
    public void run() {
        try
        {
            String wjml=System.getProperty("user.dir");
            File json=new File(wjml+"/json.txt");
            if (!json.exists())
            {
                json.createNewFile();
                JOptionPane.showMessageDialog(null, "请输入地址", "错误", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            FileReader jsons=new FileReader(json);
            char jsonr[]=new char[1024];
            int jsonlen= jsons.read(jsonr);
            String dizhi=new String(jsonr,0,jsonlen);
            String gxdizhi=dizhi+"/raw/main/yunxing.jar";//下载yunxing.jar的地址
            jiemian j=new jiemian();
            String[] b=j.getmod(j.geturl(dizhi));//云端文件列表
            String[] c=j.getwenjian(wjml+"/mods");//本地文件列表
            String[] duo=j.bendiduo(c,b,wjml+"/mods/");//本地文件多出的
            String[] shao=j.bendiduo(b,c,wjml+"/mods/");//本地文件少的
            File yunxing=new File(wjml+"/yunxing.jar");
            if (!yunxing.exists())
            {
                j.chuangjian();
                jiemian.down downs=j.new down(gxdizhi, yunxing.getPath(), 1,1);
                downs.start();
                j.a.dispose();
            }
            if (duo.length!=0||shao.length!=0)
            {
                Runtime.getRuntime().exec("java -jar "+wjml+"/yunxing.jar");
                System.exit(0);
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
