package bu420.colossal.entity;

import bu420.colossal.Main;
import bu420.colossal.entity.ai.SerpentRandomSwimGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
                    placeLight(part.blockPosition());
                }
            }
        }
    }

    private void placeLight(BlockPos pos) {
        if (level.getBlockState(pos).is(Blocks.WATER)) {
            level.setBlockAndUpdate(pos, Main.TIMED_LIGHT_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true));
        }
    }

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
