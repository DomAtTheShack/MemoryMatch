import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CardPanel extends JPanel {
    private final List<Card> cards;
    private final Timer flipTimer;

    public CardPanel(List<Card> cards) {
        this.cards = cards;

        flipTimer = new Timer(3000, e -> {
            flipBack();
            repaint();
            CardGameGUI.flipped = 0;
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    handleCardClick(e.getX(), e.getY());
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                repaint();
                CardGameGUI.checkWin(cards);
            }
        });
    }

    private void handleCardClick(int mouseX, int mouseY) throws InterruptedException {
        System.out.println(CardGameGUI.flipped);
        if(CardGameGUI.flipped == 2)
            return;
        for (Card card : cards) {
            if (card.isClicked(mouseX, mouseY) && !card.isFlipped()) {
                card.flip();
                switch (CardGameGUI.flipped) {
                    case 0:
                        CardGameGUI.flipped++;
                        CardGameGUI.tempCard = card;
                        break;
                    case 1:
                        CardGameGUI.flipped++; // Increment flipped count before timer logic

                        if (CardGameGUI.checkCards(card.getImage(), CardGameGUI.tempCard.getImage())) {
                            card.setColor(Color.BLACK);
                            card.done();
                            CardGameGUI.tempCard.setColor(Color.BLACK);
                            CardGameGUI.tempCard.done();
                            flipTimer.start();
                        } else {
                            CardGameGUI.badFlips ++;
                            CardGameGUI.toFlip = true;
                            flipTimer.start();
                        }
                        break;
                }
            }
        }
    }

    private void flipBack() {
        for (Card card : cards) {
            if (card.isFlipped() && !card.isDone()) {
                card.flip();
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Card card : cards) {
            card.draw(g);
        }
    }
}
