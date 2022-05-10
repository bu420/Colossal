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
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NightTerror extends WaterAnimal implements Serpent<NightTerror> {
    private final List<SerpentPart<NightTerror>> parts;

    public NightTerror(EntityType<? extends NightTerror> type, Level level) {
        super(type, level);

        noCulling = true;

        moveControl = new SmoothSwimmingMoveControl(this, 20, 10, 0.02F, 0.1F, true);
        lookControl = new SmoothSwimmingLookControl(this, 10);

        parts = new ArrayList<>();
        parts.add(new SerpentPart<>(this, 1.125F, 1.125F, "head"));
        for (int i = 0; i < getLength() - 2; i++) {
            parts.add(new SerpentPart<>(this, 1.125F, 1.125F, "body"));
        }
        parts.add(new SerpentPart<>(this, 1.125F, 1.125F, "tail"));
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

        for (int i = 0; i < parts.size(); i++) {
            parts.get(i).tick(i == 0 ? null : parts.get(i - 1));
        }
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

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public @NotNull PartEntity<?>[] getParts() {
        return parts.toArray(PartEntity<?>[]::new);
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
    public List<SerpentPart<NightTerror>> getPartEntities() {
        return parts;
    }

    @Override
    public SerpentPart<NightTerror> getHead() {
        return parts.get(0);
    }

    @Override
    public List<SerpentPart<NightTerror>> getBodies() {
        return parts.subList(1, parts.size() - 1);
    }

    @Override
    public SerpentPart<NightTerror> getTail() {
        return parts.get(parts.size() - 1);
    }
}
