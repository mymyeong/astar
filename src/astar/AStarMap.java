package astar;

import java.awt.Point;

class AStarMap {
	public static final int MAX_X = 3;
	public static final int MAX_Y = 3;
	public static final int BLANK_C = 0;

	private int level;
	private int mismatch;
	private int map[][] = new int[MAX_X][MAX_Y];

	public static enum Direction {
		UP, DOWN, RIGHT, LEFT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN
	};

	// 초기화
	AStarMap(int map[][]) {
		this.map = map;
	}

	AStarMap(int level, int map[][]) {
		this.level = level;
		this.map = map;
	}

	// getter
	public int[][] getMap() { // 현재 맵정보를 리턴
		return map;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	// function
	public Point getBlank(int m[][]) { // 현재 빈칸의 위치를 구함
		Point blank = null;
		for (int x = 0; x < m.length; x++) {
			for (int y = 0; y < m[x].length; y++) {
				if (m[x][y] == BLANK_C) {
					blank = new Point(y, x);
					break;
				}
			}
		}
		return blank;
	}

	public int getMismatch() {
		return mismatch;
	}

	public void setMatch(int match) {
		this.mismatch = match;
	}

	public int[][] swapMap(int m[][], Point a, Point b) { // 두 좌표간 스왑
		int temp;
		temp = m[a.y][a.x];
		m[a.y][a.x] = m[b.y][b.x];
		m[b.y][b.x] = temp;
		return m;
	}

	public void copyMap(int src[][], int dest[][]) { // 맵 복사
		for (int x = 0; x < src.length; x++) {
			for (int y = 0; y < src[x].length; y++) {
				dest[x][y] = src[x][y];
			}
		}
	}

	public AStarMap move(Direction dirction) {
		AStarMap moveMap = null;
		int temp[][] = new int[MAX_X][MAX_Y];
		copyMap(map, temp);
		Point nextPoint = getBlank(temp);
		switch (dirction) {
		case UP:
			if (nextPoint.y - 1 >= 0) {
				nextPoint.y--;
			} else {
				nextPoint = null;
			}
			break;
		case DOWN:
			if (nextPoint.y + 1 < MAX_Y) {
				nextPoint.y++;
			} else {
				nextPoint = null;
			}
			break;
		case LEFT:
			if (nextPoint.x - 1 >= 0) {
				nextPoint.x--;
			} else {
				nextPoint = null;
			}
			break;
		case RIGHT:
			if (nextPoint.x + 1 < MAX_X) {
				nextPoint.x++;
			} else {
				nextPoint = null;
			}
			break;
		case LEFT_UP:
			if (nextPoint.y - 1 >= 0 && nextPoint.x - 1 >= 0) {
				nextPoint.y--;
				nextPoint.x--;
			} else {
				nextPoint = null;
			}
			break;
		case LEFT_DOWN:
			if (nextPoint.y - 1 >= 0 && nextPoint.x + 1 < MAX_X) {
				nextPoint.y--;
				nextPoint.x++;
			} else {
				nextPoint = null;
			}
			break;
		case RIGHT_UP:
			if (nextPoint.y - 1 >= 0 && nextPoint.x + 1 < MAX_X) {
				nextPoint.y--;
				nextPoint.x++;
			} else {
				nextPoint = null;
			}
			break;
		case RIGHT_DOWN:
			if (nextPoint.y + 1 < MAX_Y && nextPoint.x + 1 < MAX_X) {
				nextPoint.y++;
				nextPoint.x++;
			} else {
				nextPoint = null;
			}
			break;
		}
		if (nextPoint != null) {
			swapMap(temp, getBlank(temp), nextPoint);
			moveMap = new AStarMap(temp);
			moveMap.setLevel(level + 1);
		}
		return moveMap;
	}
}