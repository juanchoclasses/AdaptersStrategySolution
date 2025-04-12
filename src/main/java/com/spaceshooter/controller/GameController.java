package com.spaceshooter.controller;

import com.spaceshooter.model.GameModel;
import com.spaceshooter.model.Player;
import com.spaceshooter.strategy.BasicMissileStrategy;
import com.spaceshooter.strategy.DoubleMissileStrategy;
import com.spaceshooter.strategy.TargetingMissileStrategy;
import com.spaceshooter.adapter.LaserMissileAdapter;
import com.spaceshooter.view.GameView;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GameController {
    private GameModel model;
    private GameView view;
    private Timer gameTimer;
    private static final int DELAY = 20;
    
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.view.addKeyListener(new KeyHandler());
        this.view.setStrategyButtonListeners(new StrategyButtonListener());
        this.gameTimer = new Timer(DELAY, new GameTimerListener());
    }
    
    public void startGame() {
        view.setModel(model);
        view.setFocusable(true);
        view.requestFocus();
        gameTimer.start();
    }
    
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            Player player = model.getPlayer();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    player.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    player.moveRight();
                    break;
                case KeyEvent.VK_SPACE:
                    model.fireMissile();
                    break;
                case KeyEvent.VK_X:
                    model.setMissileStrategy(new BasicMissileStrategy());
                    break;
                case KeyEvent.VK_C:
                    model.setMissileStrategy(new DoubleMissileStrategy(model));
                    break;
                case KeyEvent.VK_V:
                    model.setMissileStrategy(new TargetingMissileStrategy(model.getEnemies()));
                    break;
                case KeyEvent.VK_B:
                    model.setMissileStrategy(new LaserMissileAdapter());
                    break;
                case KeyEvent.VK_G:
                    model.toggleGodMode();
                    break;
                case KeyEvent.VK_ESCAPE:
                    model.toggleDebugMode();
                    break;
            }
        }
    }
    
    private class GameTimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.update();
            view.repaint();
            
            if (model.isGameOver()) {
                gameTimer.stop();
                view.showGameOver(model.getScore());
            }
        }
    }
    
    private class StrategyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "basic":
                    model.setMissileStrategy(new BasicMissileStrategy());
                    break;
                case "double":
                    model.setMissileStrategy(new DoubleMissileStrategy(model));
                    break;
                case "targeting":
                    model.setMissileStrategy(new TargetingMissileStrategy(model.getEnemies()));
                    break;
                case "laser":
                    model.setMissileStrategy(new LaserMissileAdapter());
                    break;
                case "restart":
                    restartGame();
                    break;
            }
            view.requestFocus();
        }
    }

    private void restartGame() {
        model = new GameModel();
        view.setModel(model);
        gameTimer.start();
    }
}
