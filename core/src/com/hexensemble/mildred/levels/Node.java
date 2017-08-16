package com.hexensemble.mildred.levels;

import com.badlogic.gdx.math.Vector2;

/**
 * A point on a tiled map. Used by enemies for path finding.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Alpha 1.0.0
 * @since Alpha 1.0.0
 */
public class Node {

	/**
	 * Current position.
	 */
	public Vector2 tile;

	/**
	 * Previous node.
	 */
	public Node parent;

	/**
	 * Node to node cost to destination.
	 */
	public float gCost;

	/**
	 * Direct line cost to destination.
	 */
	public float hCost;

	/**
	 * Sum of {@code gCost + hCost}
	 */
	public float fCost;

	/**
	 * Initialize.
	 * 
	 * @param tile
	 *            Current position.
	 * @param parent
	 *            Previous node.
	 * @param gCost
	 *            Node to node cost to destination.
	 * @param hCost
	 *            Direct line cost to destination.
	 */
	public Node(Vector2 tile, Node parent, float gCost, float hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		fCost = gCost + hCost;
	}

}
