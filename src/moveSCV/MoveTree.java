package moveSCV;

import java.awt.Point;
import java.util.ArrayList;

public class MoveTree {
	int level;
	public MoveTree parent;
	ArrayList<MoveTree> child;
	String direction;
	public Scv scv;

	public MoveTree(Scv scv) {
		child = new ArrayList<>();
		this.scv = scv;
	}

	void addChild(MoveTree node, String direction) {
		node.parent = this;
		node.level = this.level + 1;
		node.direction = direction;
		child.add(node);
	}

	public MoveTree getParent() {
		return parent;
	}

	public Scv getScv() {
		return scv;
	}

	public Point getScvPosition() {
		return scv.position;
	}

	public Point getParentScvPosition() {
		if (parent == null) {
			return null;
		} else {
			return parent.getScvPosition();
		}
	}
}
