package errorfigure.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventPacketRecieve;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.utils.Helper;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class JumpReset extends Module {
    public JumpReset() {
        super("JumpReset", ModuleType.Player);
    }

    @EventTarget
    public void onPacket(EventPacketRecieve e) {
        if (e.getPacket() instanceof S12PacketEntityVelocity && mc.thePlayer.moveForward > 0 && mc.thePlayer.onGround) {
            if (((S12PacketEntityVelocity) e.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
                mc.thePlayer.jump();
            }
        }
    }
}
