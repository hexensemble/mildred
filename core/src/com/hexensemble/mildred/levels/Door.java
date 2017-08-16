package com.hexensemble.mildred.levels;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Segment;
import com.hexensemble.mildred.system.CoreSettings;

/**
 * Represents a door in a game level.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class Door {

	private Level level;
	private int x;
	private int y;
	private float centerX;
	private float centerY;
	private Vector2 position;
	private int doorTileID;

	private boolean open;
	private float openTimer;

	private Sound sound;

	/**
	 * Initialize.
	 * 
	 * @param level
	 *            Game level.
	 * @param x
	 *            X position.
	 * @param y
	 *            Y position.
	 */
	public Door(Level level, int x, int y) {
		this.level = level;
		this.x = x;
		this.y = y;
		centerX = x + (CoreSettings.V_TILE_SIZE / 2);
		centerY = y + (CoreSettings.V_TILE_SIZE / 2);
		position = new Vector2(x, y);
		doorTileID = 0;

		open = false;
		openTimer = 0;

		sound = level.assetManager.get("sounds/door-active.wav", Sound.class);
	}

	/**
	 * Update.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	public void update(float delta) {
		if (open) {
			openTimer += delta;
		}

		if (openTimer > 2) {
			closeDoor();
			openTimer = 0;
		}

		if (level.players.size > 0 && level.players.get(0).getPosition().equals(position)) {
			level.players.get(0).openDoor();
		}
	}

	/**
	 * Dispose.
	 */
	public void dispose() {

	}

	/**
	 * Opens the door.
	 */
	public void openDoor() {
		doorTileID = level.getTileID(level.tileLayer, x, y);
		level.setTile(level.tileLayer, x, y, 1);

		for (Segment s : level.tileBoundsX) {
			Vector2 sPosition = new Vector2(s.a.x, s.a.y);
			if (sPosition.equals(position)) {
				int index = level.tileBoundsX.indexOf(s, false);
				level.tileBoundsX.removeIndex(index);
			}
		}
		for (Segment s : level.tileBoundsY) {
			Vector2 sPosition = new Vector2(s.a.x, s.a.y);
			if (sPosition.equals(position)) {
				int index = level.tileBoundsY.indexOf(s, false);
				level.tileBoundsY.removeIndex(index);
			}
		}

		sound.play(CoreSettings.sfxVol);
		open = true;

		for (int i = 0; i < 9; i++) {
			if (i == 4) {
				continue;
			}
			int xi = (i % 3) - 1;
			int yi = (i / 3) - 1;
			if (level.tileDoorCheck((int) (centerX + xi), (int) (centerY + yi))) {
				Vector2 iPosition = new Vector2((int) (centerX + xi), (int) (centerY + yi));
				for (int j = 0; j < level.doors.size; j++) {
					if (level.doors.get(j).position.equals(iPosition)) {
						level.doors.get(j).openDoor();
					}
				}
			}
		}
	}

	private void closeDoor() {
		level.setTile(level.tileLayer, x, y, doorTileID);

		level.tileBoundsX.add(new Segment(x, y, 0, x + CoreSettings.V_TILE_SIZE, y, 0));
		level.tileBoundsY.add(new Segment(x, y, 0, x, y + CoreSettings.V_TILE_SIZE, 0));

		sound.play(CoreSettings.sfxVol);
		open = false;
	}

	/**
	 * Gets a Vector2 containing X/Y positions.
	 * 
	 * @return {@code position}
	 */
	public Vector2 getPosition() {
		return position;
	}

}
