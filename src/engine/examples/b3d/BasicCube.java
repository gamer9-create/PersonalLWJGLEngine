package engine.examples.b3d;

import engine.logic.math.*;
import engine.render.globjects.*;
import engine.render.renderers.b3d.*;
import engine.windowing.*;
import org.joml.*;

import static org.lwjgl.opengl.GL15.*;

public class BasicCube {

    public static void main(String[] args) {
        Window window = new Window(800, 800, "LWJGL Cube");

        Renderer renderer = new Renderer(window);

        RawModel rawModel = new RawModel(
                new Wireframe(new float[] {
                        //Back face
                        -0.5f,0.5f,-0.5f,
                        -0.5f,-0.5f,-0.5f,
                        0.5f,-0.5f,-0.5f,
                        0.5f,0.5f,-0.5f,

                        //Front face
                        -0.5f,0.5f,0.5f,
                        -0.5f,-0.5f,0.5f,
                        0.5f,-0.5f,0.5f,
                        0.5f,0.5f,0.5f,

                        //Right face
                        0.5f,0.5f,-0.5f,
                        0.5f,-0.5f,-0.5f,
                        0.5f,-0.5f,0.5f,
                        0.5f,0.5f,0.5f,

                        //Left face
                        -0.5f,0.5f,-0.5f,
                        -0.5f,-0.5f,-0.5f,
                        -0.5f,-0.5f,0.5f,
                        -0.5f,0.5f,0.5f,

                        //Top face
                        -0.5f,0.5f,0.5f,
                        -0.5f,0.5f,-0.5f,
                        0.5f,0.5f,-0.5f,
                        0.5f,0.5f,0.5f,

                        //Bottom face
                        -0.5f,-0.5f,0.5f,
                        -0.5f,-0.5f,-0.5f,
                        0.5f,-0.5f,-0.5f,
                        0.5f,-0.5f,0.5f
                        },
                        new int[] {
                                //Back face
                                0, 1, 3,
                                3, 1, 2,

                                //Front face
                                4, 5, 7,
                                7, 5, 6,

                                //Right face
                                8, 9, 11,
                                11, 9, 10,

                                //Left face
                                12, 13, 15,
                                15, 13, 14,

                                //Top face
                                16, 17, 19,
                                19, 17, 18,

                                //Bottom face
                                20, 21, 23,
                                23, 21, 22
                }, GL_STATIC_DRAW).createTextureBuffer(new float[] {
                        //Back face
                        0, 0,
                        0, 1,
                        1, 1,
                        1, 0,

                        //Front face
                        0, 0,
                        0, 1,
                        1, 1,
                        1, 0,

                        //Right face
                        0, 0,
                        0, 1,
                        1, 1,
                        1, 0,

                        //Left face
                        0, 0,
                        0, 1,
                        1, 1,
                        1, 0,

                        //Top face
                        0, 0,
                        0, 1,
                        1, 1,
                        1, 0,

                        //Bottom face
                        0, 0,
                        0, 1,
                        1, 1,
                        1, 0,
                }),
                new Transform(new Vector3f(1, 0, -2), new Vector3f(1, 1, 1)),
                new Texture("./assets/textures/colors.png"));

        renderer.apply3DEffect();
        renderer.rawModels.add(rawModel);
        window.run = params -> {
            renderer.render();
        };
        window.loop();

        window.dispose();
    }

}
