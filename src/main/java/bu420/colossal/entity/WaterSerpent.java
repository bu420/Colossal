package bu420.colossal.entity;

import bu420.colossal.entity.ai.SerpentRandomSwimGoal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class WaterSerpent<T extends Entity> extends WaterAnimal implements Serpent<T> {
    protected final List<SerpentPart<T>> parts = new ArrayList<>();

    public WaterSerpent(EntityType<? extends WaterAnimal> type, Level level) {
        super(type, level);

        noCulling = true;

        moveControl = new SmoothSwimmingMoveControl(this, 20, 10, 0.02F, 0.1F, true);
        lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SerpentRandomSwimGoal<>(this));
    }

    @Override
    public void tick() {
        super.tick();

        for (int i = 0; i < parts.size(); i++) {
            parts.get(i).tick(i == 0 ? null : parts.get(i - 1));
        }
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
    public abstract int getLength();

    @Override
    public abstract float getPartDamageModifier();

    @Override
    public List<SerpentPart<T>> getPartEntities() {
        return parts;
    }

    @Override
    public SerpentPart<T> getHead() {
        return parts.get(0);
    }

    @Override
    public List<SerpentPart<T>> getBodies() {
        return parts.subList(1, parts.size() - 1);
    }

    @Override
    public SerpentPart<T> getTail() {
        return parts.get(parts.size() - 1);
    }
}
