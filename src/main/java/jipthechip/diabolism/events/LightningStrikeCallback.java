package jipthechip.diabolism.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public interface LightningStrikeCallback {

    Event<LightningStrikeCallback> EVENT = EventFactory.createArrayBacked(LightningStrikeCallback.class,
            (listeners)->(lightningEntity, blockPos)-> {
                for(LightningStrikeCallback listener : listeners) {
                    ActionResult result = listener.interact(lightningEntity, blockPos);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(LightningEntity lightningEntity, BlockPos blockPos);
}
