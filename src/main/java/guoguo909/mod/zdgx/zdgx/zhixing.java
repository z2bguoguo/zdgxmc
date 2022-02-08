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
            int banben=1;
            jiemian j=new jiemian();
            int gitbanban=Integer.parseInt(j.getgitbanben("z2bguoguo/zdgxmc"));
            String wjml=System.getProperty("user.dir")+"\\zdgx";
            File wjmlcz=new File(wjml);
            if (!wjmlcz .exists())
            {
                wjmlcz.mkdir();
            }
            System.out.println(wjml);
            File modmlf=new File(wjml);
            String modml= modmlf.getParent()+"/mods";
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
            String[] b=j.getmod(j.geturl(dizhi));//云端文件列表
            String[] c=j.getwenjian(modml);//本地文件列表
            String[] duo=j.bendiduo(c,b,modml);//本地文件多出的
            String[] shao=j.bendiduo(b,c,modml);//本地文件少的
            File yunxing=new File(wjml+"/yunxing.jar");
            if(gitbanban>banben)
            {
                String gxdizhi=j.getgitxiazai("z2bguoguo/zdgxmc","zdgx"+String.valueOf(gitbanban)+".jar");
                jiemian.down downs=j.new down(gxdizhi, modml+"/"+"zdgx"+String.valueOf(gitbanban)+".jar", 1,1);
                downs.run();
                File ziji = new File(modml+"/"+"zdgx"+String.valueOf(banben)+".jar");
                ziji.renameTo(new File(modml+"/"+"zdgx"+String.valueOf(banben)+".jar.duo"));
            }
            if (!yunxing.exists() || gitbanban>banben)
            {
                String gxdizhi=j.getgitxiazai("z2bguoguo/zdgxmc","yunxing.jar");//下载yunxing.jar的地址
                jiemian.down downs=j.new down(gxdizhi, yunxing.getPath(), 1,1);
                downs.run();
            }
            if (duo.length!=0||shao.length!=0)
            {
                Runtime.getRuntime().exec("java -jar "+wjml+"/yunxing.jar");
                System.out.println("java -jar "+wjml+"/yunxing.jar");
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
