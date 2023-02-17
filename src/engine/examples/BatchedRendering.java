package engine.examples;

import engine.logic.math.*;

import engine.render.globjects.*;

import engine.render.renderers.b2d.BatchedRenderer;
import engine.windowing.*;

import org.joml.*;

import java.util.Random;

public class BatchedRendering {

    public static void main(String[] args) {
        Window window = new Window(800, 600, "LWJGL3 Batched Rendering");

        BatchedRenderer batchedRenderer = new BatchedRenderer(window, 1000);

        batchedRenderer.textures[0] = new Texture("./assets/textures/colors.png");
        batchedRenderer.textures[1] = new Texture("./assets/textures/face.jpg");
        batchedRenderer.textures[2] = new Texture("./assets/textures/gtex.png");
        batchedRenderer.textures[3] = new Texture("./assets/textures/happyface.png");

        float[] dTextureCoordinates = new float[] {
                0, 1,
                1, 1,
                1, 0,
                0, 0
        };

        int s = 50;
        int times = 8;
        Random random = new Random();
        for (int i = 0; i < times; i++) {
            batchedRenderer.scene.add(new QuadModel(new Transform(new Vector3f(i * s, 0, 0), new Vector3f(i * s, i * s, 0)), random.nextInt(0, 4), dTextureCoordinates));
        }
        for (int i = 0; i < times; i++) {
            batchedRenderer.scene.add(new QuadModel(new Transform(new Vector3f(-(i * s), 0, 0), new Vector3f(i * s, i * s, 0)), random.nextInt(0, 4), dTextureCoordinates));
        }
        for (int i = 0; i < times; i++) {
            batchedRenderer.scene.add(new QuadModel(new Transform(new Vector3f(0, i * s, 0), new Vector3f(i * s, i * s, 0)), random.nextInt(0, 4), dTextureCoordinates));
        }
        for (int i = 0; i < times; i++) {
            batchedRenderer.scene.add(new QuadModel(new Transform(new Vector3f(0, -(i * s), 0), new Vector3f(i * s, i * s, 0)), random.nextInt(0, 4), dTextureCoordinates));
        }

        window.run = params -> {
            System.out.println(window.calculatedFPS);
            batchedRenderer.render();
        };

        window.loop();

        window.dispose();
    }

}
