package moveSCV;

import java.awt.Point;
import java.util.Stack;

public class Map {
	public static final int MAX_X = 7;
	public static final int MAX_Y = 7;
	public static final int WALL = -999;

	private static final int MAX_LEVEL = 25;

	public int[][] map = new int[MAX_Y][MAX_X];
	public Scv scv;
	public Point target;

	public Stack<MoveTree> route;
	MoveTree lnode;
	int count;

	public Map(int[][] map, Scv scv, Point target) {
		this.map = map;
		this.scv = scv;
		this.target = target;
		init();
	}

	public void init() {
		nextMove(new MoveTree(scv));
		getRoute(lnode);
	}

	public boolean isMoveablePoint(Point p) {
		boolean moveable = false;

		if (p.x >= 0 && p.x < MAX_X && p.y >= 0 && p.y < MAX_Y) {
			if (map[p.y][p.x] != WALL) {
				moveable = true;
			}
		}
		return moveable;
	}

	public int getDistance() {
		return getDistance(scv.position);
	}

	public int getDistance(Point position) {
		int weight = 0;
		weight = (int) Math.sqrt(Math.pow((target.x - position.x), 2) + Math.pow((target.y - position.y), 2));
		return weight;
	}

	public void printMap() {
		printMap(map, scv);
	}

	public void printMap(int map[][], Scv scv) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				if (x == scv.getX() && y == scv.getY()) {
					System.out.print("ⓢ");
				} else if (x == target.x && y == target.y) {
					System.out.print("×");
				} else if (map[y][x] == WALL) {
					System.out.print("■");
				} else {
					System.out.print("□");
				}
			}
			System.out.println();
		}
	}

	public void printlnode() {
		if (lnode == null) {
			return;
		}
		getRoute(lnode);
		while (!route.isEmpty()) {
			MoveTree temp = route.pop();
			System.out.println("level : " + temp.level + ", move " + temp.direction);
			printMap(map, temp.scv);
			if (temp.child.size() == 0) {
				System.out.println("도착");
			}
		}
	}

	private void nextMove(MoveTree node) { // 다음 이동경로들을 찾음
		if (lnode != null && node.level > lnode.level) {
			// 찾은 노드가 현재 노드보다 이동수가 적으면 종료
			return;
		} else if (getDistance(node.scv.position) == 0) { // 목표에 도착하면 종료
			if (lnode == null || lnode.level > node.level) {
				// 찾은 노드가 현재 노드보다 이동수가 많으면 교환
				lnode = node;
			}
			return;
		} else if (node.level >= MAX_LEVEL) { // 최대 찾기 레벨에 도달하면 종료
			if (lnode == null || lnode.level > node.level) {
				// 최대 이동수에 도달했을 때까지 찾은 이동경로 횟수가 현재 경로보다 크면 교환
				lnode = node;
			}
			return;
		}

		int minWeigth = Integer.MAX_VALUE; // 현재 레벨의 최소 가중치
		for (Scv.Direction d : Scv.Direction.values()) { // 모든 이동 방향에 대해
			Point nextPosition = node.scv.move(d); // 모든 이동 방향의 좌표를구함
			if (isMoveablePoint(nextPosition)) { // 이동 가능한 경로에 대해서
				if (node.getParent() != null && nextPosition == node.getParentScvPosition()) {
					// 부모가 있을때, 이동할려는 경로가 부모노드와 같으면 종료
					break;
				}
				int w = getDistance(nextPosition); // 이동하려는 경로의 가중치 구함
				if (minWeigth > w) { // 현재 레벨의 가중치중 최소값이시에
					minWeigth = w; // 최소값치환
					if (node.child.size() > 0) { // 찾은 노드가 있다면
						node.child.clear(); // 다른 찾은 노드 지우기
					}
					MoveTree temp = new MoveTree(new Scv(nextPosition));
					node.addChild(temp, d.toString()); // 다음 이동경로 자식으로 추가
				} else if (minWeigth == w) { // 이동하려는 경로와 최소값이 같으면
					MoveTree temp = new MoveTree(new Scv(nextPosition));
					node.addChild(temp, d.toString()); // 현재 경로 추가
				}
			}
		}
		
		if (node.child.size() > 0) { // 다음 이동경로가 있다면
			for (MoveTree mt : node.child) {
				nextMove(mt); // 재귀호출
			}
		}
	}

	private void getRoute(MoveTree node) { // 찾은 최소노드에 대한 부모노드를 스택에 저장
		route = new Stack<>();
		while (node.parent != null) {
			route.push(node);
			node = node.parent;
		}
	}
	
}