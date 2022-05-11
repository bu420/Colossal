package bu420.colossal.block;

import bu420.colossal.Main;
import bu420.colossal.block.entity.TimedLightBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TimedLightBlock extends Block /*implements EntityBlock*/ {
    public TimedLightBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                /*.setValue(BlockStateProperties.LEVEL, 15)*/
                /*.setValue(BlockStateProperties.WATERLOGGED, true)*/);
    }

    /*@Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        System.out.println("NEW BLOCK ENTITY!");
        return Main.TIMED_LIGHT_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == Main.TIMED_LIGHT_BLOCK_ENTITY.get() ? TimedLightBlockEntity::tick : null;
    }*/

    /*@Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }*/

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }
}
