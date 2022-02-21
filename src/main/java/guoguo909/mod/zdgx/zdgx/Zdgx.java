package guoguo909.mod.zdgx.zdgx;
import net.fabricmc.api.ModInitializer;


import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import net.minecraft.MinecraftVersion;
public class Zdgx implements ModInitializer {
    @Override
    public void onInitialize()
    {
        try
        {
            System.setProperty("java.awt.headless", "false");
            zhixing zx=new zhixing();
            zx.GameVersionName=MinecraftVersion.create().getName();
            zx.loder="fabric";
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
