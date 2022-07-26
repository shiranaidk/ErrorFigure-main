/*
 * Decompiled with CFR 0_132.
 */
package errorfigure.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;
import errorfigure.api.events.world.EventUpdate;
import errorfigure.api.value.Mode;
import errorfigure.api.value.Numbers;
import errorfigure.api.value.Option;
import errorfigure.module.Module;
import errorfigure.module.ModuleType;
import errorfigure.utils.TimerUtils;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.awt.*;

public class AutoHeal
extends Module {
    private Numbers<Double> health = new Numbers<Double>("Health", "health", 3.0, 0.0, 10.0, 0.5);
    private Numbers<Double> delay = new Numbers<Double>("Delay", "delay", 400.0, 0.0, 1000.0, 10.0);
    private Option<Boolean> jump = new Option<Boolean>("Jump", "jump", true);
    private Mode<Enum> mode = new Mode("Mode", "mode", (Enum[])HealMode.values(), (Enum)HealMode.Potion);
    static boolean currentlyPotting = false;
    private boolean isUsing;
    private int slot;
    private TimerUtils timer = new TimerUtils();

    public AutoHeal() {
        super("AutoHeal", ModuleType.Combat);
        this.setColor(new Color(76, 249, 247).getRGB());
        this.addValues(this.health, this.delay, this.jump, this.mode);
    }

    @EventTarget
    private void onUpdate(EventUpdate e) {
        if (this.timer.hasReached(this.delay.getValue()) && (double)this.mc.thePlayer.getHealth() <= this.health.getValue() * 2.0) {
            this.slot = this.mode.getValue() == HealMode.Potion ? this.getPotionSlot() : (this.mode.getValue() == HealMode.Soup ? this.getSoupSlot() : this.getPotionSlot());
            boolean bl = this.isUsing = this.slot != -1 && (this.jump.getValue() == false || this.mc.thePlayer.onGround);
            if (this.isUsing) {
                this.timer.reset();
                if (this.mode.getValue() == HealMode.Potion) {
                    currentlyPotting = true;
                    e.setPitch(this.jump.getValue() != false ? -90 : 90);
                    if (this.timer.hasReached(1.0)) {
                        currentlyPotting = false;
                        this.timer.reset();
                    }
                }
            }
        }
    }

    @EventTarget
    private void onUpdatePost(EventUpdate e) {
        if (this.isUsing) {
            int current = this.mc.thePlayer.inventory.currentItem;
            int next = this.mc.thePlayer.nextSlot();
            this.mc.thePlayer.moveToHotbar(this.slot, next);
            this.mc.thePlayer.inventory.currentItem = next;
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem());
            this.mc.thePlayer.inventory.currentItem = current;
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            this.isUsing = false;
            if (this.mc.thePlayer.onGround && this.jump.getValue().booleanValue() && this.mode.getValue() == HealMode.Potion) {
                this.mc.thePlayer.setSpeed(0.0);
                this.mc.thePlayer.motionY = 0.42;
            }
        }
    }

    private int getPotionSlot() {
        int slot = -1;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemPotion)) continue;
            ItemPotion ip = (ItemPotion)is.getItem();
            if (!ItemPotion.isSplash(is.getMetadata())) continue;
            boolean hasHealing = false;
            for (PotionEffect pe : ip.getEffects(is)) {
                if (pe.getPotionID() != Potion.heal.id) continue;
                hasHealing = true;
                break;
            }
            if (!hasHealing) continue;
            slot = s.slotNumber;
            break;
        }
        return slot;
    }

    private int getSoupSlot() {
        int slot = -1;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemSoup)) continue;
            slot = s.slotNumber;
            break;
        }
        return slot;
    }

    private int getPotionCount() {
        int count = 0;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemPotion)) continue;
            ItemPotion ip = (ItemPotion)is.getItem();
            if (!ItemPotion.isSplash(is.getMetadata())) continue;
            boolean hasHealing = false;
            for (PotionEffect pe : ip.getEffects(is)) {
                if (pe.getPotionID() != Potion.heal.id) continue;
                hasHealing = true;
                break;
            }
            if (!hasHealing) continue;
            ++count;
        }
        return count;
    }

    private int getSoupCount() {
        int count = 0;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemSoup)) continue;
            ++count;
        }
        return count;
    }

    static enum HealMode {
        Potion,
        Soup;
    }

}

