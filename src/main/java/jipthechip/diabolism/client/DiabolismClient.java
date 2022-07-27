package jipthechip.diabolism.client;

import jipthechip.diabolism.blocks.DiabolismBlocks;
import jipthechip.diabolism.entities.DiabolismEntities;
import net.fabricmc.api.ClientModInitializer;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class DiabolismClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        DiabolismBlocks.initializeClient();
        DiabolismEntities.registerBlockEntityRenderers();
    }
}
