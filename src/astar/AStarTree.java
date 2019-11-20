package astar;

import java.util.ArrayList;

class AStarTree {
	AStarTree parent; // 부모 노드
	ArrayList<AStarTree> child; // 자식 노드 리스트
	String direction; // 자신이 움직인 방향
	AStarMap map; // 자신의 맵

	AStarTree(AStarMap map) {
		child = new ArrayList<>();
		this.map = map;
	}

	public void addChild(AStarTree childNode) {
		child.add(childNode);
		childNode.parent = this;
	}
}