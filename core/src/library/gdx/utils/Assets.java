package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.mazatech.gdx.SVGAssetsConfigGDX;
import com.mazatech.gdx.SVGAssetsGDX;
import com.mazatech.gdx.SVGTexture;

public class Assets implements Disposable {
    private final FileHandle directory;
    private final TextureMap TextureMap;

    private SVGAssetsConfigGDX svgAssetsConfigGDX;
    private SVGAssetsGDX svgAssetsGDX;
    private final SVGTextureMap svgTextureMap;
    private final SoundMap soundMap;
    private final MusicMap musicMap;

    private final ShaderMap shaderMap;

    protected Assets(String path, Files.FileType fileType){
        this.directory=Gdx.files.getFileHandle(path,fileType);
        this.TextureMap =new TextureMap(directory.child("/textures"));
        svgAssetsConfigGDX=new SVGAssetsConfigGDX(Gdx.graphics.getBackBufferWidth(),Gdx.graphics.getBackBufferHeight(),Gdx.graphics.getPpiX());
        svgAssetsGDX=new SVGAssetsGDX(svgAssetsConfigGDX);
        this.svgTextureMap =new SVGTextureMap(directory.child("/svg"),svgAssetsGDX);
        this.soundMap=new SoundMap(directory.child("/sounds"));
        this.musicMap=new MusicMap(directory.child("/musics"));
        this.shaderMap=new ShaderMap(directory.child("/shaders"));
    }

    public TextureRegion findRegion(String name){
        return TextureMap.findRegion(name);
    }

    public Texture findTexture(String name){
        return TextureMap.findTexture(name);
    }
    public Sound findSound(String name){
        return soundMap.findSound(name);
    }

    public Music findMusic(String name){
        return musicMap.findMusic(name);
    }

    public SVGTexture findSVGTexture(String name){
        return svgTextureMap.findAsset(name);
    }

    public ShaderProgram findShader(String name){
        return shaderMap.findShader(name);
    }

    public SVGAssetsConfigGDX getSvgAssetsConfigGDX() {
        return svgAssetsConfigGDX;
    }

    public SVGAssetsGDX getSvgAssetsGDX() {
        return svgAssetsGDX;
    }

    public SVGTextureMap getSvgTextureMap() {
        return svgTextureMap;
    }

    public SoundMap getSoundMap() {
        return soundMap;
    }

    public MusicMap getMusicMap() {
        return musicMap;
    }

    public ShaderMap getShaderMap() {
        return shaderMap;
    }

    @Override
    public void dispose() {
        TextureMap.dispose();
        soundMap.dispose();
        musicMap.dispose();
        shaderMap.dispose();
    }
}
