import javax.swing.*;
import java.awt.*;

public class Whiteboard extends JComponent {

    private Image image;
    public Graphics2D graphics;

    public Whiteboard() {
        this.image = createImage(getSize().width,getSize().height);
        this.graphics = (Graphics2D) image.getGraphics();
    }

}
