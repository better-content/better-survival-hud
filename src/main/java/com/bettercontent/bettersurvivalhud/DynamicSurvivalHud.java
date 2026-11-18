package com.bettercontent.bettersurvivalhud;

import com.bettercontent.bettersurvivalhud.config.DynamicSurvivalHudConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(DynamicSurvivalHud.MOD_ID)
public final class DynamicSurvivalHud {
    public static final String MOD_ID = "better_survival_hud";

    public DynamicSurvivalHud() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DynamicSurvivalHudConfig.SPEC);
        // Neither dedicated servers nor standalone HUD installations load Physiology UI.
        if (net.minecraftforge.fml.ModList.get().isLoaded("better_survival_physiology")
                && net.minecraftforge.fml.loading.FMLEnvironment.dist.isClient()) ClientPhysiology.install();
    }

    private static final class ClientPhysiology {
        private static void install() {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(
                    com.bettercontent.bettersurvivalhud.client.physiology.ClientSalienceEvents.class);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(
                    com.bettercontent.bettersurvivalhud.client.physiology.DietScreenEvents.class);
        }
    }
}
