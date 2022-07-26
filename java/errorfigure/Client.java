/*
 * Decompiled with CFR 0_132.
 */
package errorfigure;

import errorfigure.command.CommandManager;
import errorfigure.management.FileManager;
import errorfigure.management.FriendManager;
import errorfigure.management.ModuleManager;
import errorfigure.api.value.Value;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.ui.clickgui.ClickGUI;
import errorfigure.ui.login.AltManager;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public final String name = "ErrorFigure";
    public static String userName = "offlineUser";
    public static final List<String> devname = new ArrayList<>();
    public final double version = 1.0;
    public static Client instance = new Client();
    private ModuleManager modulemanager;
    private CommandManager commandmanager;
    private AltManager altmanager;
    private FriendManager friendmanager;
    public static ResourceLocation CLIENT_CAPE = new ResourceLocation("ETB/cape.png");

    public void initiate() {
        Display.setTitle("ErrorFigure 220710 | エラーフィギュア 220710 | 错误系数客户端 220710");
        this.commandmanager = new CommandManager();
        this.commandmanager.init();
        this.friendmanager = new FriendManager();
        this.friendmanager.init();
        this.modulemanager = new ModuleManager();
        this.modulemanager.init();
        this.altmanager = new AltManager();
        AltManager.init();
        AltManager.setupAlts();
        FileManager.init();
        devname.add("unrealizable7226");
        for (ModuleType t : ModuleType.values()) {
            ClickGUI.extract.add(t);
        }
    }

    public ModuleManager getModuleManager() {
        return this.modulemanager;
    }

    public CommandManager getCommandManager() {
        return this.commandmanager;
    }

    public AltManager getAltManager() {
        return this.altmanager;
    }

    public void shutDown() {
        String values = "";
        System.out.println("Shutting down 1");
        instance.getModuleManager();
        System.out.println("Shutting down 2");
        for (Module m : ModuleManager.getModules()) {
            for (Value v : m.getValues()) {
                values = String.valueOf(values) + String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator());
            }
        }
        System.out.println("Shutting down 3");
        FileManager.save("Values.txt", values, false);
        System.out.println("Shutting down 4");
        String enabled = "";
        System.out.println("Shutting down 5");
        instance.getModuleManager();
        System.out.println("Shutting down 6");
        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled()) continue;
            enabled = String.valueOf(enabled) + String.format("%s%s", m.getName(), System.lineSeparator());
        }
        System.out.println("Shutting down 7");
        FileManager.save("Enabled.txt", enabled, false);
        System.out.println("Shutting down 8");
    }
}

