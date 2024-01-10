package org.tutorial.game.renderer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryStack;

public class Window {

    private static final int GL_VERSION_MAJOR = 4;
    private static final int GL_VERSION_MINOR = 0;

    private long window = NULL;
    private int fbWidth;
    private int fbHeight;

    public Window(String title, int width, int height) {
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit()) {
            throw new RuntimeException("Failed to initialise GLFW!");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, GL_VERSION_MAJOR);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, GL_VERSION_MINOR);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if(window == NULL) {
            throw new RuntimeException("Failed to create window!");
        }
        glfwMakeContextCurrent(window);
        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            this.fbWidth = w;
            this.fbHeight = h;
        });
        glfwShowWindow(window);

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer wb = stack.callocInt(1);
            IntBuffer hb = stack.callocInt(1);
            glfwGetFramebufferSize(window, wb, hb);
            this.fbWidth = wb.get();
            this.fbHeight = hb.get();
        }
    }

    public int getFrameWidth() {
        return this.fbWidth;
    }

    public int getFrameHeight() {
        return this.fbHeight;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public void destroy() {
        Callbacks.glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwSetErrorCallback(null).free();
        glfwTerminate();
    }

}
