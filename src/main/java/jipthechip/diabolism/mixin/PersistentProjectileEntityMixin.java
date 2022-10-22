package jipthechip.diabolism.mixin;

import jipthechip.diabolism.Utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends Entity {

    protected PersistentProjectileEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At(value = "INVOKE", target="Lnet/minecraft/entity/projectile/PersistentProjectileEntity;getEntityCollision(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/hit/EntityHitResult;"), method = "tick")
    private void diabolism$tick(CallbackInfo info){
        Box box = getBoundingBox();
        double d = MathUtils.distanceBetween2Points(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
        //System.out.println("bounding box in PersistentProjectileEntity tick: "+d);
    }

    /*
    @Nullable
    private static Direction traceCollisionSide(double[] traceDistanceResult, @Nullable Direction approachDirection, double deltaX, double deltaY, double deltaZ, double begin, double minX, double maxX, double minZ, double maxZ, Direction resultDirection, double startX, double startY, double startZ) {

        // the time it takes for the central pos of entity1 to reach the approached edge of the box of entity2
        double d = (begin - startX) / deltaX;
        // the pos of dimension 2 when the elapsed time of d has passed
        double e = startY + d * deltaY;
        // the pos of dimension 3 when the elapsed time of d has passed
        double f = startZ + d * deltaZ;

        // checks d is between 0 and 1.0 or the time of the closest successful collision, and the other dimensions are within the bounding box
        // of entity2 when that time has elapsed
        if (0.0D < d && d < traceDistanceResult[0] && minX - 1.0E-7D < e && e < maxX + 1.0E-7D && minZ - 1.0E-7D < f && f < maxZ + 1.0E-7D) {
            traceDistanceResult[0] = d;
            return resultDirection;
        } else {
            return approachDirection;
        }
    }
     */
}
