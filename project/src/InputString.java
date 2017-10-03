import java.awt.BasicStroke;
import java.awt.Color;
import java.io.Serializable;

public class InputString implements Serializable {
		
		private String string;
		private Color color;
		private float x;
		private float y;
	
		public InputString(String s,Color c,float x,float y ) {
			this.string = s;
			this.color = c;
			this.x = x;
			this.y = y;
		
			
		}
	
		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}
	}
