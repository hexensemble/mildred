package com.hexensemble.mildred.levels;

import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hexensemble.mildred.states.PlayState;
import com.hexensemble.mildred.states.State;
import com.hexensemble.mildred.states.StateManager;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.GameData;
import com.hexensemble.mildred.system.HUD;
import com.hexensemble.mildred.system.SavedData;

/**
 * Manages saving/loading of levels.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Alpha 1.0.0
 */
public class LevelManager {

	private AssetManager assetManager;
	private Viewport viewport;
	private OrthographicCamera camera;

	private Json json;
	private FileHandle saveFile;
	private SavedData savedData;

	private long millis;
	private Date date;
	private String dateStamp;

	private Array<String> levelFileNames;
	private int levelIndex;

	/**
	 * Error flag.
	 * 
	 */
	public boolean error;

	/**
	 * Game level
	 * 
	 */
	public Level level;

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
	public LevelManager(AssetManager assetManager, Viewport viewport, OrthographicCamera camera, int playStateType) {
		this.assetManager = assetManager;
		this.viewport = viewport;
		this.camera = camera;

		if (Gdx.files.isExternalStorageAvailable()) {
			json = new Json();
			json.setOutputType(OutputType.json);
			switch (CoreSettings.saveSlot) {
			case 1:
				saveFile = Gdx.files.external(CoreSettings.SAVE_FILE_1);
				break;
			case 2:
				saveFile = Gdx.files.external(CoreSettings.SAVE_FILE_2);
				break;
			case 3:
				saveFile = Gdx.files.external(CoreSettings.SAVE_FILE_3);
				break;
			default:
				saveFile = Gdx.files.external(CoreSettings.SAVE_FILE_1);
				break;
			}
		} else {
			saveFile = null;
		}
		savedData = new SavedData();

		millis = TimeUtils.millis();
		date = new Date(millis);
		dateStamp = date.toString();

		levelFileNames = new Array<String>();
		levelIndex = 0;

		error = false;

		addLevelFileNames();

		if (playStateType == PlayState.NEW_GAME) {
			loadFirstLevel();
		}
		if (playStateType == PlayState.CONTINUE_GAME) {
			loadSavedLevel();
		}
		if (playStateType == PlayState.NEXT_LEVEL) {
			loadNextLevel();
		}
	}

	/**
	 * Update.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	public void update(float delta) {
		level.update(delta);

		if (level.isLevelComplete()) {
			millis = TimeUtils.millis();
			date.setTime(millis);
			dateStamp = date.toString();

			GameData.save(CoreSettings.VERSION, dateStamp, CoreSettings.customLevel, levelIndex,
					level.players.get(0).getXp(), level.players.get(0).getXpLevel(),
					level.players.get(0).getXpLevelCheck(), level.players.get(0).getHealth(),
					level.players.get(0).getEnergy(), level.getTotalSecrets(), level.getSecrets(),
					level.getTotalEnemies(), level.getKills(), level.getTime());

			if (saveFile != null) {
				savedData.save(GameData.version, GameData.dateStamp, GameData.customLevel, GameData.levelIndex,
						GameData.xp, GameData.xpLevel, GameData.xpLevelCheck, GameData.health, GameData.energy);
				saveFile.writeString(Base64Coder.encodeString(json.toJson(savedData)), false);
			}

			State.stateFinished = true;

			if (levelIndex + 1 >= levelFileNames.size) {
				if (CoreSettings.customLevel) {
					State.nextStateID = StateManager.CREDITS_STATE;
				} else {
					State.nextStateID = StateManager.END_STATE;
				}
			} else {
				State.nextStateID = StateManager.PROGRESS_STATE;
			}
		}
	}

	/**
	 * Render.
	 * 
	 * @param batch
	 *            Sprite batch.
	 */
	public void render(SpriteBatch batch) {
		level.render(batch);
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		level.dispose();
	}

