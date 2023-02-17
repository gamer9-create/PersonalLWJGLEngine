package engine.render.globjects;

import engine.logic.filesystem.*;

import org.joml.*;

import java.nio.*;
import java.util.*;

import org.lwjgl.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    public int program;
    public int vertexShader;
    public int fragmentShader;

    public HashMap<String, Integer> uniforms;

    public Shader(String vsFilename, String fsFilename, FileManager fileManager) {
        this.program = glCreateProgram();
        this.uniforms = new HashMap<>();

        this.vertexShader = glCreateShader(GL_VERTEX_SHADER);
        this.fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(this.vertexShader, fileManager.readRawFile(vsFilename));
        glShaderSource(this.fragmentShader, fileManager.readRawFile(fsFilename));
        glCompileShader(this.vertexShader);
        glCompileShader(this.fragmentShader);

        glAttachShader(this.program, this.vertexShader);
        glAttachShader(this.program, this.fragmentShader);

        glBindAttribLocation(this.program, 0, "vertices");
        glBindAttribLocation(this.program, 1, "textures");

        glLinkProgram(this.program);

        glValidateProgram(this.program);

        try {
            if (glGetShaderi(this.vertexShader, GL_COMPILE_STATUS) != 1) {
                throw new RuntimeException("Couldn't load VShader filepath" + vsFilename + " Error: ---- \n" + glGetShaderInfoLog(this.vertexShader));
            }
            if (glGetShaderi(this.fragmentShader, GL_COMPILE_STATUS) != 1) {
                throw new RuntimeException("Couldn't load FShader filepath" + fsFilename + " Error: ---- \n" + glGetShaderInfoLog(this.fragmentShader));
            }
            if (glGetProgrami(this.program, GL_LINK_STATUS) != 1) {
                throw new RuntimeException("Couldn't link program! VSID: " + this.vertexShader + " FSID: " + this.fragmentShader + " VSF: " + vsFilename + " FSF: " + fsFilename +
                " Error ---- \n" + glGetProgramInfoLog(this.program));
            }
            if (glGetProgrami(this.program, GL_VALIDATE_STATUS) != 1) {
                throw new RuntimeException("Couldn't validate program! VSID: " + this.vertexShader + " FSID: " + this.fragmentShader + " VSF: " + vsFilename + " FSF: " + fsFilename +
                        " Error ---- \n" + glGetProgramInfoLog(this.program));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bind() {
        glUseProgram(this.program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void createUniform(String name) {
        this.uniforms.put(name, glGetUniformLocation(this.program, name));
    }

    public void setUniformMatrix4x4f(String name, Matrix4f matrix) {
        FloatBuffer fmatrix = BufferUtils.createFloatBuffer(16);
        matrix.get(fmatrix);
        glUniformMatrix4fv(this.uniforms.get(name), false, fmatrix);
    }
    public void setUniformInt(String name, int value) {
        glUniform1i(this.uniforms.get(name), value);
    }

    public void setUniformVec3(String name, Vector3f vec3) {
        glUniform3f(this.uniforms.get(name), vec3.x, vec3.y, vec3.z);
    }

    public void setUniformIntArray(String name, int[] array) {
        glUniform1iv(this.uniforms.get(name), array);
    }

    public void setUniformFloatArray(String name, float[] array) {
        glUniform1fv(this.uniforms.get(name), array);
    }

    public void dispose() {
        glDeleteShader(this.vertexShader);
        glDeleteShader(this.fragmentShader);
        glDeleteProgram(this.program);
    }
}
