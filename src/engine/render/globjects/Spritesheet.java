package engine.render.globjects;

import java.util.*;

public class Spritesheet {
    public Texture mainTexture;
    public HashMap<String, Float[]> textureCoordinates;

    public Spritesheet(Texture mainTexture) {
        this.mainTexture = mainTexture;
        this.textureCoordinates = new HashMap<>();
    }

    public float[] generateTextureCoordinates(int x, int y, int width, int height) {
        int imageW = this.mainTexture.width;
        int imageH = this.mainTexture.height;
        float xUnit = ((1f / imageW) * x) + ((1f / imageW) * width);
        float yUnit = ((1f / imageH) * y) + ((1f / imageH) * height);

        return new float[] {
                (1f / imageW) * x, yUnit,
                xUnit, yUnit,
                xUnit, (1f / imageH) * y,
                (1f / imageW) * x, (1f/imageH) * y
        };
    }

}
