package jipthechip.diabolism.blocks;

import jipthechip.diabolism.entities.blockentities.PillarBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CarvedPillarBlock extends AbstractPillarBlock{
    protected CarvedPillarBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        PillarBlockEntity pillarBlockEntity = new PillarBlockEntity(pos, state);
        return pillarBlockEntity;
    }
}
