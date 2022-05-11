package bu420.colossal.entity;

import bu420.colossal.Main;
import bu420.colossal.block.entity.TimedLightBlockEntity;
import bu420.colossal.entity.ai.SerpentRandomSwimGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NightTerror extends WaterSerpent<NightTerror> {
    private final List<Pair<BlockPos, MutableInt>> lights;

    private static final int LIGHT_LIFETIME = 20;

    public NightTerror(EntityType<? extends NightTerror> type, Level level) {
        super(type, level);

        parts.add(new SerpentPart<>(this, 1.125F, 1.125F, "head"));
        for (int i = 0; i < getLength() - 2; i++) {
            parts.add(new SerpentPart<>(this, 1.125F, 1.125F, "body"));
        }
        parts.add(new SerpentPart<>(this, 1.125F, 1.125F, "tail"));

        lights = new ArrayList<>();
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new SerpentRandomSwimGoal<>(this));
        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.FOLLOW_RANGE, 40)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.MOVEMENT_SPEED, 1.2);
    }

    @Override
    public void tick() {
        super.tick();

        if (!Main.isDay()) {
            for (var part : parts) {
                if (random.nextInt(0, 5) == 0) {
                    //placeLight(part.blockPosition());
                    //level.setBlockEntity(new TimedLightBlockEntity(new BlockPos(position())));
                }
            }
        }

        //updateLights();
    }

    /*private void placeLight(BlockPos pos) {
        if (level.getBlockState(pos).is(Blocks.WATER)) {
            level.setBlockAndUpdate(pos, Blocks.LIGHT.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(new BlockPos(position())).is(FluidTags.WATER)));
            lights.add(Pair.of(pos, new MutableInt(LIGHT_LIFETIME)));
        }
        else if (level.getBlockState(pos).is(Blocks.LIGHT)) {
            for (var light : lights) {
                if (light.getLeft().equals(pos)) {
                    light.getRight().setValue(LIGHT_LIFETIME);
                    break;
                }
            }
        }
    }

    private void updateLights() {
        for (var light : lights) {
            if (light.getRight().decrementAndGet() <= 0) {
                removeLight(light.getLeft());
            }
        }

        lights.removeIf((light) -> light.getRight().getValue() <= 0);
    }

    private void removeLight(BlockPos pos) {
        System.out.println("try remove");
        if (level.getBlockState(pos).is(Blocks.LIGHT)) {
            System.out.println("removed");
            level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
        }
    }

    public void removeAllLights() {
        for (var light : lights) {
            removeLight(light.getLeft());
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        int[] array = new int[lights.size() * 3];

        for (int i = 0; i < lights.size(); i++) {
            var pos = lights.get(i).getLeft();

            array[i * 3 + 0] = pos.getX();
            array[i * 3 + 1] = pos.getY();
            array[i * 3 + 2] = pos.getZ();
        }

        tag.putIntArray("Lights", array);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        int[] array = tag.getIntArray("Lights");

        for (int i = 0; i < array.length / 3; i++) {
            removeLight(new BlockPos(array[i * 3 + 0], array[i * 3 + 1], array[i * 3 + 2]));
        }
    }*/

    @Override
    public int getLength() {
        return 20;
    }

    @Override
    public float getPartDamageModifier() {
        return 2 / 3.0F;
    }

    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CONDUIT_AMBIENT_SHORT;
    }

    @Override
    protected SoundEvent getHurtSound(@Nullable DamageSource p_21239_) {
        return SoundEvents.ELDER_GUARDIAN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ELDER_GUARDIAN_DEATH;
    }

    @Override
    protected @NotNull SoundEvent getSwimSound() {
        return SoundEvents.GENERIC_SWIM;
    }
}
