package engine.render.renderers.b3d;

import engine.logic.structure.*;

import engine.render.globjects.*;

import engine.windowing.*;

import org.joml.*;

import java.util.*;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;

public class Renderer implements RendererDef {
    public Window window;
    public Shader shader;
    public ArrayList<RawModel> rawModels;
    public Matrix4f matrix;
    public Matrix4f camera;

    public boolean is3D;

    public Renderer(Window window, Shader shader) {
        this.window = window;
        this.shader = shader;

        this.is3D = false;

        if (this.shader == null) {
            this.shader = new Shader("./assets/shaders/3d/basic.vert", "./assets/shaders/3d/basic.frag", this.window.fileManager);
        }

        this.rawModels = new ArrayList<>();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        this.matrix = new Matrix4f();
        this.camera = new Matrix4f();

        this.shader.createUniform("projection");
        this.shader.createUniform("sampler");
        this.shader.createUniform("camera");
        this.shader.createUniform("object");
    }

    public Renderer(Window window) {
        this(window, null);
    }

    public void apply3DEffect() {
        this.is3D = true;
        this.matrix.setPerspective((float) toRadians(70), 2, 0.01f, 1000);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        this.shader.bind();

        for (RawModel rawModel : this.rawModels) {
            if (rawModel != null) {
                rawModel.texture.bind();
                this.shader.setUniformMatrix4x4f("projection", this.matrix);
                this.shader.setUniformMatrix4x4f("camera", this.camera);
                this.shader.setUniformMatrix4x4f("object", rawModel.transform.generateMatrix4x4f());
                this.shader.setUniformInt("sampler", 0);
                rawModel.wireframe.render();
            }
        }
    }

    public void dispose() {
        for (RawModel rawModel : this.rawModels) {
            if (rawModel != null) {
                rawModel.wireframe.dispose();
            }
        }
    }
}
