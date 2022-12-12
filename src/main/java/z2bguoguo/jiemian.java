package z2bguoguo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

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
    public String geturl(String urls,int time)
    {
        try
        {
            URL url=new URL(urls);
            URLConnection coon=url.openConnection();
            coon.setConnectTimeout(time);
            coon.setReadTimeout(time);
            BufferedReader reader=new BufferedReader(new InputStreamReader(coon.getInputStream(),"UTF-8"));
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
    public HashMap<String,String> getmodmd5(JSONArray modary)
    {
        HashMap<String,String> r=new HashMap<>();
        for (int i=0;i<modary.size();i++)
        {
            r.put(modary.getJSONObject(i).getString("md5"),modary.getJSONObject(i).getString("filename"));
        }
        return (r);
    }
    public String[] getwenjian(String mulu)
    {
        File dir = new File(mulu);
        String[] children = dir.list();
        return(children);
    }
    public HashMap<String,String> getwenjianmd5(String mulu)
    {
        try
        {
            File dir = new File(mulu);
            File[] children = dir.listFiles();
            HashMap<String,String> r=new HashMap<>();
            for(File i : children)
            {
                r.put( DigestUtils.md5Hex(new FileInputStream(i)),i.getName());
            }
            return(r);
        } catch (Exception e) {
            System.out.println("错误信息");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误内容"+e.getMessage());
        }
        return null;
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
            if(!cunzaiarray(yun,bendi[i]) && bendi[i].substring(0,5).indexOf("zdgx")<0)
            {
                duo.add(mulu+"/"+bendi[i]);
            }
        }
        return(duo.toArray(new String[0]));
    }
    public class cha
    {
        public HashMap<String,String> bendiduo;
        public HashMap<String,String> bendishao;
        cha(HashMap<String,String> duo,HashMap<String,String> shao)
        {
            bendiduo=duo;
            bendishao=shao;
        }
    }
    //0为本地多的，1为本地少的,key为md5，value为name
    public cha bendicha_md5(HashMap<String,String> bendi,HashMap<String,String> yun)
    {
        HashMap<String,String> bendis=bendi;
        HashMap<String,String> yuns=yun;
        for(Iterator<String> iterator = yuns.keySet().iterator(); iterator.hasNext();)
        {
            String a=iterator.next();
            if(bendis.containsKey(a))
            {
                bendis.remove(a);
                iterator.remove();
            }
        }
        return new cha(bendis,yuns);
    }
    public void chuliduo(String[] duo)
    {
        for(int i=0;i< duo.length;i++)
        {
            File file = new File(duo[i]);
            file.renameTo(new File(duo[i]+".duo"));
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
                chuangjian();
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
                a.dispose();
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
    public String getgitbanben(String repos,int timeout)
    {
        JSONObject json=JSONObject.parseObject(geturl("https://api.github.com/repos/"+repos+"/releases/latest",timeout));
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
    public void jsoncj(File f,String loder,String GameVersionName)
    {
        try
        {
            JSONObject js=new JSONObject();
            js.put("address","");
            js.put("enable",true);
            js.put("loder",loder);
            js.put("GameVersionName",GameVersionName);
            js.put("AutoUpdate",true);
            js.put("Timeout",1000);//检测本mod更新超时时间
            js.put("Asynchronous",true);//是否多线程启动本mod
            FileWriter jsons=new FileWriter(f);
            jsons.write(JSONObject.toJSONString(js,true));
            jsons.flush();
            jsons.close();
        }
        catch (Exception e) {
            System.out.println("错误报告");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("错误报告尾");
        }

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
                                return(getdowndz(modary, filename, json));
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
    public String getdownjson(cha chas,JSONArray modary,JSONObject json)
    {
        JSONArray r=new JSONArray();
        JSONObject x=new JSONObject();
        JSONObject re=new JSONObject();
        r.clear();
        for (String i : chas.bendiduo.values())
        {
            x.clear();
            x.put("name",i);
            r.add(x);
        }
        re.put("duo",r);
        r.clear();
        for (String i : chas.bendishao.values())
        {
            x.clear();
            x.put("name",i);
            x.put("downdz",getdowndz(modary,i,json));
            r.add(x);
        }
        re.put("shao",r);
        return(JSON.toJSONString(re, SerializerFeature.DisableCircularReferenceDetect));
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
           return(moddata.getJSONObject("data").getJSONArray("latestFiles"));
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
    public class tcptx
    {
        public void servers(String s)
        {
            try {
                ServerSocket serverSocket = new ServerSocket(20534);
                //2.等待客户端连接
                System.out.println("服务器创建完毕");
                Socket socket=serverSocket.accept();
                System.out.println("客户端已连接");
                //3.获取socket通道的输入流
                //InputStream in=socket.getInputStream();
                /*BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
                //4.获取socket 通道的输出流
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                //循环读取数据，并拼接到文本域中
                /*String line=null;
                String r=new String();
                while((line=br.readLine())!=null) {
                    r=r+line;
                }
                System.out.println("接收到客户端消息："+r);*/
                System.out.println("向服务器发送："+s);
               // bw.write(s);
                //bw.flush();
                //bw.close();
                //br.close();
                pw.print(s);
                pw.flush();
                socket.close();
                serverSocket.close();
            }catch (Exception e) {
                System.out.println("错误信息");
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                System.out.println("错误内容"+e.getMessage());
            }
        }
        public String clients()
        {
            try {
                Socket socket = new Socket("127.0.0.1",20534);
                //2.获取socket通道 的输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //3.获取socket 通道的输出流
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                //循环读取数据，并拼接到文本域中
                String line=null;
                String r=new String();
                while((line=br.readLine())!=null) {
                    r=r+line;
                }
                System.out.println("接受到服务器消息："+r);

                //4，关闭socket 通道
                socket.close();
                return (r);
            }catch (Exception e) {
                System.out.println("错误信息");
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                System.out.println("错误内容"+e.getMessage());
            }
            return null;
        }
    }
}
