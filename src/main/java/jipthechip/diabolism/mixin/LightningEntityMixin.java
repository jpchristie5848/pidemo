package jipthechip.diabolism.mixin;

import jipthechip.diabolism.events.LightningStrikeCallback;
import net.minecraft.block.Block;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin {

    @Shadow protected abstract BlockPos getAffectedBlockPos();

    @Inject(at=@At(value = "INVOKE", target = "Lnet/minecraft/entity/LightningEntity;emitGameEvent(Lnet/minecraft/world/event/GameEvent;)V"), method="tick()V")
    private void tick(CallbackInfo ci){
        ActionResult result = LightningStrikeCallback.EVENT.invoker().interact((LightningEntity)(Object)this,
                                                                                this.getAffectedBlockPos());
    }
}
