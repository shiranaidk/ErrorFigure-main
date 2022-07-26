package errorfigure.module.modules.combat;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.player.EventAttack;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Numbers;
import errorfigure.api.value.Option;
import errorfigure.management.ModuleManager;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.module.modules.movement.Scaffold;
import errorfigure.module.modules.render.SessionInfo;
import errorfigure.utils.TimerUtils;
import errorfigure.utils.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LegitAura extends Module {
    public LegitAura() {
        super("LegitAura", ModuleType.Combat);
    }

    Numbers<Integer> fov = new Numbers<>("Fov", "Fov", 30, 0, 180, 10),
            cps = new Numbers<>("CPS", "CPS", 10, 2, 18, 1);
    Numbers<Double> range = new Numbers<>("Range", "Range", 3d, 3d, 4.2, 0.1);
    Option<Boolean> onClick = new Option<>("onClick", "onClick", true),
            silent = new Option<>("Silent", "Silent", true);

    TimerUtils AttackTimer = new TimerUtils();

    List<Entity> targets = new ArrayList<>();
    EntityLivingBase target;
    Comparator<Entity> angleComparator = Comparator.comparingDouble(e2 -> e2.getDistanceToEntity(Minecraft.getMinecraft().thePlayer));
    int index;

    private boolean shouldAttack() {
        return this.AttackTimer.hasReached(1000.0 / (this.cps.getValue() + MathUtil.randomDouble(-1.0, 1.0)));
    }

    public static List<Entity> getTargets(Double value) {
        return (List<Entity>) Minecraft.getMinecraft().theWorld.loadedEntityList.stream().filter(e -> (double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e) <= value);
    }

    public void onEnable() {
        index = 0;
    }

    @EventTarget
    private void onUpdate(EventUpdate event) {
        if (event.isPre()) {
            this.targets = getTargets(range.getValue());

            targets.sort(this.angleComparator);

            if (this.targets.size() > 1) {

                if (target.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) > range.getValue()) {
                    ++index;
                } else if (target.isDead) {
                    SessionInfo.kills++;
                    ++index;
                }
            }

            if (target != null) {
                target = null;
            }

            if (!this.targets.isEmpty()) {
                if (this.index >= this.targets.size()) {
                    this.index = 0;
                }
                target = (EntityLivingBase) this.targets.get(this.index);
                if (this.shouldAttack()) {
                    EventManager.call(new EventAttack(true, target));
                    attackEntity(target);
                    EventManager.call(new EventAttack(false, target));
                    this.AttackTimer.reset();
                }
                Minecraft.getMinecraft().thePlayer.rotationYawHead = getRotation1(target)[0];
                Minecraft.getMinecraft().thePlayer.rotationPitchHead = getRotation1(target)[1];
                Minecraft.getMinecraft().thePlayer.renderYawOffset = getRotation1(target)[0];
                event.setYaw(getRotation1(target)[0]);
                event.setPitch(getRotation1(target)[1]);
            }
        }
    }

    private void attackEntity(Entity target) {
        if (!ModuleManager.getModuleByClass(Scaffold.class).isEnabled()) {
            Minecraft.getMinecraft().thePlayer.swingItem();
            Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        }
    }

    public static float[] getRotation1(EntityLivingBase entity) {
        EntityLivingBase entityLivingBase = entity;
        double diffX = entityLivingBase.posX - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = entityLivingBase.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY = entityLivingBase.posY + (double) entity.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.posY + (double) Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double X = diffX;
        double Z = diffZ;
        double dist = MathHelper.sqrt_double(X * X + Z * Z);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180 / 3.141592653589) - 90.0f;
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180 / 3.141592653589));
        return new float[]{yaw, pitch};
    }
}
