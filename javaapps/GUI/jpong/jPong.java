// Nicholas Buchanan
// December 2010

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.sound.midi.*;
import javax.swing.Timer;
import java.util.function.Function; // Required for the loadImage helper

public class jPong extends JPanel implements KeyListener, Runnable {

    // --- Constants ---
    // DEFAULT_GAME_WIDTH and DEFAULT_GAME_HEIGHT serve as the base resolution for
    // scaling.
    private static final int DEFAULT_GAME_WIDTH = 700;
    private static final int DEFAULT_GAME_HEIGHT = 700;
    private static final int BASE_BALL_SIZE = 15; // Base ball size for scaling
    private static final int BASE_PADDLE_WIDTH = 100; // Base paddle width for scaling
    private static final int BASE_PADDLE_HEIGHT = 20; // Base paddle height for scaling
    private static final int BASE_BALL_SPEED_X = 6; // Base speed for difficulty level 1
    private static final int BASE_BALL_SPEED_Y = 8; // Base speed for difficulty level 1
    private static final int BASE_PADDLE_SPEED = 8; // Base speed for keyboard paddle movement
    private static final int GAME_LOOP_DELAY = 25; // Milliseconds for Thread.sleep
    private static final int MENU_ITEM_HEIGHT = 30; // Approximate height of menu item images/text for calculation
    private static final int MENU_ITEM_SPACING = 20; // Fixed spacing between menu items (can be scaled too)
    private static final double MENU_IMAGE_SCALE_BOOST = 1.2; // Boost scaling for menu images (1.0 for no boost)
    private static final int MENU_VERTICAL_OFFSET = 20; // Vertical offset for the options menu
    private static final int PAUSE_MENU_INITIAL_PADDING = 30; // Initial padding from the top of the pause menu block to
                                                              // the first option

    // --- Brick Constants (Base values for scaling) ---
    private static final int BRICK_ROWS = 5;
    private static final int BRICK_COLS = 7;
    private static final int BASE_BRICK_WIDTH = 80; // Base width for scaling
    private static final int BASE_BRICK_HEIGHT = 20; // Base height for scaling
    private static final int BASE_BRICK_HORIZONTAL_GAP = 10; // Base horizontal gap for scaling
    private static final int BASE_BRICK_VERTICAL_GAP = 5; // Base vertical gap for scaling
    private static final int BASE_BRICKS_START_Y = 50; // Base Y position for scaling
    private static final int BRICK_GENERATION_CHANCE = 70; // Percentage chance for a brick to be generated (70%)
    private static final int INITIAL_LIVES = 3; // Starting number of lives

    // --- Game State Enum ---
    public enum GameState {
        SPLASH, MAIN_MENU, OPTIONS_MENU, PLAYING, PAUSED, GAME_OVER, WIN
    }

    // --- Global Variables ---
    private Thread gameThread;
    private volatile boolean isRunning = false; // Flag to control the game loop
    private GameState currentGameState = GameState.SPLASH; // Initial state
    private int mousex = 0; // Mouse x-coordinate for paddle
    private double ballx, bally; // Ball position (use double for smoother movement)
    private int paddlex; // Paddle x-coordinate (centered)
    private double xDirection, yDirection; // Ball movement directions (use double)
    private double paddleXVelocity = 0; // Current velocity of the paddle for keyboard movement
    private int score = 0; // Player's score
    private int lives = INITIAL_LIVES; // Player's lives
    private Random rnd = new Random();
    private boolean bouncedX = false; // Flag to prevent multiple bounces on X-axis in one frame
    private boolean bouncedY = false; // Flag to prevent multiple bounces on Y-axis in one frame
    private boolean ballInPlay = false; // New flag: true if ball is launched, false if on paddle

    private boolean[][] bricks; // true if brick is active, false if destroyed
    private int activeBricksCount; // To keep track of remaining bricks

    private Map<String, Clip> soundCache = new HashMap<>(); // Cache for loaded sounds

    // --- Image Resources (Loaded from Classpath) ---
    private Image splashImage;
    private Image startimg;
    private Image background;
    private Image difficulty;
    private Image difficulty2;
    private Image exit;
    private Image exit2;
    private Image optionsimg;
    private Image options2;
    private Image sensitivity;
    private Image sensitivity2;
    private Image start;
    private Image start2;
    private Image volume;
    private Image volume2;
    private Image high;
    private Image medium;
    private Image low;

    // Menu selections
    public int startopt = 1; // Main menu: 1(startgame) 2(options) 3(exit)
    public int optmenuopt = 1; // Options menu: 1(difficulty) 2(volume) 3(sensitivity) 4(exit)
    public int pauseMenuOpt = 1; // Pause menu: 1(resume) 2(main menu) 3(exit)

    public int difficultyLevel = 1; // 1: Low, 2: Medium, 3: High
    public int volumeLevel = 1; // 1: Low, 2: Medium, 3: High
    public int sensitivityLevel = 1; // 1: Low, 2: Medium, 3: High

    private Rectangle originalBounds; // To store windowed mode bounds for fullscreen toggle
    private FontMetrics pauseMenuFontMetrics; // To store font metrics for accurate clickable area calculation

