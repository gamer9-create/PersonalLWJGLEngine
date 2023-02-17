package engine.examples.exampleplatformer;

import engine.logic.math.*;

import engine.render.globjects.*;

import engine.render.renderers.b2d.BatchedRenderer;
import engine.windowing.*;

import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

public class ExamplePlatformerMain {

    public static void main(String[] args) {
        Window window = new Window(1280, 720, "2D Platformer");

        BatchedRenderer renderer = new BatchedRenderer(window, 100);

        renderer.textures[0] = new Texture("./assets/textures/examples/platformer/blocktex.png");
        renderer.textures[1] = new Texture("./assets/textures/examples/platformer/blocktex2.png");
        renderer.textures[2] = new Texture("./assets/textures/examples/platformer/player.png");

        float[] dTextureCoordinates = new float[] {
                0, 1,
                1, 1,
                1, 0,
                0, 0
        };

        QuadModel groundModel = new QuadModel(new Transform(new Vector3f(0, -600, 0), new Vector3f(6000, 800, 0)), 0, null);
        groundModel.textureCoordinates = new float[] {
                0, groundModel.transform.size.y / 50,
                groundModel.transform.size.x / 50, groundModel.transform.size.y / 50,
                groundModel.transform.size.x / 50, 0,
                0, 0
        };

        QuadModel floatingPlatformModel = new QuadModel(new Transform(new Vector3f(300, 0, 0), new Vector3f(400, 50, 0)), 1, null);
        floatingPlatformModel.textureCoordinates = new float[] {
                0, floatingPlatformModel.transform.size.y / 50,
                floatingPlatformModel.transform.size.x / 50, floatingPlatformModel.transform.size.y / 50,
                floatingPlatformModel.transform.size.x / 50, 0,
                0, 0
        };

        QuadModel floatingPlatformModel2 = new QuadModel(new Transform(new Vector3f(-10, -176.52585f, 0), new Vector3f(400, 50, 0)), 1, null);
        floatingPlatformModel2.textureCoordinates = new float[] {
                0, floatingPlatformModel2.transform.size.y / 50,
                floatingPlatformModel2.transform.size.x / 50, floatingPlatformModel2.transform.size.y / 50,
                floatingPlatformModel2.transform.size.x / 50, 0,
                0, 0
        };

        QuadModel player = new QuadModel(new Transform(new Vector3f(0, 0, 0), new Vector3f(50, 50, 0)), 2, dTextureCoordinates);

        renderer.scene.add(groundModel);
        renderer.scene.add(floatingPlatformModel);
        renderer.scene.add(floatingPlatformModel2);
        renderer.scene.add(player);

        int gravity = 300;
        int speed = 100;

        window.run = params -> {

            boolean fall = true;
            Direction movementDirection = null;

            if (window.inputController.isKeyDown(GLFW_KEY_A)) {
                movementDirection = Direction.LEFT;
            }

            if (window.inputController.isKeyDown(GLFW_KEY_D)) {
                movementDirection = Direction.RIGHT;
            }

            for (QuadModel model : renderer.scene) {
                if (model != null && model != player) {
                    if (player.rectangle.testCollisionWithDirection(model.rectangle, Direction.DOWN)) {
                        fall = false;
                    }
                    if (player.rectangle.testCollisionWithDirection(model.rectangle, Direction.LEFT) && movementDirection == Direction.LEFT) {
                        movementDirection = null;
                    }
                    if (player.rectangle.testCollisionWithDirection(model.rectangle, Direction.RIGHT) && movementDirection == Direction.RIGHT) {
                        movementDirection = null;
                    }
                }
            }

            if (movementDirection != null)
                player.transform.position.add(DirectionValues.directions.get(movementDirection));

            if (fall)
                player.transform.position.y -= (gravity * window.deltaTime);

            player.update();

            renderer.render();
        };

        window.loop();
        window.dispose();
    }

}
