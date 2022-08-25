package guoguo909.mod.zdgx.zdgx;
import net.fabricmc.api.ModInitializer;


import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

import net.minecraft.MinecraftVersion;

public class Zdgx implements ModInitializer {
    @Override
    public void onInitialize()
    {
        try
        {
            System.setProperty("java.awt.headless", "false");
            String wjml=System.getProperty("user.dir")+"\\zdgx";
            System.out.println("文件目录："+wjml);
            File wjmlcz=new File(wjml);
            if (!wjmlcz .exists())
            {
                wjmlcz.mkdir();
            }
            File file=new File(System.getProperty("user.dir")+"\\zdgx\\zdgxzhixing.jar");
            if(!file.exists())
            {
                System.out.println("文件不存在");
                InputStream is = getClass().getResourceAsStream("/zdgxzhixing.jar");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int len;
                while ((len=is.read(b)) != -1) {
                    fos.write(b,0,len);// 写入数据
                }
                is.close();
                fos.close();// 保存数据
            }
            ClassLoader cl=new URLClassLoader(new URL[] { new URL("file:///"+System.getProperty("user.dir")+"\\zdgx\\zdgxzhixing.jar") });
            Class<?> c = cl.loadClass("z2bguoguo.zhixing");
            Object re =  c.getDeclaredConstructor().newInstance();
            c.getMethod("zx",String.class,String.class).invoke(re,new Object[]{MinecraftVersion.create().getName(),"Fabric"});
        }
        catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }
    }
}
