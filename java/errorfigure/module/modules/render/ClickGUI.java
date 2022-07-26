package errorfigure.module.modules.render;

import errorfigure.module.Module;
import errorfigure.module.ModuleType;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", ModuleType.Render);
        setSuffix("ErrorFigure");
    }

    public static int posY = 15;

    public void onEnable() {
        mc.displayGuiScreen(new errorfigure.ui.clickgui.ClickGUI());
    }
}
