package bu420.colossal.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SerpentPart<T extends Entity> extends PartEntity<T> {
    protected final float base;
    protected final float height;
    protected final String name;

    private Vec3 prevTickPos = Vec3.ZERO;

    public SerpentPart(T parent, float base, float height, String name) {
        super(parent);
        copyPosition(parent);

        this.base = base;
        this.height = height;
        refreshDimensions();

        this.name = name;
    }

    public void tick(@Nullable SerpentPart<T> leader) {
        if (leader == null) {
            setDeltaMovement(getParent().position().subtract(position()));
            copyPosition(getParent());
        }
        else {
            Vec3 diff = leader.position().subtract(position());
            float max = (leader.base + base) / 2;

            if (diff.length() > max) {
                double amount = diff.length() - max;
                Vec3 movement = diff.normalize().multiply(amount, amount, amount);
                setDeltaMovement(movement);
                moveTo(position().add(movement));
            }
        }

        prevTickPos = position();
    }

    public String getModelName() {
        return name;
    }

    public Vec3 getPrevTickPos() {
        return prevTickPos;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return getParent().hurt(source, amount);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@Nullable Pose pose) {
        return EntityDimensions.scalable(base, height);
    }

    @Override
    protected float getEyeHeight(@Nullable Pose pose, @NotNull EntityDimensions size) {
        return size.height / 2;
    }

    @Override
    public float getEyeHeight(@Nullable Pose pose) {
        return height / 2;
    }

    @Override
    public boolean is(@NotNull Entity entity) {
        return this == entity || getParent() == entity;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {

    }
}
