package moveSCV;

import java.awt.Point;

public class MoveSCV {
	public static void main(String[] args) {
		int map[][] = new int[Map.MAX_Y][Map.MAX_X]; // �� ����
		// ��ֹ� ��ġ ����
		map[4][3] = Map.WALL;
		map[3][3] = Map.WALL;
		map[3][4] = Map.WALL;
//		map[5][3] = Map.WALL;
//		map[3][5] = Map.WALL;
//		map[6][3] = Map.WALL;
//		map[4][5] = Map.WALL;
//		map[5][5] = Map.WALL;

		// �ʱ� ���� ��ġ����
		Scv scv = new Scv(new Point(1, 1));
		// ��ǥ ����
		Point target = new Point(4, 4);

		// �� ��ü ����
		Map myMap = new Map(map, scv, target);

		System.out.println("�ʱ���ġ");
		myMap.printMap();
		System.out.println("�̵� ���� =====>");
		myMap.printlnode();
	}
}