package moveSCV;

import java.awt.Point;

public class Scv {
	public static enum Direction {
		UP, DOWN, RIGHT, LEFT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN
	};

	public Point position;

	public Scv() {
		position = new Point();
	}

	public Scv(Point position) {
		this.position = position;
	}

	public Point move(Direction dirction) {
		Point nextPoint = (Point) position.clone();
		switch (dirction) {
		case UP:
			nextPoint.y--;
			break;
		case DOWN:
			nextPoint.y++;
			break;
		case LEFT:
			nextPoint.x--;
			break;
		case RIGHT:
			nextPoint.x++;
			break;
		case LEFT_UP:
			nextPoint.y--;
			nextPoint.x--;
			break;
		case LEFT_DOWN:
			nextPoint.y++;
			nextPoint.x--;
			break;
		case RIGHT_UP:
			nextPoint.y--;
			nextPoint.x++;
			break;
		case RIGHT_DOWN:
			nextPoint.y++;
			nextPoint.x++;
			break;
		}
		return nextPoint;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public int getX() {
		return position.x;
	}
	public int getY() {
		return position.y;
	}
}
