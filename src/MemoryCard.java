import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MemoryCard {
    private final BufferedImage Image;
    private final int cardNum;

    public MemoryCard(BufferedImage Image, int place)
    {
        this.Image = Image;
        cardNum = place;
    }

    public BufferedImage getImage() {
        return Image;
    }

    public int getCardNum() {
        return cardNum;
    }
}
