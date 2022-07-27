package jipthechip.diabolism.blocks;

import jipthechip.diabolism.entities.DiabolismEntities;
import jipthechip.diabolism.entities.blockentities.PillarBlockEntity;
import jipthechip.diabolism.entities.blockentities.RunedBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public abstract class AbstractPillarBlock extends AbstractAltarComponentBlock {

    protected AbstractPillarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(ACTIVATED, false));
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, DiabolismEntities.PILLAR_BLOCKENTITY, PillarBlockEntity::ticker);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(0, 0, 0, 8, 1, 8),
                Block.createCuboidShape(4, 1, 4, 9, 13, 9),
                Block.createCuboidShape(2, 13, 2, 8, 14, 8),
                Block.createCuboidShape(1, 14, 1, 8, 15, 8),
                Block.createCuboidShape(0, 15, 0, 8, 16, 8),
                Block.createCuboidShape(8, 15, 0, 16, 16, 8),
                Block.createCuboidShape(8, 14, 1, 15, 15, 8),
                Block.createCuboidShape(8, 13, 2, 14, 14, 8),
                Block.createCuboidShape(8, 0, 0, 16, 1, 8),
                Block.createCuboidShape(7, 1, 4, 12, 13, 9),
                Block.createCuboidShape(8, 15, 8, 16, 16, 16),
                Block.createCuboidShape(8, 14, 8, 15, 15, 15),
                Block.createCuboidShape(8, 13, 8, 14, 14, 14),
                Block.createCuboidShape(8, 0, 8, 16, 1, 16),
                Block.createCuboidShape(7, 1, 7, 12, 13, 12),
                Block.createCuboidShape(0, 15, 8, 8, 16, 16),
                Block.createCuboidShape(1, 14, 8, 8, 15, 15),
                Block.createCuboidShape(2, 13, 8, 8, 14, 14),
                Block.createCuboidShape(4, 1, 7, 9, 13, 12),
                Block.createCuboidShape(0, 0, 8, 8, 1, 16),
                Block.createCuboidShape(3, 1, 5, 4, 13, 11),
                Block.createCuboidShape(5, 1, 3, 11, 13, 4),
                Block.createCuboidShape(12, 1, 5, 13, 13, 11),
                Block.createCuboidShape(5, 1, 12, 11, 13, 13)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}
