package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.Cutter;

/**
 * Represents the prologue state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class PrologueState extends State {

	private BitmapFont mediumFont;
	private GlyphLayout layout;

	private String line1;
	private String line2;
	private String line3;
	private String line4;
	private String line5;
	private String line6;
	private String line7;
	private String line8;
	private String line9;

	private float stateTime;
	private Texture texture;
	private TextureRegion currentFrame;
	private Cutter cutter;
	private Animation animation;
	private boolean runAnimation;

	private Music music;

	private float timer;

	/**
	 * Initialize.
	 * 
	 * @param assetManager
	 *            Asset manager.
	 * @param viewport
	 *            Viewport.
	 * @param camera
	 *            Camera.
	 */
	public PrologueState(AssetManager assetManager, Viewport viewport, OrthographicCamera camera) {
		super(assetManager, viewport, camera);

		viewport.setWorldSize(CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("graphics/mouse-blank.png")),
				(int) (CoreSettings.P_TILE_SIZE / 2), (int) (CoreSettings.P_TILE_SIZE / 2));

		mediumFont = assetManager.get("fonts/Track-20.fnt", BitmapFont.class);
		layout = new GlyphLayout();

		line1 = "\"Want some soup Ian?\"";
		line2 = "\"Sure, give me a second to lock up the prisoner\"";
		line3 = "\"It's now or never mate\"";
		line4 = "\"Bloody hell, alright, I'm on me way\"";
		line5 = "Mildred is trapped!";
		line6 = "Imprisoned in a dungeon.";
		line7 = "But her cell has been left unlocked.";
		line8 = "Good job you have super powers.";
		line9 = "Escape, find your captors, and get some answers!";

		animationInit();

		music = assetManager.get("music/prologue.ogg", Music.class);
		music.setVolume(CoreSettings.musicVol);
		music.setLooping(false);
		music.play();

		timer = 0;
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

		timer += delta;

		if (timer > 17) {
			stateTime += delta;
			runAnimation = true;
		}
		if (timer > 37) {
			runAnimation = false;
		}
		if (timer > 38) {
			stateFinished = true;
			nextStateID = StateManager.PLAY_STATE;
		}

		if (stateFinished) {
			music.stop();
		}
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

		if (timer >= 1 && timer < 5) {
			batch.begin();
			layout.setText(mediumFont, line1);
			mediumFont.setColor(Color.valueOf("00ff00"));
			mediumFont.draw(batch, line1, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 8));
			batch.end();
		}
		if (timer >= 5 && timer < 9) {
			batch.begin();
			layout.setText(mediumFont, line2);
			mediumFont.setColor(Color.valueOf("ffff00"));
			mediumFont.draw(batch, line2, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();
		}
		if (timer >= 9 && timer < 13) {
			batch.begin();
			layout.setText(mediumFont, line3);
			mediumFont.setColor(Color.valueOf("00ff00"));
			mediumFont.draw(batch, line3, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 8));
			batch.end();
		}
		if (timer >= 13 && timer < 17) {
			batch.begin();
			layout.setText(mediumFont, line4);
			mediumFont.setColor(Color.valueOf("ffff00"));
			mediumFont.draw(batch, line4, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();
		}
		if (timer >= 17 && timer < 21) {
			batch.begin();
			layout.setText(mediumFont, line5);
			mediumFont.setColor(Color.valueOf("ffffff"));
			mediumFont.draw(batch, line5, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 4));
			batch.end();
		}
		if (timer >= 21 && timer < 25) {
			batch.begin();
			layout.setText(mediumFont, line6);
			mediumFont.setColor(Color.valueOf("ffffff"));
			mediumFont.draw(batch, line6, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 4));
			batch.end();
		}
		if (timer >= 25 && timer < 29) {
			batch.begin();
			layout.setText(mediumFont, line7);
			mediumFont.setColor(Color.valueOf("ffffff"));
			mediumFont.draw(batch, line7, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 4));
			batch.end();
		}
		if (timer >= 29 && timer < 33) {
			batch.begin();
			layout.setText(mediumFont, line8);
			mediumFont.setColor(Color.valueOf("ffffff"));
			mediumFont.draw(batch, line8, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 4));
			batch.end();
		}
		if (timer >= 33 && timer < 37) {
			batch.begin();
			layout.setText(mediumFont, line9);
			mediumFont.setColor(Color.valueOf("ffffff"));
			mediumFont.draw(batch, line9, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 4));
			batch.end();
		}

		if (runAnimation) {
			batch.begin();
			currentFrame = animation.getKeyFrame(stateTime, false);
			batch.draw(currentFrame, (CoreSettings.P_WIDTH - currentFrame.getRegionWidth()) / 2,
					(CoreSettings.P_HEIGHT - currentFrame.getRegionHeight()) / 2 - (CoreSettings.P_TILE_SIZE * 2));
			batch.end();
		}
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
	protected void controls() {
		super.controls();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			stateFinished = true;
			nextStateID = StateManager.PLAY_STATE;
		}
	}

	protected void animationInit() {
		stateTime = 0;
		texture = assetManager.get("graphics/prologue-animation.png", Texture.class);
		cutter = new Cutter(texture, 24, 1);
		animation = new Animation(0.5f, cutter.frames[0], cutter.frames[1], cutter.frames[2], cutter.frames[3],
				cutter.frames[4], cutter.frames[5], cutter.frames[6], cutter.frames[7], cutter.frames[8],
				cutter.frames[9], cutter.frames[10], cutter.frames[11], cutter.frames[12], cutter.frames[13],
				cutter.frames[14], cutter.frames[15], cutter.frames[16], cutter.frames[17], cutter.frames[18],
				cutter.frames[19], cutter.frames[20], cutter.frames[21], cutter.frames[22], cutter.frames[23]);
		runAnimation = false;
	}

}
