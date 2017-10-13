import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class ColoredShape implements Serializable {

    private String text;
    private Font font;
    private int x;
    private int y;
    private boolean isText = false;
    private transient BasicStroke stroke;
    private float strokeNum;
    private Shape shape;
    private Color color;

    public ColoredShape(String text, Color c, int x, int y) {
        isText = true;
        this.text = text;
        this.color = c;
        this.x = x;
        this.y = y;
    }

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
        if(isText) { return null; }
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

    public String getText() {
        if (!isText) { return null; }
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getX() {
        if (isText) { return x; }
        return shape.getBounds().x;
    }

    public void setX(int x) {
        if (isText) {
            this.x = x;
        } else {
            // TODO fix movement of non-text shapes
            throw new NotImplementedException();
        }
    }

    public int getY() {
        if (isText) { return y; }
        return shape.getBounds().y;
    }

    public void setY(int y) {
        if (isText) {
            this.y = y;
        } else {
            // TODO fix movement of non-text shapes
            throw new NotImplementedException();
        }
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public boolean IsText () {
        return isText;
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