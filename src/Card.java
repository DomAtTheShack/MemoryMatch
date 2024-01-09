import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Card class represents a playing card in a card game.
 */
public class Card
{
    /**
     * The variable 'x' represents the X coordinate of a playing card on the game board.
     * It is a private final integer variable that cannot be modified once assigned a value.
     *
     * The value of 'x' is set in the constructor of the Card class, and represents the horizontal position
     * of the card on the game board grid.
     * The Card object is a part of a card game and is used to
     * represent a playing card with its associated attributes and behaviors.
     *
     * The Card class provides several methods to interact with the card object, such as drawing the card,
     * checking if the card has been clicked, getting the card's image, setting the card's color, flipping
     * the card over, and checking if the card has been marked as done.
     *
     * Please refer to the Card class documentation for more information about the context and usage of
     * the 'x' variable.
     */
    private final int x;
    /**
     * The variable 'y' represents the Y coordinate of a playing card on the game board.
     * It is a private final integer variable that cannot be modified once assigned a value.
     *
     * The value of 'y' is set in the constructor of the Card class, and represents the vertical position
     * of the card on the game board grid. The Card object is a part of a card game and is used to
     * represent a playing card with its associated attributes and behaviors.
     *
     * The Card class provides several methods to interact with the card object, such as drawing the card,
     * checking if the card has been clicked, getting the card's image, setting the card's color, flipping
     * the card over, and checking if the card has been marked as done.
     *
     * Please refer to the Card class documentation for more information about the context and usage of
     * the 'y' variable.
     */
    private final int y;
    /**
     * The width of a card.
     *
     * <p>This variable represents the width of a card in pixels. It is a constant value that is set to 80.</p>
     *
     * @since 1.0
     */
    private static final int CARD_WIDTH = 80;
    /**
     * The constant variable CARD_HEIGHT represents the height of a card.
     *
     * @since 1.0
     */
    private static final int CARD_HEIGHT = 120;
    /**
     * The boundingBox represents the rectangular area that contains the card.
     * It is a private final variable of the Card class.
     */
    private final Rectangle BOUNDING_BOX;
    /**
     * Represents the color of a card.
     *
     * <p>
     * The color of a card is a property that determines its appearance.
     * </p>
     *
     * @see Card
     *
     */
    private Color color;
    /**
     * This variable represents whether a card is flipped or not.
     */
    private boolean isFlipped;
    /**
     * Represents the completion status of the card.
     */
    private boolean done;
    /**
     * Represents an image used in a Card object.
     *
     * This variable is used to store the image displayed on the card.
     * It is a private and final field, indicating that it cannot be changed once initialized.
     *
     * The Image field is of type BufferedImage, which is a subclass of Image. BufferedImage
     * provides methods for manipulating and getting information about the image.
     *
     * This field is initialized through the constructor of the Card class. The constructor
     * takes an instance of BufferedImage as one of its parameters and assigns it to the Image field.
     *
     */
    private final BufferedImage Image;

    /**
     * Constructs a new Card object.
     *
     * @param x     the x-coordinate of the card's position
     * @param y     the y-coordinate of the card's position
     * @param color the color of the card
     * @param Image the image of the card
     */
    public Card(int x, int y, Color color, BufferedImage Image) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.BOUNDING_BOX = new Rectangle(x, y, CARD_WIDTH, CARD_HEIGHT);
        this.Image = Image;
        isFlipped = false;
        done = false;
    }

    /**
     * Draws the card on the graphics object with the specified color, position, and image.
     *
     * @param g the graphics object to draw on
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        if (Image != null && isFlipped) {
            g.drawImage(Image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
        }
    }

    /**
     * Determines if the specified point is within the bounding box of the card.
     *
     * @param mouseX the x-coordinate of the mouse click
     * @param mouseY the y-coordinate of the mouse click
     * @return true if the point is within the bounding box of the card, false otherwise
     */
    public boolean isClicked(int mouseX, int mouseY) {
        return BOUNDING_BOX.contains(mouseX, mouseY);
    }

    /**
     * Returns the image of the card.
     *
     * @return the image of the card
     */


    public BufferedImage getImage() {
        return Image;
    }

    /**
     * Sets the color of the card.
     *
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Flips the card by changing the state of {@code isFlipped}.
     */
    public void flip()
    {
        isFlipped = !isFlipped;
    }

    /**
     * Marks the card as done by setting the done flag to true.
     */
    public void done() {
        done = true;
    }

    /**
     * Determines whether the card is flipped or not.
     *
     * @return true if the card is flipped, false otherwise
     */
    public boolean isFlipped()
    {
        return isFlipped;
    }

    /**
     * Determines whether the card is marked as done or not.
     *
     * @return true if the card is marked as done, false otherwise
     */
    public boolean isDone()
    {
        return done;
    }
}
