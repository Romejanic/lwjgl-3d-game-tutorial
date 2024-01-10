package org.tutorial.game;

import org.tutorial.game.renderer.Renderer;
import org.tutorial.game.renderer.Window;

public class GameApp {
    
    public static void main(String[] args) {
        Window window = new Window("My Game", 1280, 720);
        Renderer renderer = new Renderer();

        renderer.init();

        while(!window.shouldClose()) {
            renderer.render(window.getFrameWidth(), window.getFrameHeight());
            window.update();
        }

        renderer.destroy();
        window.destroy();
    }

}
