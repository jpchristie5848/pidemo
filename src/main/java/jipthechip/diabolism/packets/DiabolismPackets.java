package jipthechip.diabolism.packets;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class DiabolismPackets {

    public static final Identifier SET_BLOCK_PACKET = new Identifier("diabolism", "setblock");

    public static void registerPacketReceivers(){

        // register SET BLOCK packet receiver
        ServerPlayNetworking.registerGlobalReceiver(SET_BLOCK_PACKET, (server, player, handler, buf, sender) -> {
            BlockPos pos = buf.readBlockPos();
            Block blockToSet = Registry.BLOCK.get(buf.readIdentifier());

            server.execute(()->{
                player.getWorld().setBlockState(pos, blockToSet.getDefaultState());
            });
        });
    }
}
