package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;

import java.awt.*;

public class PepseGameManager extends GameManager {

    /************ Game Settings Constants ***************/
    public static final String WINDOWS_NAME = "Pepse Game";
    private static final int BOARD_HEIGHT = 720;
    private static final int BOARD_WIDTH = 1005;

    /************** day/night properties ***************/

    private static final float NIGHT_CYCLE_LEN = 12;
    private static final float SUNSET_CYCLE = NIGHT_CYCLE_LEN * 2;
    private static final Color HALO_COLOR = new Color(255, 255, 0, 20);

    /************** Terrain properties ***************/
    public static final int TERRAIN_SEED = 1000;
    private Vector2 windowDimensions;
    private Terrain terrain;

    /************ Class Functions ***********/

    /**
     * The constructor of Pepse Game Manager
     *
     * @param windowTitle      The name of the window
     * @param windowDimensions the windows dimension
     */
    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }


    /**
     * Initializes the PepseGameManager
     *
     * @param imageReader      the image reader to use for loading images
     * @param soundReader      the sound reader to use for loading sounds
     * @param inputListener    the user input listener to use for handling user input
     * @param windowController the window controller to use for controlling the game window
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        skyCreator();
        terrainCreator(0, (int) windowDimensions.x());
        treesCreator(0, (int)windowDimensions.x());
    }

    /**
     * Creates a new terrain and adds it to the list of game objects.
     */
    private void terrainCreator(int minX, int maxX) {
        this.terrain = new Terrain(this.gameObjects(), Layer.STATIC_OBJECTS, windowDimensions, TERRAIN_SEED);
        terrain.createInRange(minX, maxX);
    }

    /**
     * Creates a new sky and adds it to the list of game objects.
     */
    private void skyCreator() {
        GameObject sky = Sky.create(gameObjects(), windowDimensions, Layer.BACKGROUND);
        GameObject night = Night.create(gameObjects(), Layer.FOREGROUND, windowDimensions, NIGHT_CYCLE_LEN);
        GameObject sun = Sun.create(gameObjects(), Layer.BACKGROUND, windowDimensions, SUNSET_CYCLE);
        GameObject sunHalo = SunHalo.create(gameObjects(), Layer.BACKGROUND, sun, HALO_COLOR);
    }


    private void treesCreator(int minX, int maxX) {
        for (int curX = minX; curX <= maxX; curX += 2 * Block.SIZE) {
            if (Tree.shouldPlantTree()) {
                float curY = (float) Math.floor(terrain.groundHeightAt(curX) / Block.SIZE) * Block.SIZE;
                Tree.Create(gameObjects(), new Vector2(curX, curY - Block.SIZE), Layer.STATIC_OBJECTS);
            }
        }
    }


    public static void main(String[] args) {
        new PepseGameManager(WINDOWS_NAME, new Vector2(BOARD_WIDTH, BOARD_HEIGHT)).run();
    }
}