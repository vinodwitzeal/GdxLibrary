#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform float smoothing;

uniform int shadowEnable;
uniform vec2 shadowOffset;
uniform vec4 shadowColor;
uniform float shadowSmoothing;

uniform int outlineEnable;
uniform float outlineDistance;
uniform vec4 outlineColor;


void main() {
    float distance = texture2D(u_texture, v_texCoords).a;
    float alpha = smoothstep(0.5 - smoothing, 0.5 + smoothing, distance);
    vec4 text = vec4(v_color.rgb, v_color.a * alpha);
    vec4 resultText=text;


    if(outlineEnable>0){
        float outlineFactor = smoothstep(0.5 - smoothing, 0.5 + smoothing, distance);
        vec4 color = mix(outlineColor, v_color, outlineFactor);
        float alpha = smoothstep(outlineDistance - smoothing, outlineDistance + smoothing, distance);
        resultText= vec4(color.rgb, color.a * alpha*v_color.a);
    }

    if(shadowEnable>0){
        float shadowDistance = texture2D(u_texture, v_texCoords).a;
        float shadowAlpha = smoothstep(0.5 - shadowSmoothing, 0.5 + shadowSmoothing, shadowDistance);
        vec4 shadow = vec4(shadowColor.rgb, shadowColor.a*shadowAlpha*v_color.a);
        resultText = mix(shadow,resultText,resultText.a);
    }

    gl_FragColor=resultText;
}