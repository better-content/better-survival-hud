package com.bettercontent.bettersurvivalhud.diagnostics;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/** Native lifecycle bridge only; the bounded sink retains no player, menu or world references. */
@Mod.EventBusSubscriber(modid = "better_survival_hud")
public final class PlaytestDiagnostics {
    public static final PlaytestLog LOG = new PlaytestLog("better_survival_hud");
    private PlaytestDiagnostics() {}
    private static String version(String id) {
        return ModList.get().getModContainerById(id)
                .map(mod -> mod.getModInfo().getVersion().toString()).orElse("absent");
    }

    private static String source() {
        var file = ModList.get().getModFileById("better_survival_hud");
        return LOG.sourceIdentity(file == null || file.getFile() == null ? (java.nio.file.Path) null
                : file.getFile().findResource("META-INF", "better-content-source.properties"));
    }
    @Mod.EventBusSubscriber(modid = "better_survival_hud", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Startup {
        @SubscribeEvent public static void setup(FMLCommonSetupEvent event) {
            LOG.status("common", version("better_survival_hud"), source(), () -> PlaytestLog.fields("enabled", net.minecraftforge.fml.loading.FMLEnvironment.dist == Dist.CLIENT ? com.bettercontent.bettersurvivalhud.config.DynamicSurvivalHudConfig.dynamicHudEnabled() : "not_applicable", "thirst_version", version("thirst"), "cold_sweat_version", version("cold_sweat")));
        }
    }
    @Mod.EventBusSubscriber(modid = "better_survival_hud", value = Dist.CLIENT)
    public static final class Client {
        @SubscribeEvent public static void tick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END && LOG.enabled("summary")) LOG.summary("client");
        }
        @SubscribeEvent public static void logout(ClientPlayerNetworkEvent.LoggingOut event) { LOG.reset("client"); }
    }
}
