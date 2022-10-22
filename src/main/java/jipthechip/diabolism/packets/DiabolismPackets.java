package jipthechip.diabolism.packets;

import jipthechip.diabolism.entities.ProjectileSpellEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public class DiabolismPackets {

    public static final Identifier SET_BLOCK_PACKET = new Identifier("diabolism", "set_block");
    public static final Identifier SET_ENTITY_RADIUS_PACKET = new Identifier("diabolism", "set_entity_radius");

    public static void registerPacketReceivers(){

        // register SET BLOCK packet receiver
        ServerPlayNetworking.registerGlobalReceiver(SET_BLOCK_PACKET, (server, player, handler, buf, sender) -> {
            BlockPos pos = buf.readBlockPos();
            Block blockToSet = Registry.BLOCK.get(buf.readIdentifier());

            server.execute(()->{
                player.getWorld().setBlockState(pos, blockToSet.getDefaultState());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(SET_ENTITY_RADIUS_PACKET, (client, handler, buf, responseSender) -> {
            int entityId = buf.readInt();
            float radius = buf.readFloat();

            client.execute(()->{
                ((ProjectileSpellEntity) Objects.requireNonNull(handler.getWorld().getEntityById(entityId))).setRadius(radius);
                System.out.println("set radius to "+radius);
            });
        });
    }
}
