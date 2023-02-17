#version 330

in vec3 fVertex;
in vec2 fTextures;
uniform sampler2D[8] samplers;

void main() {
    gl_FragColor = texture2D(samplers[int(fVertex.z)], fTextures);
}