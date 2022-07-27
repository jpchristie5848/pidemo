package jipthechip.diabolism.Utils;

import jipthechip.diabolism.blocks.AbstractRunedBlock;
import jipthechip.diabolism.entities.blockentities.AltarBlockEntity;
import jipthechip.diabolism.entities.blockentities.AltarComponentBlockEntity;
import jipthechip.diabolism.entities.blockentities.PillarBlockEntity;
import jipthechip.diabolism.entities.blockentities.RunedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class BlockHelpers {

    public static boolean unlinkBlockFromAltar(BlockPos pos, BlockPos altarPos, BlockState state, World world, boolean isPillar){
        AltarComponentBlockEntity blockEntity = (AltarComponentBlockEntity) world.getBlockEntity(pos);
        if(blockEntity != null && blockEntity.getLinkedAltar() != null && blockEntity.getLinkedAltar().equals(altarPos)){
            BlockState newState = state;
            if(!isPillar){
                newState = state.with(AbstractRunedBlock.ACTIVATED, false);
                world.setBlockState(pos, newState);
            }else{
                ((PillarBlockEntity)blockEntity).setDirectionFromAltar(null);
            }
            blockEntity.setLinkedAltar(null);
            blockEntity.markDirty();
            world.updateListeners(pos, state, newState, Block.NOTIFY_LISTENERS);
            //System.out.println("successfully unlinked block "+pos);
            return true;
        }
        //System.out.println("runed blockentity is null: "+(blockEntity == null));
        //if(blockEntity != null && blockEntity.getLinkedAltar() != null) System.out.println("runed blockentity altar: "+blockEntity.getLinkedAltar().toShortString()+" altar pos: "+altarPos.toShortString());
        //else System.out.println("runed blockentity can't be unlinked because it or its linked altar is null");
        return false;
    }


    public static boolean linkBlockToAltar(BlockPos pos, BlockPos altarPos, World world){
        return linkBlockToAltar(pos, altarPos, world, null);
    }

    public static boolean linkBlockToAltar(BlockPos pos, BlockPos altarPos, World world, Vec3i directionFromAltar){
        AltarComponentBlockEntity blockEntity = (AltarComponentBlockEntity) world.getBlockEntity(pos);
        if(blockEntity != null){
            BlockState state = world.getBlockState(pos);
            if(blockEntity.getLinkedAltar() == null) {
                AltarBlockEntity altarBlockEntity = (AltarBlockEntity) world.getBlockEntity(altarPos);
                if (altarBlockEntity != null) {
                    blockEntity.setLinkedAltar(altarPos);
                }
            }
            if(directionFromAltar != null) ((PillarBlockEntity)blockEntity).setDirectionFromAltar(directionFromAltar);
            blockEntity.markDirty();
            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
            return blockEntity.getLinkedAltar().equals(altarPos);
        }
        return false;
    }
}
