import javax.swing.JPanel;
import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardPanel extends JPanel {
    private final List<Card> cards;

    public CardPanel(List<Card> cards) {
        this.cards = cards;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    handleCardClick(e.getX(), e.getY());
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                repaint();
            }
        });
    }
    private void handleCardClick(int mouseX, int mouseY) throws InterruptedException {
        for (Card card : cards) {
            if (card.isClicked(mouseX, mouseY)) {
                card.flip();
                switch (CardGameGUI.flipped){
                    case 0:
                        CardGameGUI.flipped ++;
                        CardGameGUI.tempCard = card;
                        break;
                    case 1:
                        if(CardGameGUI.checkCards(card.getImage(), CardGameGUI.tempCard.getImage()))
                        {
                            card.flip();
                            card.setColor(Color.BLACK);
                            card.done();
                            CardGameGUI.tempCard.flip();
                            CardGameGUI.tempCard.setColor(Color.BLACK);
                            CardGameGUI.tempCard.done();
                            CardGameGUI.flipped = 0;
                        }
                }
                System.out.println("Card Clicked!");
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
