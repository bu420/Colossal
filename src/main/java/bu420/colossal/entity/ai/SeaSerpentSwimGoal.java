package bu420.colossal.entity.ai;

import bu420.colossal.entity.SeaSerpent;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SeaSerpentSwimGoal extends Goal {
    private final SeaSerpent seaSerpent;
    private Vec3 dest;

    public SeaSerpentSwimGoal(SeaSerpent seaSerpent) {
        this.seaSerpent = seaSerpent;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (seaSerpent.isInWater()) {
            Vec3 pos = BehaviorUtils.getRandomSwimmablePos(seaSerpent, 16, 6);

            if (pos != null) {
                dest = pos;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !seaSerpent.getNavigation().isDone() && seaSerpent.isInWater();
    }

    @Override
    public void start() {
        seaSerpent.getNavigation().moveTo(dest.x, dest.y, dest.z, 1);
    }

    @Override
    public void stop() {
        seaSerpent.getNavigation().stop();
        super.stop();
    }
}
