import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The CardPanel class represents a panel that displays a collection of cards in a card game.
 * It extends the JPanel class and provides the functionality to handle card clicks, update the cards' state,
 **/
public class CardPanel extends JPanel
{
    /**
     * The 'cards' variable is a private final List of Card objects.
     *
     * It represents the list of cards in a card game.
     *
     * This variable is used to store and manage the Card objects that are part of the game.
     * It is a private variable, indicating that it is only accessible within the containing class.
     * It is also marked as final, indicating that the reference to the list cannot be changed once it is assigned.
     *
     * The 'cards' field is initialized through the constructor of the containing class, CardPanel.
     * The constructor takes an instance of {@code List<Card>} as one of its parameters and assigns it to the 'cards' field.
     *
     * The List interface is a part of the Java Collections Framework and represents an ordered collection of elements.
     * In this case, it is used to store the Card objects that are part of the game.
     * The List interface provides methods to add, remove, and access elements in the list, among others.
     *
     * Please refer to the CardPanel class documentation for more information about the context and usage of the 'cards'*/
    private final List<Card> cards;

    /**
     * The CardPanel class represents a JPanel that contains a list of Card objects.
     * It handles mouse click events on the cards and updates their states accordingly.
     *
     * @param cards A list of Card objects to display in the panel.
     */
    public CardPanel(List<Card> cards)
    {
        this.cards = cards;


        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    handleCardClick(e.getX(), e.getY());
                } catch (InterruptedException ex)
                {
                    throw new RuntimeException(ex);
                }
                repaint();
                CardGameGUI.checkWin(cards);
            }
        });
    }

    /**
     * Handles the click event on a card. If allowed, flips the card and checks for matches with the previously flipped card.
     * If a match is found, marks both cards as done. If not, increments the bad flips count and triggers a flip back after a delay.
     *
     * @param mouseX The x-coordinate of the mouse click event.
     * @param mouseY The y-coordinate of the mouse click event.
     * @throws InterruptedException if the thread is interrupted while waiting for a delay.
     */
    private void handleCardClick(int mouseX, int mouseY) throws InterruptedException
    {
        System.out.println(CardGameGUI.flipped);
        if(CardGameGUI.flipped == 2)
            return;
        for (Card card : cards)
        {
            if (card.isClicked(mouseX, mouseY) && !card.isFlipped())
            {
                card.flip();
                switch (CardGameGUI.flipped) {
                    case 0:
                        CardGameGUI.flipped++;
                        CardGameGUI.tempCard = card;
                        break;
                    case 1:
                        CardGameGUI.flipped++; // Increment flipped count before timer logic

                        if (CardGameGUI.checkCards(card.getImage(), CardGameGUI.tempCard.getImage()))
                        {
                            card.setColor(Color.BLACK);
                            card.done();
                            CardGameGUI.tempCard.setColor(Color.BLACK);
                            CardGameGUI.tempCard.done();
                            new Thread(() -> {
                                try {
                                    waitThenFlip();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();
                        } else {
                            CardGameGUI.badFlips ++;
                            CardGameGUI.toFlip = true;
                            new Thread(() -> {
                                try {
                                    waitThenFlip();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();
                        }
                        break;
                }
            }
        }
    }

    /**
     * Waits for a certain amount of time, then flips back all the cards that are currently flipped.
     *
     * @throws InterruptedException if the thread is interrupted while waiting for a delay.
     */
    private void waitThenFlip() throws InterruptedException {
        Thread.sleep(1200);
        flipBack();
        repaint();
        CardGameGUI.flipped = 0;
    }

    /**
     * Flips back all the cards that are currently flipped.
     */
    private void flipBack()
    {
        for (Card card : cards)
        {
            if (card.isFlipped() && !card.isDone())
            {
                card.flip();
            }
        }
    }


    /**
     * Draws the Card object on the specified Graphics object.
     *
     * This method is responsible for rendering the card's appearance on the graphics object.
     * It fills a rectangle with the card's color, draws a black outline, and optionally, if the card is flipped,
     * draws the card's image on the graphics object.
     *
     * @param g the Graphics object to draw on
     * @see Card
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for (Card card : cards)
        {
            card.draw(g);
        }
    }
}
