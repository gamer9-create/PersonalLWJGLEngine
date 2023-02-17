#version 330

in vec3 vertices;
in vec2 textures;

out vec3 fVertex;
out vec2 fTextures;

uniform mat4 projection;

void main() {
    fVertex = vertices;
    fTextures = textures;
    gl_Position = projection * vec4(vertices, 1);
}
