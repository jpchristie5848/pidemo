package jipthechip.diabolism.mixin;

import jipthechip.diabolism.Diabolism;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

//    @Shadow
//    EntityDimensions dimensions;
//
//    @Inject(at = @At("HEAD"), method = "readNbt")
//    private void diabolism$readNbt(NbtCompound tag, CallbackInfo info)
//    {
//        float width = tag.getFloat(Diabolism.MOD_ID+":dimensions_width");
//        float height = tag.getFloat(Diabolism.MOD_ID+":dimensions_height");
//        if(!(width == 0.0 && height == 0.0)){
//            this.dimensions = EntityDimensions.changing(width, height);
//            System.out.println("changed entity dimensions to "+this.dimensions);
//        }
//    }
//
//    @Inject(at = @At("HEAD"), method = "tick")
//    private void diabolism$tick(CallbackInfo ci){
//
//    }
}
