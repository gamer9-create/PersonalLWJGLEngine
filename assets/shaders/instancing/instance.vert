#version 440

in vec3 vertices;

out vec3 color;
out vec4 fInfo;

uniform mat4[64] projections;
uniform vec3[64] colors;
uniform float[64][8] textureCoordinates;
uniform int[64] samplerIDs;
uniform int usingColors;

void main() {
    color = colors[gl_InstanceID];
    int usingColorsF = usingColors;
    int samplerIndexF = samplerIDs[gl_InstanceID];
    vec2 textureCoordinate = vec2(textureCoordinates[gl_InstanceID][(int(vertices.z) * 2)], textureCoordinates[gl_InstanceID][((int(vertices.z) * 2)) + 1]);
    fInfo = vec4(usingColorsF, samplerIndexF, textureCoordinate.x, textureCoordinate.y);

    gl_Position = projections[gl_InstanceID] * vec4(vertices.x, vertices.y, 0, 1);
}