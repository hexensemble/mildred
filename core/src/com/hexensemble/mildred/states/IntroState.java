package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
 * Represents the introduction state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class IntroState extends State {

	private Texture hotBakedGoodsLogo;
	private Texture hexEnsembleLogo;
	private Texture mildredLogo;

	private BitmapFont smallFont;
	private GlyphLayout layout;

	private float stateTime;
	private Texture mildredTexture;
	private Texture startTexture;
	private TextureRegion mildredCurrentFrame;
	private TextureRegion startCurrentFrame;
	private Cutter mildredCutter;
	private Cutter startCutter;
	private Animation mildred;
	private Animation start;

	private String copyright;

	private Sound hotBakedGoodsSnd;
	private Sound hexEnsembleSnd;
	private Music music;
	private boolean hotBakedGoodsSndOn;
	private boolean hexEnsembleSndOn;
	private boolean musicOn;

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
	public IntroState(AssetManager assetManager, Viewport viewport, OrthographicCamera camera) {
		super(assetManager, viewport, camera);

		viewport.setWorldSize(CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("graphics/mouse-blank.png")),
				(int) (CoreSettings.P_TILE_SIZE / 2), (int) (CoreSettings.P_TILE_SIZE / 2));

		hotBakedGoodsLogo = assetManager.get("graphics/intro-hotbakedgoodslogo.png", Texture.class);
		hexEnsembleLogo = assetManager.get("graphics/intro-hexensemblelogo.png", Texture.class);
		mildredLogo = assetManager.get("graphics/intro-mildredlogo.png", Texture.class);

		smallFont = assetManager.get("fonts/Track-14.fnt", BitmapFont.class);
		layout = new GlyphLayout();

		animationInit();

		copyright = CoreSettings.COPYRIGHT;

		hotBakedGoodsSnd = assetManager.get("sounds/intro-hotbakedgoods.wav", Sound.class);
		hexEnsembleSnd = assetManager.get("sounds/intro-hexensemble.wav", Sound.class);
		music = assetManager.get("music/intro.ogg", Music.class);
		music.setVolume(CoreSettings.musicVol);
		music.setLooping(true);
		hotBakedGoodsSndOn = false;
		hexEnsembleSndOn = false;
		musicOn = false;

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

		stateTime += delta;
		timer += delta;

		if (timer <= 2.5f) {
			if (!hotBakedGoodsSndOn) {
				hotBakedGoodsSnd.play(CoreSettings.sfxVol);
				hotBakedGoodsSndOn = true;
			}
		}
		if (timer > 2.5f && timer <= 3.5f) {
			if (!hexEnsembleSndOn) {
				hexEnsembleSnd.play(CoreSettings.sfxVol);
				hexEnsembleSndOn = true;
			}
		}
		if (timer > 3.5f) {
			if (!musicOn) {
				music.play();
				musicOn = true;
			}
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

		if (timer <= 2.5) {
			batch.begin();
			batch.draw(hotBakedGoodsLogo, (CoreSettings.P_WIDTH - hotBakedGoodsLogo.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hotBakedGoodsLogo.getHeight()) / 2);
			batch.end();
		}
		if (timer > 2.5 && timer <= 3.5) {
			batch.begin();
			batch.draw(hexEnsembleLogo, (CoreSettings.P_WIDTH - hexEnsembleLogo.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hexEnsembleLogo.getHeight()) / 2);
			batch.end();
		}
		if (timer > 3.5) {
			batch.begin();
			batch.draw(mildredLogo, (CoreSettings.P_WIDTH - mildredLogo.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - mildredLogo.getHeight()) / 2 + (CoreSettings.P_TILE_SIZE * 7));
			batch.end();

			batch.begin();
			mildredCurrentFrame = mildred.getKeyFrame(stateTime, true);
			batch.draw(mildredCurrentFrame, (CoreSettings.P_WIDTH - mildredCurrentFrame.getRegionWidth()) / 2,
					(CoreSettings.P_HEIGHT - mildredCurrentFrame.getRegionHeight()) / 2);
			batch.end();

			batch.begin();
			startCurrentFrame = start.getKeyFrame(stateTime, true);
			batch.draw(startCurrentFrame, (CoreSettings.P_WIDTH - startCurrentFrame.getRegionWidth()) / 2,
					(CoreSettings.P_HEIGHT - startCurrentFrame.getRegionHeight()) / 2 - (CoreSettings.P_TILE_SIZE * 6));
			batch.end();

			batch.begin();
			layout.setText(smallFont, copyright);
			smallFont.setColor(Color.valueOf("ffffff"));
			smallFont.draw(batch, copyright, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 10));
			batch.end();
		}
	}

	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		super.dispose();

		mildredCutter.dispose();
		startCutter.dispose();
	}

	@Override
	protected void controls() {
		if (timer > 3.5 && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
				|| timer > 3.5 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			stateFinished = true;
			nextStateID = StateManager.MENU_STATE;
		}
	}

	protected void animationInit() {
		stateTime = 0;
		mildredTexture = assetManager.get("graphics/intro-mildred.png", Texture.class);
		startTexture = assetManager.get("graphics/intro-start.png", Texture.class);
		mildredCutter = new Cutter(mildredTexture, 16, 1);
		startCutter = new Cutter(startTexture, 3, 1);
		mildred = new Animation(0.2f, mildredCutter.frames[0], mildredCutter.frames[1], mildredCutter.frames[2],
				mildredCutter.frames[3], mildredCutter.frames[4], mildredCutter.frames[5], mildredCutter.frames[6],
				mildredCutter.frames[7], mildredCutter.frames[8], mildredCutter.frames[9], mildredCutter.frames[10],
				mildredCutter.frames[11], mildredCutter.frames[12], mildredCutter.frames[13], mildredCutter.frames[14],
				mildredCutter.frames[15]);
		start = new Animation(0.1f, startCutter.frames[0], startCutter.frames[1], startCutter.frames[2]);
	}

}
