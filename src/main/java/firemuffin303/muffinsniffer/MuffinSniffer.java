package firemuffin303.muffinsniffer;

import firemuffin303.muffinsniffer.block.ModBlocks;
import firemuffin303.muffinsniffer.entity.ModEntities;
import firemuffin303.muffinsniffer.item.ModItems;
import firemuffin303.muffinsniffer.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class MuffinSniffer implements ModInitializer {
    public static final String MODID = "muffinsniffer";
    public static final ItemGroup MOBVOTETAB = FabricItemGroupBuilder.build( new Identifier(MODID,"main"),() -> new ItemStack(ModItems.SNIFFER_EGG));


    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModItems.init();
        //ModItemTags.init();
        ModSoundEvents.init();
        ModEntities.init();

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(source.isBuiltin() && (LootTables.UNDERWATER_RUIN_BIG_CHEST.equals(id) || LootTables.UNDERWATER_RUIN_SMALL_CHEST.equals(id))){
                LootPool.Builder poolBuilder =
                        LootPool.builder().with(ItemEntry.builder(ModItems.SNIFFER_EGG).weight(3)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0,3))));

            tableBuilder.pool(poolBuilder);
            }
        }));
    }
}
