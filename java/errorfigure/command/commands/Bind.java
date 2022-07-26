/*
 * Decompiled with CFR 0_132.
 *
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package errorfigure.command.commands;

import errorfigure.command.Command;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.utils.Helper;
import org.lwjgl.input.Keyboard;

public class Bind
        extends Command {
    public Bind() {
        super("Bind", new String[]{"b"}, "", "sketit");
    }

    @Override
    public String execute(String[] args) {
        if (args.length >= 2) {
            Module m;
            m = null;
            for (Module module : ModuleManager.getModules()) {
                if (module.getName().equalsIgnoreCase(args[0])) {
                    m = module;
                }
            }
            if (m != null) {
                int k = Keyboard.getKeyIndex((String)args[1].toUpperCase());
                m.setKey(k);
                Object[] arrobject = new Object[2];
                arrobject[0] = m.getName();
                arrobject[1] = k == 0 ? "none" : args[1].toUpperCase();
                Helper.sendMessage(String.format("> Bound %s to %s", arrobject));
            } else {
                Helper.sendMessage("> Invalid module name, double check spelling.");
            }
        } else {
            Helper.sendMessageWithoutPrefix("\u00a7bCorrect usage:\u00a77 .bind <module> <key>");
        }
        return null;
    }
}

