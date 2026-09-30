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
    }
}
