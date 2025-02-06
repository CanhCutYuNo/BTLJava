package application;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Main {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Player player;
    private Image backgroundImage; 
    private List<Bullet> bullets;
    private GameLoop gameLoop;

    public Main() {
        frame = new JFrame("Chicken Invaders");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);

        // Fullscreen
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(frame);

        // CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loadBackgroundImage();

        // Creat Menu Panel and Game Panel
        JPanel menuPanel = createMenuPanel();
        JPanel gamePanel = createGamePanel();

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Game");

        frame.add(mainPanel);
        frame.setVisible(true);

        // Show menu first
        cardLayout.show(mainPanel, "Menu");
        
        gameLoop = new GameLoop(100, this::updateGame, gamePanel::repaint);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if(backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        panel.setLayout(new BorderLayout()); // Giữ bố cục BorderLayout

        // Menu title
        JLabel titleLabel = new JLabel("Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent button panel
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 50));
        startButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Game");
            hideCursor();
            gameLoop.start();
        });

        JButton settingButton = new JButton("Setting");
        settingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingButton.setMaximumSize(new Dimension(200, 50));
        settingButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Chưa làm", "Setting", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 50));
        exitButton.addActionListener(e -> System.exit(0));

        // add buttons to buttonPanel
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(settingButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());

        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // render bgr, player, bullets,...
                if(backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }

                for(Bullet bullet : bullets) {
                    bullet.render(g);
                }

                if(player != null) {
                    player.render(g);
                }
                
            }
        };
        panel.setLayout(null); 
        
        // create objects
        player = new Player(100, 10, 1.0, 400, 300); //bug: phải di chuột player mới xuất hiện
        bullets = new ArrayList<>();
        
        // MouseMotionListener
        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updatePlayerPos(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                updatePlayerPos(e);
            }

            private void updatePlayerPos(MouseEvent e) {
            	// Mouse cursor in center of the player
                player.setPosX(e.getX() - 32);
                player.setPosY(e.getY() - 32);
                panel.repaint();
            }
        });
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int bulletWidth = 9;
                int bulletHeight = 57;

                int bulletX = e.getX() - bulletWidth / 2;
                int bulletY = e.getY() - bulletHeight / 2;

                Bullet bullet = new Bullet(bulletX, bulletY, 5.0, 1.0);
                bullets.add(bullet);
            }
        });



        // ESC để hiện hộp thoại thoát ra menu (bug)
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    showConfirmDialog();
                }
            }
        });
        panel.setFocusable(true); // bug

        // bug
        panel.addHierarchyListener(e -> {
            if((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && panel.isShowing()) {
                SwingUtilities.invokeLater(panel::requestFocusInWindow);
            }
        });

        return panel;
    }

    private void updateGame() {
        updateBullets();
    }
    
    private void loadBackgroundImage() {
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/asset/resources/background.png")).getImage();
        } catch(Exception e) {
            System.out.println("Error: Could not load background image.");
            e.printStackTrace();
        }
    }
    
    private void updateBullets() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        for(Bullet bullet : bullets) {
            bullet.update();
            if(bullet.isOffScreen(frame.getHeight())) {
                bulletsToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletsToRemove);
    }


    private void hideCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "InvisibleCursor");

        frame.setCursor(invisibleCursor);
    }

    private void showConfirmDialog() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Do you want to return to the menu?",
                "Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if(result == JOptionPane.YES_OPTION) {
            cardLayout.show(mainPanel, "Menu");
            frame.setCursor(Cursor.getDefaultCursor());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
