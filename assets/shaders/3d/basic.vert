#version 330

in vec3 vertices;
in vec2 textures;

out vec2 fTextures;

uniform mat4 camera;
uniform mat4 object;
uniform mat4 projection;

void main() {
    fTextures = textures;
    gl_Position = projection * camera * object * vec4(vertices, 1);
}
