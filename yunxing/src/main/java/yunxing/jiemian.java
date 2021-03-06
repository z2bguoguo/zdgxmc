package yunxing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class jiemian {
    protected static final Logger logger = LogManager.getLogger(jiemian.class);
    JFrame a=new JFrame("更新");
    JLabel text=new JLabel("更新中");
    Container con =a.getContentPane();
    JProgressBar jindu=new JProgressBar(0,100);
    JProgressBar jindu2=new JProgressBar(0,100);
    int downdirect=-1;
    public void chuangjian()
    {
        try
        {

            a.setLayout(null);
            text.setHorizontalAlignment(SwingConstants.CENTER);
            text.setBounds(0,0,400,50);
            con.add(text);
            jindu.setBounds(10,50,360,5);
            con.add(jindu);
            jindu2.setBounds(10,70,360,5);
            con.add(jindu2);
            a.setSize(400,150);
            a.setLocation(320,240);
            a.setVisible(true);
        }
        catch (Exception e)
        {
            System.out.println("错误信息");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误内容"+e.getMessage());
        }
    }
    public String geturl(String urls)
    {
        try
        {
            URL url=new URL(urls);
            BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
            String line;
            String s="";
            while((line=reader.readLine())!=null)
            {
                s=s+line;
            }
            reader.close();
            return (s);
        }
        catch (MalformedURLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return ("");
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return ("");
        }
    }
    public String[] getmod(String str)
    {
        String s;
        s=str.substring(str.indexOf("这是内容开始")+6, str.indexOf("这是内容结束"));
        String[] s2=s.split(",");
        return(s2);
    }
    public String[] getwenjian(String mulu)
    {
        File dir = new File(mulu);
        String[] children = dir.list();
        return(children);
    }
    public boolean cunzaiarray(String[] a,String b)
    {
        boolean x=false;
        for(int i=0;i< a.length;i++)
        {
            if (b.equals(a[i]))
            {
                x=true;
                return(x);
            }
        }
        return (x);
    }
    public String[] bendiduo(String[] bendi,String[] yun,String mulu)
    {
        java.util.List<String> duo=new ArrayList<>();
        for (int i=0;i<bendi.length;i++)
        {
            int sx=Arrays.binarySearch(yun,bendi[i]);
            if(!cunzaiarray(yun,bendi[i]))
            {
                duo.add(mulu+bendi[i]);
            }
        }
        return(duo.toArray(new String[0]));
    }
    public void chuliduo(String[] duo)
    {
        for(int i=0;i< duo.length;i++)
        {
            File file = new File(duo[i]);
            if(file.getName().substring(0,5).indexOf("zdgx")<0)
            {
                file.renameTo(new File(duo[i]+".duo"));
            }

        }
    }
    public class down extends Thread
    {
        String urls;
        String mulu;
        float n;
        float max;
        down(String url,String filename,int s,int m)
        {
            urls=url;
            mulu=filename;
            n=s;
            max=m;
        }
        public void run()
        {
            try
            {
                URL url=new URL(urls);
                HttpURLConnection con = (HttpURLConnection) (url.openConnection());
                InputStream reader=con.getInputStream();
                File file=new File(mulu);
                FileOutputStream out=new FileOutputStream(file);
                int len;
                byte[] b=new byte[1024];
                float lang=(float)con.getContentLength();
                float down=0;
                float jindus=0;
                while ((len=reader.read(b))!=-1)
                {
                    out.write(b,0,len);
                    down=down+len;
                    jindus=down/lang*100;
                    jindu2.setValue((int) jindus);
                    System.out.println(down+","+lang+","+jindus);
                }
                float jd=n/max*100;
                jindu.setValue((int) jd);
                out.close();
                reader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public String getgitbanben(String repos)
    {
        JSONObject json=JSONObject.parseObject(geturl("https://api.github.com/repos/"+repos+"/releases/latest"));
        String name= json.getString("name");
        return (name);
    }
    public String getgitxiazai(String repos,String filename)
    {
        JSONObject json=JSONObject.parseObject(geturl("https://api.github.com/repos/"+repos+"/releases/latest"));
        String name= json.getString("name");
        String url="https://github.com/"+repos+"/releases/download/"+name+"/"+filename;
        return (url);
    }
    public JSONArray getmodary(String str)
    {
        JSONArray a=JSONArray.parseArray(str);
        return(a);
    }
    public String[] getmodfilenames(JSONArray modary)
    {
        String[] r=new String[modary.size()];
        for (int i=0;i<modary.size();i++)
        {
            r[i]=modary.getJSONObject(i).getString("filename");
        }
        return (r);
    }
    public String getdowndz(JSONArray modary,String filename,JSONObject json)
    {
        try
        {
            for (int i=0;i<modary.size();i++)
            {
                JSONObject j=modary.getJSONObject(i);
                if (j.getString("filename").equals(filename))
                {
                    switch (j.getString("method"))
                    {
                        case "direct":
                            if (downdirect==-1)
                            {
                                String[] baq={"是","否"};
                                downdirect= JOptionPane.showOptionDialog(null, "" +"是否允许从不安全的地址下载mod", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,baq, baq[0]);
                            }
                            if (downdirect==0)
                            {
                                return(j.getString("download"));
                            }
                            return("");
                        case "curseforge":
                            curseforge cu=new curseforge();
                            String GameVersionTypeId= cu.GetGameVersionTypeId(json.getString("GameVersionName"));
                            JSONObject moddata=cu.getmoddata(j.getString("modid"),GameVersionTypeId);
                            JSONArray modaryx=cu.getmodary(moddata);
                            JSONObject modfiledata=cu.getmodfiledata(modaryx,j.getString("modname"),json.getString("loder"));
                            return(cu.getxzdz(modfiledata));
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("错误报告");
            logger.error(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }
        return("");
    }
    public static class curseforge
    {
        public String getcuurl(String urls)
        {
            try
            {
                URL url=new URL(urls);
                HttpURLConnection b=(HttpURLConnection) url.openConnection();
                b.addRequestProperty("x-api-key","$2a$10$qgRhXiDWOmZq.60.6Lejlu8tkZiVf0otjILqJ2i8OmK9pT/ag5Ugi");
                BufferedReader reader=new BufferedReader(new InputStreamReader(b.getInputStream(),"UTF-8"));
                String line;
                String s="";
                while((line=reader.readLine())!=null)
                {
                    s=s+line;
                }
                reader.close();
                return (s);
            }
            catch (MalformedURLException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return ("");
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return ("");
            }
        }
        public JSONObject getmoddata(String modid,String gameVersionTypeId)
        {
            JSONObject json=JSONObject.parseObject(getcuurl("https://api.curseforge.com/v1/mods/"+modid+"/files?gameVersionTypeId="+gameVersionTypeId));
            return(json);
        }
        public JSONArray getmodary(JSONObject moddata)
        {
            return(moddata.getJSONArray("data"));
        }
        public String GetGameVersionTypeId(String GameVersionName)
        {
            JSONArray j;
            JSONArray vj=JSONObject.parseObject(getcuurl("https://api.curseforge.com/v1/games/432/versions")).getJSONArray("data");
            for(int i=0;i<vj.size();i++)
            {
                j=vj.getJSONObject(i).getJSONArray("versions");
                if ( j.contains(GameVersionName) && j.size()<20)
                {
                    return(vj.getJSONObject(i).getString("type"));
                }
            }
            return("");
        }
        public JSONObject getmodfiledata(JSONArray modary,String modname,String loder)//loder是mod加载器
        {
            for(int i=0;i<modary.size();i++)
            {
                JSONObject j=modary.getJSONObject(i);
                if(j.getString("displayName").equals(modname) && j.getJSONArray("gameVersions").contains(loder))
                {
                    return(j);
                }
            }
            return(null);
        }
        public String getxzdz(JSONObject modfiledata)//获取下载地址
        {
            return(modfiledata.getString("downloadUrl"));
        }
    }
}
