import java.awt.*;
import java.awt.image.BufferedImage;

public class Card {
    private final int x;
    private final int y;
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;
    private final Rectangle boundingBox;
    private Color color;
    private boolean isFlipped;
    private boolean done;
    private final MemoryCard MemoryCard;

    public Card(int x, int y, Color color, MemoryCard MemoryCard) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.boundingBox = new Rectangle(x, y, CARD_WIDTH, CARD_HEIGHT);
        this.MemoryCard = MemoryCard;
        isFlipped = false;
        done = false;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        if (MemoryCard.getImage() != null && isFlipped) {
            g.drawImage(MemoryCard.getImage(), x, y, CARD_WIDTH, CARD_HEIGHT, null);
        }
    }

    public boolean isClicked(int mouseX, int mouseY) {
        return boundingBox.contains(mouseX, mouseY);
    }

    public BufferedImage getImage() {
        return MemoryCard.getImage();
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void flip()
    {
        isFlipped = !isFlipped;
    }

    public void done() {
        done = true;
    }
}
