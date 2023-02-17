#version 330

in vec2 fTextures;
uniform sampler2D texture;

void main() {
    gl_FragColor = texture2D(texture, fTextures);
}