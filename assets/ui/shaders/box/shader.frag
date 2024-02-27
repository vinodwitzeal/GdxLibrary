#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

//Shape Types
const int type_none=0;
const int type_shadow=1;
const int type_box=2;
const int type_outline=3;

//Fill Types
const int fill_none=0;
const int fill_solid=1;
const int fill_linear=2;
const int fill_radial=3;

//Outline Types
const int outline_out=0;
const int outline_in=1;
const int outline_center=2;


varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

//Type to determine which operation to execute
uniform int u_type;
uniform int u_fill_type;
uniform int u_outline_type;

//Smoothness
uniform float u_smoothness;
uniform float u_shadow_smoothness;

//Colors
uniform vec4 u_solid;
uniform vec4 u_shadowColor;
uniform vec4 u_start;
uniform vec4 u_end;

//Dimensions, Corner Radius, Gradient Position, Angle and Radius
uniform vec2 u_size;
uniform float u_outline_size;
uniform vec4 u_radius;
uniform vec2 u_gradient_position;
uniform float u_gradient_angle;
uniform float u_gradient_radius;


float rounded_rect(vec2 center, vec2 size, vec4 radius)
{
    radius.xy = (center.x > 0.0) ? radius.xy : radius.zw;
    radius.x  = (center.y > 0.0) ? radius.x  : radius.y;

    vec2 q = abs(center)-size+radius.x;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - radius.x;
}

float fill_factor(float dst, float smoothness){
    return 1.0-smoothstep(-smoothness, smoothness, dst);
}

vec4 apply_factor(vec4 color, float f){
    return vec4(color.rgb, color.a*f);
}

float sdf_grow(float by, float dst){
    return dst+by;
}

vec4 composite(vec4 back, vec4 front){
    return mix(back, front, front.a);
}

float linear_distance(vec2 p0, vec2 pf)
{
    return sqrt((pf.x - p0.x) * (pf.x - p0.x) + (pf.y - p0.y) * (pf.y - p0.y));
}

float fill_sdf(float rectDistance,float outline,int outlineType){
    float growBy=outlineType==outline_out?outline:outlineType==outline_center?outline/2.0:0.0;
    return sdf_grow(growBy,rectDistance);
}

vec4 fill_color(int fill_type){
    if(fill_type==fill_solid){
        return u_solid;
    }else if(fill_type==fill_linear){
        vec2 uv =v_texCoords;
        uv -= u_gradient_position;
        float angle = radians(u_gradient_angle) + atan(uv.y, uv.x);
        float len = length(uv);
        uv = vec2(cos(angle) * len, sin(angle) * len) + u_gradient_position;
        return mix(u_end, u_start, smoothstep(0.0, 1.0, uv.x));
    }else if(fill_type==fill_radial){
        float distance =linear_distance(u_gradient_position, v_texCoords)/u_gradient_radius;
        if (distance>1.0){
            distance=1.0;
        }
        return mix(u_end, u_start, smoothstep(0.0, 1.0, distance));
    }else{
        return vec4(0.0);
    }
}

void main(){
    vec2 half_size=u_size.xy/2.0;
    float rect_distance=rounded_rect(v_texCoords*u_size-half_size, half_size, u_radius);
    vec4 result=vec4(0.0);
    if(u_type==type_shadow){
        rect_distance=sdf_grow(u_shadow_smoothness*0.5, rect_distance);
        result=vec4(u_shadowColor.rgb,u_shadowColor.a*fill_factor(rect_distance, u_shadow_smoothness));
//        result=composite(vec4(0.0), apply_factor(u_shadowColor, fill_factor(rect_distance, u_shadow_smoothness)));
    }else if(u_type==type_box){
        vec4 box_color=fill_color(u_fill_type);
        rect_distance=fill_sdf(rect_distance,u_outline_size,u_outline_type);
        result=apply_factor(box_color, fill_factor(rect_distance, u_smoothness));
    }else if(u_type==type_outline){
        vec4 outline_color=fill_color(u_fill_type);
        float border_rect_distance=sdf_grow(u_outline_size+u_smoothness, rect_distance);
        vec4 border_color=apply_factor(outline_color,1.0-fill_factor(border_rect_distance,u_smoothness));
        result=apply_factor(border_color, fill_factor(rect_distance,u_smoothness));
    }
    gl_FragColor=result*v_color;
}
