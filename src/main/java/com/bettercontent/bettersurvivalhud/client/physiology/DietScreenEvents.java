package com.bettercontent.bettersurvivalhud.client.physiology;

import com.illusivesoulworks.diet.client.screen.DietScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class DietScreenEvents {
    private DietScreenEvents() {}

    @SubscribeEvent
    public static void opening(ScreenEvent.Opening event) {
        if (event.getNewScreen() instanceof DietScreen && Minecraft.getInstance().player != null) {
            event.setNewScreen(new InventoryScreen(Minecraft.getInstance().player));
        }
    }
}
