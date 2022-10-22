package jipthechip.diabolism.events;

import jipthechip.diabolism.Utils.MathUtils;
import jipthechip.diabolism.blocks.AbstractAltarBlock;
import jipthechip.diabolism.blocks.AbstractAltarComponentBlock;
import jipthechip.diabolism.blocks.DiabolismBlocks;
import jipthechip.diabolism.blocks.RunedCopperBlock;
import jipthechip.diabolism.entities.DiabolismEntities;
import jipthechip.diabolism.entities.ProjectileSpellEntity;
import jipthechip.diabolism.entities.blockentities.AltarBlockEntity;
import jipthechip.diabolism.items.DiabolismItems;
import jipthechip.diabolism.items.RunicPowder;
import jipthechip.diabolism.packets.DiabolismPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

import static java.lang.Math.sin;
import static net.minecraft.util.math.MathHelper.cos;

public class DiabolismEvents {

    public static void registerEvents(){

        // LIGHTNING STRIKE EVENT
        LightningStrikeCallback.EVENT.register(((lightningEntity, blockPos) -> {

            BlockState belowState = lightningEntity.world.getBlockState(blockPos.down());

            if(belowState.getBlock() == DiabolismBlocks.CONDUCTIVE_PILLAR){
                BlockState state = lightningEntity.world.getBlockState(blockPos);
                if(state.getBlock() == Blocks.LIGHTNING_ROD) {
                    if (!belowState.get(AbstractAltarComponentBlock.ACTIVATED))
                        lightningEntity.world.setBlockState(blockPos.down(),
                                belowState.with(AbstractAltarComponentBlock.ACTIVATED, true));
                }
            }

            return ActionResult.SUCCESS;
        }));

        UseItemCallback.EVENT.register((player, world, hand)->
        {
            ItemStack stackInHand = player.getStackInHand(hand);

            if(stackInHand.getItem() == DiabolismItems.BASIC_WAND){

                //System.out.println("pitch: "+player.getPitch());
                //System.out.println("yaw: "+player.getYaw());
                Vec3d direction = Vec3d.fromPolar(player.getPitch(), player.getYaw());
                ProjectileSpellEntity spellEntity = new ProjectileSpellEntity(DiabolismEntities.PROJECTILE_SPELL,
                        player.getX() + (direction.getX() * 2),
                        player.getY() + (direction.getY() * 2)+1,
                        player.getZ() + (direction.getZ() * 2),
                        world, direction.multiply(0.3));

                world.spawnEntity(spellEntity);
            }

            return TypedActionResult.pass(stackInHand);
        });

        // USE BLOCK EVENT
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
        {

            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();

            // check if player is holding runic powder
            if(block.equals(Blocks.GLASS) || block.equals(Blocks.COPPER_BLOCK)){

                ItemStack stack = player.getStackInHand(hand);
                // check if used block is a glass block
                if(stack.getItem() instanceof RunicPowder){

                    // tell server to set block to a runed glass block
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBlockPos(hitResult.getBlockPos());
                    String[] splitBlockName = block.getName().getString().toLowerCase().split(" ");
                    buf.writeIdentifier(new Identifier("diabolism", "runed_"+splitBlockName[splitBlockName.length-1]));
                    ClientPlayNetworking.send(DiabolismPackets.SET_BLOCK_PACKET, buf);

                    // remove one runic powder from player's hand
                    if(stack.getCount() > 1){
                        stack.setCount(stack.getCount()-1);
                        player.setStackInHand(hand, stack);
                    }else{
                        player.setStackInHand(hand, ItemStack.EMPTY);
                    }

                    return ActionResult.SUCCESS;
                }
            }else if(block instanceof AbstractAltarBlock) {

                BlockPos pos = hitResult.getBlockPos();
                AltarBlockEntity blockEntity = (AltarBlockEntity) world.getBlockEntity(pos);

                if(blockEntity != null && blockEntity.storedItemUpdatable()){

                    ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
                    BlockState state = world.getBlockState(pos);
                    Item storedItem = blockEntity.getStoredItem();

                    if(storedItem != Items.AIR && stack.getItem() != storedItem){
                        if(player.isSneaking()) {
                            blockEntity.setStoredItem(Items.AIR);
                            if (stack == ItemStack.EMPTY) {
                                player.setStackInHand(Hand.MAIN_HAND, new ItemStack(storedItem, 1));
                            } else {
                                world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(storedItem, 1)));
                            }
                            blockEntity.markDirty();
                            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                            return ActionResult.SUCCESS;
                        }
                    }else if(stack != ItemStack.EMPTY && storedItem == Items.AIR){
                        blockEntity.setStoredItem(stack.getItem());
                        if(stack.getCount() > 1){
                            stack.setCount(stack.getCount()-1);
                            player.setStackInHand(Hand.MAIN_HAND, stack);
                        }else{
                            player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                        }
                        blockEntity.markDirty();
                        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                        return ActionResult.SUCCESS;
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}
