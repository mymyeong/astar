package gui;

import java.awt.Point;

import moveSCV.Map;
import moveSCV.Scv;

public class GuiMain {
	public static void main(String[] args) {
		int map[][] = new int[Map.MAX_Y][Map.MAX_X]; // 맵 생성
		// 장애물 위치 설정
		map[4][3] = Map.WALL;
		map[3][3] = Map.WALL;
		map[3][4] = Map.WALL;
		map[5][3] = Map.WALL;
		map[3][5] = Map.WALL;
		map[6][3] = Map.WALL;
		map[4][5] = Map.WALL;
		map[5][5] = Map.WALL;

		Scv scv = new Scv(new Point(1, 1));
		Point target = new Point(4, 4);
		Map myMap = new Map(map, scv, target);
		new AStarAwt(myMap);
	}
}