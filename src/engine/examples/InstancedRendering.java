package engine.examples;

import engine.logic.math.*;

import engine.render.globjects.*;

import engine.render.renderers.b2d.InstancedRenderer;
import engine.windowing.*;

import org.joml.*;

import static org.lwjgl.opengl.GL15.*;

public class InstancedRendering {

    public static void main(String[] args) {
        // Instanced Rendering - 02 Example
        /*
        This example tells you how to render using instanced rendering,
        which helps speed up performance.
        */

        /*
        Here, we create our GLFW window that we can display our game on.
        It's like an art canvas for our game.
        */
        Window window = new Window(800, 600, "LWJGL3 Instancing");

        /*
        This is the wireframe. The wireframe tells OpenGL the shape of the object we want to render.
        The wireframe is like a greyscale version of the painting you want to paint in your head; the basic shape of it.
        */
        Wireframe base = new Wireframe(new float[] {
                /*
                A square is made up of 4 points,
                so we'll specify those points using the OpenGL coordinate system.
                 */
                -1, -1, 0, // Bottom left point
                1, -1, 1, // Bottom right point
                1, 1, 2, // Up right point
                -1, 1, 3 // Up left point
        }, new int[] {
                /*
                In order to render a square, we need to render 2 triangles first.
                The first three indices tell OpenGL the points required to render the first triangle,
                and the rest are for the second triangle.
                 */
                3, 2, 0, 0, 2, 1
        }, GL_STATIC_DRAW);

        /*
        Here, the renderer is created.
        The renderer is like the painter who's going to be painting our game.
        However, this renderer is alike a normal renderer.
        A painter paints every object or background one-by-one.
        An instanced painter has a magic ability that allows him to draw possibly millions of objects all at once.
         */
        InstancedRenderer instancedRenderer = new InstancedRenderer(window, base);

        /*
        Here, we add all the objects we want to render to the instanced renderer,
        so our magical painter can show us his magic ability in action!
         */
        instancedRenderer.scene.add(new QuadModel(new Transform(new Vector3f(0, 0, 0), new Vector3f(50, 50, 0)), 0, new float[] {
                0, 1,
                1, 1,
                1, 0,
                0, 0
        }));

        /*
        This is the window's run function,
        which allows us to calculate logic in the main loop.
        This is the function that actually makes the painter paint.
         */
        window.run = params -> {
            instancedRenderer.render();
        };

        /*
        Here, we begin the window's game loop.
        This loop repeatedly fires draw calls and processes logic.
        It sends a signal to the painter to begin painting.
         */
        window.loop();

        /*
        When the user decides to close the program,
        everything in our game is disposed of and removed from memory.
         */
        window.dispose();
        instancedRenderer.dispose();
    }

}
