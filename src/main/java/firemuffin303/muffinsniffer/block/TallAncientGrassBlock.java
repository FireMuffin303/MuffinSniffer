package firemuffin303.muffinsniffer.block;

import firemuffin303.muffinsniffer.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TallAncientGrassBlock extends TallPlantBlock implements Fertilizable {
    public TallAncientGrassBlock(Settings settings) {
        super(settings);
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        dropStack(world, pos, new ItemStack(ModItems.ANCIENT_SEED));
    }
}
