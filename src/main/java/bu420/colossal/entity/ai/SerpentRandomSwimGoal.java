package bu420.colossal.entity.ai;

import bu420.colossal.entity.SeaSerpent;
import bu420.colossal.entity.Serpent;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SerpentRandomSwimGoal<T extends WaterAnimal & Serpent<?>> extends Goal {
    private final T serpent;
    private Vec3 dest;

    public SerpentRandomSwimGoal(T serpent) {
        this.serpent = serpent;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (serpent.isInWater()) {
            Vec3 pos = BehaviorUtils.getRandomSwimmablePos(serpent, 16, 6);

            if (pos != null) {
                dest = pos;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !serpent.getNavigation().isDone() && serpent.isInWater();
    }

    @Override
    public void start() {
        serpent.getNavigation().moveTo(dest.x, dest.y, dest.z, 1);
    }

    @Override
    public void stop() {
        serpent.getNavigation().stop();
        super.stop();
    }
}
