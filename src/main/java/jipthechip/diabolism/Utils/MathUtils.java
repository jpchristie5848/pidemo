package jipthechip.diabolism.Utils;

import net.minecraft.util.math.Vec3d;

import static java.lang.Math.sin;
import static net.minecraft.util.math.MathHelper.cos;

public class MathUtils {

    public static Vec3d PitchAndYawToVec3d(float pitch, float yaw){

        double pitchRadians = Math.toRadians(pitch);
        double yawRadians = Math.toRadians(yaw);

        double sinPitch = Math.sin(pitchRadians);
        double cosPitch = Math.cos(pitchRadians);
        double sinYaw = Math.sin(yawRadians);
        double cosYaw = Math.cos(yawRadians);

        return new Vec3d(-cosPitch * sinYaw, sinPitch, -cosPitch * cosYaw);
    }

    public static Vec3d getPointOnSphere(float pitch, float yaw, float radius, Vec3d centerPos){
        return PitchAndYawToVec3d(pitch, yaw).multiply(radius).add(centerPos);
    }

    public static double distanceBetween2Points(double x1, double y1, double z1, double x2, double y2, double z2){
        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2) + Math.pow(z2-z1, 2));
    }
}
