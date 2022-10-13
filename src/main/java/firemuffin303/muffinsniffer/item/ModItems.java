package firemuffin303.muffinsniffer.item;

import firemuffin303.muffinsniffer.MuffinSniffer;
import firemuffin303.muffinsniffer.block.ModBlocks;
import firemuffin303.muffinsniffer.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item SNIFFER_SPAWN_EGG = new SpawnEggItem(ModEntities.SNIFFER,14699797,2935395,new FabricItemSettings().group(MuffinSniffer.MOBVOTETAB));
    //public static final Item SCULK_SNIFFER_SPAWN_EGG = new SpawnEggItem(ModEntities.SCULK_SNIFFER,658224,4704479,new FabricItemSettings().group(Mobvote22.MOBVOTETAB));
    public static final Item ANCIENT_SEED = new AliasedBlockItem(ModBlocks.ANCIENT_GRASS,new FabricItemSettings().group(MuffinSniffer.MOBVOTETAB));
    public static final Item SNIFFER_EGG = new BlockItem(ModBlocks.SNIFFER_EGG,new FabricItemSettings().group(MuffinSniffer.MOBVOTETAB));

    public static void init(){
        register("ancient_grass_seed",ANCIENT_SEED);
        register("sniffer_egg",SNIFFER_EGG);
        register("sniffer_spawn_egg",SNIFFER_SPAWN_EGG);
        //register("sculk_sniffer_spawn_egg",SCULK_SNIFFER_SPAWN_EGG);
    }

    private static void register(String id,Item item){
        Registry.register(Registry.ITEM,new Identifier(MuffinSniffer.MODID,id),item);
    }
}
