package bu420.colossal.entity;

import bu420.colossal.entity.ai.SerpentRandomSwimGoal;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SeaSerpent extends WaterSerpent<SeaSerpent> {
    public SeaSerpent(EntityType<? extends SeaSerpent> type, Level level) {
        super(type, level);

        parts.add(new SerpentPart<>(this, 1, 0.625F, "head"));
        for (int i = 0; i < getLength() - 2; i++) {
            parts.add(new SerpentPart<>(this, 0.5F, 0.625F, "body"));
        }
        parts.add(new SerpentPart<>(this, 1, 0.625F, "tail"));
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
    public int getLength() {
        return 24;
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
