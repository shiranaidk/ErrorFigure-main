/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.misc.EventCollideWithBlock;
import errorfigure.api.events.world.EventPacketSend;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

import java.awt.*;

public class Jesus
extends Module {
    public Jesus() {
        super("Jesus", ModuleType.Movement);
        this.setColor(new Color(188, 233, 248).getRGB());
    }

    private boolean canJeboos() {
        if (!(this.mc.thePlayer.fallDistance >= 3.0f || this.mc.gameSettings.keyBindJump.isPressed() || BlockHelper.isInLiquid() || this.mc.thePlayer.isSneaking())) {
            boolean b = true;
            return b;
        }
        return false;
    }

    @EventTarget
    public void onPre(EventUpdate e) {
        if (BlockHelper.isInLiquid() && !this.mc.thePlayer.isSneaking() && !this.mc.gameSettings.keyBindJump.isPressed()) {
            this.mc.thePlayer.motionY = 0.05;
            this.mc.thePlayer.onGround = true;
        }
    }

    @EventTarget
    public void onPacket(EventPacketSend e) {
        if (e.getPacket() instanceof C03PacketPlayer && this.canJeboos() && BlockHelper.isOnLiquid()) {
            C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
            packet.y = this.mc.thePlayer.ticksExisted % 2 == 0 ? packet.y + 0.01 : packet.y - 0.01;
        }
    }

    @EventTarget
    public void onBB(EventCollideWithBlock e) {
        if (e.getBlock() instanceof BlockLiquid && this.canJeboos()) {
            e.setBoundingBox(new AxisAlignedBB(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ(), (double)e.getPos().getX() + 1.0, (double)e.getPos().getY() + 1.0, (double)e.getPos().getZ() + 1.0));
        }
    }
}

