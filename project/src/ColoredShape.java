import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class ColoredShape implements Serializable {
    private transient BasicStroke stroke;

    private float strokeNum;
    private Shape shape;
    private Color color;

    public ColoredShape(Shape s, Color c, BasicStroke stroke) {
        this.shape = s;
        this.color = c;
        this.stroke = stroke;
        this.strokeNum = this.stroke.getLineWidth();
    }

    public ColoredShape(Shape s, Color c, float strokeNum) {
        this.shape = s;
        this.color = c;
        this.strokeNum = strokeNum;
        this.stroke = new BasicStroke(this.strokeNum);
    }

    public BasicStroke getStroke() {
        return stroke;
    }

    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
        this.strokeNum = this.stroke.getLineWidth();
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getStrokeNum() {
        return strokeNum;
    }

    public void setStrokeNum(float strokeNum) {
        this.strokeNum = strokeNum;
    }


    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        this.stroke = new BasicStroke(this.strokeNum);
    }
}