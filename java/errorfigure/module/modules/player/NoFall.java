
package errorfigure.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventPacketSend;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.module.modules.movement.Flight;
import errorfigure.utils.Helper;
import errorfigure.utils.PlayerUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", ModuleType.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix("HypixelSpoof");
    }

    @EventTarget
    public void onPacket(EventPacketSend e) {
        if (e.getPacket() instanceof C03PacketPlayer && mc.thePlayer.motionY < 0 && PlayerUtils.isBlockUnder() && mc.thePlayer.fallDistance >= 2.5
        && !ModuleManager.getModuleByClass(Flight.class).isEnabled() && !mc.thePlayer.capabilities.isFlying) {
            ((C03PacketPlayer) e.getPacket()).onGround = true;
            ((C03PacketPlayer) e.getPacket()).setMoving(false);
        }
    }
}


