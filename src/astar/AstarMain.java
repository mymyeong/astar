package astar;

public class AstarMain {

	public static void main(String[] args) {
		int src[][] = { { 2, 8, 3 }, { 1, 6, 4 }, { 7, 0, 5 }, }; // ����
		int target[][] = { { 1, 2, 3 }, { 8, 0, 4 }, { 7, 6, 5 } }; // ��ǥ

		AStar ma = new AStar(src, target);
		System.out.println("target");
		AStar.printMap(new AStarMap(target));

		// System.out.println("��ü ��� ���");
		//		MainAlgorithm.printTree(ma.ast);

		System.out.println("�������");
		ma.PrintleastNode();
	}
}