    // --- Constructor Method ---
    public jPong() {
        setPreferredSize(new Dimension(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Initialize ball and paddle positions and bricks for the first time
        // This will set up the initial game state, including the first brick layout
        resetGameElements();

        // Load all resources
        loadResources();

        // Start splash screen transition after a delay
        // This timer will fire once and move the game to the main menu
        new Timer(2000, new ActionListener() { // 2 seconds delay for splash screen
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGameState = GameState.MAIN_MENU;
                System.out.println("State transition (Initial Splash Delay): SPLASH -> MAIN_MENU");
                ((Timer) e.getSource()).stop(); // Stop this one-shot timer
                repaint();
            }
        }).start();

        // Mouse movement listener for paddle and menu selection
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousex = e.getX();

                // Calculate current scaled paddle width
                double scaleFactor = Math.min((double) getWidth() / DEFAULT_GAME_WIDTH,
                        (double) getHeight() / DEFAULT_GAME_HEIGHT);
                int scaledPaddleWidth = (int) (BASE_PADDLE_WIDTH * scaleFactor);

                // Update paddle position based on mouse X
                paddlex = mousex - scaledPaddleWidth / 2;
                // Keep paddle within bounds
                if (paddlex < 0)
                    paddlex = 0;
                if (paddlex > getWidth() - scaledPaddleWidth)
                    paddlex = getWidth() - scaledPaddleWidth;

                // If ball is not in play, keep it centered on the paddle
                if (!ballInPlay && currentGameState == GameState.PLAYING) {
                    int scaledBallSize = (int) (BASE_BALL_SIZE * scaleFactor);
                    int scaledPaddleHeight = (int) (BASE_PADDLE_HEIGHT * scaleFactor);
                    ballx = paddlex + (scaledPaddleWidth / 2) - (scaledBallSize / 2);
                    bally = getHeight() - scaledPaddleHeight - scaledBallSize;
                }

                // Update menu selection based on mouse Y
                if (currentGameState == GameState.MAIN_MENU) {
                    // Use scaled image heights for accurate clickable areas, applying the boost
                    int scaledStartHeight = (int) (start.getHeight(jPong.this) * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                    int scaledOptionsHeight = (int) (optionsimg.getHeight(jPong.this) * scaleFactor
                            * MENU_IMAGE_SCALE_BOOST);
                    int scaledExitHeight = (int) (exit.getHeight(jPong.this) * scaleFactor * MENU_IMAGE_SCALE_BOOST);

                    int totalMainMenuItemsHeight = scaledStartHeight + scaledOptionsHeight + scaledExitHeight;
                    int totalMainMenuSpacing = (int) (MENU_ITEM_SPACING * 2 * scaleFactor * MENU_IMAGE_SCALE_BOOST); // Scale
                                                                                                                     // spacing
                                                                                                                     // too
                    int mainMenuBlockHeight = totalMainMenuItemsHeight + totalMainMenuSpacing;
                    int mainMenuBlockY = (getHeight() / 2) - (mainMenuBlockHeight / 2);

                    int y1 = mainMenuBlockY;
                    int y2 = y1 + scaledStartHeight + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                    int y3 = y2 + scaledOptionsHeight
                            + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);

                    if (e.getY() >= y1 && e.getY() < y1 + scaledStartHeight) {
                        startopt = 1;
                    } else if (e.getY() >= y2 && e.getY() < y2 + scaledOptionsHeight) {
                        startopt = 2;
                    } else if (e.getY() >= y3 && e.getY() < y3 + scaledExitHeight) {
                        startopt = 3;
                    }
                } else if (currentGameState == GameState.OPTIONS_MENU) {
                    // Use scaled image heights for accurate clickable areas, applying the boost
                    int scaledDifficultyHeight = (int) (difficulty.getHeight(jPong.this) * scaleFactor
                            * MENU_IMAGE_SCALE_BOOST);
                    int scaledVolumeHeight = (int) (volume.getHeight(jPong.this) * scaleFactor
                            * MENU_IMAGE_SCALE_BOOST);
                    int scaledSensitivityHeight = (int) (sensitivity.getHeight(jPong.this) * scaleFactor
                            * MENU_IMAGE_SCALE_BOOST);
                    int scaledExitOptionsHeight = (int) (exit.getHeight(jPong.this) * scaleFactor
                            * MENU_IMAGE_SCALE_BOOST);

                    int totalOptionsMenuItemsHeight = scaledDifficultyHeight + scaledVolumeHeight
                            + scaledSensitivityHeight + scaledExitOptionsHeight;
                    int totalOptionsMenuSpacing = (int) (MENU_ITEM_SPACING * 3 * scaleFactor * MENU_IMAGE_SCALE_BOOST); // Scale
                                                                                                                        // spacing
                                                                                                                        // too
                    int optionsMenuBlockHeight = totalOptionsMenuItemsHeight + totalOptionsMenuSpacing;
                    int optionsMenuBlockY = (getHeight() / 2) - (optionsMenuBlockHeight / 2)
                            + (int) (MENU_VERTICAL_OFFSET * scaleFactor); // Apply offset

                    int y1 = optionsMenuBlockY;
                    int y2 = y1 + scaledDifficultyHeight
                            + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                    int y3 = y2 + scaledVolumeHeight + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                    int y4 = y3 + scaledSensitivityHeight
                            + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);

                    if (e.getY() >= y1 && e.getY() < y1 + scaledDifficultyHeight) {
                        optmenuopt = 1;
                    } else if (e.getY() >= y2 && e.getY() < y2 + scaledVolumeHeight) {
                        optmenuopt = 2;
                    } else if (e.getY() >= y3 && e.getY() < y3 + scaledSensitivityHeight) {
                        optmenuopt = 3;
                    } else if (e.getY() >= y4 && e.getY() < y4 + scaledExitOptionsHeight) {
                        optmenuopt = 4;
                    }
                } else if (currentGameState == GameState.PAUSED) { // Pause menu selection
                    // Use stored FontMetrics for accurate text height, fallback to scaled
                    // MENU_ITEM_HEIGHT
                    int textHeight = (pauseMenuFontMetrics != null) ? pauseMenuFontMetrics.getHeight()
                            : (int) (MENU_ITEM_HEIGHT * scaleFactor);

                    // Calculate the starting Y for the menu options directly
                    // This is the Y position of the "PAUSED" text + initial padding
                    int pausedTextY = (int) (getHeight() / 2 - 100 * scaleFactor);
                    int pauseMenuBlockY = pausedTextY + (int) (PAUSE_MENU_INITIAL_PADDING * scaleFactor);

                    int y1 = pauseMenuBlockY;
                    int y2 = y1 + textHeight + (int) (MENU_ITEM_SPACING * scaleFactor);
                    int y3 = y2 + textHeight + (int) (MENU_ITEM_SPACING * scaleFactor);

                    if (e.getY() >= y1 && e.getY() < y1 + textHeight) {
                        pauseMenuOpt = 1; // Resume
                    } else if (e.getY() >= y2 && e.getY() < y2 + textHeight) {
                        pauseMenuOpt = 2; // Main Menu
                    } else if (e.getY() >= y3 && e.getY() < y3 + textHeight) {
                        pauseMenuOpt = 3; // Exit
                    }
                }
                repaint(); // Repaint to show updated paddle/menu highlight
            }
        });

        // Mouse click listener for menu interactions
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Call the new centralized handler
                handleMenuClick(e.getY());

                // If in PLAYING state and ball is not yet launched, launch it
                if (currentGameState == GameState.PLAYING && !ballInPlay) {
                    launchBall();
                }
            }
        });
    }

    // --- Resource Loading Method ---
    private void loadResources() {
        // Helper to load images safely
        Function<String, Image> loadImage = (path) -> {
            try {
                URL imageUrl = getClass().getResource(path);
                if (imageUrl != null) {
                    return new ImageIcon(imageUrl).getImage();
                } else {
                    System.err.println("Resource not found: " + path);
                    return new ImageIcon().getImage(); // Fallback to an empty image
                }
            } catch (Exception e) {
                System.err.println("Error loading image " + path + ": " + e.getMessage());
                return new ImageIcon().getImage();
            }
        };

        splashImage = loadImage.apply("/wav/intro.png");
        startimg = loadImage.apply("/wav/bin/cover.gif");
        background = loadImage.apply("/wav/bin/game.gif");
        difficulty = loadImage.apply("/wav/bin/difficulty.png");
        difficulty2 = loadImage.apply("/wav/bin/difficulty2.png");
        exit = loadImage.apply("/wav/bin/exit.png");
        exit2 = loadImage.apply("/wav/bin/exit2.png");
        optionsimg = loadImage.apply("/wav/bin/options.png");
        options2 = loadImage.apply("/wav/bin/options2.png");
        sensitivity = loadImage.apply("/wav/bin/sensitivity.png");
        sensitivity2 = loadImage.apply("/wav/bin/sensitivity2.png");
        start = loadImage.apply("/wav/bin/start.png");
        start2 = loadImage.apply("/wav/bin/start2.png");
        volume = loadImage.apply("/wav/bin/volume.png");
        volume2 = loadImage.apply("/wav/bin/volume2.png");
        high = loadImage.apply("/wav/bin/high.png");
        medium = loadImage.apply("/wav/bin/medium.png");
        low = loadImage.apply("/wav/bin/low.png");
    }

    /**
     * Resets the ball and paddle to their initial positions and states.
     * This method does NOT affect the brick layout or score/lives.
     */
    private void resetBallAndPaddlePosition() {
        // Calculate initial scale factor for accurate positioning
        double scaleFactor = Math.min((double) getWidth() / DEFAULT_GAME_WIDTH,
                (double) getHeight() / DEFAULT_GAME_HEIGHT);
        int scaledBallSize = (int) (BASE_BALL_SIZE * scaleFactor);
        int scaledPaddleWidth = (int) (BASE_PADDLE_WIDTH * scaleFactor);
        int scaledPaddleHeight = (int) (BASE_PADDLE_HEIGHT * scaleFactor);

        paddlex = getWidth() / 2 - scaledPaddleWidth / 2;
        ballx = paddlex + (scaledPaddleWidth / 2) - (scaledBallSize / 2);
        bally = getHeight() - scaledPaddleHeight - scaledBallSize; // Place ball on top of paddle

        xDirection = BASE_BALL_SPEED_X * (rnd.nextBoolean() ? 1 : -1); // Keep random horizontal direction
        yDirection = 0; // Ball starts stationary vertically
        bouncedX = false;
        bouncedY = false;
        paddleXVelocity = 0; // Reset paddle velocity
        ballInPlay = false; // Ball is not in play until launched
    }

    /**
     * Launches the ball from the paddle.
     */
    private void launchBall() {
        if (!ballInPlay) {
            ballInPlay = true;
            // Set initial upward direction
            yDirection = -BASE_BALL_SPEED_Y;
            // Play a sound for launching the ball (optional)
            // playSound("/wav/launch.wav"); // Uncomment if you have a launch sound
        }
    }

    /**
     * Resets all game elements for a new game or a new level.
     * This includes initializing a new brick layout and resetting ball/paddle.
     */
    private void resetGameElements() {
        initializeBricks(); // Always generate new bricks for a new game/level
        resetBallAndPaddlePosition(); // Reset ball and paddle for the new level
    }

    // Initializes the grid of bricks with a random pattern
    private void initializeBricks() {
        bricks = new boolean[BRICK_ROWS][BRICK_COLS];
        activeBricksCount = 0;
        boolean atLeastOneBrick = false; // Flag to ensure at least one brick is generated

        for (int r = 0; r < BRICK_ROWS; r++) {
            for (int c = 0; c < BRICK_COLS; c++) {
                if (rnd.nextInt(100) < BRICK_GENERATION_CHANCE) { // 70% chance to generate a brick
                    bricks[r][c] = true;
                    activeBricksCount++;
                    atLeastOneBrick = true;
                } else {
                    bricks[r][c] = false;
                }
            }
        }

        // If no bricks were generated, force at least one to prevent immediate win
        if (!atLeastOneBrick) {
            int randomRow = rnd.nextInt(BRICK_ROWS);
            int randomCol = rnd.nextInt(BRICK_COLS);
            bricks[randomRow][randomCol] = true;
            activeBricksCount = 1;
        }
    }

    // --- Game Logic Loop ---
    @Override
    public void run() {
        isRunning = true; // Set running flag to true when the thread starts
        while (isRunning) { // The game loop runs as long as isRunning is true
            if (currentGameState == GameState.PLAYING) {
                updateGame();
            }
            repaint(); // Request a repaint regardless of game state
            try {
                Thread.sleep(GAME_LOOP_DELAY); // Control frame rate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt status
                System.err.println("Game thread interrupted: " + e.getMessage());
                break; // Exit loop if interrupted
            }
        }
        System.out.println("Game thread stopped."); // Log when the thread terminates
    }

    // --- Sound Playback Method ---
    private void playSound(String filepath) {
        try {
            Clip clip = soundCache.get(filepath);
            if (clip == null) {
                URL soundUrl = getClass().getResource(filepath);
                if (soundUrl != null) {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundUrl);
                    clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    soundCache.put(filepath, clip);
                } else {
                    System.err.println("Sound resource not found: " + filepath);
                    return; // Don't try to play if not found
                }
            }
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0); // Rewind to the beginning

            // Adjust volume based on volumeLevel
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float volumeDb = 0.0f; // Default to max volume (0 dB)
                switch (volumeLevel) {
                    case 1: // Low
                        volumeDb = -20.0f; // Example: 20 dB quieter
                        break;
                    case 2: // Medium
                        volumeDb = -10.0f; // Example: 10 dB quieter
                        break;
                    case 3: // High (already 0.0f)
                        volumeDb = 0.0f; // Full volume
                        break;
                }
                // Ensure the volumeDb is within the supported range
                volumeDb = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), volumeDb));
                gainControl.setValue(volumeDb);
            } else {
                System.out.println("MASTER_GAIN control not supported for " + filepath);
            }

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing sound " + filepath + ": " + e.getMessage());
        }
    }

    // --- Centralized Menu Click Handler ---
    private void handleMenuClick(int y) {
        GameState previousState = currentGameState; // Capture state before potential change

        // Calculate scale factor for dynamic positioning
        double scaleFactor = Math.min((double) getWidth() / DEFAULT_GAME_WIDTH,
                (double) getHeight() / DEFAULT_GAME_HEIGHT);

        switch (currentGameState) {
            case MAIN_MENU:
                // Use scaled image heights for accurate clickable areas, applying the boost
                int scaledStartHeight = (int) (start.getHeight(jPong.this) * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                int scaledOptionsHeight = (int) (optionsimg.getHeight(jPong.this) * scaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledExitHeight = (int) (exit.getHeight(jPong.this) * scaleFactor * MENU_IMAGE_SCALE_BOOST);

                int totalMainMenuItemsHeight = scaledStartHeight + scaledOptionsHeight + scaledExitHeight;
                int totalMainMenuSpacing = (int) (MENU_ITEM_SPACING * 2 * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                int mainMenuBlockHeight = totalMainMenuItemsHeight + totalMainMenuSpacing;
                int mainMenuBlockY = (getHeight() / 2) - (mainMenuBlockHeight / 2);

                int y1 = mainMenuBlockY;
                int y2 = y1 + scaledStartHeight + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                int y3 = y2 + scaledOptionsHeight + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);

                if (y >= y1 && y < y1 + scaledStartHeight) { // Start clicked
                    currentGameState = GameState.PLAYING;
                    lives = INITIAL_LIVES; // Reset lives at the start of a new game from main menu
                    score = 0; // Reset score too
                    resetGameElements(); // This will initialize bricks and reset ball/paddle
                    // Start the game thread only if it's not already running
                    if (gameThread == null || !gameThread.isAlive()) {
                        gameThread = new Thread(jPong.this);
                        gameThread.start();
                    }
                } else if (y >= y2 && y < y2 + scaledOptionsHeight) { // Options clicked
                    currentGameState = GameState.OPTIONS_MENU;
                } else if (y >= y3 && y < y3 + scaledExitHeight) { // Exit clicked
                    System.exit(0);
                }
                break;
            case OPTIONS_MENU:
                // Use scaled image heights for accurate clickable areas, applying the boost
                int scaledDifficultyHeight = (int) (difficulty.getHeight(jPong.this) * scaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledVolumeHeight = (int) (volume.getHeight(jPong.this) * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                int scaledSensitivityHeight = (int) (sensitivity.getHeight(jPong.this) * scaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledExitOptionsHeight = (int) (exit.getHeight(jPong.this) * scaleFactor * MENU_IMAGE_SCALE_BOOST); // Declared
                                                                                                                         // here

                int totalOptionsMenuItemsHeight = scaledDifficultyHeight + scaledVolumeHeight + scaledSensitivityHeight
                        + scaledExitOptionsHeight;
                int totalOptionsMenuSpacing = (int) (MENU_ITEM_SPACING * 3 * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                int optionsMenuBlockHeight = totalOptionsMenuItemsHeight + totalOptionsMenuSpacing;
                int optionsMenuBlockY = (getHeight() / 2) - (optionsMenuBlockHeight / 2)
                        + (int) (MENU_VERTICAL_OFFSET * scaleFactor); // Apply offset

                y1 = optionsMenuBlockY;
                y2 = y1 + scaledDifficultyHeight + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                y3 = y2 + scaledVolumeHeight + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);
                int y4 = y3 + scaledSensitivityHeight
                        + (int) (MENU_ITEM_SPACING * scaleFactor * MENU_IMAGE_SCALE_BOOST);

                if (y >= y1 && y < y1 + scaledDifficultyHeight) { // Difficulty
                    optmenuopt = 1; // Set selection to Difficulty
                    difficultyLevel = (difficultyLevel % 3) + 1; // Cycle 1, 2, 3
                } else if (y >= y2 && y < y2 + scaledVolumeHeight) { // Volume
                    optmenuopt = 2; // Set selection to Volume
                    volumeLevel = (volumeLevel % 3) + 1; // Cycle 1, 2, 3
                    // The actual volume will be applied when sounds are played via playSound()
                } else if (y >= y3 && y < y3 + scaledSensitivityHeight) { // Sensitivity
                    optmenuopt = 3; // Set selection to Sensitivity
                    sensitivityLevel = (sensitivityLevel % 3) + 1; // Cycle 1, 2, 3
                    // The actual paddle speed will be updated in keyPressed/keyReleased based on
                    // this level
                } else if (y >= y4 && y < y4 + scaledExitOptionsHeight) { // Exit from options menu
                    optmenuopt = 4; // Set selection to Exit
                    currentGameState = GameState.MAIN_MENU;
                }
                break;
            case PAUSED: // Handle clicks in the paused state
                // Use stored FontMetrics for accurate text height, fallback to scaled
                // MENU_ITEM_HEIGHT
                int textHeight = (pauseMenuFontMetrics != null) ? pauseMenuFontMetrics.getHeight()
                        : (int) (MENU_ITEM_HEIGHT * scaleFactor);

                // Calculate the starting Y for the menu options directly
                // This is the Y position of the "PAUSED" text + initial padding
                int pausedTextY = (int) (getHeight() / 2 - 100 * scaleFactor);
                int pauseMenuBlockY = pausedTextY + (int) (PAUSE_MENU_INITIAL_PADDING * scaleFactor);

                y1 = pauseMenuBlockY;
                y2 = y1 + textHeight + (int) (MENU_ITEM_SPACING * scaleFactor);
                y3 = y2 + textHeight + (int) (MENU_ITEM_SPACING * scaleFactor);

                if (y >= y1 && y < y1 + textHeight) {
                    pauseMenuOpt = 1; // Resume
                    currentGameState = GameState.PLAYING; // Resume game
                } else if (y >= y2 && y < y2 + textHeight) {
                    pauseMenuOpt = 2; // Main Menu
                    currentGameState = GameState.MAIN_MENU; // Go to main menu
                    score = 0; // Reset score if going back to main menu
                    lives = INITIAL_LIVES; // Reset lives if going back to main menu
                    resetGameElements(); // Reset game elements for a fresh start (new bricks, new ball/paddle pos)
                } else if (y >= y3 && y < y3 + textHeight) {
                    pauseMenuOpt = 3; // Exit
                    System.exit(0); // Exit application
                }
                break;
            case GAME_OVER:
            case WIN: // Handle clicks in WIN state to restart
                score = 0; // Reset score only on game over/win, not for new levels
                lives = INITIAL_LIVES; // Reset lives for a new game
                resetGameElements(); // Reset ball, paddle, and generate new bricks
                currentGameState = GameState.PLAYING; // Go back to playing
                // Ensure thread is running, though it should be from initial start
                if (gameThread == null || !gameThread.isAlive()) {
                    gameThread = new Thread(jPong.this);
                    gameThread.start();
                }
                break;
        }
        // Log state transition if it actually changed
        if (currentGameState != previousState) {
            System.out.println("State transition: " + previousState + " -> " + currentGameState);
        }
        repaint(); // Repaint after state changes
    }

    // --- Game Update Logic ---
    private void updateGame() {
        // Calculate dynamic scale factors based on current window dimensions
        double widthScaleFactor = (double) getWidth() / DEFAULT_GAME_WIDTH;
        double heightScaleFactor = (double) getHeight() / DEFAULT_GAME_HEIGHT;
        double overallScaleFactor = Math.min(widthScaleFactor, heightScaleFactor); // For elements maintaining aspect
                                                                                   // ratio

        // Calculate scaled dimensions for game elements
        int scaledBallSize = (int) (BASE_BALL_SIZE * overallScaleFactor);
        int scaledPaddleWidth = (int) (BASE_PADDLE_WIDTH * overallScaleFactor);
        int scaledPaddleHeight = (int) (BASE_PADDLE_HEIGHT * overallScaleFactor);

        // Apply difficulty level to ball speed
        // Scale base speeds by overallScaleFactor to maintain consistent perceived
        // speed
        double currentXSpeed = BASE_BALL_SPEED_X * overallScaleFactor;
        double currentYSpeed = BASE_BALL_SPEED_Y * overallScaleFactor;

        if (difficultyLevel == 2) {
            currentXSpeed += (2 * overallScaleFactor); // Increase speed for medium, scaled
            currentYSpeed += (2 * overallScaleFactor);
        } else if (difficultyLevel == 3) {
            currentXSpeed += (4 * overallScaleFactor); // Further increase speed for high, scaled
            currentYSpeed += (4 * overallScaleFactor);
        }

        // Update paddle position based on keyboard input
        paddlex += paddleXVelocity;

        // Keep paddle within bounds
        if (paddlex < 0)
            paddlex = 0;
        if (paddlex > getWidth() - scaledPaddleWidth)
            paddlex = getWidth() - scaledPaddleWidth;

        // If ball is not in play, keep it on the paddle
        if (!ballInPlay) {
            ballx = paddlex + (scaledPaddleWidth / 2) - (scaledBallSize / 2);
            bally = getHeight() - scaledPaddleHeight - scaledBallSize;
        } else {
            // Bounce off left and right walls
            if (ballx <= 0) {
                if (!bouncedX) {
                    ballx = 0; // Prevent sticking
                    xDirection = currentXSpeed; // Use current speed
                    playSound("/wav/sidehit.wav");
                    bouncedX = true;
                }
            } else if (ballx >= getWidth() - scaledBallSize) { // Use scaledBallSize for boundary
                if (!bouncedX) {
                    ballx = getWidth() - scaledBallSize; // Prevent sticking
                    xDirection = -currentXSpeed; // Use current speed
                    playSound("/wav/sidehit.wav");
                    bouncedX = true;
                }
            } else {
                bouncedX = false;
            }

            // Bounce off top wall
            if (bally <= 0) {
                if (!bouncedY) {
                    bally = 0; // Prevent sticking
                    yDirection = currentYSpeed; // Use current speed
                    playSound("/wav/sidehit.wav");
                    bouncedY = true;
                }
            } else if (bally < getHeight() - scaledPaddleHeight - scaledBallSize) { // Use scaledPaddleHeight and
                                                                                    // scaledBallSize
                // Reset bounce flag if we're back in regular play area (above paddle area)
                bouncedY = false;
            }

            // Check if ball goes past bottom (lose a life or Game Over)
            if (bally >= getHeight() - scaledBallSize) { // Ball's bottom edge is below screen bottom
                lives--; // Decrement a life
                playSound("/wav/lose_life.wav"); // Play a sound for losing a life (assuming you have one)

                if (lives <= 0) {
                    currentGameState = GameState.GAME_OVER; // Transition to game over state
                    System.out.println("Game State Changed: GAME_OVER (Ball missed, no lives left)"); // Log the state
                                                                                                      // change
                } else {
                    // Reset ball and paddle for next attempt if lives remain, KEEPING THE CURRENT
                    // BRICK LAYOUT
                    resetBallAndPaddlePosition(); // Reset ball and paddle position, but not score or bricks
                    System.out.println("Lost a life! Lives remaining: " + lives);
                }
                return; // Stop further updates for this frame
            }

            // --- Paddle Collision Detection (Improved to prevent tunneling) ---
            // Calculate the ball's next potential position based on current direction and
            // speed
            double nextBallX = ballx + (xDirection > 0 ? currentXSpeed : -currentXSpeed);
            double nextBallY = bally + (yDirection > 0 ? currentYSpeed : -currentYSpeed);

            // Paddle's top edge Y coordinate
            int paddleTopY = getHeight() - scaledPaddleHeight; // Use scaledPaddleHeight

            // Check for collision with paddle only if moving downwards
            if (yDirection > 0) {
                // Check if the ball's current bottom edge is above the paddle's top edge
                // AND the ball's next bottom edge will be at or below the paddle's top edge
                // AND there is horizontal overlap
                if ((bally + scaledBallSize <= paddleTopY) && // Use scaledBallSize
                        (nextBallY + scaledBallSize >= paddleTopY) && // Use scaledBallSize
                        (nextBallX + scaledBallSize > paddlex && nextBallX < paddlex + scaledPaddleWidth)) { // Use
                                                                                                             // scaledBallSize
                                                                                                             // and
                                                                                                             // scaledPaddleWidth

                    if (!bouncedY) {
                        // Adjust ball's Y position to be exactly at the top of the paddle
                        bally = paddleTopY - scaledBallSize; // Use scaledBallSize
                        yDirection = -currentYSpeed; // Reverse Y direction using current speed
                        playSound("/wav/sidehit.wav"); // Play bounce sound
                        score++; // Increment score
                        bouncedY = true; // Set bounce flag
                    }
                } else {
                    bouncedY = false; // Reset bounce flag if no collision this frame
                }
            }

            // --- Ball-Brick Collision Detection ---
            Rectangle ballBounds = new Rectangle((int) ballx, (int) bally, scaledBallSize, scaledBallSize); // Use
                                                                                                            // scaledBallSize
            boolean brickHit = false;

            // Calculate scaled brick dimensions and gaps using respective scale factors
            int scaledBrickWidth = (int) (BASE_BRICK_WIDTH * widthScaleFactor);
            int scaledBrickHeight = (int) (BASE_BRICK_HEIGHT * heightScaleFactor);
            int scaledBrickHorizontalGap = (int) (BASE_BRICK_HORIZONTAL_GAP * widthScaleFactor);
            int scaledBrickVerticalGap = (int) (BASE_BRICK_VERTICAL_GAP * heightScaleFactor);
            int scaledBricksStartY = (int) (BASE_BRICKS_START_Y * heightScaleFactor);

            // Calculate the starting X position for the bricks to center them
            int totalBricksWidth = BRICK_COLS * scaledBrickWidth + (BRICK_COLS - 1) * scaledBrickHorizontalGap;
            int bricksStartX = (getWidth() - totalBricksWidth) / 2;

            for (int r = 0; r < BRICK_ROWS; r++) {
                for (int c = 0; c < BRICK_COLS; c++) {
                    if (bricks[r][c]) { // Only check active bricks
                        int brickX = bricksStartX + c * (scaledBrickWidth + scaledBrickHorizontalGap);
                        int brickY = scaledBricksStartY + r * (scaledBrickHeight + scaledBrickVerticalGap);
                        Rectangle brickBounds = new Rectangle(brickX, brickY, scaledBrickWidth, scaledBrickHeight);

                        if (ballBounds.intersects(brickBounds)) {
                            bricks[r][c] = false; // Mark brick as destroyed
                            activeBricksCount--;
                            score += 10; // Score for destroying a brick
                            playSound("/wav/brickhit.wav"); // Play brick hit sound
                            brickHit = true;

                            // --- Improved Collision Response for Bricks ---
                            // Calculate overlap on X and Y axes
                            double overlapX = Math.min(ballBounds.x + ballBounds.width,
                                    brickBounds.x + brickBounds.width) - Math.max(ballBounds.x, brickBounds.x);
                            double overlapY = Math.min(ballBounds.y + ballBounds.height,
                                    brickBounds.y + brickBounds.height) - Math.max(ballBounds.y, brickBounds.y);

                            if (overlapX > overlapY) { // Vertical collision is dominant (hit top/bottom of brick)
                                yDirection *= -1; // Reverse Y direction
                                // Reposition ball to be just outside the brick along the Y axis
                                if (ballBounds.y < brickBounds.y) { // Ball hit top of brick
                                    bally = brickBounds.y - scaledBallSize;
                                } else { // Ball hit bottom of brick
                                    bally = brickBounds.y + scaledBrickHeight;
                                }
                            } else { // Horizontal collision is dominant (hit left/right of brick)
                                xDirection *= -1; // Reverse X direction
                                // Reposition ball to be just outside the brick along the X axis
                                if (ballBounds.x < brickBounds.x) { // Ball hit left of brick
                                    ballx = brickBounds.x - scaledBallSize;
                                } else { // Ball hit right of brick
                                    ballx = brickBounds.x + scaledBrickWidth;
                                }
                            }
                            // End improved collision response

                            // Break after first brick hit to prevent multiple bounces from one ball
                            // movement
                            break;
                        }
                    }
                }
                if (brickHit)
                    break; // Break outer loop if a brick was hit
            }

            // Check for win condition (all bricks destroyed)
            if (activeBricksCount <= 0 && currentGameState == GameState.PLAYING) {
                // Generate a new level instead of ending the game
                resetGameElements(); // This will re-initialize bricks and reset ball/paddle for the next level
                System.out.println("All bricks destroyed! Generating new level.");
                // No need to change currentGameState, it remains PLAYING
                return; // Stop further updates for this frame
            }

            // Update ball position using the current effective speeds
            ballx += (xDirection > 0 ? currentXSpeed : -currentXSpeed);
            bally += (yDirection > 0 ? currentYSpeed : -currentYSpeed);
        }
    }

    // --- Rendering ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Log the current state being painted
        System.out.println("Painting State: " + currentGameState);

        // Calculate dynamic scale factors based on current window dimensions
        double widthScaleFactor = (double) getWidth() / DEFAULT_GAME_WIDTH;
        double heightScaleFactor = (double) getHeight() / DEFAULT_GAME_HEIGHT;
        double overallScaleFactor = Math.min(widthScaleFactor, heightScaleFactor); // For elements maintaining aspect
                                                                                   // ratio

        switch (currentGameState) {
            case SPLASH:
                if (splashImage != null) {
                    // Draw the image to fill the entire panel
                    g2d.drawImage(splashImage, 0, 0, getWidth(), getHeight(), this);
                }
                break;
            case MAIN_MENU:
                g.drawImage(startimg, 0, 0, getWidth(), getHeight(), this); // Stretch background

                // Get scaled dimensions for menu items, applying the boost
                int scaledStartWidth = (int) (start.getWidth(this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int scaledStartHeight = (int) (start.getHeight(this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int scaledOptionsWidth = (int) (optionsimg.getWidth(this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledOptionsHeight = (int) (optionsimg.getHeight(this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledExitWidth = (int) (exit.getWidth(this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int scaledExitHeight = (int) (exit.getHeight(this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);

                // Calculate total height of menu block for vertical centering
                int totalMainMenuItemsHeight = scaledStartHeight + scaledOptionsHeight + scaledExitHeight;
                int totalMainMenuSpacing = (int) (MENU_ITEM_SPACING * 2 * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int mainMenuBlockHeight = totalMainMenuItemsHeight + totalMainMenuSpacing;
                int mainMenuBlockY = (getHeight() / 2) - (mainMenuBlockHeight / 2);

                int currentY = mainMenuBlockY;

                // Draw "Start" option
                int startX = (getWidth() - scaledStartWidth) / 2; // Center "Start" image
                g.drawImage(start, startX, currentY, scaledStartWidth, scaledStartHeight, this);
                if (startopt == 1)
                    g.drawImage(start2, startX, currentY, scaledStartWidth, scaledStartHeight, this);
                currentY += scaledStartHeight + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);

                // Draw "Options" option
                int optionsX = (getWidth() - scaledOptionsWidth) / 2; // Center "Options" image
                g.drawImage(optionsimg, optionsX, currentY, scaledOptionsWidth, scaledOptionsHeight, this);
                if (startopt == 2)
                    g.drawImage(options2, optionsX, currentY, scaledOptionsWidth, scaledOptionsHeight, this);
                currentY += scaledOptionsHeight
                        + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);

                // Draw "Exit" option
                int exitX = (getWidth() - scaledExitWidth) / 2; // Center "Exit" image
                g.drawImage(exit, exitX, currentY, scaledExitWidth, scaledExitHeight, this);
                if (startopt == 3)
                    g.drawImage(exit2, exitX, currentY, scaledExitWidth, scaledExitHeight, this);
                break;
            case OPTIONS_MENU:
                g.drawImage(startimg, 0, 0, getWidth(), getHeight(), this); // Use startimg as background for options

                // Declare variables locally within this case
                int scaledDifficultyWidth = (int) (difficulty.getWidth(this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledDifficultyHeight = (int) (difficulty.getHeight(this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledVolumeWidth = (int) (volume.getWidth(this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int scaledVolumeHeight = (int) (volume.getHeight(this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int scaledSensitivityWidth = (int) (sensitivity.getWidth(this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledSensitivityHeight = (int) (sensitivity.getHeight(this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledOptionsExitWidth = (int) (exit.getWidth(this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int scaledOptionsExitHeight = (int) (exit.getHeight(this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);

                int totalOptionsMenuItemsHeight = scaledDifficultyHeight + scaledVolumeHeight + scaledSensitivityHeight
                        + scaledOptionsExitHeight;
                int totalOptionsMenuSpacing = (int) (MENU_ITEM_SPACING * 3 * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int optionsMenuBlockHeight = totalOptionsMenuItemsHeight + totalOptionsMenuSpacing;
                int optionsMenuBlockY = (getHeight() / 2) - (optionsMenuBlockHeight / 2)
                        + (int) (MENU_VERTICAL_OFFSET * overallScaleFactor); // Apply offset

                currentY = optionsMenuBlockY;

                // Draw "Difficulty" option
                int difficultyX = (getWidth() - scaledDifficultyWidth) / 2;
                g.drawImage(difficulty, difficultyX, currentY, scaledDifficultyWidth, scaledDifficultyHeight, this);
                if (optmenuopt == 1)
                    g.drawImage(difficulty2, difficultyX, currentY, scaledDifficultyWidth, scaledDifficultyHeight,
                            this);
                // Display current level setting for Difficulty
                int scaledLowWidth = (int) (low.getWidth(this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int scaledLowHeight = (int) (low.getHeight(this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int settingXOffset = (int) (250 * overallScaleFactor * MENU_IMAGE_SCALE_BOOST); // Adjusted for spacing
                if (difficultyLevel == 1) {
                    g.drawImage(low, difficultyX + settingXOffset, currentY, scaledLowWidth, scaledLowHeight, this);
                } else if (difficultyLevel == 2) {
                    g.drawImage(medium, difficultyX + settingXOffset, currentY, scaledLowWidth, scaledLowHeight, this);
                } else if (difficultyLevel == 3) {
                    g.drawImage(high, difficultyX + settingXOffset, currentY, scaledLowWidth, scaledLowHeight, this);
                }
                currentY += scaledDifficultyHeight
                        + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);

                // Draw "Volume" option
                int volumeX = (getWidth() - scaledVolumeWidth) / 2;
                g.drawImage(volume, volumeX, currentY, scaledVolumeWidth, scaledVolumeHeight, this);
                if (optmenuopt == 2)
                    g.drawImage(volume2, volumeX, currentY, scaledVolumeWidth, scaledVolumeHeight, this);
                // Display current level setting for Volume
                if (volumeLevel == 1) {
                    g.drawImage(low, volumeX + settingXOffset, currentY, scaledLowWidth, scaledLowHeight, this);
                } else if (volumeLevel == 2) {
                    g.drawImage(medium, volumeX + settingXOffset, currentY, scaledLowWidth, scaledLowHeight, this);
                } else if (volumeLevel == 3) {
                    g.drawImage(high, volumeX + settingXOffset, currentY, scaledLowWidth, scaledLowHeight, this);
                }
                currentY += scaledVolumeHeight
                        + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);

                // Draw "Sensitivity" option
                int sensitivityX = (getWidth() - scaledSensitivityWidth) / 2;
                g.drawImage(sensitivity, sensitivityX, currentY, scaledSensitivityWidth, scaledSensitivityHeight, this);
                if (optmenuopt == 3)
                    g.drawImage(sensitivity2, sensitivityX, currentY, scaledSensitivityWidth, scaledSensitivityHeight,
                            this);
                // Display current level setting for Sensitivity
                if (sensitivityLevel == 1) {
                    g.drawImage(low, sensitivityX + settingXOffset, currentY, scaledLowWidth, scaledLowHeight, this);
                } else if (sensitivityLevel == 2) {
                    g.drawImage(medium, sensitivityX + settingXOffset, currentY, scaledLowWidth, scaledLowHeight, this);
                } else if (sensitivityLevel == 3) {
                    g.drawImage(high, sensitivityX + settingXOffset, currentY, scaledLowWidth, scaledLowHeight, this);
                }
                currentY += scaledSensitivityHeight
                        + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);

                // Draw "Exit" from options menu
                int optionsExitX = (getWidth() - scaledOptionsExitWidth) / 2;
                g.drawImage(exit, optionsExitX, currentY, scaledOptionsExitWidth, scaledOptionsExitHeight, this);
                if (optmenuopt == 4)
                    g.drawImage(exit2, optionsExitX, currentY, scaledOptionsExitWidth, scaledOptionsExitHeight, this);
                break;
            case PLAYING:
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this); // Stretch background

                // Calculate scaled brick dimensions and gaps using respective scale factors
                int scaledBrickWidth = (int) (BASE_BRICK_WIDTH * widthScaleFactor);
                int scaledBrickHeight = (int) (BASE_BRICK_HEIGHT * heightScaleFactor);
                int scaledBrickHorizontalGap = (int) (BASE_BRICK_HORIZONTAL_GAP * widthScaleFactor);
                int scaledBrickVerticalGap = (int) (BASE_BRICK_VERTICAL_GAP * heightScaleFactor);
                int scaledBricksStartY = (int) (BASE_BRICKS_START_Y * heightScaleFactor);

                // Calculate the starting X position for the bricks to center them
                int totalBricksWidth = BRICK_COLS * scaledBrickWidth + (BRICK_COLS - 1) * scaledBrickHorizontalGap;
                int bricksStartX = (getWidth() - totalBricksWidth) / 2;

                // Draw bricks
                for (int r = 0; r < BRICK_ROWS; r++) {
                    for (int c = 0; c < BRICK_COLS; c++) {
                        if (bricks[r][c]) { // Only draw active bricks
                            // Assign different colors to rows for visual variety
                            switch (r) {
                                case 0:
                                    g.setColor(Color.CYAN);
                                    break;
                                case 1:
                                    g.setColor(Color.MAGENTA);
                                    break;
                                case 2:
                                    g.setColor(Color.YELLOW);
                                    break;
                                case 3:
                                    g.setColor(Color.ORANGE);
                                    break;
                                case 4:
                                    g.setColor(Color.PINK);
                                    break;
                                default:
                                    g.setColor(Color.LIGHT_GRAY);
                                    break;
                            }
                            int brickX = bricksStartX + c * (scaledBrickWidth + scaledBrickHorizontalGap);
                            int brickY = scaledBricksStartY + r * (scaledBrickHeight + scaledBrickVerticalGap);
                            g.fillRect(brickX, brickY, scaledBrickWidth, scaledBrickHeight);
                            g.setColor(Color.BLACK); // Draw black border for definition
                            g.drawRect(brickX, brickY, scaledBrickWidth, scaledBrickHeight);
                        }
                    }
                }

                // Calculate scaled ball and paddle dimensions for rendering
                int scaledBallSize = (int) (BASE_BALL_SIZE * overallScaleFactor);
                int scaledPaddleWidth = (int) (BASE_PADDLE_WIDTH * overallScaleFactor);
                int scaledPaddleHeight = (int) (BASE_PADDLE_HEIGHT * overallScaleFactor);

                // Draw ball
                g.setColor(Color.RED);
                g.fillOval((int) ballx, (int) bally, scaledBallSize, scaledBallSize);

                // Draw paddle
                g.setColor(Color.BLUE);
                g.fillRect(paddlex, getHeight() - scaledPaddleHeight, scaledPaddleWidth, scaledPaddleHeight);

                // Draw score
                g.setColor(Color.GREEN);
                g.setFont(new Font("Arial", Font.BOLD, (int) (20 * overallScaleFactor))); // Scale font size
                g.drawString("Score: " + score, (int) (10 * overallScaleFactor), (int) (25 * overallScaleFactor)); // Scale
                                                                                                                   // position

                // Draw lives
                g.setColor(Color.WHITE);
                g.drawString("Lives: " + lives, (int) (10 * overallScaleFactor), (int) (50 * overallScaleFactor)); // Scale
                                                                                                                   // position
                break;
            case PAUSED:
                // Draw semi-transparent overlay
                g2d.setColor(new Color(0, 0, 0, 150)); // Black with 150 alpha (out of 255)
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, (int) (40 * overallScaleFactor))); // Scale font size
                String pausedText = "PAUSED";
                int pausedTextWidth = g2d.getFontMetrics().stringWidth(pausedText);
                int pausedTextY = (int) (getHeight() / 2 - 100 * overallScaleFactor);
                g2d.drawString(pausedText, (getWidth() - pausedTextWidth) / 2, pausedTextY); // Centered above options

                // Draw pause menu options
                g2d.setFont(new Font("Arial", Font.PLAIN, (int) (30 * overallScaleFactor))); // Scale font size
                pauseMenuFontMetrics = g2d.getFontMetrics(); // Store font metrics for use in event handlers
                String[] pauseOptions = { "Resume", "Main Menu", "Exit" };
                int textHeight = pauseMenuFontMetrics.getHeight(); // Get actual scaled font height

                // Find the longest option for centering options
                int longestOptionWidth = 0;
                for (String option : pauseOptions) {
                    longestOptionWidth = Math.max(longestOptionWidth, pauseMenuFontMetrics.stringWidth(option));
                }

                // Calculate pause menu block position (start of the first option)
                // This should be below the "PAUSED" text with some initial padding
                int pauseMenuBlockY = pausedTextY + (int) (PAUSE_MENU_INITIAL_PADDING * overallScaleFactor);

                for (int i = 0; i < pauseOptions.length; i++) {
                    String option = pauseOptions[i];
                    int optionWidth = g2d.getFontMetrics().stringWidth(option);
                    int x = (getWidth() - optionWidth) / 2;
                    // Each option is positioned relative to pauseMenuBlockY
                    int y = pauseMenuBlockY + (i * (textHeight + (int) (MENU_ITEM_SPACING * overallScaleFactor)));

                    if (pauseMenuOpt == (i + 1)) { // Highlight selected option
                        g2d.setColor(Color.RED);
                    } else {
                        g2d.setColor(Color.WHITE);
                    }
                    g2d.drawString(option, x, y);
                }

                break;
            case GAME_OVER:
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this); // Use game background

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, (int) (50 * overallScaleFactor))); // Scale font size
                String gameOverText = "GAME OVER!";
                int textWidth = g.getFontMetrics().stringWidth(gameOverText);
                g.drawString(gameOverText, (getWidth() - textWidth) / 2,
                        (int) (getHeight() / 2 - 50 * overallScaleFactor));

                g.setFont(new Font("Arial", Font.PLAIN, (int) (30 * overallScaleFactor))); // Scale font size
                String scoreText = "Final Score: " + score;
                int scoreTextWidth = g.getFontMetrics().stringWidth(scoreText);
                g.drawString(scoreText, (getWidth() - scoreTextWidth) / 2, (int) (getHeight() / 2));

                g.setFont(new Font("Arial", Font.PLAIN, (int) (20 * overallScaleFactor))); // Scale font size
                String restartPrompt = "Click anywhere to Play Again!";
                int promptWidth = g.getFontMetrics().stringWidth(restartPrompt);
                g.drawString(restartPrompt, (getWidth() - promptWidth) / 2,
                        (int) (getHeight() / 2 + 50 * overallScaleFactor));
                break;
            case WIN:
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this); // Use game background

                g.setColor(Color.GREEN);
                g.setFont(new Font("Arial", Font.BOLD, (int) (60 * overallScaleFactor))); // Scale font size
                String winText = "YOU WIN!";
                int winTextWidth = g.getFontMetrics().stringWidth(winText);
                g.drawString(winText, (getWidth() - winTextWidth) / 2,
                        (int) (getHeight() / 2 - 50 * overallScaleFactor));

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, (int) (30 * overallScaleFactor))); // Scale font size
                String finalScoreText = "Final Score: " + score;
                int finalScoreTextWidth = g.getFontMetrics().stringWidth(finalScoreText);
                g.drawString(finalScoreText, (getWidth() - finalScoreTextWidth) / 2, (int) (getHeight() / 2));

                g.setFont(new Font("Arial", Font.PLAIN, (int) (20 * overallScaleFactor))); // Scale font size
                String winRestartPrompt = "Click anywhere to Play Again!";
                int winPromptWidth = g.getFontMetrics().stringWidth(winRestartPrompt);
                g.drawString(winRestartPrompt, (getWidth() - winPromptWidth) / 2,
                        (int) (getHeight() / 2 + 50 * overallScaleFactor));
                break;
        }
    }

    // --- Key Events ---
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (currentGameState == GameState.PLAYING) {
                currentGameState = GameState.PAUSED;
                pauseMenuOpt = 1; // Reset selection to "Resume"
                System.out.println("State transition: PLAYING -> PAUSED");
            } else if (currentGameState == GameState.PAUSED) {
                // If already paused, Escape can act as a "Resume" shortcut or exit if currently
                // on "Resume" option
                if (pauseMenuOpt == 1) {
                    currentGameState = GameState.PLAYING;
                    System.out.println("State transition: PAUSED -> PLAYING (via Escape)");
                }
            } else {
                // Allow ESC to exit from other states if not in game or paused
                System.exit(0);
            }
            repaint();
            return; // Consume the event
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
                if (frame.isUndecorated()) { // Currently fullscreen
                    frame.dispose();
                    frame.setUndecorated(false);
                    frame.setExtendedState(JFrame.NORMAL);
                    if (originalBounds != null) {
                        frame.setBounds(originalBounds); // Restore original size and position
                    } else {
                        frame.pack(); // Fallback if originalBounds not captured
                    }
                    frame.setLocationRelativeTo(null); // Center the window
                    frame.setVisible(true);
                } else { // Currently windowed
                    originalBounds = frame.getBounds(); // Store current bounds
                    frame.dispose();
                    frame.setUndecorated(true);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setVisible(true);
                }
            }
            repaint(); // Repaint after fullscreen toggle
            return; // Consume the event
        }

        // Calculate scale factor for dynamic positioning
        double overallScaleFactor = Math.min((double) getWidth() / DEFAULT_GAME_WIDTH,
                (double) getHeight() / DEFAULT_GAME_HEIGHT);

        // Handle keyboard navigation for menus
        if (currentGameState == GameState.MAIN_MENU) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                startopt = Math.max(1, startopt - 1);
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                startopt = Math.min(3, startopt + 1);
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                int scaledStartHeight = (int) (start.getHeight(jPong.this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledOptionsHeight = (int) (optionsimg.getHeight(jPong.this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledExitHeight = (int) (exit.getHeight(jPong.this) * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);

                int totalMainMenuItemsHeight = scaledStartHeight + scaledOptionsHeight + scaledExitHeight;
                int totalMainMenuSpacing = (int) (MENU_ITEM_SPACING * 2 * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                int mainMenuBlockHeight = totalMainMenuItemsHeight + totalMainMenuSpacing;
                int mainMenuBlockY = (getHeight() / 2) - (mainMenuBlockHeight / 2);

                int simulatedY = 0;
                if (startopt == 1) {
                    simulatedY = mainMenuBlockY;
                } else if (startopt == 2) {
                    simulatedY = mainMenuBlockY + scaledStartHeight
                            + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                } else if (startopt == 3) {
                    simulatedY = mainMenuBlockY + scaledStartHeight
                            + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST)
                            + scaledOptionsHeight
                            + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                }
                handleMenuClick(simulatedY);
            }
        } else if (currentGameState == GameState.OPTIONS_MENU) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                optmenuopt = Math.max(1, optmenuopt - 1);
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                optmenuopt = Math.min(4, optmenuopt + 1);
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                int scaledDifficultyHeight = (int) (difficulty.getHeight(jPong.this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledVolumeHeight = (int) (volume.getHeight(jPong.this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledSensitivityHeight = (int) (sensitivity.getHeight(jPong.this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int scaledExitOptionsHeight = (int) (exit.getHeight(jPong.this) * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);

                int totalOptionsMenuItemsHeight = scaledDifficultyHeight + scaledVolumeHeight + scaledSensitivityHeight
                        + scaledExitOptionsHeight;
                int totalOptionsMenuSpacing = (int) (MENU_ITEM_SPACING * 3 * overallScaleFactor
                        * MENU_IMAGE_SCALE_BOOST);
                int optionsMenuBlockHeight = totalOptionsMenuItemsHeight + totalOptionsMenuSpacing;
                int optionsMenuBlockY = (getHeight() / 2) - (optionsMenuBlockHeight / 2)
                        + (int) (MENU_VERTICAL_OFFSET * overallScaleFactor); // Apply offset

                int simulatedY = 0;
                if (optmenuopt == 1) {
                    simulatedY = optionsMenuBlockY;
                } else if (optmenuopt == 2) {
                    simulatedY = optionsMenuBlockY + scaledDifficultyHeight
                            + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                } else if (optmenuopt == 3) {
                    simulatedY = optionsMenuBlockY + scaledDifficultyHeight
                            + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST)
                            + scaledVolumeHeight
                            + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                } else if (optmenuopt == 4) {
                    simulatedY = optionsMenuBlockY + scaledDifficultyHeight
                            + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST)
                            + scaledVolumeHeight
                            + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST)
                            + scaledSensitivityHeight
                            + (int) (MENU_ITEM_SPACING * overallScaleFactor * MENU_IMAGE_SCALE_BOOST);
                }
                handleMenuClick(simulatedY);
            }
        } else if (currentGameState == GameState.PLAYING) {
            double currentPaddleSpeed = BASE_PADDLE_SPEED;
            if (sensitivityLevel == 2) {
                currentPaddleSpeed *= 1.2; // Slightly faster
            } else if (sensitivityLevel == 3) {
                currentPaddleSpeed *= 1.4; // Even faster
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                paddleXVelocity = -currentPaddleSpeed;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                paddleXVelocity = currentPaddleSpeed;
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Spacebar to launch ball
                launchBall();
            }
        } else if (currentGameState == GameState.PAUSED) { // Keyboard navigation for pause menu
            double currentScaleFactor = Math.min((double) getWidth() / DEFAULT_GAME_WIDTH,
                    (double) getHeight() / DEFAULT_GAME_HEIGHT);
            // Use stored FontMetrics for accurate text height, fallback to scaled
            // MENU_ITEM_HEIGHT
            int textHeight = (pauseMenuFontMetrics != null) ? pauseMenuFontMetrics.getHeight()
                    : (int) (MENU_ITEM_HEIGHT * currentScaleFactor);

            // Replicate the calculation from paintComponent for consistency
            int pausedTextY = (int) (getHeight() / 2 - 100 * currentScaleFactor);
            int dynamicPauseMenuBlockY = pausedTextY + (int) (PAUSE_MENU_INITIAL_PADDING * currentScaleFactor);

            int simulatedY = dynamicPauseMenuBlockY
                    + (pauseMenuOpt - 1) * (textHeight + (int) (MENU_ITEM_SPACING * currentScaleFactor));
            handleMenuClick(simulatedY);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (currentGameState == GameState.PLAYING) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A ||
                    e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                paddleXVelocity = 0; // Stop paddle movement when key is released
            }
        }
    }

    // --- Main Method ---
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Pong by CodeFox");
        jPong gamePanel = new jPong();
        frame.add(gamePanel);
        frame.pack();
        frame.setResizable(true); // Allow resizing
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Capture original bounds after frame is visible and packed
        gamePanel.originalBounds = frame.getBounds();

    }
}

/*
 * PROGRAM DESCRIPTION:
 * jPong.java
 *
 * This program is a feature-rich clone of the classic Pong game, enhanced with
 * "Breakout"-style brick breaking mechanics.
 *
 * HOW IT WORKS:
 * 1. Features a comprehensive menu system (Start, Options, Exit).
 * 2. Options allow customization of Difficulty, Volume, and Sensitivity.
 * 3. Gameplay involves controlling a paddle to bounce a ball and destroy
 * bricks.
 * 4. Includes game states for Splash Screen, Menu, Playing, Paused, and Game
 * Over.
 * 5. Handles collision detection for paddle, walls, and bricks.
 */
