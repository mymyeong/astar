package astar;

import java.util.Stack;

class AStar {
	public static int MAX_SERCH = 10; // 최대 레벨
	AStarTree ast; // 초기에 주어진 Root
	AStarTree lnode; // 최소비용 노드
	int target[][]; // 목표 모양

	public AStar(int src[][], int target[][]) { // 객체 생성자
		this.target = target; // 목표 설정
		AStarMap rootMap = new AStarMap(src);
		rootMap.setLevel(0); // root의 Level은 0
		rootMap.setMatch(getMismatch(rootMap.getMap())); // Root의 mismacth 구하기
		ast = new AStarTree(rootMap); // root 설정
		getDirection(ast); // 모든 경우의 수 구하기
	}

	public void getDirection(AStarTree node) { // 모든 방향에 대한 경우의 수 구하는 function
		if (node.map.getMismatch() == 0 || node.map.getLevel() >= MAX_SERCH) {
			// 주어진 노드가 목표노드와 일치 하거나 주어진 최대레벨에 도달했을 시엔 return
			if (lnode == null) { // 최소노드가 설정되어 있지 않으시엔
				lnode = node; // 현재 노드를 최소 노드 설정
			} else { // 최소 노드가 설정 되어 있다면
				int l = lnode.map.getLevel() + lnode.map.getMismatch(); // 최소노드의 가중치
				if (l > node.map.getLevel() + node.map.getMismatch()) { // 현재노드의 가중치가 더 낮다면
					lnode = node; // 현재노드로 최소노드 교체
				}
			}
			return;
		}
		getNextNode(node);
		for (int i = 0; i < node.child.size(); i++) { // 자식 노득 있다면
			getDirection(node.child.get(i)); // 자식 노드들의 경로 구함
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
					System.out.print("■");
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
			System.out.println("먼저 경로를 찾아 주세요");
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