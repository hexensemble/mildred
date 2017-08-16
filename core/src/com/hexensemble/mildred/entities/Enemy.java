package com.hexensemble.mildred.entities;

import java.util.Comparator;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Segment;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Sort;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.levels.Node;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.Cutter;

/**
 * Represents an enemy.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class Enemy extends AbstractEntity implements Poolable {

	protected Segment lineOfSight;
	protected Vector2 intersection;
	protected Player target;
	protected boolean targetIdentified;
	protected float targetTimer;
	protected boolean attacking;
	protected float fireTimer;
	protected Array<Node> path;
	protected Comparator<Node> nodeCompare;
	protected Sort nodeSort;
	protected boolean directFindMode;
	protected boolean pathFindMode;
	protected float pathFindModeTimer;

	private Sound hitPlayer;

	protected Cutter cutter;
	protected Animation stationary;
	protected Animation up;
	protected Animation down;
	protected Animation left;
	protected Animation right;
	protected Animation attack;
	protected TextureRegion currentFrame;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Enemy() {

	}

	/**
	 * Initialize.
	 * 
	 * @param type
	 *            Entity type.
	 * @param level
	 *            Game level.
	 * @param x
	 *            Initial X position.
	 * @param y
	 *            Initial Y position.
	 */
	@Override
	public void init(EntityType type, Level level, float x, float y) {
		super.init(type, level, x, y);

		lineOfSight = new Segment(centerX, centerY, 0, centerX, centerY, 0);
		intersection = new Vector2();
		target = null;
		targetIdentified = false;
		targetTimer = 0;
		attacking = false;
		fireTimer = 0;
		path = null;
		nodeCompare = new Comparator<Node>() {
			@Override
			public int compare(Node node1, Node node2) {
				if (node1.fCost > node2.fCost) {
					return +1;
				}
				if (node1.fCost < node2.fCost) {
					return -1;
				}
				return 0;
			}
		};
		nodeSort = new Sort();
		directFindMode = true;
		pathFindMode = false;
		pathFindModeTimer = 0;

		hitPlayer = level.assetManager.get("sounds/hit-player.wav", Sound.class);

		animationInit();
	}

	/**
	 * Update.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	@Override
	public void update(float delta) {
		super.update(delta);

		fireTimer -= delta;
		if (targetIdentified) {
			targetTimer += delta;
		}
		if (pathFindMode) {
			pathFindModeTimer += delta;
		}

		dX = 0;
		dY = 0;

		attacking = false;

		ai();
	}

	/**
	 * Render.
	 * 
	 * @param batch
	 *            Sprite batch.
	 */
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);

		if (dX != 0 || dY != 0) {
			if (dY > 0) {
				currentFrame = up.getKeyFrame(stateTime, true);
			}
			if (dY < 0) {
				currentFrame = down.getKeyFrame(stateTime, true);
			}
			if (dX < 0) {
				currentFrame = left.getKeyFrame(stateTime, true);
			}
			if (dX > 0) {
				currentFrame = right.getKeyFrame(stateTime, true);
			}
		} else if (attacking) {
			currentFrame = attack.getKeyFrame(stateTime, true);
		} else {
			currentFrame = stationary.getKeyFrame(stateTime, false);
		}

		batch.begin();
		batch.draw(currentFrame, x, y, width, height);
		batch.end();
	}

	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		super.dispose();

		cutter.dispose();
	}

	@Override
	protected void animationInit() {
		super.animationInit();

		cutter = new Cutter(sprite, 3, 6);
		stationary = new Animation(1, cutter.frames[0], cutter.frames[1], cutter.frames[2]);
		up = new Animation(0.2f, cutter.frames[3], cutter.frames[4], cutter.frames[5]);
		down = new Animation(0.2f, cutter.frames[6], cutter.frames[7], cutter.frames[8]);
		left = new Animation(0.2f, cutter.frames[9], cutter.frames[10], cutter.frames[11]);
		right = new Animation(0.2f, cutter.frames[12], cutter.frames[13], cutter.frames[14]);
		attack = new Animation(0.1f, cutter.frames[15], cutter.frames[16], cutter.frames[17]);
	}

	@Override
	protected void deathCheck() {
		super.deathCheck();

		if (health <= 0) {
			for (int i = 0; i < 4; i++) {
				Gib g = level.gibPool.obtain();
				g.init(gibType, level, centerX, centerY);
				level.gibs.add(g);
			}
			for (Player p : level.players) {
				p.setXp(p.getXp() + xpDrop);
			}
			dead = true;
		}
	}

	protected void ai() {
		if (!targetIdentified) {
			for (Player p : level.players) {
				if (distanceToTarget(p) < 15) {
					if (lineOfSightCheck(p)) {
						target = p;
						targetIdentified = true;
						break;
					}
				}
			}
		}

		if (targetIdentified) {
			if (directFindMode) {
				angle = directionToTarget(target);
				dX = (int) (Math.cos(Math.toRadians(angle)) * speed);
				dY = (int) (Math.sin(Math.toRadians(angle)) * speed);
				move(dX, dY);
			}
			if (pathFindMode) {
				Vector2 start = new Vector2((int) x, (int) y);
				Vector2 destination = new Vector2((int) target.getX(), (int) target.getY());
				path = findPath(start, destination);
				if (path != null) {
					if (path.size > 0) {
						Vector2 vec = path.get(path.size - 1).tile;
						if ((int) y < vec.y) {
							dX = 0;
							dY = speed;
							move(dX, dY);
						}
						if ((int) y > vec.y) {
							dX = 0;
							dY = -speed;
							move(dX, dY);
						}
						if ((int) x < vec.x) {
							dX = speed;
							dY = 0;
							move(dX, dY);
						}
						if ((int) x > vec.x) {
							dX = -speed;
							dY = 0;
							move(dX, dY);
						}
					}
				}
			}

			if (collisionTiles()) {
				openDoor();
				directFindMode = false;
				pathFindMode = true;
			}
			if (collisionEntities()) {
				directFindMode = false;
				pathFindMode = true;
			}
			if (pathFindModeTimer > 2) {
				pathFindMode = false;
				directFindMode = true;
				pathFindModeTimer = 0;
			}

			if (attackType == AttackType.RANGED && distanceToTarget(target) < 8 && lineOfSightCheck(target)) {
				attacking = true;
				fire();
			}
			if (attackType == AttackType.MELEE && distanceToTarget(target) <= 1) {
				attacking = true;
				melee();
			}

			if (targetTimer > 10 || target.isDead()) {
				targetIdentified = false;
				targetTimer = 0;
			}
		}
	}

	protected boolean lineOfSightCheck(Player player) {
		boolean islineOfSight = true;
		lineOfSight.a.set(centerX, centerY, 0);
		lineOfSight.b.set(player.getCenterX(), player.getCenterY(), 0);
		for (Segment s : level.tileBoundsX) {
			if (Intersector.intersectSegments(lineOfSight.a.x, lineOfSight.a.y, lineOfSight.b.x, lineOfSight.b.y, s.a.x,
					s.a.y, s.b.x, s.b.y, intersection)) {
				islineOfSight = false;
				return islineOfSight;
			}
		}
		for (Segment s : level.tileBoundsY) {
			if (Intersector.intersectSegments(lineOfSight.a.x, lineOfSight.a.y, lineOfSight.b.x, lineOfSight.b.y, s.a.x,
					s.a.y, s.b.x, s.b.y, intersection)) {
				islineOfSight = false;
				return islineOfSight;
			}
		}
		return islineOfSight;
	}

	private Array<Node> findPath(Vector2 start, Vector2 destination) {
		Array<Node> openList = new Array<Node>();
		Array<Node> closedList = new Array<Node>();
		Node current = new Node(start, null, 0, distanceToDestination(start, destination));
		openList.add(current);
		while (openList.size > 0) {
			nodeSort.sort(openList, nodeCompare);
			current = openList.get(0);
			if (current.tile.equals(destination)) {
				Array<Node> path = new Array<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.removeIndex(0);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) {
					continue;
				}
				int x = (int) current.tile.x;
				int y = (int) current.tile.y;
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				if (level.tileNullCheck(level.tileLayer, x + xi, y + yi)) {
					continue;
				}
				if (level.tileBlockedCheck(x + xi, y + yi) && !level.tileDoorCheck(x + xi, y + yi)) {
					continue;
				}
				if (level.entityBlockedCheck(x + xi, y + yi)) {
					continue;
				}
				Vector2 a = new Vector2(x + xi, y + yi);
				float gCost = (float) (current.gCost + (distanceToDestination(current.tile, a) == 1 ? 1 : 0.95));
				float hCost = distanceToDestination(a, destination);
				Node node = new Node(a, current, gCost, hCost);
				if (vectorInList(a, closedList) && gCost >= node.gCost) {
					continue;
				}
				if (!vectorInList(a, openList) || gCost < node.gCost) {
					openList.add(node);
				}
			}
		}
		closedList.clear();
		return null;
	}

	protected float directionToTarget(Player player) {
		float angle = 0;
		float distanceX = player.getCenterX() - centerX;
		float distanceY = player.getCenterY() - centerY;
		angle = (float) (Math.toDegrees(Math.atan2(distanceY, distanceX)));
		return angle;
	}

	protected float distanceToTarget(Player player) {
		float distance = 0;
		float x1 = player.getCenterX();
		float x2 = centerX;
		float y1 = player.getCenterY();
		float y2 = centerY;
		distance = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return distance;
	}

	private float distanceToDestination(Vector2 start, Vector2 destination) {
		float distance = 0;
		float distanceX = start.x - destination.x;
		float distanceY = start.y - destination.y;
		distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
		return distance;
	}

	private boolean vectorInList(Vector2 vector, Array<Node> list) {
		for (Node n : list) {
			if (n.tile.x == vector.x && n.tile.y == vector.y) {
				return true;
			}
		}
		return false;
	}

	protected void fire() {
		if (fireTimer <= 0) {
			angle = directionToTarget(target);
			Projectile p = level.projectilePool.obtain();
			p.init(projectileType, level, centerX, centerY, angle, entityID);
			level.projectiles.add(p);
			fireTimer = actionValue;
		}
	}

	private void melee() {
		if (fireTimer <= 0) {
			sound.play(CoreSettings.sfxVol);
			hitPlayer.play(CoreSettings.sfxVol);
			target.setHealth(target.getHealth() - damage);
			fireTimer = actionValue;
		}
	}

	private void openDoor() {
		for (int i = 0; i < 9; i++) {
			int xi = (i % 3) - 1;
			int yi = (i / 3) - 1;
			if (level.tileDoorCheck((int) (centerX + xi), (int) (centerY + yi))) {
				Vector2 iPosition = new Vector2((int) (centerX + xi), (int) (centerY + yi));
				for (int j = 0; j < level.doors.size; j++) {
					if (level.doors.get(j).getPosition().equals(iPosition)) {
						level.doors.get(j).openDoor();
					}
				}
			}
		}
	}

}
