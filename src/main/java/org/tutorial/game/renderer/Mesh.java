package org.tutorial.game.renderer;

import static org.lwjgl.opengl.GL40.*;

import java.util.ArrayList;
import java.util.List;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

public class Mesh {

    private static final List<Mesh> allMeshes = new ArrayList<>();
    
    private int vao;
    private int vbo;
    private int vertexCount;

    public Mesh(float[] vertices) {
        this.vao = glGenVertexArrays();
        this.vbo = glGenBuffers();
        this.vertexCount = vertices.length / 3;

        try(MemoryStack stack = MemoryStack.stackPush()) {
            glBindVertexArray(this.vao);
            glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
            glBufferData(GL_ARRAY_BUFFER, stack.floats(vertices), GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }

        allMeshes.add(this);
    }

    public void draw() {
        glBindVertexArray(this.vao);
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLES, 0, this.vertexCount);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    public void delete() {
        glDeleteVertexArrays(this.vao);
        glDeleteBuffers(this.vbo);
        allMeshes.remove(this);
    }

    public static void deleteAll() {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            // delete all VAOs at once
            IntBuffer vaos = stack.callocInt(allMeshes.size());
            for(Mesh mesh : allMeshes) vaos.put(mesh.vao);
            vaos.flip();
            glDeleteVertexArrays(vaos);

            // delete all VBOs at once
            IntBuffer vbos = stack.callocInt(allMeshes.size());
            for(Mesh mesh : allMeshes) vbos.put(mesh.vbo);
            vbos.flip();
            glDeleteBuffers(vbos);
        }
        allMeshes.clear();
    }

}
