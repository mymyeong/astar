package moveSCV;

import java.awt.Point;

public class MoveSCV {
	public static void main(String[] args) {
		int map[][] = new int[Map.MAX_Y][Map.MAX_X]; // 맵 생성
		// 장애물 위치 설정
		map[4][3] = Map.WALL;
		map[3][3] = Map.WALL;
		map[3][4] = Map.WALL;
//		map[5][3] = Map.WALL;
//		map[3][5] = Map.WALL;
//		map[6][3] = Map.WALL;
//		map[4][5] = Map.WALL;
//		map[5][5] = Map.WALL;

		// 초기 유닛 유치지정
		Scv scv = new Scv(new Point(1, 1));
		// 목표 지정
		Point target = new Point(4, 4);

		// 맵 객체 생성
		Map myMap = new Map(map, scv, target);

		System.out.println("초기위치");
		myMap.printMap();
		System.out.println("이동 시작 =====>");
		myMap.printlnode();
	}
}