package bu420.colossal.block.entity;

import bu420.colossal.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TimedLightBlockEntity extends BlockEntity {
    private static final int LIFETIME = 10;
    private int current = LIFETIME;

    public TimedLightBlockEntity(BlockPos pos, BlockState state) {
        super(Main.TIMED_LIGHT_BLOCK_ENTITY.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T entity) {
        if (entity instanceof TimedLightBlockEntity light) {
            if (--light.current == 0) {
                if (level.getBlockEntity(light.getBlockPos()).getType() == Main.TIMED_LIGHT_BLOCK_ENTITY.get()) {
                    level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Lifetime", current);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        current = tag.getInt("Lifetime");
        setChanged();
    }
}
