package jipthechip.diabolism.mixin;

import jipthechip.diabolism.Utils.MathUtils;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Box.class)
public class BoxMixin {

//    @ModifyVariable(method = "traceCollisionSide([DLnet/minecraft/util/math/Direction;DDDDDDDDLnet/minecraft/util/math/Direction;DDD)Lnet/minecraft/util/math/Direction;", at = @At("STORE"), ordinal = 11)
//    private static double diabolism$d(double x) {
//        System.out.println("d: "+x);
//        return x;
//    }
//    @ModifyVariable(method = "traceCollisionSide([DLnet/minecraft/util/math/Direction;DDDDDDDDLnet/minecraft/util/math/Direction;DDD)Lnet/minecraft/util/math/Direction;", at = @At("STORE"), ordinal = 12)
//    private static double diabolism$e(double x) {
//        System.out.println("e: "+x);
//        return x;
//    }
//    @ModifyVariable(method = "traceCollisionSide([DLnet/minecraft/util/math/Direction;DDDDDDDDLnet/minecraft/util/math/Direction;DDD)Lnet/minecraft/util/math/Direction;", at = @At("STORE"), ordinal = 13)
//    private static double diabolism$f(double x) {
//        System.out.println("f: "+x);
//        return x;
//    }
//
//    @Inject(at=@At(value = "RETURN"), method = "traceCollisionSide([DLnet/minecraft/util/math/Direction;DDDDDDDDLnet/minecraft/util/math/Direction;DDD)Lnet/minecraft/util/math/Direction;")
//    private static void diabolism$traceCollisionSideReturn(double[] traceDistanceResult, Direction approachDirection, double deltaX, double deltaY, double deltaZ, double begin, double minX, double maxX, double minZ, double maxZ, Direction resultDirection, double startX, double startY, double startZ, CallbackInfoReturnable<Direction> cir){
//        System.out.println("traceDistanceResult: "+traceDistanceResult[0]);
//        System.out.println("return value of tracecollisionside: "+cir.getReturnValue());
//        System.out.println("------------------------------------------------------");
//    }
}
