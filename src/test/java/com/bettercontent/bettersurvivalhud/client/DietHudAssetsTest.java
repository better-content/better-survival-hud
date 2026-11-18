package com.bettercontent.bettersurvivalhud.client;

import com.bettercontent.bettersurvivalphysiology.presentation.AspectIdentity;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import static org.junit.jupiter.api.Assertions.*;

class DietHudAssetsTest {
    @Test void dietUsesTheFullGuideScreen() throws IOException {
        Path clientRoot = Path.of("../better-survival-physiology/src/main/java/com/bettercontent/bettersurvivalphysiology/client");
        String opening = Files.readString(Path.of("src/main/java/com/bettercontent/bettersurvivalhud/client/physiology/DietScreenEvents.java"));
        assertFalse(Files.exists(clientRoot.resolve("SystemicDietScreen.java")), "Journal is the sole nutrition screen");
        assertTrue(opening.contains("ScreenEvent.Opening"));
        assertTrue(opening.contains("setNewScreen"));
        assertFalse(Files.exists(clientRoot.resolve("DietBenefitsOverlay.java")));
    }

    @Test void legacyReadinessStripKeepsEightPhysicalCellsWhileNativeGroupsAreFive() throws IOException {
        assertEquals(5, AspectIdentity.values().length);
        try (var stream = getClass().getResourceAsStream("/assets/better_survival_physiology/textures/gui/nutrition_states.png")) {
            assertNotNull(stream);
            var image = ImageIO.read(stream);
            assertNotNull(image);
            assertEquals(18, image.getHeight());
            assertEquals(image.getHeight() * 8, image.getWidth());
        }
    }
}
