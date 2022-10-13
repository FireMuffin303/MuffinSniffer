package firemuffin303.muffinsniffer.block;

import firemuffin303.muffinsniffer.entity.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SnifferEggBlock extends Block {
    public static final IntProperty HATCH;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public EntityType entityType;



    public SnifferEggBlock(Settings settings) {
        super(settings);
        entityType =  ModEntities.SNIFFER;

    }

    public SnifferEggBlock(Settings settings,EntityType entityType) {
        super(settings);
        this.entityType = entityType;

    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public static boolean isSandBelow(BlockView world, BlockPos pos) {
        return isSand(world, pos.down());
    }

    public static boolean isSand(BlockView world, BlockPos pos) {
        return world.getBlockState(pos).isIn(BlockTags.SAND);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldHatchProgress(world) && isSandBelow(world, pos)) {
            int i = (Integer)state.get(HATCH);
            if (i < 2) {
                world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.setBlockState(pos, (BlockState)state.with(HATCH, i + 1), 2);
            } else {
                world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.removeBlock(pos, false);


                world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
                AnimalEntity entity = (AnimalEntity) entityType.create(world);
                entity.setBreedingAge(-24000);
                entity.refreshPositionAndAngles((double)pos.getX() + 0.3D, (double)pos.getY(), (double)pos.getZ() + 0.3D, 0.0F, 0.0F);
                world.spawnEntity(entity);
            }
        }
    }


    private boolean shouldHatchProgress(World world) {
        float f = world.getSkyAngle(1.0F);
        if ((double)f < 0.69D && (double)f > 0.65D) {
            return true;
        } else {
            return world.random.nextInt(500) == 0;
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    static{
        HATCH = Properties.HATCH;
    }
}
