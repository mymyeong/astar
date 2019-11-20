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
					System.out.print("��");
				} else if (x == target.x && y == target.y) {
					System.out.print("��");
				} else if (map[y][x] == WALL) {
					System.out.print("��");
				} else {
					System.out.print("��");
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
				System.out.println("����");
			}
		}
	}

	private void nextMove(MoveTree node) { // ���� �̵���ε��� ã��
		if (lnode != null && node.level > lnode.level) {
			// ã�� ��尡 ���� ��庸�� �̵����� ������ ����
			return;
		} else if (getDistance(node.scv.position) == 0) { // ��ǥ�� �����ϸ� ����
			if (lnode == null || lnode.level > node.level) {
				// ã�� ��尡 ���� ��庸�� �̵����� ������ ��ȯ
				lnode = node;
			}
			return;
		} else if (node.level >= MAX_LEVEL) { // �ִ� ã�� ������ �����ϸ� ����
			if (lnode == null || lnode.level > node.level) {
				// �ִ� �̵����� �������� ������ ã�� �̵���� Ƚ���� ���� ��κ��� ũ�� ��ȯ
				lnode = node;
			}
			return;
		}

		int minWeigth = Integer.MAX_VALUE; // ���� ������ �ּ� ����ġ
		for (Scv.Direction d : Scv.Direction.values()) { // ��� �̵� ���⿡ ����
			Point nextPosition = node.scv.move(d); // ��� �̵� ������ ��ǥ������
			if (isMoveablePoint(nextPosition)) { // �̵� ������ ��ο� ���ؼ�
				if (node.getParent() != null && nextPosition == node.getParentScvPosition()) {
					// �θ� ������, �̵��ҷ��� ��ΰ� �θ���� ������ ����
					break;
				}
				int w = getDistance(nextPosition); // �̵��Ϸ��� ����� ����ġ ����
				if (minWeigth > w) { // ���� ������ ����ġ�� �ּҰ��̽ÿ�
					minWeigth = w; // �ּҰ�ġȯ
					if (node.child.size() > 0) { // ã�� ��尡 �ִٸ�
						node.child.clear(); // �ٸ� ã�� ��� �����
					}
					MoveTree temp = new MoveTree(new Scv(nextPosition));
					node.addChild(temp, d.toString()); // ���� �̵���� �ڽ����� �߰�
				} else if (minWeigth == w) { // �̵��Ϸ��� ��ο� �ּҰ��� ������
					MoveTree temp = new MoveTree(new Scv(nextPosition));
					node.addChild(temp, d.toString()); // ���� ��� �߰�
				}
			}
		}
		
		if (node.child.size() > 0) { // ���� �̵���ΰ� �ִٸ�
			for (MoveTree mt : node.child) {
				nextMove(mt); // ���ȣ��
			}
		}
	}

	private void getRoute(MoveTree node) { // ã�� �ּҳ�忡 ���� �θ��带 ���ÿ� ����
		route = new Stack<>();
		while (node.parent != null) {
			route.push(node);
			node = node.parent;
		}
	}
	
}