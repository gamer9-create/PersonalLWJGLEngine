package engine.render.globjects;

import engine.logic.math.*;

public class RawModel {
    public Wireframe wireframe;
    public Transform transform;
    public Texture texture;

    public RawModel(Wireframe wireframe, Transform transform, Texture texture) {
        this.wireframe = wireframe;
        this.transform = transform;
        this.texture = texture;
    }
}
