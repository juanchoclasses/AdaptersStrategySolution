package com.spaceshooter.game;

import com.spaceshooter.controller.GameController;
import com.spaceshooter.model.GameModel;
import com.spaceshooter.view.GameView;

/**
 * The main entry point for the Space Shooter game.
 * This class initializes the game's Model-View-Controller (MVC) architecture
 * and starts the game loop.
 */
public class SpaceShooterGame {
    /**
     * The main method that starts the Space Shooter game.
     * It creates the game model, view, and controller, then starts the game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        GameModel model = new GameModel();
        GameView view = new GameView();
        GameController controller = new GameController(model, view);
        controller.startGame();
    }
}
