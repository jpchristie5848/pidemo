package jipthechip.diabolism.blocks;

import jipthechip.diabolism.Utils.BlockHelpers;
import jipthechip.diabolism.entities.DiabolismEntities;
import jipthechip.diabolism.entities.blockentities.AltarBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.stream.Stream;

public abstract class AbstractAltarBlock extends AbstractAltarComponentBlock {

    protected AbstractAltarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(ACTIVATED, false));
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

        if(state.get(AbstractAltarBlock.ACTIVATED)){
            for (Direction direction : Direction.Type.HORIZONTAL) {
                for(int i = 5; i >= 1; i--) {
                    if (/*(long)world.random.nextInt(200) <= world.getTime() % 10000L && */pos.getY() == world.getTopY(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ()) - 1) {
                        Vec3i directionVector = direction.getVector();
                        directionVector.add(0, -10000000, 0);
                        world.addParticle(ParticleTypes.ELECTRIC_SPARK, (pos.getX() + 0.5) + (directionVector.getX() * (0.5 + (0.5 * i))), pos.getY() + 0.6 + (0.25 * i), (pos.getZ() + 0.5) + (directionVector.getZ() * (0.5 + (0.5 * i))), ((double) directionVector.getX() + (random.nextDouble() * 0.5)) * -1, (double) directionVector.getY() - (random.nextDouble()*0.4),((double)directionVector.getZ() + random.nextDouble() * 0.5) * -1) ;
                        //ParticleUtil.spawnParticle(direction.getAxis(), world, pos.add(0, 1.00, 0).add(direction.getVector()), 0.125D, ParticleTypes.ELECTRIC_SPARK, UniformIntProvider.create(1, 2));
                    }
                }
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, DiabolismEntities.ALTAR_BLOCKENTITY, AltarBlockEntity::ticker);
    }

    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        final Vec3i[] directions = {new Vec3i(0,0,-1), new Vec3i(0,0,1), new Vec3i(1,0,0),
                new Vec3i(-1,0,0)};

        for(Vec3i direction : directions) {

            BlockPos block1Pos = pos.add(direction).add(0, -1, 0);
            BlockPos block2Pos = pos.add(direction.multiply(2)).add(0, -1, 0);
            BlockPos block3Pos = pos.add(direction.multiply(3));

            BlockState blockState1 = world.getBlockState(block1Pos);
            BlockState blockState2 = world.getBlockState(block2Pos);
            BlockState blockState3 = world.getBlockState(block3Pos);

            boolean block1IsRunedBlock = blockState1.getBlock() instanceof AbstractRunedBlock;
            boolean block2IsRunedBlock = blockState2.getBlock() instanceof AbstractRunedBlock;
            boolean block3IsPillarBlock = blockState3.getBlock() instanceof AbstractPillarBlock;

            if(block1IsRunedBlock){
                BlockHelpers.unlinkBlockFromAltar(block1Pos, pos, blockState1, world, false);
            }
            if(block2IsRunedBlock){
                BlockHelpers.unlinkBlockFromAltar(block2Pos, pos, blockState2, world, false);
            }
            if(block3IsPillarBlock){
                BlockHelpers.unlinkBlockFromAltar(block3Pos, pos, blockState3, world, true);
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(8, 1, 8, 14, 8, 14),
                Block.createCuboidShape(0, 0, 0, 8, 1, 8),
                Block.createCuboidShape(2, 1, 2, 8, 8, 8),
                Block.createCuboidShape(5, 8, 5, 8, 9, 6),
                Block.createCuboidShape(5, 8, 6, 6, 9, 8),
                Block.createCuboidShape(5, 9, 4, 8, 10, 5),
                Block.createCuboidShape(4, 9, 4, 5, 10, 8),
                Block.createCuboidShape(3, 10, 3, 8, 11, 4),
                Block.createCuboidShape(3, 10, 4, 4, 11, 8),
                Block.createCuboidShape(2, 1, 1, 4, 8, 2),
                Block.createCuboidShape(1, 1, 2, 2, 8, 4),
                Block.createCuboidShape(2, 1, 14, 4, 8, 15),
                Block.createCuboidShape(1, 1, 12, 2, 8, 14),
                Block.createCuboidShape(4, 10, 12, 8, 11, 13),
                Block.createCuboidShape(3, 10, 8, 4, 11, 13),
                Block.createCuboidShape(4, 9, 11, 8, 10, 12),
                Block.createCuboidShape(4, 9, 8, 5, 10, 11),
                Block.createCuboidShape(6, 8, 10, 8, 9, 11),
                Block.createCuboidShape(5, 8, 8, 6, 9, 11),
                Block.createCuboidShape(2, 1, 8, 8, 8, 14),
                Block.createCuboidShape(0, 0, 8, 8, 1, 16),
                Block.createCuboidShape(14, 1, 12, 15, 8, 14),
                Block.createCuboidShape(12, 1, 14, 14, 8, 15),
                Block.createCuboidShape(12, 10, 8, 13, 11, 12),
                Block.createCuboidShape(8, 10, 12, 13, 11, 13),
                Block.createCuboidShape(11, 9, 8, 12, 10, 12),
                Block.createCuboidShape(8, 9, 11, 11, 10, 12),
                Block.createCuboidShape(10, 8, 8, 11, 9, 10),
                Block.createCuboidShape(8, 8, 10, 11, 9, 11),
                Block.createCuboidShape(8, 0, 8, 16, 1, 16),
                Block.createCuboidShape(11, 9, 5, 12, 10, 8),
                Block.createCuboidShape(12, 10, 3, 13, 11, 8),
                Block.createCuboidShape(8, 10, 3, 12, 11, 4),
                Block.createCuboidShape(8, 9, 4, 12, 10, 5),
                Block.createCuboidShape(8, 8, 5, 10, 9, 6),
                Block.createCuboidShape(10, 8, 5, 11, 9, 8),
                Block.createCuboidShape(12, 1, 1, 14, 8, 2),
                Block.createCuboidShape(14, 1, 2, 15, 8, 4),
                Block.createCuboidShape(8, 0, 0, 16, 1, 8),
                Block.createCuboidShape(8, 1, 2, 14, 8, 8)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}
