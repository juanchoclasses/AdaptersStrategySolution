package com.spaceshooter.view;

import com.spaceshooter.model.Enemy;
import com.spaceshooter.model.GameModel;
import com.spaceshooter.model.Missile;
import com.spaceshooter.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameView extends JFrame {
    private GameModel model;
    private GamePanel gamePanel;
    private JPanel controlPanel;
    private JButton basicButton;
    private JButton doubleButton;
    private JButton targetingButton;
    private JButton laserButton;
    private JButton restartButton;
    private JButton moveLeftButton;
    private JButton moveRightButton;
    private Timer leftMoveTimer;
    private Timer rightMoveTimer;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    
    public GameView() {
        setTitle("Space Shooter - Strategy & Adapter Patterns Demo");
        setSize(WIDTH, HEIGHT + 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        gamePanel = new GamePanel();
        controlPanel = createControlPanel();
        JPanel movementPanel = createMovementPanel();
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(movementPanel, BorderLayout.SOUTH);
        
        add(gamePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setupMovementTimers();
        
        setVisible(true);
    }
    
    private void setupMovementTimers() {
        leftMoveTimer = new Timer(50, e -> {
            if (model != null) {
                model.getPlayer().moveLeft();
                gamePanel.repaint();
            }
        });
        
        rightMoveTimer = new Timer(50, e -> {
            if (model != null) {
                model.getPlayer().moveRight();
                gamePanel.repaint();
            }
        });
    }
    
    private JPanel createMovementPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, 50));
        panel.setBackground(Color.DARK_GRAY);
        
        moveLeftButton = new JButton("← Move Left");
        moveRightButton = new JButton("Move Right →");
        
        moveLeftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                leftMoveTimer.start();
                requestFocus();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                leftMoveTimer.stop();
                requestFocus();
            }
        });
        
        moveRightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rightMoveTimer.start();
                requestFocus();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                rightMoveTimer.stop();
                requestFocus();
            }
        });
        
        panel.add(moveLeftButton);
        panel.add(moveRightButton);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, 70));
        panel.setBackground(Color.DARK_GRAY);
        
        JLabel strategyLabel = new JLabel("Missile Strategy: ");
        strategyLabel.setForeground(Color.WHITE);
        panel.add(strategyLabel);
        
        basicButton = new JButton("Basic (X)");
        basicButton.setActionCommand("basic");
        
        doubleButton = new JButton("Double (C)");
        doubleButton.setActionCommand("double");
        
        targetingButton = new JButton("Targeting (V)");
        targetingButton.setActionCommand("targeting");
        
        laserButton = new JButton("Laser (B)");
        laserButton.setActionCommand("laser");
        
        restartButton = new JButton("Restart");
        restartButton.setActionCommand("restart");
        
        panel.add(basicButton);
        panel.add(doubleButton);
        panel.add(targetingButton);
        panel.add(laserButton);
        panel.add(restartButton);
        
        return panel;
    }
    
    public void setStrategyButtonListeners(ActionListener listener) {
        basicButton.addActionListener(listener);
        doubleButton.addActionListener(listener);
        targetingButton.addActionListener(listener);
        laserButton.addActionListener(listener);
        restartButton.addActionListener(listener);
    }
    
    public void setModel(GameModel model) {
        this.model = model;
        gamePanel.setModel(model);
    }
    
    public void showGameOver(int score) {
        JOptionPane.showMessageDialog(this, 
            "Game Over! Your score: " + score, 
            "Game Over", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private class GamePanel extends JPanel {
        private GameModel model;
        
        public GamePanel() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBackground(Color.BLACK);
        }
        
        public void setModel(GameModel model) {
            this.model = model;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (model == null) return;
            
            renderGame(g);
        }
        
        private void renderGame(Graphics g) {
            // Draw background
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            
            // Draw player
            Player player = model.getPlayer();
            g.setColor(Color.GREEN);
            g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
            
            // Draw enemies
            g.setColor(Color.RED);
            for (Enemy enemy : model.getEnemies()) {
                g.fillRect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
                // Draw health text
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                String health = String.valueOf(enemy.getHealth());
                FontMetrics metrics = g.getFontMetrics();
                int textX = enemy.getX() + (enemy.getWidth() - metrics.stringWidth(health)) / 2;
                int textY = enemy.getY() + (enemy.getHeight() + metrics.getHeight()) / 2;
                g.drawString(health, textX, textY);
                g.setColor(Color.RED);
            }
            
            // Draw missiles
            for (Missile missile : model.getMissiles()) {
                if (missile.isPlayerMissile()) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(missile.getX(), missile.getY(), missile.getWidth(), missile.getHeight());
            }
            
            // Draw score and debug info
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + model.getScore(), 10, 30);
            
            if (model.isDebugMode()) {
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                g.drawString("Debug - Left: " + model.getLeftmostX() + " Right: " + model.getRightmostX() + 
                            " Dir: " + (model.getEnemyDirection() > 0 ? "Right" : "Left"), 10, 50);
                g.drawString("Player - X: " + model.getPlayer().getX() + " Y: " + model.getPlayer().getY(), 10, 70);
            }
            
            // Draw missile counts
            g.setFont(new Font("Arial", Font.BOLD, 16));
            int missileY = getHeight() - 20;
            g.drawString("Basic: " + model.getBasicMissilesLive(), 10, missileY);
            g.drawString("Double: " + model.getDoubleMissilesLive(), 100, missileY);
            g.drawString("Targeting: " + model.getTargetingMissilesLive(), 190, missileY);
            g.drawString("Laser: " + model.getLaserMissilesLive(), 280, missileY);
            
            // Draw game over message if game is over
            if (model.isGameOver()) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 48));
                String gameOverText = "GAME OVER";
                int textWidth = g.getFontMetrics().stringWidth(gameOverText);
                g.drawString(gameOverText, (getWidth() - textWidth) / 2, getHeight() / 2);
            }
        }
    }
}
