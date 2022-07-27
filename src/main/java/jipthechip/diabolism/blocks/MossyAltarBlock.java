package jipthechip.diabolism.blocks;

import jipthechip.diabolism.Diabolism;
import jipthechip.diabolism.entities.blockentities.AltarBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MossyAltarBlock extends AbstractAltarBlock {
    protected MossyAltarBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        AltarBlockEntity altarBlockEntity = new AltarBlockEntity(pos, state);
        return altarBlockEntity;
    }
}
