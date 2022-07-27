package jipthechip.diabolism.entities.blockentities;

import jipthechip.diabolism.blocks.AbstractAltarComponentBlock;
import jipthechip.diabolism.blocks.DiabolismBlocks;
import jipthechip.diabolism.entities.DiabolismEntities;
import joptsimple.internal.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PillarBlockEntity extends AltarComponentBlockEntity {

    private int tickCounter = 0;
    private ArrayList<Block> ACTIVATOR_BLOCKS;
    private boolean CHARGED_FROM_EVENT;
    private boolean REQUIRES_UNIQUE_ACTIVATOR_BLOCKS;
    private Vec3i directionFromAltar;

    public PillarBlockEntity(BlockPos pos, BlockState state) {
        super(DiabolismEntities.PILLAR_BLOCKENTITY, pos, state);
        Block block = state.getBlock();
        if(block == DiabolismBlocks.CONDUCTIVE_PILLAR){
            setProperties(new ArrayList<>(List.of(Blocks.LIGHTNING_ROD)), true, false);
        }else if(block == DiabolismBlocks.MOSSY_PILLAR){
            setProperties(new ArrayList<>(List.of(Blocks.ALLIUM, Blocks.SUNFLOWER,
            Blocks.ROSE_BUSH, Blocks.PEONY, Blocks.BLUE_ORCHID, Blocks.AZURE_BLUET, Blocks.ORANGE_TULIP, Blocks.PINK_TULIP,
            Blocks.RED_TULIP, Blocks.WHITE_TULIP, Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY,
            Blocks.LILAC)), false, true);
        }else if(block == DiabolismBlocks.CARVED_PILLAR){
            setProperties(new ArrayList<>(List.of(Blocks.ZOMBIE_HEAD, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL,
            Blocks.PLAYER_HEAD, Blocks.CREEPER_HEAD)), false, false);
        }
    }

    public void setProperties(ArrayList<Block> activatorBlocks, boolean chargedFromEvent, boolean requiresUniqueActivatorBlocks){
        ACTIVATOR_BLOCKS = activatorBlocks;
        CHARGED_FROM_EVENT = chargedFromEvent;
        REQUIRES_UNIQUE_ACTIVATOR_BLOCKS = requiresUniqueActivatorBlocks;
        directionFromAltar = new Vec3i(0,0,0);
    }

    public void setDirectionFromAltar(Vec3i direction){
        directionFromAltar = direction;
    }

    public static void ticker(World world, BlockPos pos, BlockState state, PillarBlockEntity be) {
        be.tick(world, pos, state);
    }

    private void tick(World world, BlockPos pos, BlockState state) {
        tickCounter++;
        if(tickCounter % 50 == 0){
            BlockState aboveState = world.getBlockState(pos.up());
            AltarBlockEntity altarBlockEntity = linkedAltar == null ? null : (AltarBlockEntity)world.getBlockEntity(linkedAltar);
            HashMap<Vec3i, BlockState> abovePillarBlocks = new HashMap<>();
            boolean alreadyExists = false;

            // if unique activator blocks are required on pillar, get the activator blocks of other pillars
            if(REQUIRES_UNIQUE_ACTIVATOR_BLOCKS && altarBlockEntity != null)
            {
                abovePillarBlocks = altarBlockEntity.getAbovePillarBlocks();
                abovePillarBlocks.remove(directionFromAltar);
                System.out.println("other pillars for "+directionFromAltar.toShortString()+": ");
                for(Vec3i key : abovePillarBlocks.keySet()){
                    System.out.println("\t"+key.toShortString()+": "+abovePillarBlocks.get(key).getBlock().toString());
                }
                for(Vec3i direction : abovePillarBlocks.keySet()){
                    // above pillar block in direction is an activator block, equal to this above pillar block, and has an activated pillar below it, do not activate the current pillar
                    if(ACTIVATOR_BLOCKS.contains(abovePillarBlocks.get(direction).getBlock()) && abovePillarBlocks.get(direction).getBlock() == aboveState.getBlock() && world.getBlockState(getLinkedAltar().add(direction.multiply(3))).get(AbstractAltarComponentBlock.ACTIVATED)){
                        System.out.println("found an activated pillar with the same activator block, not activating pillar "+directionFromAltar.toShortString());
                        alreadyExists = true;
                    }
                }
            }
            if(ACTIVATOR_BLOCKS.contains(aboveState.getBlock()) && !CHARGED_FROM_EVENT){
                System.out.println("setting pillar "+directionFromAltar.toShortString()+" to "+!alreadyExists);
                world.setBlockState(pos, state.with(AbstractAltarComponentBlock.ACTIVATED, !alreadyExists));
            }else if(!ACTIVATOR_BLOCKS.contains(aboveState.getBlock())){world.setBlockState(pos, state.with(AbstractAltarComponentBlock.ACTIVATED, false));
            }
        }
    }
}
