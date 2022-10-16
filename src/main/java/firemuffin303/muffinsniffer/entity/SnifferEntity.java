package firemuffin303.muffinsniffer.entity;

import com.google.common.collect.UnmodifiableIterator;
import firemuffin303.muffinsniffer.item.ModItems;
import firemuffin303.muffinsniffer.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SnifferEntity extends AnimalEntity implements Saddleable, ItemSteerable {
    private static final TrackedData<BlockPos> SEED_POS;
    private static final TrackedData<Boolean> HAS_SEED;
    private final SaddledComponent saddledComponent;
    private static final TrackedData<Boolean> SADDLED;
    private static final TrackedData<Integer> BOOST_TIME;
    private static final Ingredient BREEDING_INGREDIENT;
    private static final Ingredient SEED_INGREDIENT;
    int preSniffingCounter;
    int sniffingCounter;
    private static final TrackedData<Boolean> SNIFFING;

    protected SnifferEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(1, new SnifferEntity.SniffingGoal(this));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(new ItemConvertible[]{Items.CARROT_ON_A_STICK}), false));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.add(4, new WalkToDirt(this,1.15D));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224D);
    }

    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.ENTITY_SNIFFER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_SNIFFER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTIY_SNIFFER_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.saddledComponent.writeNbt(nbt);
        nbt.putBoolean("HasSeed", this.hasSeed());
        nbt.putBoolean("IsSniffing",this.isSniffing());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.saddledComponent.readNbt(nbt);
        this.setHasSeed(nbt.getBoolean("HasSeed"));
        this.setSniffing(nbt.getBoolean("IsSniffing"));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SADDLED, false);
        this.dataTracker.startTracking(BOOST_TIME, 0);
        this.dataTracker.startTracking(HAS_SEED,false);
        this.dataTracker.startTracking(SNIFFING,false);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (BOOST_TIME.equals(data) && this.world.isClient) {
            this.saddledComponent.boost();
        }

        super.onTrackedDataSet(data);
    }

    protected void dropInventory() {
        super.dropInventory();
        if (this.isSaddled()) {
            this.dropItem(Items.SADDLE);
        }

    }

    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.updatePassengerForDismount(passenger);
        } else {
            int[][] is = Dismounting.getDismountOffsets(direction);
            BlockPos blockPos = this.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            UnmodifiableIterator var6 = passenger.getPoses().iterator();

            while(var6.hasNext()) {
                EntityPose entityPose = (EntityPose)var6.next();
                Box box = passenger.getBoundingBox(entityPose);
                int[][] var9 = is;
                int var10 = is.length;

                for(int var11 = 0; var11 < var10; ++var11) {
                    int[] js = var9[var11];
                    mutable.set(blockPos.getX() + js[0], blockPos.getY(), blockPos.getZ() + js[1]);
                    double d = this.world.getDismountHeight(mutable);
                    if (Dismounting.canDismountInBlock(d)) {
                        Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                        if (Dismounting.canPlaceEntityAt(this.world, passenger, box.offset(vec3d))) {
                            passenger.setPose(entityPose);
                            return vec3d;
                        }
                    }
                }
            }

            return super.updatePassengerForDismount(passenger);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = this.isBreedingItem(player.getStackInHand(hand));
        if (!bl && this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
            if (!this.world.isClient) {
                player.startRiding(this);
            }

            return ActionResult.success(this.world.isClient);
        } else {
            ActionResult actionResult = super.interactMob(player, hand);
            if (!actionResult.isAccepted()) {
                ItemStack itemStack = player.getStackInHand(hand);
                if (itemStack.isOf(Items.SADDLE)){
                    return itemStack.useOnEntity(player,this,hand);
                }else if (SEED_INGREDIENT.test(itemStack) && preSniffingCounter < 1){
                    this.setHasSeed(true);
                    itemStack.decrement(1);
                    this.world.playSoundFromEntity(null,this,SoundEvents.ENTITY_HORSE_EAT,SoundCategory.NEUTRAL,0.5F,1.0F);
                    return ActionResult.SUCCESS;
                }
            } else {
                return actionResult;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public void setHasSeed(boolean hasSeed){
        this.dataTracker.set(HAS_SEED,hasSeed);
    }

    public boolean hasSeed(){
        return this.dataTracker.get(HAS_SEED);
    }

    public void setSniffing(boolean sniffing){
        this.sniffingCounter = sniffing ? 1:0;
        this.dataTracker.set(SNIFFING,sniffing);
    }

    public boolean isSniffing(){
       return this.dataTracker.get(SNIFFING);
    }

    public Entity getPrimaryPassenger() {
        Entity entity = this.getFirstPassenger();
        return entity != null && this.canBeControlledByRider(entity) ? entity : null;
    }

    private boolean canBeControlledByRider(Entity entity) {
        if (this.isSaddled() && entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)entity;
            return playerEntity.getMainHandStack().isOf(Items.CARROT_ON_A_STICK) || playerEntity.getOffHandStack().isOf(Items.CARROT_ON_A_STICK);
        } else {
            return false;
        }
    }

    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive() && this.isSniffing() && this.sniffingCounter >= 1 && this.sniffingCounter % 5 == 0) {
            BlockPos blockPos = this.getBlockPos();
            if (world.getBlockState(blockPos.down()).isIn(BlockTags.DIRT)) {
                this.world.syncWorldEvent(2001, blockPos, Block.getRawIdFromState(this.world.getBlockState(blockPos.down())));
            }
        }

        if(this.hasSeed()){
            ++preSniffingCounter;
        }else{
            preSniffingCounter = 0;
        }
    }

    public float getHeadAngle(float delta) {
        if (this.sniffingCounter > 4 && this.sniffingCounter <= 200) {
            float f = ((float)(this.sniffingCounter - 4) - delta) / 32.0F;
            return 1.62831855F + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.sniffingCounter > 0 ? 0.62831855F : this.getPitch() * 0.017453292F;
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SNIFFER.create(world);
    }

    @Override
    public boolean consumeOnAStickItem() {
        return this.saddledComponent.boost(this.getRandom());
    }

    public void travel(Vec3d movementInput) {
        this.travel(this, this.saddledComponent, movementInput);
    }

    @Override
    public void setMovementInput(Vec3d movementInput) {
        super.travel(movementInput);
    }

    @Override
    public float getSaddledSpeed() {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.225F;
    }

    @Override
    public boolean canBeSaddled() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void saddle(@Nullable SoundCategory sound) {
        this.saddledComponent.setSaddled(true);
        if (sound != null) {
            this.world.playSoundFromEntity((PlayerEntity)null, this, SoundEvents.ENTITY_PIG_SADDLE, sound, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSaddled() {
        return this.saddledComponent.isSaddled();
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.5F : 1.3F;
    }


    static {
        SADDLED = DataTracker.registerData(SnifferEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(SnifferEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BREEDING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{Items.WHEAT,Items.CARROT, Items.POTATO, Items.BEETROOT});
        SEED_POS = DataTracker.registerData(SnifferEntity.class,TrackedDataHandlerRegistry.BLOCK_POS);
        HAS_SEED = DataTracker.registerData(SnifferEntity.class,TrackedDataHandlerRegistry.BOOLEAN);
        SNIFFING = DataTracker.registerData(SnifferEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        SEED_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS,ModItems.ANCIENT_SEED});
    }

    static class SniffingGoal extends Goal{
        private final SnifferEntity snifferEntity;

        SniffingGoal(SnifferEntity snifferEntity) {
            this.snifferEntity = snifferEntity;
        }

        @Override
        public boolean canStart() {
            return this.snifferEntity.hasSeed() && this.snifferEntity.preSniffingCounter >= 100 && this.snifferEntity.world.getBlockState(this.snifferEntity.getBlockPos().down()).isIn(BlockTags.DIRT);
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.snifferEntity.hasSeed() && this.snifferEntity.preSniffingCounter >= 100 && this.snifferEntity.world.getBlockState(this.snifferEntity.getBlockPos().down()).isIn(BlockTags.DIRT);
        }

        @Override
        public void stop() {
            super.stop();
            this.snifferEntity.setHasSeed(false);
            this.snifferEntity.preSniffingCounter = 0;
        }

        @Override
        public void start() {
            super.start();
            this.snifferEntity.world.sendEntityStatus(this.snifferEntity, (byte)10);
        }

        @Override
        public void tick() {
            super.tick();
            this.snifferEntity.getNavigation().stop();
            BlockPos blockPos = this.snifferEntity.getBlockPos();
            if(this.snifferEntity.isOnGround() && this.snifferEntity.hasSeed() ){
                if (this.snifferEntity.sniffingCounter < 1){
                    this.snifferEntity.setSniffing(true);

                }else if(this.snifferEntity.sniffingCounter > this.getTickCount(200)){
                    World world = this.snifferEntity.world;
                    world.playSound(null,blockPos,SoundEvents.ENTITY_ITEM_PICKUP,SoundCategory.BLOCKS,0.3F,1.0F);
                    this.snifferEntity.dropItem(ModItems.ANCIENT_SEED,1);
                    this.snifferEntity.setSniffing(false);
                    this.snifferEntity.setHasSeed(false);
                    //this.snifferEntity.setSeedPos(BlockPos.ORIGIN);
                }

                if(this.snifferEntity.isSniffing()){
                    ++this.snifferEntity.sniffingCounter;
                }
            }
        }

    }

    static class WalkToDirt extends Goal{
        private SnifferEntity snifferEntity;
        protected double targetX;
        protected double targetY;
        protected double targetZ;
        private double speed;
        public WalkToDirt(SnifferEntity snifferEntity,Double speed){
            this.snifferEntity = snifferEntity;
            this.speed = speed;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if(this.snifferEntity.preSniffingCounter > 0){
                BlockPos blockPos = this.locateClosestDirt(16);
                if(blockPos != null){
                    this.targetX = blockPos.getX();
                    this.targetY = blockPos.getY();
                    this.targetZ = blockPos.getZ();
                    return true;
                }
            }
             return false;
        }

        @Override
        public void start() {
            this.snifferEntity.getNavigation().startMovingTo(this.targetX,this.targetY,this.targetZ,this.speed);
        }

        protected BlockPos locateClosestDirt(int rangeX){
            BlockView world = this.snifferEntity.world;
            BlockPos blockPos = this.snifferEntity.getBlockPos();
            return !world.getBlockState(blockPos).getCollisionShape(world,blockPos).isEmpty() ? null : BlockPos.findClosest(this.snifferEntity.getBlockPos(),rangeX,1,(pos) -> {
                return world.getBlockState(pos).isIn(BlockTags.DIRT);
            }).orElse(null);
        }
    }
}
