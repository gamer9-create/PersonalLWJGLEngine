package engine.render.globjects;

import engine.logic.math.*;

import java.io.*;

public class QuadModel implements Serializable {
    public Transform transform;
    public Rectangle rectangle;
    public int texture;
    public float[] textureCoordinates;

    public QuadModel(Transform transform, int texture, float[] textureCoordinates) {
        this.transform = transform;
        this.texture = texture;
        this.textureCoordinates = textureCoordinates;
        this.rectangle = new Rectangle(this.transform);
    }

    public void update() {
        this.rectangle.setToTransform(this.transform);
    }

}
