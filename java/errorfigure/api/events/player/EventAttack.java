package errorfigure.api.events.player;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EventAttack implements Event {
    static boolean pre;
    static Entity entity;
    public EventAttack(boolean pre, Entity entity) {
        this.pre = pre;
        this.entity = entity;
    }
    public static boolean isPre() {
        return pre;
    }
    public static boolean isPost() {
        return !pre;
    }
    public static Entity getEntity() {
        return entity;
    }
}
