#version 330

in vec3 color;
in vec4 fInfo;

uniform sampler2D[8] samplers;

void main() {
    vec4 color = texture2D(samplers[int(fInfo.y)], vec2(fInfo.z, fInfo.w));

    gl_FragColor = color;
}