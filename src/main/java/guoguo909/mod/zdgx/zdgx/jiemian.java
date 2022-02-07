package guoguo909.mod.zdgx.zdgx;

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
    JFrame a=new JFrame("更新");
    JLabel text=new JLabel("更新中");
    Container con =a.getContentPane();
    JProgressBar jindu=new JProgressBar(0,100);
    JProgressBar jindu2=new JProgressBar(0,100);
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
}
