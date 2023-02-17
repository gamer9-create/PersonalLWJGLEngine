package engine.render.globjects;

import static org.lwjgl.opengl.GL30.*;

public class Framebuffer {
    public int width;
    public int height;

    public int frameBufferID;
    public int renderBufferID;

    public int textureID;

    public Framebuffer(int width, int height) {
        this.width = width;
        this.height = height;

        try {
            int fboID = glGenFramebuffers();
            glBindFramebuffer(GL_FRAMEBUFFER, fboID);

            int texture = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, texture);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height,
                    0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

            glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D,
                    texture, 0);

            int rboID = glGenRenderbuffers();
            glBindRenderbuffer(GL_RENDERBUFFER, rboID);
            glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
            glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboID);

            if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
                throw new RuntimeException("Couldn't complete framebuffer.");
            }
            glBindFramebuffer(GL_FRAMEBUFFER, 0);

            this.textureID = texture;
            this.frameBufferID = fboID;
            this.renderBufferID = rboID;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
