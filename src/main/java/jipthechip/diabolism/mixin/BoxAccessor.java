package jipthechip.diabolism.mixin;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Box.class)
public interface BoxAccessor {

    @Invoker("traceCollisionSide")
    public static Direction traceCollisionSide(Box box, Vec3d intersectingVector, double[] traceDistanceResult, @Nullable Direction approachDirection, double deltaX, double deltaY, double deltaZ) {
        throw new AssertionError();
    }
}
