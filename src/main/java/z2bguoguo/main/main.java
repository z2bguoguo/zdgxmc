package z2bguoguo.main;

import z2bguoguo.zhixing;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        try
        {
            System.setProperty("java.awt.headless", "false");
            JOptionPane.showMessageDialog(null, "创建", "创建", JOptionPane.ERROR_MESSAGE);
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
