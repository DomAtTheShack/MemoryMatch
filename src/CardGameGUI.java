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

public class CardGameGUI {
    public static List<Card> cards = new ArrayList<>();
    public static byte flipped = 0;
    public static Card tempCard;
    public static boolean toFlip;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CardGameGUI::createAndShowGUI);
    }
    private static final JFrame frame = new JFrame("Card Game");
    public static short badFlips;
    private static Timer timer;

    private static long startTime;


    private static void createAndShowGUI() {
        frame.setSize(800, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        generateCards();

        CardPanel cardPanel = new CardPanel(cards);
        frame.add(cardPanel);

        frame.setVisible(true);
        startTimer();
    }

    private static void generateCards() {

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

    private static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(CardGameGUI.class.getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static boolean notUsed(int x, ArrayList<Integer> list)
    {
        for(int y: list)
        {
            if(y==x)
                return false;
        }
        return true;
    }
    public static boolean checkCards(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false;
        }

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void checkWin(List<Card> cards) {
        for (Card card : cards) {
            if (!card.isDone()) {
                return;
            }
        }

        stopTimer(); // Stop the timer when the player wins

        if (badFlips < 0) {
            JOptionPane.showMessageDialog(frame, "You won finally but you suck! Also im not even going to list the time loser!", "Winner", JOptionPane.INFORMATION_MESSAGE);
        } else {
            long elapsedTime = System.currentTimeMillis() - startTime;
            String formattedTime = formatElapsedTime(elapsedTime);

            JOptionPane.showMessageDialog(frame, "You won the game with " + badFlips + " unnecessary flips in " + formattedTime, "Winner", JOptionPane.INFORMATION_MESSAGE);
        }

        System.exit(0);
    }

    private static void startTimer() {
        startTime = System.currentTimeMillis();
        timer = new Timer(1000, e -> {});
        timer.start();
    }

    private static void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
    private static String formatElapsedTime(long elapsedTime) {
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        seconds %= 60;

        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(minutes) + ":" + decimalFormat.format(seconds);
    }

}
