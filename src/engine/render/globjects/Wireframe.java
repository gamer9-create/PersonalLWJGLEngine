package engine.render.globjects;

import static org.lwjgl.opengl.GL31.*;

public class Wireframe {
    public float[] vertices;
    public float[] textureCoordinates;
    public int[] edges;

    public int vertexBuffer;
    public int textureCoordinateBuffer;
    public int edgeBuffer;
    public int renderLength;

    public int uploadType = GL_STATIC_DRAW;

    public Wireframe(float[] vertices, int[] edges, int uploadType) {
        this.vertexBuffer = glGenBuffers();
        this.edgeBuffer = glGenBuffers();

        this.vertices = vertices;
        this.edges = edges;
        this.renderLength = this.edges.length;
        this.uploadType = uploadType;

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, this.vertexBuffer);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBufferData(GL_ARRAY_BUFFER, this.vertices, this.uploadType);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.edgeBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.edges, this.uploadType);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public Wireframe createTextureBuffer(float[] textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
        this.textureCoordinateBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.textureCoordinateBuffer);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glBufferData(GL_ARRAY_BUFFER, this.textureCoordinates, this.uploadType);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return this;
    }

    public void render() {
        renderInstanced(1);
    }

    public void renderInstanced(int num) {

        glBindBuffer(GL_ARRAY_BUFFER, this.vertexBuffer);

        if (this.uploadType == GL_DYNAMIC_DRAW) {
            glBufferSubData(GL_ARRAY_BUFFER, 0, this.vertices);
        }

        glBindBuffer(GL_ARRAY_BUFFER, this.textureCoordinateBuffer);

        if (this.uploadType == GL_DYNAMIC_DRAW) {
            glBufferSubData(GL_ARRAY_BUFFER, 0, this.textureCoordinates);
        }

        glBindBuffer(GL_ARRAY_BUFFER, this.vertexBuffer);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.edgeBuffer);

        if (this.uploadType == GL_DYNAMIC_DRAW) {
            glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, this.edges);
        }

        glDrawElementsInstanced(GL_TRIANGLES, this.renderLength, GL_UNSIGNED_INT, 0, num);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

    }

    public void dispose() {
        glDeleteBuffers(this.vertexBuffer);
        glDeleteBuffers(this.edgeBuffer);

    }
}
