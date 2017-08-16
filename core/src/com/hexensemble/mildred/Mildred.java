package com.hexensemble.mildred;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.hexensemble.mildred.states.StateManager;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.PlayList;

/**
 * Main application class.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.0
 * @since Alpha 1.0.0
 */
public class Mildred extends ApplicationAdapter {

	private StateManager stateManager;

	/**
	 * Initialize.
	 * 
	 */
	public Mildred() {

	}

	/**
	 * Create.
	 */
	@Override
	public void create() {
		CoreSettings.load();
		PlayList.init();

		stateManager = new StateManager();
	}

	/**
	 * Update and render.
	 */
	@Override
	public void render() {
		stateManager.update(Gdx.graphics.getDeltaTime());
		stateManager.render();
	}

	/**
	 * Resizes the screen.
	 * 
	 * @param width
	 *            New screen width.
	 * @param height
	 *            New screen height.
	 */
	@Override
	public void resize(int width, int height) {
		stateManager.resize(width, height);
	}

	/**
	 * Pauses the application.
	 * 
	 */
	@Override
	public void pause() {

	}

	/**
	 * Resumes the application.
	 */
	@Override
	public void resume() {

	}

	/**
	 * Disposes the application.
	 * 
	 */
	@Override
	public void dispose() {
		stateManager.dispose();
	}

}
