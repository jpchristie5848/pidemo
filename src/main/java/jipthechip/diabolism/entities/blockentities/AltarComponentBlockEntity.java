package jipthechip.diabolism.entities.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class AltarComponentBlockEntity extends BlockEntity {

    BlockPos linkedAltar;

    public AltarComponentBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void setLinkedAltar(BlockPos pos){
        linkedAltar = pos;
    }

    @Nullable
    public BlockPos getLinkedAltar(){
        return linkedAltar;
    }
}
