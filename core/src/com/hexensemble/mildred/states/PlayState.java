package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hexensemble.mildred.levels.LevelManager;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.HUD;

/**
 * Represents the play state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.0
 * @since Alpha 1.0.0
 */
public class PlayState extends State {

	/**
	 * New game.
	 */
	public static final int NEW_GAME = 0;

	/**
	 * Continue game.
	 */
	public static final int CONTINUE_GAME = 1;

	/**
	 * Load next level.
	 */
	public static final int NEXT_LEVEL = 2;

	private LevelManager levelManager;
	private HUD hud;

	/**
	 * Initialize.
	 * 
	 * @param assetManager
	 *            Asset manager.
	 * @param viewport
	 *            Viewport.
	 * @param camera
	 *            Camera.
	 * @param playStateType.
	 *            Play state type.
	 */
	public PlayState(AssetManager assetManager, Viewport viewport, OrthographicCamera camera, int playStateType) {
		super(assetManager, viewport, camera);

		viewport.setWorldSize(CoreSettings.V_WIDTH, CoreSettings.V_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("graphics/mouse-crosshair.png")),
				(int) (CoreSettings.P_TILE_SIZE / 2), (int) (CoreSettings.P_TILE_SIZE / 2));

		levelManager = new LevelManager(assetManager, viewport, camera, playStateType);

		if (!levelManager.error) {
			hud = new HUD(assetManager, levelManager.level);
		}
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

		if (!levelManager.error) {
			if (Gdx.graphics.getFramesPerSecond() > 30) {
				levelManager.update(delta);
				hud.update(delta);
			}
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

		if (!levelManager.error) {
			levelManager.render(batch);
			hud.render();
		}
	}

	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		super.dispose();

		if (!levelManager.error) {
			levelManager.dispose();
			hud.dispose();
		}
	}

	@Override
	protected void controls() {
		if (!levelManager.error) {
			if (levelManager.level.players.size == 0 && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
					|| levelManager.level.players.size == 0 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				stateFinished = true;
				nextStateID = StateManager.CONTINUE_PLAY_STATE;
			}

			if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
				HUD.showQuit = true;
			}
			if (HUD.showQuit && Gdx.input.isKeyPressed(Input.Keys.Y)) {
				stateFinished = true;
				nextStateID = StateManager.MENU_STATE;
			}
			if (HUD.showQuit && Gdx.input.isKeyPressed(Input.Keys.N)) {
				HUD.showQuit = false;
			}
		}
	}

}
