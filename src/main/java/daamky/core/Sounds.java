package daamky.core;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;


public final class Sounds {
    public static final SoundEvent ENABLE = SoundEvent.of(Identifier.of("daamky", "module.enable"));
    public static final SoundEvent DISABLE = SoundEvent.of(Identifier.of("daamky", "module.disable"));
    public static final SoundEvent NOTIFICATION = SoundEvent.of(Identifier.of("daamky", "notification"));
    public static final SoundEvent HIT_BONK = SoundEvent.of(Identifier.of("daamky", "hit.bonk"));
    public static final SoundEvent HIT_MARKER = SoundEvent.of(Identifier.of("daamky", "hit.marker"));
    public static final SoundEvent HIT_UWU = SoundEvent.of(Identifier.of("daamky", "hit.uwu"));

    private Sounds() {}

    public static void play(SoundEvent e, float volume) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(e, 1.0f, volume));
    }
}
