package errorfigure.api.events.world;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.multiplayer.WorldClient;

public class EventWorldLoad implements Event {
    WorldClient worldClientIn;
    public EventWorldLoad(WorldClient worldClientIn) {
        this.worldClientIn = worldClientIn;
    }
}
