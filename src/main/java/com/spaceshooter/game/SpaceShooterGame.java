package com.spaceshooter.game;

import com.spaceshooter.controller.GameController;
import com.spaceshooter.model.GameModel;
import com.spaceshooter.view.GameView;

public class SpaceShooterGame {
    public static void main(String[] args) {
        GameModel model = new GameModel();
        GameView view = new GameView();
        GameController controller = new GameController(model, view);
        controller.startGame();
    }
}
