package errorfigure.ui.clickgui;

import errorfigure.api.value.Mode;
import errorfigure.api.value.Numbers;
import errorfigure.api.value.Option;
import errorfigure.api.value.Value;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.ui.font.FontLoaders;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClickGUI extends GuiScreen {
    private int posY = errorfigure.module.modules.render.ClickGUI.posY;
    private static List<Module> inSetting = new CopyOnWriteArrayList<>();
    public static List<ModuleType> extract = new CopyOnWriteArrayList<>();

    @Override
    public void initGui() {
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(0, 0, width, height, new Color(255, 255, 255, 50).getRGB()); // BackGround
        int counter = 0; // Type Counter
        for (ModuleType t : ModuleType.values()) {
            counter++;
            int posX = counter * width / 6;
            posY -= Mouse.getDWheel() / 5;
            boolean onTypeButton = mouseX > posX - 40 && mouseY > posY - 5 && mouseX < posX + 40 && mouseY < posY + 11;;
            Gui.drawRect(posX - 40, posY - 5, posX + 40, posY + 11, new Color(40, 40, 40).getRGB());
            FontLoaders.kiona20.drawCenteredString(t.toString(), posX, posY, new Color(255, 255, 255).getRGB());
            if (extract.contains(t)) {
                int counter2 = 0;
                int counter3 = 0;
                for (Module m: ModuleManager.getModules()) {
                    if (m.getType() == t) {
                        counter2++;
                        boolean isOnButton = mouseX > posX - 38 && mouseY > posY - 4 + counter2 * 15 + counter3 && mouseX < posX + 38 && mouseY < posY + 11 + counter2 * 15 + counter3;
                        if (isOnButton) { // Change color if is on Button
                            if (!m.isEnabled()) { // Difference if enabled or not
                                Gui.drawRect(posX - 38, posY - 4 + counter2 * 15 + counter3, posX + 38, posY + 11 + counter2 * 15 + counter3, new Color(30, 30, 30, 250).getRGB());
                                FontLoaders.kiona16.drawCenteredString(m.getName(), posX, posY + counter2 * 15 + counter3, new Color(221, 186, 251).getRGB());
                            }
                            else {
                                Gui.drawRect(posX - 38, posY - 4 + counter2 * 15 + counter3, posX + 38, posY + 11 + counter2 * 15 + counter3, new Color(200, 200, 200, 250).getRGB());
                                FontLoaders.kiona16.drawCenteredString(m.getName(), posX, posY + counter2 * 15 + counter3, new Color(57, 102, 183).getRGB());
                            }
                        }
                        else {
                            if (!m.isEnabled()) { // Difference if enabled or not
                                Gui.drawRect(posX - 38, posY - 4 + counter2 * 15 + counter3, posX + 38, posY + 11 + counter2 * 15 + counter3, new Color(30, 30, 30, 200).getRGB());
                                FontLoaders.kiona16.drawCenteredString(m.getName(), posX, posY + counter2 * 15 + counter3, new Color(221, 186, 251).getRGB());
                            }
                            else {
                                Gui.drawRect(posX - 38, posY - 4 + counter2 * 15 + counter3, posX + 38, posY + 11 + counter2 * 15 + counter3, new Color(200, 200, 200, 200).getRGB());
                                FontLoaders.kiona16.drawCenteredString(m.getName(), posX, posY + counter2 * 15 + counter3, new Color(57, 102, 183).getRGB());
                            }
                        }
                        if (inSetting.contains(m)) {
                            for (Value<?> s : m.getValues()) {
                                int counter4 = counter3;
                                counter3 += 15;
                                Gui.drawRect(posX - 38, posY + 11 + counter2 * 15 + counter4, posX + 38, posY + 11 + counter2 * 15 + counter3, new Color(50, 50, 50, 200).getRGB());
                                if (s instanceof Numbers) {
                                    Numbers<?> setting = (Numbers) s;
                                    FontLoaders.kiona16.drawCenteredString(s.getDisplayName() + " : " + String.format("%.2f",setting.getValue().doubleValue()), posX, posY + counter2 * 15 + counter3, new Color(142, 202, 234).getRGB());
                                }
                                if (s instanceof Mode) {
                                    FontLoaders.kiona16.drawCenteredString(s.getDisplayName() + " : " + s.getValue().toString(), posX, posY + counter2 * 15 + counter3, new Color(142, 202, 234).getRGB());
                                }
                                if (s instanceof Option) {
                                    Option<Boolean> setting = (Option) s;
                                    if (setting.getValue()) {
                                        FontLoaders.kiona16.drawCenteredString(s.getDisplayName(), posX, posY + counter2 * 15 + counter3, new Color(142, 202, 234).getRGB());
                                    }
                                    else {
                                        FontLoaders.kiona16.drawCenteredString(s.getDisplayName(), posX, posY+ counter2 * 15 + counter3, new Color(248, 202, 234).getRGB());
                                    }
                                }
                            }
                        }
                    }
                }
                // Ending rectangle
                Gui.drawRect(posX - 38, posY + 11 + counter2 * 15 + counter3, posX + 38, posY + 13 + counter2 * 15 + counter3, new Color(107, 146, 241).getRGB());
            }
        }
        Gui.drawRect(0, height - 15, width, height, new Color(20, 20, 20, 200).getRGB());// Bottom bar
        FontLoaders.kiona24.drawString("Reset", width - 50, height - 12, new Color(221, 186, 251).getRGB());
        FontLoaders.kiona24.drawString("ErrorFigure client, made by unrealizable#7226", 5, height - 12, new Color(221, 186, 251).getRGB());
    }
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int counter = 0;
        if (mouseX > width - 55 && mouseY > height - 15 && mouseX < width - 15 && mouseY < height) {
            posY = 15;
        }
        for (ModuleType t : ModuleType.values()) {
            counter++;
            int posX = counter * width / 6;
            boolean onTypeButton = mouseX > posX - 40 && mouseY > posY - 5 && mouseX < posX + 40 && mouseY < posY + 11;
            if (onTypeButton && mouseButton == 1) {
                if (extract.contains(t)) {
                    extract.remove(t);
                }
                else {
                    extract.add(t);
                }
            }
            if (extract.contains(t)) {
                int counter2 = 0;
                int counter3 = 0;
                for (Module m : ModuleManager.getModules()) {
                    if (m.getType() == t) {
                        counter2++;
                        boolean isOnButton = mouseX > posX - 38 && mouseY > posY - 4 + counter2 * 15 + counter3 && mouseX < posX + 38 && mouseY < posY + 11 + counter2 * 15 + counter3;
                        if (isOnButton && mouseButton == 0) {
                            m.setEnabled(!m.isEnabled()); // Enable module when clicking
                        }
                        if (isOnButton && mouseButton == 1 && !m.getValues().isEmpty()) {
                            if (inSetting.contains(m)) {
                                inSetting.remove(m); // Close setting when right click the module
                            }
                            else if (m.getValues() != null) {
                                inSetting.add(m);
                            }
                        }
                        if (inSetting.contains(m)) {
                            for (Value<?> s : m.getValues()) {
                                int counter4 = counter3;
                                counter3 += 15;
                                boolean isOnText = mouseX > posX - 38 && mouseY > posY + 11 + counter2 * 15 + counter4 && mouseX < posX + 38 && mouseY < posY + 11 + counter2 * 15 + counter3;
                                if (isOnText) {
                                    if (s instanceof Numbers) {
                                        Numbers<Number> setting = (Numbers) s;
                                        if (setting.getValue() instanceof Double) {
                                            if (mouseButton == 0 && setting.getValue().doubleValue() + setting.inc.doubleValue() <= setting.getMaximum().doubleValue()) {
                                                setting.setValue(setting.getValue().doubleValue() + setting.inc.doubleValue());
                                            }
                                            else if (mouseButton == 0 && (setting.getValue()) != setting.getMaximum()){
                                                setting.setValue(setting.getMaximum());
                                            }
                                            if (mouseButton == 1 && setting.getValue().doubleValue() - setting.inc.doubleValue() >= setting.getMinimum().doubleValue()) {
                                                setting.setValue(setting.getValue().doubleValue() - setting.inc.doubleValue());
                                            }
                                            else if (mouseButton == 1 && (setting.getValue()) != setting.getMinimum()){
                                                setting.setValue(setting.getMinimum());
                                            }
                                        }
                                        else if (setting.getValue() instanceof Float) {
                                            if (mouseButton == 0 && setting.getValue().floatValue() + setting.inc.floatValue() <= setting.getMaximum().floatValue()) {
                                                setting.setValue(setting.getValue().floatValue() + setting.inc.floatValue());
                                            }
                                            else if (mouseButton == 0 && (setting.getValue()) != setting.getMaximum()){
                                                setting.setValue(setting.getMaximum());
                                            }
                                            if (mouseButton == 1 && setting.getValue().floatValue() - setting.inc.floatValue() >= setting.getMinimum().floatValue()) {
                                                setting.setValue(setting.getValue().floatValue() - setting.inc.floatValue());
                                            }
                                            else if (mouseButton == 1 && (setting.getValue()) != setting.getMinimum()){
                                                setting.setValue(setting.getMinimum());
                                            }
                                        }
                                        else if (setting.getValue() instanceof Integer) {
                                            if (mouseButton == 0 && setting.getValue().intValue() + setting.inc.intValue() <= setting.getMaximum().intValue()) {
                                                setting.setValue(setting.getValue().intValue() + setting.inc.intValue());
                                            }
                                            else if (mouseButton == 0 && (setting.getValue()) != setting.getMaximum()){
                                                setting.setValue(setting.getMaximum().intValue());
                                            }
                                            if (mouseButton == 1 && setting.getValue().intValue() - setting.inc.intValue() >= setting.getMinimum().intValue()) {
                                                setting.setValue(setting.getValue().intValue() - setting.inc.intValue());
                                            }
                                            else if (mouseButton == 1 && (setting.getValue()) != setting.getMinimum()){
                                                setting.setValue(setting.getMinimum().intValue());
                                            }
                                        }
                                    }
                                    if (s instanceof Mode) {
                                        Mode setting = (Mode) s;
                                        if (mouseButton == 1) {
                                            Enum current = (Enum) s.getValue();
                                            setting.setValue(setting.getModes()[current.ordinal() - 1 < 0 ? setting.getModes().length - 1
                                                    : current.ordinal() - 1]);
                                        }
                                        if (mouseButton == 0) {
                                            Enum current = (Enum) s.getValue();
                                            int next = (current.ordinal() + 1 >= setting.getModes().length) ? 0
                                                    : (current.ordinal() + 1);
                                            setting.setValue(setting.getModes()[next]);
                                        }
                                    }
                                    if (s instanceof Option) {
                                        Option<Boolean> setting = (Option) s;
                                        setting.setValue(!setting.getValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        ModuleManager.getModuleByClass(errorfigure.module.modules.render.ClickGUI.class).setEnabled(false);
        errorfigure.module.modules.render.ClickGUI.posY = posY;
    }
}