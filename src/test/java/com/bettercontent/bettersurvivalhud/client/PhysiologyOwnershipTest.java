package com.bettercontent.bettersurvivalhud.client;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class PhysiologyOwnershipTest {
    @Test void onlyHudRegistersNutritionClientSubscribers() throws Exception {
        var root = Path.of("src/main/java/com/bettercontent/bettersurvivalhud");
        String startup = Files.readString(root.resolve("DynamicSurvivalHud.java"));
        String render = Files.readString(root.resolve("client/physiology/ClientSalienceEvents.java"));
        String route = Files.readString(root.resolve("client/physiology/DietScreenEvents.java"));
        assertTrue(startup.contains("ModList.get().isLoaded(\"better_survival_physiology\")"));
        assertTrue(startup.contains("FMLEnvironment.dist.isClient()"));
        assertTrue(startup.contains("EVENT_BUS.register(\n                    com.bettercontent.bettersurvivalhud.client.physiology.ClientSalienceEvents.class)"));
        assertTrue(render.contains("NutritionHud.entries(state)"));
        assertTrue(render.contains("FoodNutrients.entries(gains"));
        assertTrue(route.contains("ScreenEvent.Opening"));
        assertTrue(route.contains("new InventoryScreen("));
        assertFalse(Files.exists(Path.of("../better-survival-physiology/src/main/java/com/bettercontent/bettersurvivalphysiology/client/ClientSalienceEvents.java")));
        assertFalse(Files.exists(Path.of("../better-survival-physiology/src/main/java/com/bettercontent/bettersurvivalphysiology/client/DietScreenEvents.java")));
    }
}
