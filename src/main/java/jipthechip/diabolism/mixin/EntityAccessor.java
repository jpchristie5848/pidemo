package jipthechip.diabolism.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {

    @Accessor("dimensions")
    public void setDimensions(EntityDimensions dimensions);

    @Accessor("dimensions")
    public EntityDimensions getDimensions();


}