	private void addLevelFileNames() {
		if (CoreSettings.customLevel) {
			for (String s : CoreSettings.customLevelList) {
				levelFileNames.add(s);
			}
		} else {
			levelFileNames.add(new String("levels/level-01.tmx"));
			levelFileNames.add(new String("levels/level-02.tmx"));
			levelFileNames.add(new String("levels/level-03.tmx"));
			levelFileNames.add(new String("levels/level-04.tmx"));
			levelFileNames.add(new String("levels/level-05.tmx"));
			levelFileNames.add(new String("levels/level-06.tmx"));
			levelFileNames.add(new String("levels/level-07.tmx"));
			levelFileNames.add(new String("levels/level-08.tmx"));
			levelFileNames.add(new String("levels/level-09.tmx"));
			levelFileNames.add(new String("levels/level-10.tmx"));
			levelFileNames.add(new String("levels/level-11.tmx"));
			levelFileNames.add(new String("levels/level-12.tmx"));
			levelFileNames.add(new String("levels/level-13.tmx"));
			levelFileNames.add(new String("levels/level-14.tmx"));
			levelFileNames.add(new String("levels/level-15.tmx"));
			levelFileNames.add(new String("levels/level-16.tmx"));
			levelFileNames.add(new String("levels/level-17.tmx"));
			levelFileNames.add(new String("levels/level-18.tmx"));
			levelFileNames.add(new String("levels/level-19.tmx"));
			levelFileNames.add(new String("levels/level-20.tmx"));
			levelFileNames.add(new String("levels/level-21.tmx"));
		}
	}

	private void loadFirstLevel() {
		levelIndex = 0;
		level = new Level(assetManager, viewport, camera, levelFileNames.get(levelIndex));

		GameData.save(CoreSettings.VERSION, dateStamp, CoreSettings.customLevel, levelIndex,
				level.players.get(0).getXp(), level.players.get(0).getXpLevel(), level.players.get(0).getXpLevelCheck(),
				level.players.get(0).getHealth(), level.players.get(0).getEnergy(), level.getTotalSecrets(),
				level.getSecrets(), level.getTotalEnemies(), level.getKills(), level.getTime());

		if (saveFile != null) {
			savedData.save(GameData.version, GameData.dateStamp, GameData.customLevel, GameData.levelIndex, GameData.xp,
					GameData.xpLevel, GameData.xpLevelCheck, GameData.health, GameData.energy);
			saveFile.writeString(Base64Coder.encodeString(json.toJson(savedData)), false);
		}
	}

	private void loadSavedLevel() {
		if (saveFile != null) {
			savedData = json.fromJson(SavedData.class, Base64Coder.decodeString(saveFile.readString()));
			if (CoreSettings.customLevel && !savedData.customLevel
					|| !CoreSettings.customLevel && savedData.customLevel) {
				error = true;
				State.stateFinished = true;
				State.nextStateID = StateManager.ERROR_STATE;
			} else {
				levelIndex = savedData.levelIndex;
				level = new Level(assetManager, viewport, camera, levelFileNames.get(levelIndex));
				level.players.get(0).setXp(savedData.xp);
				level.players.get(0).setXpLevel(savedData.xpLevel);
				level.players.get(0).setXpLevelCheck(savedData.xpLevelCheck);
				level.players.get(0).setHealth(savedData.health);
				level.players.get(0).setEnergy(savedData.energy);
			}
		} else {
			levelIndex = GameData.levelIndex;
			level = new Level(assetManager, viewport, camera, levelFileNames.get(levelIndex));
			level.players.get(0).setXp(GameData.xp);
			level.players.get(0).setXpLevel(GameData.xpLevel);
			level.players.get(0).setXpLevelCheck(GameData.xpLevelCheck);
			level.players.get(0).setHealth(GameData.health);
			level.players.get(0).setEnergy(GameData.energy);
		}
	}

	private void loadNextLevel() {
		levelIndex = GameData.levelIndex + 1;
		level = new Level(assetManager, viewport, camera, levelFileNames.get(levelIndex));
		level.players.get(0).setXp(GameData.xp);
		level.players.get(0).setXpLevel(GameData.xpLevel);
		level.players.get(0).setXpLevelCheck(GameData.xpLevelCheck);
		level.players.get(0).setHealth(GameData.health);
		level.players.get(0).setEnergy(GameData.energy);

		GameData.save(CoreSettings.VERSION, dateStamp, CoreSettings.customLevel, levelIndex,
				level.players.get(0).getXp(), level.players.get(0).getXpLevel(), level.players.get(0).getXpLevelCheck(),
				level.players.get(0).getHealth(), level.players.get(0).getEnergy(), level.getTotalSecrets(),
				level.getSecrets(), level.getTotalEnemies(), level.getKills(), level.getTime());

		if (saveFile != null) {
			savedData.save(GameData.version, GameData.dateStamp, GameData.customLevel, GameData.levelIndex, GameData.xp,
					GameData.xpLevel, GameData.xpLevelCheck, GameData.health, GameData.energy);
			saveFile.writeString(Base64Coder.encodeString(json.toJson(savedData)), false);
			HUD.showProgressSaved = true;
		}
	}

}
