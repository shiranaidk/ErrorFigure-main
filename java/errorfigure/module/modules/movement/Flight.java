/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventMove;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.ui.notification.Notification;
import errorfigure.ui.notification.NotificationManager;
import errorfigure.ui.notification.NotificationType;
import errorfigure.utils.MoveUtils;
import errorfigure.utils.PacketUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class Flight
extends Module {

	public Flight() {
		super("Flight", ModuleType.Movement);
	}

	int ticks, stages;

	public void damagePlayer(int damage) {
		// skid from etb
		if (damage < 1)
			damage = 1;
		if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
			damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

		double offset = 0.0625;
		if (mc.thePlayer != null && mc.getNetHandler() != null && mc.thePlayer.onGround) {
			for (int i = 0; i <= ((3 + damage) / offset); i++) { // TODO: teach rederpz (and myself) how math works
				mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
				mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
			}
		}
	}

	@EventTarget
	public void onUpdate(EventUpdate e) {
//		setSuffix("HypixelNew");
//		if (e.isPre()) {
//			ticks ++;
//			if (stages == 2 || ticks >= 11) {
//				mc.timer.timerSpeed = 1;
//
//				mc.thePlayer.motionY = 0;
//				mc.thePlayer.lastReportedPosY = 0;
//				mc.thePlayer.jumpMovementFactor = 0;
//
//				mc.thePlayer.onGround = true;
//			}
//
//			if (stages == 0) {
//				if (ticks == 1) {
//					damagePlayer(4);
//				} else if (ticks == 12) {
//					mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.38, mc.thePlayer.posZ);
//				} else if (ticks == 13) {
//					mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.46, mc.thePlayer.posZ);
//				} else if (ticks == 14) {
//					mc.thePlayer.motionY = -0.46;
//					stages = 2;
//				}
//			}
//		}
//		Module speed = ModuleManager.getModuleByClass(Speed.class);
//		if (speed.isEnabled()) {
//			speed.setEnabled(false);
//		}
 	}

	@EventTarget
	public void onMove(EventMove e) {
//		MoveUtils.setMotion(ticks > 12 ? MoveUtils.defaultSpeed() : 0);
	}

	@Override
	public void onEnable() {
//		ticks = stages = 0;
//		mc.timer.timerSpeed = 1;
		super.onEnable();
		setEnabled(false);
		NotificationManager.show(new Notification(NotificationType.WARNING, "Patched", 3));
	}

	@Override
	public void onDisable() {
//		mc.timer.timerSpeed = 1;
		super.onDisable();
	}
}

