package engine.windowing;

import engine.logic.filesystem.*;
import engine.logic.structure.*;

import org.lwjgl.opengl.*;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;

public class Window {
    public long window;
    public int windowWidth;
    public int windowHeight;
    public String windowTitle;

    public float deltaTime;
    public float beginMSTime;
    public float lastMSTime;
    public float calculatedFPS;

    public ObjectFunction run;
    public Object[] inputParameters;

    public InputController inputController;

    public FileManager fileManager;

    public GLFWVidMode videoMode;

    public Window(int width, int height, String title) {
        this.windowWidth = width;
        this.windowHeight = height;
        this.windowTitle = title;

        this.run = params -> {

        };

        this.inputParameters = new Object[10];

        this.fileManager = new FileManager();

        if (!glfwInit()) {
            throw new RuntimeException("Couldn't initialize GLFW!");
        }

        this.window = glfwCreateWindow(this.windowWidth, this.windowHeight, this.windowTitle, 0, 0);

        glfwMakeContextCurrent(this.window);
        GL.createCapabilities();

        this.videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        assert videoMode != null;
        glfwSetWindowPos(this.window, videoMode.width() / 2 - this.windowWidth / 2, videoMode.height() / 2 - this.windowHeight / 2);

        this.inputController = new InputController(this);

        glfwSwapInterval(1);
    }

    public void loop() {
        this.beginMSTime = (float) glfwGetTime();
        this.deltaTime = -1.0f;

        while (!glfwWindowShouldClose(this.window)) {
            glfwPollEvents();

            this.run.function(this.inputParameters);

            this.lastMSTime = (float) glfwGetTime();
            this.deltaTime = this.lastMSTime - this.beginMSTime;
            this.beginMSTime = this.lastMSTime;

            this.calculatedFPS = Math.round(1 / this.deltaTime);

            glfwSwapBuffers(this.window);
        }
    }

    public void dispose() {
        glfwFreeCallbacks(this.window);
        glfwDestroyWindow(this.window);
        glfwTerminate();
    }

}
