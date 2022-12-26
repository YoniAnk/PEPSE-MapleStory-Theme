package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Sky;
import pepse.world.Terrain;

public class PepseGameManager extends GameManager {

    /************ Game Settings Constants ***************/
    public static final String WINDOWS_NAME = "Pepse Game";
    private static final int BOARD_HEIGHT = 700;
    private static final int BOARD_WIDTH = 900;

    /************** Terrain properties ***************/
    public static final int TERRAIN_SEED = 1000;


    /************ Class Functions ***********/

    /**
     * The constructor of Pepse Game Manager
     *
     * @param windowTitle The name of the window
     * @param windowDimensions the windows dimension
     */
    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle,windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        skyCreator(windowController);
        terrainCreator(windowController);
    }
    /**
     * Creates a new terrain and adds it to the list of game objects.
     *
     * @param windowController the window controller that controls the game window
     */
    private void terrainCreator(WindowController windowController) {
        Terrain terrain = new Terrain(this.gameObjects(),Layer.STATIC_OBJECTS,
                windowController.getWindowDimensions(), TERRAIN_SEED);
        terrain.createInRange(0, (int) windowController.getWindowDimensions().x());
    }

    /**
     * Creates a new sky and adds it to the list of game objects.
     *
     * @param windowController the window controller that controls the game window
     */
    private void skyCreator(WindowController windowController) {
        GameObject sky = Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);
    }


    public static void main(String[] args) {
        new PepseGameManager(WINDOWS_NAME, new Vector2(BOARD_WIDTH, BOARD_HEIGHT)).run();
    }
}