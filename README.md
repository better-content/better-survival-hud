# Better Survival HUD

## Scope and authority

This repository owns its mod-specific behavior and authoring inputs. Read [local instructions](AGENTS.md)
and the [shared documentation/policy index](../../better-content-modpack/docs/README.md).


Forge 1.20.1 client mod that fades inactive survival HUD elements while retaining danger and interaction cues.

## Bounded playtest diagnostics

`bc.playtest.better_survival_hud.*` uses loader SLF4J: one INFO startup status with effective
client enabled state/provider versions and owner source metadata (or UNKNOWN), DEBUG danger
edges, physical-peek/config/player-context and semantic Death's Door transitions. Native ticks,
render/alpha queries, changing raw values and unchanged danger states do not produce detail.
No health authority, item data or player references are retained by diagnostics. One shared
120/minute burst20 bucket, 256 aggregation keys, 2048-character record limit and 30-second
summary window bound output; disconnect clears aggregate state without replenishing allowance.
Normal standalone remains INFO. These records are not native framebuffer acceptance.

## Verification

Run `./gradlew verifyFull stageRuntimeJar` before committing or pushing. `verifyFull`
runs the deterministic JVM/resource checks and validates the runtime JAR's production
mixin refmap. `verifyFast` runs just the deterministic checks.

This client mod has no server GameTests. Starting the optional `headlessGameTest`
server therefore provides no behavioral coverage and is not part of `verifyFull`.
HUD fading, danger/interaction cues, layout, and compatibility rendering require
manual review in a client; automated verification does not claim to check them.

Death’s Door supplies semantic health and actual injury risk through `DynamicSurvivalHudClientApi.setInjuryHealth`. Semantic zero keeps health visible during Death’s Door; changed injury risk reveals the health bar after treatment or maiming. Companion skull marks use `healthAlpha` to follow the same fading above zero. These semantic values control visibility and danger cues; they do not replace the health values consumed by Forge or Mantle when drawing hearts. Death’s Door owns the renderer compatibility that hides its engine-only survival sentinel. Injury authority and probabilities remain in Revival.
