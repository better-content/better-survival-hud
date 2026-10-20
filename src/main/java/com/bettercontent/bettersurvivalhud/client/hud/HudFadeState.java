package com.bettercontent.bettersurvivalhud.client.hud;

import com.bettercontent.bettersurvivalhud.diagnostics.PlaytestDiagnostics;
import com.bettercontent.bettersurvivalhud.diagnostics.PlaytestLog;

public final class HudFadeState {
    private int ageTicks;
    private boolean dangerous;
    private final String element;
    private final PlaytestLog diagnostics;

    public HudFadeState() { this("unknown", PlaytestDiagnostics.LOG); }
    HudFadeState(String element, PlaytestLog diagnostics) { this.element = element; this.diagnostics = diagnostics; }

    public void tick() {
        if (!dangerous && ageTicks < Integer.MAX_VALUE) {
            ageTicks++;
        }
    }

    public void reveal() {
        ageTicks = 0;
    }

    public void hide() {
        dangerous = false;
        ageTicks = Integer.MAX_VALUE;
    }

    public void setDangerous(final boolean dangerous) {
        boolean changed = this.dangerous != dangerous;
        if (this.dangerous && !dangerous) {
            reveal();
        }
        this.dangerous = dangerous;
        if (changed && diagnostics.enabled("visibility")) {
            diagnostics.event("visibility", "hud.danger", "client", dangerous ? "entered" : "left", "semantic_threshold",
                    () -> PlaytestLog.fields("element", element));
        }
    }

    public boolean isDangerous() {
        return dangerous;
    }

    public float alpha(final float partialTick, final int holdTicks, final int fadeTicks, final boolean peeking) {
        if (dangerous || peeking) {
            return 1.0F;
        }
        if (ageTicks < holdTicks) {
            return 1.0F;
        }
        if (fadeTicks <= 0) {
            return 0.0F;
        }
        final float fadeAge = ageTicks - holdTicks + clamp(partialTick, 0.0F, 1.0F);
        return 1.0F - clamp(fadeAge / fadeTicks, 0.0F, 1.0F);
    }

    int ageTicks() {
        return ageTicks;
    }

    private static float clamp(final float value, final float minimum, final float maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }
}
