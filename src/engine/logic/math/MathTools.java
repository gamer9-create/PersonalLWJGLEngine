package engine.logic.math;

import org.joml.*;

public class MathTools {

    public static float[] generateQuadShape(float fx, float fy, float fw, float fh, int id) {
        float x = fx - (fw / 2);
        float y = fy - (fh / 2);
        return new float[] {
            x, y, id,
            x + fw, y, id,
            x + fw, y + fh, id,
            x, y + fh, id
        };
    }

    public static int[] generateQuadEdges(int shapes) {
        int[] elementArray = new int[shapes * 6];

        for (int i=0; i < shapes; i++) {
            processEdge(elementArray, i);
        }

        return elementArray;
    }

    private static void processEdge(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset;
        elements[offsetArrayIndex + 3] = offset;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public static float clamp(float min, float max, float val) {
        float fVal = val;

        if (fVal > max) {
            fVal = max;
        }

        if (fVal < min) {
            fVal = min;
        }

        return fVal;
    }

}
