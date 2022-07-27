package jipthechip.diabolism.entities.blockentities;

import jipthechip.diabolism.entities.DiabolismEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.SerializationUtils;

import javax.annotation.Nullable;

public class RunedBlockEntity extends AltarComponentBlockEntity {

    private boolean isPillarBlock;
    private int tickCounter = 0;

    public RunedBlockEntity(BlockPos pos, BlockState state) {
        super(DiabolismEntities.RUNED_BLOCKENTITY, pos, state);
        linkedAltar = null;
        isPillarBlock = false;
    }

    @Override
    public void writeNbt(NbtCompound tag){
        tag.putString("linked_altar", linkedAltar == null ? "null" : linkedAltar.toShortString());
        tag.putBoolean("is_pillar", isPillarBlock);
        super.writeNbt(tag);
    }

    @Override
    public void readNbt(NbtCompound tag){
        super.readNbt(tag);
        String posString = tag.getString("linked_altar");
        if(posString.equals("null")){
            linkedAltar = null;
        }else{
            String[] splitPos = tag.getString("linked_altar").split(", ");
            linkedAltar = new BlockPos(Integer.parseInt(splitPos[0]), Integer.parseInt(splitPos[1]), Integer.parseInt(splitPos[2]));
        }
        isPillarBlock = tag.getBoolean("is_pillar");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public static void ticker(World world, BlockPos pos, BlockState state, RunedBlockEntity be) {
        be.tick(world, pos, state);
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        tickCounter++;
        if(tickCounter % 50 == 0 && isPillarBlock){

        }
    }

}
