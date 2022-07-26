package errorfigure.module.modules.combat;

import errorfigure.api.value.Numbers;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;

public class HitBox extends Module {
    public HitBox() {
        super("HitBox", ModuleType.Combat);
        addValues(size);
    }

    public static Numbers<Float> size = new Numbers<>("Size", "Size", 0.1f, 0f, 1f, 0.1f);
}
