# Daamky 1.0 RELEASE — Interface Stage 1

## Scope

This stage is deliberately limited to the client identity, ClickGUI and HUD. Existing gameplay modules were not expanded or redesigned in this pass.

## Interface modes

- **Daamky Interface (default, mode 2):** wider blue/black ClickGUI, single-column module browser, docked settings inspector and a separate Daamky HUD layout.
- **Celestial Legacy (mode 1):** the previous Celestial-style ClickGUI and HUD are preserved as separate implementations.

Both modes use the same module registry and settings. Right Shift opens whichever interface is currently selected.

## Local chat command

- `.itest set 1` — enable Celestial Legacy ClickGUI + HUD.
- `.itest set 2` — enable Daamky ClickGUI + HUD.
- `.itest` — show command usage.

The command is handled on the client and is not sent to the multiplayer server. The selected mode is saved in `config/daamky/interface.txt`. A fresh install defaults to mode 2.

## Identity changes

- Java package and Fabric mod ID changed from `msdfgui` to `daamky`.
- Resource namespace and generated font namespace changed to `daamky`.
- Default SDF gradient is now blue; Celestial Legacy restores the original purple gradient.
- Fabric metadata and keybinding translation names now identify Daamky.

## Target/build status

The source configuration remains on Minecraft **1.21.11**. The Loom plugin is pinned to stable **1.14.10** and the GitHub Actions workflow uses Gradle **9.2.1** with Java 21. Static checks passed for JSON, Java package paths, entrypoint and mixin references, and stale namespace references.

A full Gradle build and in-game test could not be run in this environment: there is no Gradle executable or cached Fabric/Minecraft dependency set, and external dependency downloads are unavailable. The included `.github/workflows/build.yml` builds the project on GitHub Actions and uploads the JAR as an artifact. Therefore the output is a source-stage patch, not a verified release JAR.
