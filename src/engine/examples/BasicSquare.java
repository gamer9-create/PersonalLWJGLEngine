package engine.examples;

import engine.logic.math.*
        ;
import engine.render.globjects.*;

import engine.render.renderers.b3d.Renderer;
import engine.windowing.*;

import org.joml.*;

import static org.lwjgl.opengl.GL15.*;

public class BasicSquare {
    public static void main(String[] args) {
        // Basic Square - 01 Example
        /*
        This example tells you how to render a basic square on the screen.
        */

        /*
        Here, we create our GLFW window that we can display our game on.
        It's like an art canvas for our game.
         */
        Window window = new Window(800, 600, "LWJGL3 Square");

        /*
        Here, the renderer is created.
        The renderer is like the painter who's going to be painting our game.
         */
        Renderer renderer = new Renderer(window);

        /*
        This is the wireframe. The wireframe tells OpenGL the shape of the object we want to render.
        The wireframe is like a greyscale version of the painting you want to paint in your head; the basic shape of it.
         */
        Wireframe wireframe = new Wireframe(new float[] {
                /*
                A square is made up of 4 points,
                so we'll specify those points using the OpenGL coordinate system.
                 */
                -0.1f, -0.1f, 0, // Bottom left point
                0.1f, -0.1f, 0, // Bottom right point
                0.1f, 0.1f, 0, // Up right point
                -0.1f, 0.1f, 0 // Up left point
        }, new int[] {
                /*
                In order to render a square, we need to render 2 triangles first.
                The first three indices tell OpenGL the points required to render the first triangle,
                and the rest are for the second triangle.
                 */
                3, 2, 0, 0, 2, 1
        }, GL_STATIC_DRAW);

        /*
        These are our texture coordinates.
        They tell the painter how to put textures on our square and what parts of the texture to display.
         */
        wireframe.createTextureBuffer(new float[] {
                0, 1,
                1, 1,
                1, 0,
                0, 0
        });

        /*
        Here, we add a wireframe, transform and texture to the renderer.
        The transform tells our painter where to draw our square as well as what size to paint the square at.
        The texture tells the painter what to put on top of the square.
        The renderer fires draw calls to OpenGL, so we can see our wonderful square!
        This is when the painter gets the instructions on what to draw.
         */
        renderer.rawModels.add(new RawModel(wireframe, new Transform(new Vector3f(0, 0, 0), new Vector3f(1, 1, 0)), new Texture("./assets/textures/colors.png")));

        /*
        This is the window's run function,
        which allows us to calculate logic in the main loop.
        This is the function that actually makes the painter paint.
         */
        window.run = params -> {renderer.render(); System.out.println(window.calculatedFPS);};

        /*
        Here, we begin the window's game loop.
        This loop repeatedly fires draw calls and processes logic.
        It sends a signal to the painter to begin painting.
         */
        window.loop();

        /*
        When the user decides to close the program, our shader, wireframe, and GLFW window is disposed and removed from memory.
        This is when the painter finishes with his masterpiece, and cleans up his clothes then washes his brush.
         */
        wireframe.dispose();
        renderer.dispose();
        window.dispose();

    }
}