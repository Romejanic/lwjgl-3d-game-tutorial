package org.tutorial.game.renderer;

import static org.lwjgl.opengl.GL40.*;

import org.lwjgl.opengl.GL;

public class Renderer {

    private Shader testShader;
    private Mesh mesh;

    public void init() {
        GL.createCapabilities();
        glClearColor(0.4f, 0.6f, 0.9f, 1.0f);

        this.testShader = Shader.get("test");
        this.mesh = new Mesh(new float[] {
            -0.5f, -0.5f, 0.0f,
             0.0f,  0.5f, 0.0f,
             0.5f, -0.5f, 0.0f
        });
    }

    public void render(int width, int height) {
        glViewport(0, 0, width, height);
        glClear(GL_COLOR_BUFFER_BIT);

        this.testShader.bind();
        this.mesh.draw();
        this.testShader.unbind();
    }

    public void destroy() {
        Shader.deleteAll();
        Mesh.deleteAll();
    }

}
