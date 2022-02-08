package yunxing;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;

public class yunxing {
    public static void main(String[] args)
    {
        try
        {
            jiemian j=new jiemian();
            File wjml=new File(System.getProperty("user.dir"));
            File json=new File(wjml+"/zdgx/json.txt");
            FileReader jsons=new FileReader(json);
            char jsonr[]=new char[1024];
            int jsonlen= jsons.read(jsonr);
            String dizhi=new String(jsonr,0,jsonlen);
            System.setProperty("java.awt.headless", "false");
            j.chuangjian();
            String[] b=j.getmod(j.geturl(dizhi));//云端文件列表
            String[] c=j.getwenjian(wjml+"/mods");//本地文件列表
            //String[] c=j.getwenjian("D:\\mc\\.minecraft\\versions\\mod测试\\mods");
            String[] duo=j.bendiduo(c,b,wjml+"/mods/");//本地文件多出的
            String[] shao=j.bendiduo(b,c,"/");//本地文件少的
            if (duo.length!=0)
            {
                j.chuliduo(duo);
            }
            if(shao.length!=0)
            {
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
