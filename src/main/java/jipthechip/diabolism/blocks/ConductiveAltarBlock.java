package jipthechip.diabolism.blocks;

import jipthechip.diabolism.entities.blockentities.AltarBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConductiveAltarBlock extends AbstractAltarBlock {

    public ConductiveAltarBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        AltarBlockEntity altarBlockEntity = new AltarBlockEntity(pos, state);
        return altarBlockEntity;
    }

}
