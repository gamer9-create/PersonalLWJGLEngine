package engine.logic.math;

import org.jbox2d.common.*;

import org.joml.*;

import java.io.*;

import java.lang.Math;

import static engine.logic.math.MathTools.*;

public class Rectangle implements Serializable {
    public int x;
    public int y;
    public int width;
    public int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Transform transform) {
        this((int) transform.position.x, (int) transform.position.y, (int) transform.size.x, (int) transform.size.y);
    }

    public Rectangle(int width, int height) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
    }

    public boolean isCollidingXAxis(Rectangle otherRectangle) {
        return (Math.abs(this.x - otherRectangle.x) <= (this.width/2)+(otherRectangle.width/2));
    }

    public boolean isCollidingYAxis(Rectangle otherRectangle) {
        return (Math.abs(this.y - otherRectangle.y) <= (this.height/2)+(otherRectangle.height/2));
    }

    public boolean isColliding(Rectangle otherRectangle) {
        return (isCollidingXAxis(otherRectangle) && isCollidingYAxis(otherRectangle));
    }

    public Vector2f getCollisionDirection(Rectangle rectangle, Rectangle otherRectangle) {
        return new Vector2f(clamp(-1, 1, rectangle.x - otherRectangle.x), clamp(-1, 1, rectangle.y - otherRectangle.y));
    }

    public Direction[] getCollisionDirectionAsDirections(Rectangle rectangle, Rectangle otherRectangle) {
        Direction[] directions = new Direction[6];
        int nextIndex = 0;
        Vector2f cDirection = this.getCollisionDirection(rectangle, otherRectangle);

        if (cDirection.x == -1) {
            directions[nextIndex] = Direction.RIGHT;
            nextIndex += 1;
        }
        if (cDirection.x == 1) {
            directions[nextIndex] = Direction.LEFT;
            nextIndex += 1;
        }
        if (cDirection.y == -1) {
            directions[nextIndex] = Direction.UP;
            nextIndex += 1;
        }
        if (cDirection.y == 1) {
            directions[nextIndex] = Direction.DOWN;
        }

        return directions;
    }

    public boolean testCollisionWithOffset(Rectangle otherRectangle, Vector3f offset) {
        Rectangle fRectangle = this.recreate();
        fRectangle.x -= (offset.x * 2);
        fRectangle.y -= (offset.y * 2);
        return fRectangle.isColliding(otherRectangle);
    }

    public boolean testCollisionWithDirection(Rectangle otherRectangle, Direction direction) {
        return testCollisionWithOffset(otherRectangle, DirectionValues.directions.get(direction));
    }

    public void correct(Rectangle otherRectangle) {
        int xDistance = Math.abs(this.x - otherRectangle.x);
        int yDistance = Math.abs(this.y - otherRectangle.y);

        if (xDistance < (this.width/2) + (otherRectangle.width/2)) {
            xDistance = (this.width/2)+(otherRectangle.width/2);
        }
        if (yDistance < (this.height/2) + (otherRectangle.height/2)) {
            yDistance = (this.height/2) + (otherRectangle.height/2);
        }

        
    }

    public Rectangle recreate() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void setToTransform(Transform transform) {
        this.x = (int) transform.position.x;
        this.y = (int) transform.position.y;
        this.width = (int) transform.size.x;
        this.height = (int) transform.size.y;
    }

    public Vec2 getPositionAsBox2DVector() {
        return new Vec2(this.x / 100f, this.y / 100f);
    }
}
