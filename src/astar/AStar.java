package astar;

import java.util.Stack;

class AStar {
	public static int MAX_SERCH = 10; // �ִ� ����
	AStarTree ast; // �ʱ⿡ �־��� Root
	AStarTree lnode; // �ּҺ�� ���
	int target[][]; // ��ǥ ���

	public AStar(int src[][], int target[][]) { // ��ü ������
		this.target = target; // ��ǥ ����
		AStarMap rootMap = new AStarMap(src);
		rootMap.setLevel(0); // root�� Level�� 0
		rootMap.setMatch(getMismatch(rootMap.getMap())); // Root�� mismacth ���ϱ�
		ast = new AStarTree(rootMap); // root ����
		getDirection(ast); // ��� ����� �� ���ϱ�
	}

	public void getDirection(AStarTree node) { // ��� ���⿡ ���� ����� �� ���ϴ� function
		if (node.map.getMismatch() == 0 || node.map.getLevel() >= MAX_SERCH) {
			// �־��� ��尡 ��ǥ���� ��ġ �ϰų� �־��� �ִ뷹���� �������� �ÿ� return
			if (lnode == null) { // �ּҳ�尡 �����Ǿ� ���� �����ÿ�
				lnode = node; // ���� ��带 �ּ� ��� ����
			} else { // �ּ� ��尡 ���� �Ǿ� �ִٸ�
				int l = lnode.map.getLevel() + lnode.map.getMismatch(); // �ּҳ���� ����ġ
				if (l > node.map.getLevel() + node.map.getMismatch()) { // �������� ����ġ�� �� ���ٸ�
					lnode = node; // ������� �ּҳ�� ��ü
				}
			}
			return;
		}
		getNextNode(node);
		for (int i = 0; i < node.child.size(); i++) { // �ڽ� ��� �ִٸ�
			getDirection(node.child.get(i)); // �ڽ� ������ ��� ����
		}
	}

	public void getNextNode(AStarTree node) {
		int maxMisMatch = Integer.MAX_VALUE;
		for (AStarMap.Direction d : AStarMap.Direction.values()) {
			if (node.map.move(d) != null) {
				AStarTree temp = new AStarTree(node.map.move(d));
				temp.direction = d.toString();
				temp.map.setMatch(getMismatch(temp.map.getMap()));
				if (maxMisMatch > temp.map.getMismatch()) {
					node.child.clear();
					node.addChild(temp);
					maxMisMatch = temp.map.getMismatch();
				} else if (maxMisMatch == temp.map.getMismatch()) {
					node.addChild(temp);
				}
			}
		}
	}

	public int getMismatch(int a[][]) {
		int match = 0;
		for (int x = 0; x < target.length; x++) {
			for (int y = 0; y < target[x].length; y++) {
				if (target[x][y] != a[x][y]) {
					match++;
				}
			}
		}
		return match;
	}

	public static void printTree(AStarTree tree) {
		if (tree.parent == null) {
			System.out.println("loot");
		} else {
			System.out.println();
		}
		printMap(tree.map);
		if (tree.child.size() != 0) {
			for (int i = 0; i < tree.child.size(); i++) {
				printTree(tree.child.get(i));
			}
		}
	}

	public static void printMap(AStarMap map) {
		int tempMap[][] = map.getMap();
		System.out.println("level : " + map.getLevel() + ", mismatch : " + map.getMismatch());
		for (int x = 0; x < tempMap.length; x++) {
			for (int y = 0; y < tempMap[x].length; y++) {
				if (tempMap[x][y] == AStarMap.BLANK_C) {
					System.out.print("��");
				} else {
					System.out.print(tempMap[x][y]);
				}
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	public void PrintleastNode() {
		if (lnode == null) {
			System.out.println("���� ��θ� ã�� �ּ���");
		} else {
			Stack<AStarTree> nodeStack = new Stack<>();
			nodeStack.push(lnode);

			if (lnode.parent != null) {
				AStarTree temp = lnode.parent;
				while (temp != null) {
					nodeStack.push(temp);
					temp = temp.parent;
				}
			}

			while (!nodeStack.isEmpty()) {
				AStarTree node = nodeStack.pop();
				if (node.direction == null) {
					System.out.println("Root");
				} else {
					System.out.println(node.direction);
				}
				printMap(node.map);
			}
		}
	}
}