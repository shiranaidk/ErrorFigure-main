package errorfigure.module.modules.combat;

import errorfigure.api.value.Numbers;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;

public class Reach extends Module {
    public Reach() {
        super("Reach", ModuleType.Combat);
        addValues(range);
    }

    public static Numbers<Double> range = new Numbers<>("Range", "Range", 3d, 3d, 6d, 0.1);
}
