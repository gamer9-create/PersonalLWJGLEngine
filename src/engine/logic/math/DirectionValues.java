package engine.logic.math;

import org.joml.*;

import java.util.*;

public class DirectionValues {
    public static HashMap<Direction, Vector3f> directions;

    static {
        directions = new HashMap<>();
        directions.put(Direction.UP, new Vector3f(0, 1, 0));
        directions.put(Direction.DOWN, new Vector3f(0, -1, 0));
        directions.put(Direction.LEFT, new Vector3f(-1, 0, 0));
        directions.put(Direction.RIGHT, new Vector3f(1, 0, 0));
        directions.put(Direction.FORWARD, new Vector3f(0, 0, 1));
        directions.put(Direction.BACKWARD, new Vector3f(0, 0, -1));
    }
}
