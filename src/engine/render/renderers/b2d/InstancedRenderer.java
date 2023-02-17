package engine.render.renderers.b2d;

import engine.logic.math.*;

import engine.logic.structure.*;

import engine.render.globjects.*;
import engine.render.tools.*;

import engine.windowing.*;

import org.joml.*;

import java.lang.Math;
import java.util.*;

import static org.lwjgl.opengl.GL13.*;

public class InstancedRenderer implements RendererDef {
    public Window window;

    public Wireframe base;
    public Shader shader;

    public ArrayList<QuadModel> scene;

    public Matrix4f matrix;
    public Vector2f pixelResolution;
    public Vector2f cameraOffset;

    public Texture[] textures;

    public InstancedRenderer(Window window, Wireframe base, Shader shader) {
        this.window = window;
        this.base = base;
        this.shader = shader;
        this.scene = new ArrayList<>();
        this.pixelResolution = new Vector2f(1f / this.window.windowWidth, 1f / this.window.windowHeight);
        this.cameraOffset = new Vector2f(0, 0);
        this.matrix = new Matrix4f();
        this.matrix.scale(pixelResolution.x, pixelResolution.y, 0);
        this.textures = new Texture[8];
        this.shader.bind();

        TextureHandyman.setShaderSamplers(this.shader, this.textures.length);
    }

    public InstancedRenderer(Window window, Wireframe base) {
        this(window, base, new Shader("./assets/shaders/instancing/instance.vert", "./assets/shaders/instancing/instance.frag", window.fileManager));
    }

    public void render() {
        int size = this.scene.size();
        int drawCalls = (int) MathTools.clamp(1, 1, (int) Math.floor(size / 64f));

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (int g = 0; g < drawCalls; g++) {
            for (int i = g * 64; i < size; i++) {
                if (this.scene.get(i) != null) {
                    this.shader.unbind();
                    this.shader.bind();
                    TextureHandyman.bindTextureList(this.textures);
                    Matrix4f newMat = this.scene.get(i).transform.generateMatrix4x4f(true, true, new Vector3f(pixelResolution, 0), new Vector3f(this.cameraOffset, 0));
                    if (this.scene.get(i).textureCoordinates != null) {
                        if (this.shader.uniforms.get("textureCoordinates[" + i + "]") == null)
                            this.shader.createUniform("textureCoordinates[" + i + "]");
                        this.shader.setUniformFloatArray("textureCoordinates[" + i + "]", this.scene.get(i).textureCoordinates);
                    }
                    if (this.shader.uniforms.get("projections[" + i + "]") == null)
                        this.shader.createUniform("projections[" + i + "]");
                    this.shader.setUniformMatrix4x4f("projections[" + i + "]", newMat);

                    if (this.shader.uniforms.get("samplerIDs[" + i + "]") == null)
                        this.shader.createUniform("samplerIDs[" + i + "]");
                    this.shader.setUniformInt("samplerIDs[" + i + "]", this.scene.get(i).texture);

                }
            }
            this.base.renderInstanced(size - (g * 64));
        }
    }

    public void renderArray(ArrayList<QuadModel> list) {
        this.scene = list;
        render();
    }

    public void dispose() {
        this.base.dispose();
        this.shader.dispose();
        for (int i = 0; i < this.textures.length; i++) {
            if (this.textures[i] != null) {
                this.textures[i].dispose();
            }
        }
    }
}
