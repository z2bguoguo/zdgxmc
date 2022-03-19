package z2bguoguo;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class chajian {
    public cj[] ccj;
    public String[] getwenjian(String mulu)
    {
        List<String> re=new ArrayList<String>();
        File a=new File(mulu);
        String[] files = a.list();
        for (int i = 0; i < files.length; i++)
        {
            File file = new File(a, files[i]);
            if (file.isFile())
            {
                re.add(file.getPath());
            }
        }
        String []res=re.toArray(new String[0]);
        return res;
    }
    public JSONObject getjarjson(String mulu)
    {
        try
        {
            JarFile jarFile = new JarFile(mulu);
            JarEntry jarEntry = jarFile.getJarEntry("json.json");
            InputStream i= jarFile.getInputStream(jarEntry);
            byte a[] = i.readAllBytes();
            String s = new String(a, "utf-8");
            return JSONObject.parseObject(s);
        }
        catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            System.out.println("错误报告尾");
            return null;
        }
    }
    public cj getclas(String mulu,String leiname)
    {
        try {
            ClassLoader cl=new URLClassLoader(new URL[] { new URL("file:///"+mulu) });
            Class<?> c = cl.loadClass(leiname);
            Object re =  c.getDeclaredConstructor().newInstance();
            return new cj(c.getDeclaredMethods(),re);
        }
        catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
            return null;
        }
    }
    chajian(String mulu)
    {
        String []wenjian=getwenjian(mulu);
        ArrayList<cj> r=new ArrayList<cj>();
        for(int i=0;i<wenjian.length;i++)
        {
            String a= getjarjson(wenjian[i]).getString("ClassName");
            r.add(getclas(wenjian[i],a));
        }
        ccj= (cj[]) r.toArray(r.toArray(new cj[0]));
    }
    public void zhixing(String name,Object... arg)
    {
        try
        {
            for (int i=0;i<ccj.length;i++)
            {
                ccj[i].m.get(name).invoke(ccj[i].obj,arg);
            }
        }catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }
    }
}
