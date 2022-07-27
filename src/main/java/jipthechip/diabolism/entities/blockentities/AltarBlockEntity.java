package jipthechip.diabolism.entities.blockentities;

import jipthechip.diabolism.Utils.BlockHelpers;
import jipthechip.diabolism.blocks.*;
import jipthechip.diabolism.entities.DiabolismEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.SerializationUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AltarBlockEntity extends BlockEntity {

    private final int ITEM_UPDATE_DELAY = 10;
    private int itemUpdateDelayStart = -1;
    private int tickCounter = 0;
    private Identifier storedItem;
    private Block RUNED_BLOCK;
    private Block PILLAR_BLOCK;
    private HashMap<Vec3i, BlockState> abovePillarBlocks;

    // north, south, east, and west
    private final Vec3i[] directions = {new Vec3i(0,0,-1), new Vec3i(0,0,1), new Vec3i(1,0,0),
            new Vec3i(-1,0,0)};

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(DiabolismEntities.ALTAR_BLOCKENTITY, pos, state);
        Block block = state.getBlock();
        if(block == DiabolismBlocks.CONDUCTIVE_ALTAR){
            setParameters(DiabolismBlocks.RUNED_COPPER, DiabolismBlocks.CONDUCTIVE_PILLAR);
        }else if(block == DiabolismBlocks.MOSSY_ALTAR){
            setParameters(DiabolismBlocks.RUNED_MOSS, DiabolismBlocks.MOSSY_PILLAR);
        }else if(block == DiabolismBlocks.CARVED_ALTAR){
            setParameters(DiabolismBlocks.RUNED_BONE, DiabolismBlocks.CARVED_PILLAR);
        }
        System.out.println("altar constructor called");
    }

    public void setParameters(Block runedBlock, Block pillarBlock){
        this.RUNED_BLOCK = runedBlock;
        this.PILLAR_BLOCK = pillarBlock;
    }

    @Override
    public void writeNbt(NbtCompound tag){
        if (this.storedItem == null){
            tag.putString("item", "null:null");
        }else{
            tag.putString("item", storedItem.getNamespace()+":"+storedItem.getPath());
        }
        //tag.putByteArray("above_pillar_blocks", SerializationUtils.serialize(abovePillarBlocks));
        super.writeNbt(tag);
    }

    @Override
    public void readNbt(NbtCompound tag){
        super.readNbt(tag);
        storedItem = new Identifier(tag.getString("item").split(":")[0],tag.getString("item").split(":")[1]);
        //abovePillarBlocks = SerializationUtils.deserialize(tag.getByteArray("above_pillar_blocks"));
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

    public Item getStoredItem(){
        Item item = Registry.ITEM.get(storedItem);
        //System.out.println("get stored item: "+item.toString());
        return item;
    }

    public void setStoredItem(@Nullable Item item){
        storedItem = Registry.ITEM.getId(item);
        itemUpdateDelayStart = tickCounter;
        System.out.println("set stored item to: "+(item == null? "null" : item.toString()));
    }

    public boolean storedItemUpdatable(){
        return itemUpdateDelayStart == -1;
    }

    public HashMap<Vec3i, BlockState> getAbovePillarBlocks(){
        return abovePillarBlocks;
    }

    public static void ticker(World world, BlockPos pos, BlockState state, AltarBlockEntity be) {
        be.tick(world, pos, state);
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        tickCounter++;
        if(tickCounter % 50 == 0){
            boolean allActivated = true;

            if(abovePillarBlocks == null) abovePillarBlocks = new HashMap<>();
            System.out.println("length of abovePillarBlocks: "+abovePillarBlocks.keySet().size());
            // check all 4 directions from altar for activated pillars
            for(Vec3i direction : directions){

                // get 2 blocks leading to pillar in given direction and their blockstates
                BlockPos block1Pos = pos.add(direction).add(0, -1, 0);
                BlockPos block2Pos = pos.add(direction.multiply(2)).add(0, -1, 0);
                BlockState blockState1 = world.getBlockState(block1Pos);
                BlockState blockState2 = world.getBlockState(block2Pos);

                // check if the blocks are the correct runed blocks and linked to the altar
                boolean block1IsLinkedRunedBlock = blockState1.getBlock() == this.RUNED_BLOCK &&
                        BlockHelpers.linkBlockToAltar(block1Pos, pos, world);
                boolean block2IsLinkedRunedBlock = blockState2.getBlock() == this.RUNED_BLOCK &&
                        BlockHelpers.linkBlockToAltar(block2Pos, pos, world);

                // only want to bother checking the blocks if at least one of them is linked to the altar
                boolean checkBlocks = block1IsLinkedRunedBlock || block2IsLinkedRunedBlock;

                boolean deactivateBlocks = true;

                // check the block(s) to see if they need to be activated
                if(checkBlocks) {

                    BlockPos pillarPos = pos.add(direction.multiply(3));
                    BlockState pillarState = world.getBlockState(pillarPos);

                    // check if the pillar block is the correct type of block, and it's linked to the altar
                    if (pillarState.getBlock() == this.PILLAR_BLOCK && BlockHelpers.linkBlockToAltar(pillarPos, pos, world, direction)){

                        abovePillarBlocks.put(direction, world.getBlockState(pillarPos.up()));
                        System.out.println("putting "+direction.toShortString()+" "+world.getBlockState(pillarPos.up()).getBlock().toString());

                        // check if the pillar is activated
                        if (pillarState.get(AbstractAltarComponentBlock.ACTIVATED)) {
                            // only activate the runed blocks if they are both present and linked and the pillar block is activated
                            if (block1IsLinkedRunedBlock && block2IsLinkedRunedBlock) {
                                deactivateBlocks = false;
                                world.setBlockState(block1Pos, blockState1.with(AbstractAltarComponentBlock.ACTIVATED, true));
                                world.setBlockState(block2Pos, blockState2.with(AbstractAltarComponentBlock.ACTIVATED, true));
                            }
                        }
                    }
                }
                // if one or both of the blocks leading to the altar is not linked to it, or the pillar block is not
                // activated, deactivate any block that is linked
                if (deactivateBlocks){
                    allActivated = false;
                    if(block1IsLinkedRunedBlock) world.setBlockState(block1Pos, blockState1.with(AbstractAltarComponentBlock.ACTIVATED, false));
                    if(block2IsLinkedRunedBlock) world.setBlockState(block2Pos, blockState2.with(AbstractAltarComponentBlock.ACTIVATED, false));
                }
            }

            //System.out.println("setting altar state to "+allActivated);
            // if all the necessary blocks are activated, activate the altar
            world.setBlockState(pos, state.with(AbstractAltarBlock.ACTIVATED, allActivated));
        }
        // delay update of stored item in altar
        if(itemUpdateDelayStart != -1){
            if(tickCounter == itemUpdateDelayStart + ITEM_UPDATE_DELAY){
                itemUpdateDelayStart = -1;
            }
        }
    }
}
