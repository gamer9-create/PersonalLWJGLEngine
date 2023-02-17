package engine.render.tools;

import engine.render.globjects.*;

import static org.lwjgl.opengl.GL45.*;

public class TextureHandyman {

    public static void bindTextureList(Texture[] textures) {
        for (int i = 0; i < textures.length; i++) {
            if (textures[i] != null) {
                glActiveTexture(GL_TEXTURE0 + i);
                textures[i].bind();
            }
        }
    }

    public static void setShaderSamplers(Shader shader, int length) {
        for (int i = 0; i < length; i++) {
            if (shader.uniforms.get("samplers[" + i + "]") == null)
                shader.createUniform("samplers[" + i + "]");
            shader.setUniformInt("samplers[" + i + "]", i);
        }
    }

}
