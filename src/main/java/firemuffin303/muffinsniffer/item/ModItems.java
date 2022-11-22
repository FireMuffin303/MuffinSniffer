package firemuffin303.muffinsniffer.item;

import firemuffin303.muffinsniffer.MuffinSniffer;
import firemuffin303.muffinsniffer.block.ModBlocks;
import firemuffin303.muffinsniffer.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item SNIFFER_EGG = new BlockItem(ModBlocks.SNIFFER_EGG,new FabricItemSettings().group(MuffinSniffer.MOBVOTETAB));
    public static final Item SNIFFER_SPAWN_EGG = new SpawnEggItem(ModEntities.SNIFFER,14699797,2935395,new FabricItemSettings().group(MuffinSniffer.MOBVOTETAB));
    public static final Item ANCIENT_SEED = new AliasedBlockItem(ModBlocks.ANCIENT_GRASS,new FabricItemSettings().group(MuffinSniffer.MOBVOTETAB));
    public static Item TALL_ANCIENT_GRASS;
    //public static final Item ANCIENT_CACTUS_SEED = new AliasedBlockItem();
    //public static final Item SCULK_SNIFFER_SPAWN_EGG = new SpawnEggItem(ModEntities.SCULK_SNIFFER,658224,4704479,new FabricItemSettings().group(Mobvote22.MOBVOTETAB));

    public static void init(){
        register("sniffer_egg",SNIFFER_EGG);
        register("ancient_grass_seed",ANCIENT_SEED);
        register("sniffer_spawn_egg",SNIFFER_SPAWN_EGG);
        TALL_ANCIENT_GRASS = register("tall_ancient_grass",ModBlocks.TALL_ANCIENT_GRASS);

    }

    private static void register(String id,Item item){
        Registry.register(Registry.ITEM,new Identifier(MuffinSniffer.MODID,id),item);
    }

    private static Item register(String id, Block block){
        return Registry.register(Registry.ITEM, new Identifier(MuffinSniffer.MODID,id),new BlockItem(block,new FabricItemSettings().group(MuffinSniffer.MOBVOTETAB)));
    }
}
