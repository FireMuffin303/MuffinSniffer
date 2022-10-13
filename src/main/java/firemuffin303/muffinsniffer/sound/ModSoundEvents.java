package firemuffin303.muffinsniffer.sound;

import firemuffin303.muffinsniffer.MuffinSniffer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
    public static SoundEvent ENTITY_SNIFFER_AMBIENT;
    public static SoundEvent ENTITY_SNIFFER_HURT;
    public static SoundEvent ENTIY_SNIFFER_DEATH;
    public static SoundEvent ENTITY_SNIFFER_STEP;

    public static void init(){
        ENTITY_SNIFFER_AMBIENT = register("entity.sniffer.ambient");
        ENTITY_SNIFFER_HURT = register("entity.sniffer.hurt");
        ENTIY_SNIFFER_DEATH = register("entity.sniffer.death");
        ENTITY_SNIFFER_STEP = register("entity.sniffer.step");
    }

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(new Identifier(MuffinSniffer.MODID,id)));
    }
}
