import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The CardGameGUI class represents the graphical user interface for a card game.
 * It provides methods to generate and display cards, check if two cards match,
 * check if the player has won the game, and display a timer.
 */
public class CardGameGUI
{
    /**
     *
     */
    public static List<Card> cards = new ArrayList<>();
    /**
     * The flipped variable represents the state of a card being flipped or not in the card game.
     * It is a public static byte variable that can hold the values 0 or 1.
     *
     * <p>
     * The value of flipped determines whether a card is flipped or not. A value of 0 represents a card
     * that is not flipped, while a value of 1 represents a card that is flipped.
     * </p>
     *
     * <p>
     * The flipped variable is used in the CardGameGUI class to track the state of the cards in the game.
     * It is used to check if a card is flipped or not, and to set the flipped state of a card.
     * </p>
     *
     * <p>
     * The flipped variable is initialized to 0, indicating that no cards are flipped initially.
     * </p>
     *
     * @since 1.0
     * @see CardGameGUI
     */
    public static byte flipped = 0;
    /**
     * This variable represents a temporary card in the card game.
     *
     * <p>
     * The tempCard variable is a static variable of the CardGameGUI class.
     * It is used to temporarily store a Card object during the game.
     * It can be accessed and modified from different methods within the CardGameGUI class.
     * </p>
     *
     * <p>
     * The tempCard variable is initially set to null and can be assigned a Card object
     * during the game based on certain conditions and actions.
     * </p>
     *
     * <p>
     * The tempCard variable is static because it needs to be shared among different methods
     * without the need to create an instance of the CardGameGUI class.
     * </p>
     *
     * @see CardGameGUI
     */
    public static Card tempCard;
    /**
     * The {@code toFlip} variable represents whether a card is flipped or not.
     * It is a public static boolean variable that determines the state of a card.
     */
    public static boolean toFlip;

    /**
     * The main method is the entry point for the Java application.
     * It starts the Swing GUI by invoking the createAndShowGUI method in the CardGameGUI class.
     *
     * @param args the command line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CardGameGUI::createAndShowGUI);
    }

    /**
     * The CardGameGUI class represents the graphical user interface for a card game.
     * It contains a JFrame object called 'frame' that represents the main window of the game.
     * The frame is initialized with a title "Card Game".
     *
     * The CardGameGUI class also contains a private static final field 'frame', which
     * is an instance of the JFrame class. The 'frame' object is used to display the
     * graphical user interface of the card game. The 'frame' is set to be non-resizable
     * and has a fixed size of 800 x 1000 pixels.
     *
     * The CardGameGUI class provides a static method called 'createAndShowGUI' that
     * creates the main window of the card game*/
    private static final JFrame frame = new JFrame("Card Game");
    /**
     * The variable 'badFlips' represents the number of times the user has made
     * incorrect flips in the card game.
     *
     */
    public static short badFlips;
    /**
     * The timer used in timing the game for end results.
     * */
    private static Timer timer;

    /**
     * The startTime variable represents the starting time of the game.
     * It is a private static-long variable that stores the time in milliseconds.
     *
     * The startTime variable is used in the CardGameGUI class to calculate the elapsed time
     * since the game started.
     *
     * Please refer to the CardGameGUI class documentation for more information about the context and usage of
     * the startTime variable.
     */
    private static long startTime;

