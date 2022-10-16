package firemuffin303.muffinsniffer.block;

import firemuffin303.muffinsniffer.MuffinSniffer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block SNIFFER_EGG = new SnifferEggBlock(AbstractBlock.Settings.of(Material.EGG, MapColor.ORANGE).strength(0.5F).sounds(BlockSoundGroup.METAL).ticksRandomly().nonOpaque());
    public static final Block ANCIENT_GRASS = new AncientGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XYZ));
    public static final Block TALL_ANCIENT_GRASS = new TallAncientGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XYZ));

    public static void init(){
        Registry.register(Registry.BLOCK, new Identifier(MuffinSniffer.MODID,"sniffer_egg"),SNIFFER_EGG);
        Registry.register(Registry.BLOCK, new Identifier(MuffinSniffer.MODID,"ancient_grass"),ANCIENT_GRASS);
        addBlock("tall_ancient_grass",TALL_ANCIENT_GRASS);
    }

    private static void addBlock(String id,Block block){
        Registry.register(Registry.BLOCK, new Identifier(MuffinSniffer.MODID,id),block);
        Registry.register(Registry.ITEM, new Identifier(MuffinSniffer.MODID,id),new BlockItem(block,new FabricItemSettings().group(MuffinSniffer.MOBVOTETAB)));
    }
}
