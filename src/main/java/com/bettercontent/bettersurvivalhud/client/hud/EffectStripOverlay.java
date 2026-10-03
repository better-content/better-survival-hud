package com.bettercontent.bettersurvivalhud.client.hud;

import com.bettercontent.bettersurvivalhud.DynamicSurvivalHud;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Comparator;

/** A quiet gameplay-only view of the effects that vanilla places in the upper corner. */
@Mod.EventBusSubscriber(modid = DynamicSurvivalHud.MOD_ID, value = Dist.CLIENT)
public final class EffectStripOverlay {
    private static final int CELL_WIDTH = 25;
    private static final int CELL_HEIGHT = 30;

    private EffectStripOverlay() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void render(RenderGuiOverlayEvent.Pre event) {
        if (!event.getOverlay().id().equals(VanillaGuiOverlay.POTION_ICONS.id())) return;
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui || mc.screen != null) return;

        var effects = new ArrayList<>(mc.player.getActiveEffects());
        effects.removeIf(effect -> !effect.showIcon());
        if (effects.isEmpty()) return;
        effects.sort(Comparator.comparing((MobEffectInstance effect) -> effect.getEffect().isBeneficial())
            .thenComparing(effect -> effect.getEffect().getDisplayName().getString()));

        event.setCanceled(true);
        var graphics = event.getGuiGraphics();
        int width = event.getWindow().getGuiScaledWidth();
        int columns = Math.min(8, Math.max(1, (width - 16) / CELL_WIDTH));
        for (int i = 0; i < effects.size(); i++) {
            var effect = effects.get(i);
            int col = i % columns, row = i / columns;
            int rowCount = Math.min(columns, effects.size() - row * columns);
            int x = width - 8 - (rowCount - col) * CELL_WIDTH;
            int y = 6 + row * CELL_HEIGHT;
            int edge = effect.getEffect().isBeneficial() ? 0xff789b7a : 0xffad7770;
            graphics.fill(x, y, x + 23, y + 28, 0x96302221);
            graphics.fill(x, y, x + 23, y + 1, edge);
            graphics.blit(x + 3, y + 2, 0, 16, 16, mc.getMobEffectTextures().get(effect.getEffect()));
            if (effect.getAmplifier() > 0) {
                String level = Integer.toString(effect.getAmplifier() + 1);
                graphics.drawString(mc.font, level, x + 22 - mc.font.width(level), y + 1, 0xfff4e6c7, true);
            }
            String time = duration(effect);
            graphics.drawString(mc.font, time, x + (23 - mc.font.width(time)) / 2, y + 19, 0xffe4ddca, false);
        }
    }

    static String duration(MobEffectInstance effect) {
        if (effect.isInfiniteDuration()) return "∞";
        int seconds = Math.max(1, (effect.getDuration() + 19) / 20);
        if (seconds >= 3600) return Math.min(99, (seconds + 3599) / 3600) + "h";
        if (seconds >= 60) return Math.min(99, (seconds + 59) / 60) + "m";
        return seconds + "s";
    }
}
