package jipthechip.diabolism;

import jipthechip.diabolism.blocks.DiabolismBlockTagProvider;
import jipthechip.diabolism.blocks.DiabolismBlocks;
import jipthechip.diabolism.entities.DiabolismEntities;
import jipthechip.diabolism.events.DiabolismEvents;
import jipthechip.diabolism.items.DiabolismItems;
import jipthechip.diabolism.packets.DiabolismPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.data.server.BlockTagProvider;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;

import static jipthechip.diabolism.items.DiabolismItems.*;
import static jipthechip.diabolism.blocks.DiabolismBlocks.*;

public class Diabolism implements ModInitializer {

    @Override
    public void onInitialize() {
        DiabolismBlocks.registerBlocks();
        DiabolismItems.registerItems();
        DiabolismPackets.registerPacketReceivers();
        DiabolismEvents.registerEvents();
        DiabolismEntities.registerBlockEntities();
    }
}
