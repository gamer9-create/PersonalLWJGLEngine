package engine.logic.math;

import org.joml.*;

import java.io.*;

public class Transform implements Serializable {
    public Vector3f position;
    public Vector3f size;

    public Transform(Vector3f position, Vector3f size) {
        this.position = position;
        this.size = size;
    }

    public Matrix4f generateMatrix4x4f(boolean includePosition, boolean includeScaling, Vector3f dScale, Vector3f offset) {
        Matrix4f matrix4f = new Matrix4f();

        if (includePosition) matrix4f.translate(dScale.x * (this.position.x - offset.x) * 2, dScale.y * (this.position.y - offset.y) * 2, dScale.z * (this.position.z - offset.z) * 2);
        if (includeScaling) matrix4f.scale(dScale.x * this.size.x, dScale.y * this.size.y, dScale.z * this.size.z);

        return matrix4f;
    }

    public Matrix4f generateMatrix4x4f() {
        return this.generateMatrix4x4f(true, true, new Vector3f(1, 1, 1), new Vector3f());
    }
}
