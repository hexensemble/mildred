package com.hexensemble.mildred.system;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Cuts a single sprite or texture into frames for use in animation.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class Cutter {

	private Sprite sprite;
	private Texture texture;
	private int cols;
	private int rows;

	private TextureRegion[][] splitFrames;

	/**
	 * List of cut frames.
	 * 
	 */
	public TextureRegion[] frames;

	/**
	 * Initialize (Sprite).
	 * 
	 * @param sprite
	 *            Sprite to be cut.
	 * @param cols
	 *            Number of columns.
	 * @param rows
	 *            Number of rows.
	 */
	public Cutter(Sprite sprite, int cols, int rows) {
		this.sprite = sprite;
		this.cols = cols;
		this.rows = rows;

		spriteFrameMaker();
	}

	/**
	 * Initialize (Texture).
	 * 
	 * @param texture
	 *            Texture to be cut.
	 * @param cols
	 *            Number of columns.
	 * @param rows
	 *            Number of rows.
	 */
	public Cutter(Texture texture, int cols, int rows) {
		this.texture = texture;
		this.cols = cols;
		this.rows = rows;

		textureFrameMaker();
	}

	/**
	 * Dispose
	 * 
	 */
	public void dispose() {

	}

	private void spriteFrameMaker() {
		splitFrames = sprite.split(sprite.getRegionWidth() / cols, sprite.getRegionHeight() / rows);
		frames = new TextureRegion[cols * rows];
		int index = 0;
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				frames[index++] = splitFrames[x][y];
			}
		}
	}

	private void textureFrameMaker() {
		splitFrames = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);
		frames = new TextureRegion[cols * rows];
		int index = 0;
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				frames[index++] = splitFrames[x][y];
			}
		}
	}

}
