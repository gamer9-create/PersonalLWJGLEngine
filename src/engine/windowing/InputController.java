package engine.windowing;

import engine.logic.structure.*;

import org.joml.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;

import java.nio.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class InputController {
    public Window window;
    public HashMap<Integer, ObjectFunction> keyActions;
    public HashMap<Integer, ObjectFunction> mouseActions;

    public InputController(Window window) {
        this.window = window;
        this.keyActions = new HashMap<>();
        this.mouseActions = new HashMap<>();

        glfwSetKeyCallback(this.window.window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (keyActions.get(key) != null) {
                    keyActions.get(key).function(new Object[] {window, key, scancode, action, scancode});
                }
            }
        });

        glfwSetMouseButtonCallback(this.window.window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (mouseActions.get(button) != null) {
                    mouseActions.get(button).function(new Object[] {window, button, action, mods});
                }
            }
        });
    }

    public boolean isKeyDown(int key) {
        return glfwGetKey(this.window.window, key) != 0;
    }

    public boolean isKeyUp(int key) {
        return glfwGetKey(this.window.window, key) != 1;
    }

    public Vector2f getMousePosition() {
        DoubleBuffer mouseX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer mouseY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(this.window.window, mouseX, mouseY);
        return new Vector2f((float) mouseX.get(0), (float) mouseY.get(0));
    }
}