    /**
     * Creates and shows the GUI for the card game.
     * The GUI consists of a JFrame that displays a collection of cards.
     * The method sets up the frame, generates the cards, adds them to the frame, and starts the timer.
     * Once the GUI is ready, it sets the frame to be visible.
     */
    private static void createAndShowGUI()
    {
        frame.setSize(800, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        generateCards();

        CardPanel cardPanel = new CardPanel(cards);
        frame.add(cardPanel);

        frame.setVisible(true);
        startTimer();
    }

    /**
     * Generates a collection of cards for the card game.
     *
     * This method initializes and generates a collection of playing cards for the card game. It uses a random number generator to randomly select images for each card from a given
     * set, and assigns each card with a position on the game board. The generated cards are then added to a {@code List<Card>} object.
     *
     * The method starts by creating a new instance of the Random class to generate random numbers. It also initializes variables for the X and Y coordinates of the cards on the game
     * board, and creates an {@code ArrayList<Integer>} object to keep track of the indices of the images that have been used. It also creates an array of BufferedImage objects for the images
     * of the cards.
     *
     * The method then loops through the array of BufferedImage objects and loads each image using the loadImage method.
     *
     * Next, the method enters a loop to generate 56 cards. In each iteration, it generates a random number between 0 and 55 (inclusive) using the random.nextInt() method. It then
     * checks if the random number has already been used in the cards generated so far by calling the not*/

    private static void generateCards()
    {

        Random random = new Random();
        int cardX = 10;
        int cardY = 10;
        ArrayList<Integer> used = new ArrayList<>();
        BufferedImage[] Images = new BufferedImage[56];
        for(int  i = 0; i < Images.length; i++)
        {
            Images[i] = loadImage("image" + i + ".png");
        }

        for (int i = 0; i < 56; i++)
        {
            int Random = random.nextInt(56);
            while (!notUsed(Random, used))
            {
                Random = random.nextInt(56);
            }
            used.add(Random);
            cards.add(new Card(cardX, cardY, Color.WHITE, Images[Random]));

            cardX += 90; // Space between cards
            if (cardX + 80 > 800) {
                cardX = 10;
                cardY += 130; // Move to the next row
            }
        }
    }

    /**
     * Loads an image from the specified path.
     *
     * @param path the path to the image file
     * @return the loaded image as a BufferedImage, or null if an error occurred
     */
    private static BufferedImage loadImage(String path)
    {
        try
        {
            return ImageIO.read(Objects.requireNonNull(CardGameGUI.class.getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if a given integer is not present in the provided list.
     *
     * @param x the integer value to check
     * @param list the list of integers to search for x
     * @return {@code true} if x is not present in the*/
    private static boolean notUsed(int x, ArrayList<Integer> list)
    {
        for(int y: list)
        {
            if(y==x)
                return false;
        }
        return true;
    }

    /**
     *
     * Checks if two BufferedImage objects have the same dimensions and pixel values.
     *
     * @param img1 the first BufferedImage object to compare
     * @param img2 the second BufferedImage object to compare
     * @return true if the two images have the same dimensions and pixel values, false otherwise
     */
    public static boolean checkCards(BufferedImage img1, BufferedImage img2)
    {
        if (img1.getWidth() != img2.getWidth() ||
                img1.getHeight() != img2.getHeight())
        {
            return false;
        }

        for (int y = 0; y < img1.getHeight(); y++)
        {
            for (int x = 0; x < img1.getWidth(); x++)
            {
                if (img1.getRGB(x, y) != img2.getRGB(x, y))
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the player has won the game by checking if all the cards are flipped.
     * Stops the timer and displays a message with the game result.
     *
     * @param cards the list of cards to check
     */
    public static void checkWin(List<Card> cards)
    {
        for (Card card : cards)
        {
            if (!card.isDone())
            {
                return;
            }
        }

        stopTimer(); // Stop the timer when the player wins

        if (badFlips < 0)
        {
            JOptionPane.showMessageDialog(frame, "You won finally but you suck! Also im not even going to list the time loser!",
                    "Winner", JOptionPane.INFORMATION_MESSAGE);
        } else {
            long elapsedTime = System.currentTimeMillis() - startTime;
            String formattedTime = formatElapsedTime(elapsedTime);

            JOptionPane.showMessageDialog(frame, "You won the game with " + badFlips + " unnecessary flips in " +
                    formattedTime, "Winner", JOptionPane.INFORMATION_MESSAGE);
        }

        System.exit(0);
    }

    /**
     * Starts the timer for the card game.
     *
     * This method initializes the startTime variable with the current system time in milliseconds.
     * It creates a Timer object that fires an action event every 1000 milliseconds without performing any action.
     * The timer is started using the start() method.
     *
     * @see java.awt.event.ActionListener
     * @see Timer
     */
    private static void startTimer()
    {
        startTime = System.currentTimeMillis();
        timer = new Timer(1000, e -> {});
        timer.start();
    }

    /**
     * Stops the timer for the card game.
     *
     * This method checks if the timer is running and stops it if it is.
     *
     * @see Timer
     */
    private static void stopTimer()
    {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    /**
     * Converts the given elapsed time in milliseconds to a formatted string in the format "mm:ss".
     *
     * @param elapsedTime the elapsed time in milliseconds
     * @return a formatted string representing the elapsed time in "mm:ss" format
     */
    private static String formatElapsedTime(long elapsedTime)
    {
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        seconds %= 60;

        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(minutes) + ":" + decimalFormat.format(seconds);
    }

}
