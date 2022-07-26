package errorfigure.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.rendering.EventRender2D;
import errorfigure.api.events.world.EventWorldLoad;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.ui.notification.NotificationManager;
import errorfigure.ui.notification.NotificationType;

public class Notification extends Module {
    public Notification() {
        super("Notification", ModuleType.Render);
    }

    @EventTarget
    public void onRender(EventRender2D e) {
        if (ModuleManager.getModuleByClass(HUD.class).isEnabled()) {
            setSuffix("Theresa");
            NotificationManager.render();
        }
    }

    @EventTarget
    public void onWorldLoad(EventWorldLoad e) {
        }
}
