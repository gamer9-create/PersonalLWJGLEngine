package engine.render.renderers.b2d;

import engine.logic.math.*;
import engine.logic.structure.*;

import engine.render.globjects.*;
import engine.render.tools.*;

import engine.windowing.*;

import org.joml.*;

import java.util.*;

import static org.lwjgl.opengl.GL15.*;

public class BatchedRenderer implements RendererDef {

    public Wireframe finalModel;
    public Shader shader;
    public Window window;
    public Vector2f pixelResolution;
    public Vector2f cameraOffset;
    public Matrix4f matrix;
    public Texture[] textures;
    public ArrayList<QuadModel> scene;

    public int maxSceneSize;

    public BatchedRenderer(Window window, Shader shader, int maxSceneSize) {
        this.window = window;
        this.shader = shader;
        this.scene = new ArrayList<>();
        this.maxSceneSize = maxSceneSize;
        this.textures = new Texture[8];

        this.shader.bind();

        this.shader.createUniform("projection");
        this.shader.createUniform("samplers");

        TextureHandyman.setShaderSamplers(this.shader, this.textures.length);

        this.cameraOffset = new Vector2f();
        this.pixelResolution = new Vector2f(1f / this.window.windowWidth, 1f / this.window.windowHeight);
        this.matrix = new Matrix4f();
        this.matrix.scale(pixelResolution.x, pixelResolution.y, 0);

        this.finalModel = new Wireframe(new float[this.maxSceneSize * 12], new int[this.maxSceneSize * 6], GL_DYNAMIC_DRAW);
        this.finalModel.createTextureBuffer(new float[this.maxSceneSize * 8]);
    }

    public BatchedRenderer(Window window, int maxSceneSize) {
        this(window, new Shader("./assets/shaders/batched/batched.vert", "./assets/shaders/batched/batched.frag", window.fileManager), maxSceneSize);
    }

    @Override
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        this.shader.bind();
        TextureHandyman.bindTextureList(this.textures);

        this.shader.setUniformMatrix4x4f("projection", this.matrix);

        Arrays.fill(this.finalModel.vertices, 0);
        Arrays.fill(this.finalModel.textureCoordinates, 0);
        int i = 0;
        for (QuadModel model : this.scene) {
            if (model != null) {
                float[] modelVertices = MathTools.generateQuadShape((model.transform.position.x - this.cameraOffset.x) * 2, (model.transform.position.y - this.cameraOffset.y) * 2, model.transform.size.x * 2, model.transform.size.y * 2, model.texture);
                float[] modelTextureCoordinates = model.textureCoordinates;

                System.arraycopy(modelVertices, 0, this.finalModel.vertices, i * 12, modelVertices.length);
                System.arraycopy(modelTextureCoordinates, 0, this.finalModel.textureCoordinates, i * 8, modelTextureCoordinates.length);
            }
            i += 1;
        }

        this.finalModel.edges = MathTools.generateQuadEdges(i);

        this.finalModel.render();
    }

    @Override
    public void dispose() {
        this.finalModel.dispose();
        this.shader.dispose();
    }
}
