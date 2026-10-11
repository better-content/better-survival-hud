package com.bettercontent.bettersurvivalhud.client.hud;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DynamicHudHealthModelTest {
    @Test void deathDoorSuppliesZeroSemanticHealthForVisibility() {
        assertEquals(0, DynamicHudController.displayHealth(true, 0.0001f));
        assertEquals(0, DynamicHudController.displayHealth(true, 8));
    }

    @Test void resumedHealthIsPreservedAndNegativeInputsAreClamped() {
        assertEquals(7.5f, DynamicHudController.displayHealth(false, 7.5f));
        assertEquals(0, DynamicHudController.displayHealth(false, -2));
    }
}
