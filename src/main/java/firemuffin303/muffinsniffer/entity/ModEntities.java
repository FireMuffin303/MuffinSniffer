package firemuffin303.muffinsniffer.entity;

import firemuffin303.muffinsniffer.MuffinSniffer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
    public static final EntityType<SnifferEntity> SNIFFER;

    public static void init(){
        FabricDefaultAttributeRegistry.register(SNIFFER,SnifferEntity.createAttributes());
    }

    static {
        SNIFFER = Registry.register(Registry.ENTITY_TYPE,new Identifier(MuffinSniffer.MODID,"sniffer"),FabricEntityTypeBuilder.create(SpawnGroup.CREATURE,SnifferEntity::new).dimensions(EntityDimensions.changing(1.5F,1.8F)).build());
    }
}
