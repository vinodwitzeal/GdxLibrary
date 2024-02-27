#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform int u_type;

uniform sampler2D u_mask;
void main(void) {
    vec4 c = v_color * texture2D(u_texture, v_texCoords);
    if(u_type==0){
    float grey = (c.r + c.g + c.b) / 3.0;
    gl_FragColor = vec4(grey, grey, grey, c.a);
    gl_FragColor =vec4(mix(gl_FragColor.rgb,vec3(1.0),0.75),c.a);

    }else{
       gl_FragColor =vec4(mix(c.rgb,vec3(1.0),0.75),c.a);
    }

}