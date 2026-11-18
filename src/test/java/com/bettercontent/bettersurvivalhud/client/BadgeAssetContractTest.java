package com.bettercontent.bettersurvivalhud.client;

import com.bettercontent.bettersurvivalphysiology.presentation.AspectIdentity;
import org.junit.jupiter.api.Test;
import javax.imageio.ImageIO;
import java.security.MessageDigest;
import java.util.HexFormat;
import static org.junit.jupiter.api.Assertions.*;

final class BadgeAssetContractTest {
    @Test void canonicalBadgeAndFontCoverEightIdentities() throws Exception {
        var resource = getClass().getResourceAsStream("/assets/better_survival_physiology/textures/gui/aspect_badges.png");
        assertNotNull(resource);
        byte[] bytes = resource.readAllBytes();
        assertEquals("523fb4bbecc016df70c149eb0d49bd3a50e8d32b1a837c011670b83247825af4",
                HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes)));
        var image = ImageIO.read(new java.io.ByteArrayInputStream(bytes));
        assertEquals(144, image.getWidth()); assertEquals(18, image.getHeight());
        BadgeResourcePureTest.run(path -> getClass().getResourceAsStream(path));
        for (AspectIdentity aspect : AspectIdentity.values())
            assertFalse(aspect.badge().chars().anyMatch(Character::isWhitespace));
    }
}
