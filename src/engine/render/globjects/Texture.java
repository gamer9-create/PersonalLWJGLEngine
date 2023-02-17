package engine.render.globjects;

import javax.imageio.*;

import java.awt.image.*;
import java.io.*;
import java.nio.*;

import org.lwjgl.*;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    public static int globalSamplerID;
    public int samplerID;
    public int textureID;
    public int width;
    public int height;

    public Texture(String filepath) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(filepath, width, height, channels, 4);

        this.width = width.get(0);
        this.height = height.get(0);

        this.samplerID = Texture.globalSamplerID;

        Texture.globalSamplerID += 1;

        this.textureID = glGenTextures();
        this.bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        if (image != null) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                    0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        } else {
            System.out.println("Unable to load texture.");
        }

        assert image != null;
        stbi_image_free(image);
    }

    public Texture(String filepath, int f) {
        BufferedImage bufferedTexture = null;

        try {
            bufferedTexture = ImageIO.read(new File(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert bufferedTexture != null;
        int btWidth = bufferedTexture.getWidth();
        int btHeight = bufferedTexture.getHeight();
        int[] btPixels = bufferedTexture.getRGB(0, 0, btWidth, btHeight, null, 0, btWidth);

        this.width = btWidth;
        this.height = btHeight;

        ByteBuffer byteTexBuffer = BufferUtils.createByteBuffer((btWidth * btHeight) * 4);
        textureID = glGenTextures();

        for (int i = 0; i < btPixels.length; i++) {
            byte red = (byte) ((btPixels[i] >> 16) & 0xFF);
            byte green = (byte) ((btPixels[i] >> 8) & 0xFF);
            byte blue = (byte) ((btPixels[i]) & 0xFF);
            byte a = 1;

            byteTexBuffer.put(red);
            byteTexBuffer.put(green);
            byteTexBuffer.put(blue);
            byteTexBuffer.put(a);
        }

        byteTexBuffer.flip();
        this.bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, btWidth, btHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteTexBuffer);

        this.unbind();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, this.textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void dispose() {
        glDeleteTextures(this.textureID);
    }

}
