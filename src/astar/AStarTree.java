package astar;

import java.util.ArrayList;

class AStarTree {
	AStarTree parent; // �θ� ���
	ArrayList<AStarTree> child; // �ڽ� ��� ����Ʈ
	String direction; // �ڽ��� ������ ����
	AStarMap map; // �ڽ��� ��

	AStarTree(AStarMap map) {
		child = new ArrayList<>();
		this.map = map;
	}

	public void addChild(AStarTree childNode) {
		child.add(childNode);
		childNode.parent = this;
	}
}