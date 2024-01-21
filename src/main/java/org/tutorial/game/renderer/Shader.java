package org.tutorial.game.renderer;

import static org.lwjgl.opengl.GL40.*;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import org.tutorial.game.utils.Resources;

public class Shader {

    private static final Map<String, Shader> shaderMap = new HashMap<>();
    
    private int program;
    
    private Shader(String name) throws IOException {
        int vs = loadShader(name + "_vs", GL_VERTEX_SHADER);
        int fs = loadShader(name + "_fs", GL_FRAGMENT_SHADER);
        this.program = glCreateProgram();

        glAttachShader(this.program, vs);
        glAttachShader(this.program, fs);
        glLinkProgram(this.program);
        if(glGetProgrami(this.program, GL_LINK_STATUS) != GL_TRUE) {
            String log = glGetProgramInfoLog(this.program);
            glDeleteShader(vs);
            glDeleteShader(fs);
            glDeleteProgram(program);
            throw new RuntimeException("Failed to link program!\n" + log);
        }

        glDetachShader(this.program, vs);
        glDetachShader(this.program, fs);
        glDeleteShader(vs);
        glDeleteShader(fs);
    }

    public void bind() {
        glUseProgram(this.program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    private void delete() {
        glDeleteProgram(this.program);
    }

    private int loadShader(String file, int type) throws IOException {
        String source = Resources.readFull("shaders/" + file + ".glsl");
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);
        if(glGetShaderi(shader, GL_COMPILE_STATUS) != GL_TRUE) {
            String log = glGetShaderInfoLog(shader);
            glDeleteShader(shader);
            throw new RuntimeException("Failed to compile " + file + "!\n" + log);
        }
        return shader;
    }

    public static Shader get(String name) {
        if(shaderMap.containsKey(name)) return shaderMap.get(name);
        Shader shader = null;
        try {
            shader = new Shader(name);
        } catch (Exception e) {
            System.err.println("Failed to load shader " + name);
            e.printStackTrace();
        }
        shaderMap.put(name, shader);
        return shader;
    }

    public static void deleteAll() {
        for(Shader shader : shaderMap.values()) {
            shader.delete();
        }
        shaderMap.clear();
    }

}
