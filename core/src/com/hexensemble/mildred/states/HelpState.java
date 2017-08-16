package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hexensemble.mildred.system.CoreSettings;

/**
 * Represents the help state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.0
 * @since Alpha 1.2.0
 */
public class HelpState extends State {

	private Texture background;

	private BitmapFont largeFont;
	private BitmapFont mediumFont;
	private GlyphLayout layout;

	private String title;

	private String up;
	private String down;
	private String left;
	private String right;
	private String fire;
	private String open;
	private String quit;

	private String wUp;
	private String sDown;
	private String aLeft;
	private String dRight;
	private String mouseButtons;
	private String spaceEnter;
	private String esc;

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
	public HelpState(AssetManager assetManager, Viewport viewport, OrthographicCamera camera) {
		super(assetManager, viewport, camera);

		viewport.setWorldSize(CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("graphics/mouse-blank.png")),
				(int) (CoreSettings.P_TILE_SIZE / 2), (int) (CoreSettings.P_TILE_SIZE / 2));

		background = assetManager.get("graphics/help-background.png", Texture.class);

		largeFont = assetManager.get("fonts/Track-30.fnt", BitmapFont.class);
		mediumFont = assetManager.get("fonts/Track-20.fnt", BitmapFont.class);
		layout = new GlyphLayout();

		title = "Help";

		up = "Up:";
		down = "Down:";
		left = "Left:";
		right = "Right:";
		fire = "Fire:";
		open = "Open:";
		quit = "Quit:";

		wUp = "W or UP ARROW";
		sDown = "S or DOWN ARROW";
		aLeft = "A or LEFT ARROW";
		dRight = "D or RIGHT ARROW";
		mouseButtons = "MOUSE BUTTONS";
		spaceEnter = "SPACE BAR or ENTER";
		esc = "ESC";
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

		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();

		batch.begin();
		layout.setText(largeFont, title);
		largeFont.setColor(Color.valueOf("ff0000"));
		largeFont.draw(batch, title, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 8));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, up);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, up, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 3));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, wUp);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, wUp, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 3));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, down);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, down, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, sDown);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, sDown, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, left);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, left, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 + CoreSettings.P_TILE_SIZE);
		batch.end();

		batch.begin();
		layout.setText(mediumFont, aLeft);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, aLeft, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 + CoreSettings.P_TILE_SIZE);
		batch.end();

		batch.begin();
		layout.setText(mediumFont, right);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, right, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2);
		batch.end();

		batch.begin();
		layout.setText(mediumFont, dRight);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, dRight, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2);
		batch.end();

		batch.begin();
		layout.setText(mediumFont, fire);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, fire, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 - CoreSettings.P_TILE_SIZE);
		batch.end();

		batch.begin();
		layout.setText(mediumFont, mouseButtons);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, mouseButtons, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 - CoreSettings.P_TILE_SIZE);
		batch.end();

		batch.begin();
		layout.setText(mediumFont, open);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, open, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, spaceEnter);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, spaceEnter, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, quit);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, quit, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 3));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, esc);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, esc, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 3),
				(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 3));
		batch.end();
	}

	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		super.dispose();

	}

	@Override
	protected void controls() {
		super.controls();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			stateFinished = true;
			nextStateID = StateManager.MENU_STATE;
		}
	}

}
