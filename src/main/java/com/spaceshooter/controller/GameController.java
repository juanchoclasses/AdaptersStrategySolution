package com.spaceshooter.controller;

import com.spaceshooter.model.GameModel;
import com.spaceshooter.model.Player;
import com.spaceshooter.strategy.BasicMissileStrategy;
import com.spaceshooter.strategy.DoubleMissileStrategy;
import com.spaceshooter.strategy.TargetingMissileStrategy;
import com.spaceshooter.adapter.LaserMissileAdapter;
import com.spaceshooter.adapter.LaserWeapon;
import com.spaceshooter.view.GameView;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * Controls the game flow and user input in the Space Shooter game.
 * This class manages the game model, view, and user interactions.
 * It handles:
 * - Game timing and updates
 * - Keyboard input for player movement and weapon selection
 * - Strategy selection through both keyboard and UI buttons
 * - Game state management (start, restart, game over)
 */
public class GameController {
  private static final int DELAY = 20;
  private GameModel model;
  private GameView view;
  private Timer gameTimer;

  /**
   * Constructs a new GameController with the specified model and view.
   * Initializes the game timer and sets up input listeners.
   *
   * @param model the game model to control
   * @param view  the game view to update
   */
  public GameController(GameModel model, GameView view) {
    this.model = model;
    this.view = view;
    this.view.addKeyListener(new KeyHandler());
    this.view.setStrategyButtonListeners(new StrategyButtonListener());
    this.gameTimer = new Timer(DELAY, new GameTimerListener());
  }

  /**
   * Starts the game by initializing the view and starting the game timer.
   * Sets up the view to receive keyboard input.
   */
  public void startGame() {
    view.setModel(model);
    view.setFocusable(true);
    view.requestFocus();
    gameTimer.start();
  }

  /**
   * Restarts the game by creating a new game model and resetting the view.
   * The game timer is restarted to begin the new game.
   */
  private void restartGame() {
    model = new GameModel();
    view.setModel(model);
    gameTimer.start();
  }

  /**
   * Handles keyboard input for the game.
   * Controls include:
   * - Arrow keys for player movement
   * - Space for firing missiles
   * - X, C, V, B for selecting different weapon strategies
   * - G for toggling god mode
   * - ESC for toggling debug mode
   */
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

  /**
   * Handles the game timer events.
   * Updates the game model and view on each tick.
   * Checks for game over condition and stops the timer if necessary.
   */
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

  /**
   * Handles strategy selection through UI buttons.
   * Allows switching between different missile strategies:
   * - Basic missile strategy
   * - Double missile strategy
   * - Targeting missile strategy
   * - Laser weapon strategy
   */
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
}
