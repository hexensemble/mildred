package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.SavedData;

/**
 * Represents the main menu state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class MenuState extends State {

	private Json json;
	private FileHandle saveFile1;
	private FileHandle saveFile2;
	private FileHandle saveFile3;
	private SavedData savedData1;
	private SavedData savedData2;
	private SavedData savedData3;
	private String level1;
	private String level2;
	private String level3;
	private String dateStamp1;
	private String dateStamp2;
	private String dateStamp3;

	private Texture background;
	private Texture menu;
	private Texture message;

	private Texture socketSound;
	private Texture sliderSound;
	private float sliderSoundX;
	private Texture socketMusic;
	private Texture sliderMusic;
	private float sliderMusicX;

	private BitmapFont largeFont;
	private BitmapFont smallFont;
	private GlyphLayout layout;

	private String title;
	private String[] menuOptions;
	private int currentMenuOption;

	private boolean showSaveSlots;
	private String saveSlots;
	private String[] saveSlotOptions;
	private int currentSaveSlotOption;

	private boolean showOverwrite;
	private String overwrite;
	private String[] overwriteOptions;
	private int currentOverwriteOption;

	private boolean showNoSaveFile;
	private String noSaveFile;
	private String noSaveFileOK;

	private boolean showNoSaving;
	private String noSaving;
	private String noSavingOK;

	private boolean showSettings;
	private String[] settingsOptions;
	private int currentSettingsOption;

	private boolean newGame;
	private boolean continueGame;

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
	public MenuState(AssetManager assetManager, Viewport viewport, OrthographicCamera camera) {
		super(assetManager, viewport, camera);

		viewport.setWorldSize(CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("graphics/mouse-blank.png")),
				(int) (CoreSettings.P_TILE_SIZE / 2), (int) (CoreSettings.P_TILE_SIZE / 2));

		if (Gdx.files.isExternalStorageAvailable()) {
			json = new Json();

			saveFile1 = Gdx.files.external(CoreSettings.SAVE_FILE_1);
			saveFile2 = Gdx.files.external(CoreSettings.SAVE_FILE_2);
			saveFile3 = Gdx.files.external(CoreSettings.SAVE_FILE_3);

			if (saveFile1.exists()) {
				savedData1 = json.fromJson(SavedData.class, Base64Coder.decodeString(saveFile1.readString()));

				if (savedData1.customLevel) {
					level1 = "CUSTOM - ";
				} else {
					level1 = "Level " + (savedData1.levelIndex + 1) + " - ";
				}
				dateStamp1 = savedData1.dateStamp;
			} else {
				level1 = "";
				dateStamp1 = "Empty";
			}
			if (saveFile2.exists()) {
				savedData2 = json.fromJson(SavedData.class, Base64Coder.decodeString(saveFile2.readString()));

				if (savedData2.customLevel) {
					level2 = "CUSTOM - ";
				} else {
					level2 = "Level " + (savedData2.levelIndex + 1) + " - ";
				}
				dateStamp2 = savedData2.dateStamp;
			} else {
				level2 = "";
				dateStamp2 = "Empty";
			}
			if (saveFile3.exists()) {
				savedData3 = json.fromJson(SavedData.class, Base64Coder.decodeString(saveFile3.readString()));

				if (savedData3.customLevel) {
					level3 = "CUSTOM - ";
				} else {
					level3 = "Level " + (savedData3.levelIndex + 1) + " - ";
				}
				dateStamp3 = savedData3.dateStamp;
			} else {
				level3 = "";
				dateStamp3 = "Empty";
			}
		} else {
			saveFile1 = null;
			saveFile2 = null;
			saveFile3 = null;
		}

		background = assetManager.get("graphics/menu-background.png", Texture.class);
		menu = assetManager.get("graphics/menu-menu.png", Texture.class);
		message = assetManager.get("graphics/menu-message.png", Texture.class);

		socketSound = assetManager.get("graphics/menu-socket.png", Texture.class);
		sliderSound = assetManager.get("graphics/menu-slider.png", Texture.class);
		if (CoreSettings.sfxVol == 0) {
			sliderSoundX = ((CoreSettings.P_WIDTH - sliderSound.getWidth()) / 2) - ((CoreSettings.P_TILE_SIZE * 2) / 2);
		}
		if (CoreSettings.sfxVol == 0.5) {
			sliderSoundX = ((CoreSettings.P_WIDTH - sliderSound.getWidth()) / 2) - ((CoreSettings.P_TILE_SIZE) / 2);
		}
		if (CoreSettings.sfxVol == 1) {
			sliderSoundX = (CoreSettings.P_WIDTH - sliderSound.getWidth()) / 2;
		}
		if (CoreSettings.sfxVol == 1.5) {
			sliderSoundX = ((CoreSettings.P_WIDTH - sliderSound.getWidth()) / 2) + ((CoreSettings.P_TILE_SIZE) / 2);
		}
		if (CoreSettings.sfxVol == 2) {
			sliderSoundX = ((CoreSettings.P_WIDTH - sliderSound.getWidth()) / 2) + ((CoreSettings.P_TILE_SIZE * 2) / 2);
		}

		socketMusic = assetManager.get("graphics/menu-socket.png", Texture.class);
		sliderMusic = assetManager.get("graphics/menu-slider.png", Texture.class);
		if (CoreSettings.musicVol == 0) {
			sliderMusicX = ((CoreSettings.P_WIDTH - sliderMusic.getWidth()) / 2) - ((CoreSettings.P_TILE_SIZE * 2) / 2);
		}
		if (CoreSettings.musicVol == 0.5) {
			sliderMusicX = ((CoreSettings.P_WIDTH - sliderMusic.getWidth()) / 2) - ((CoreSettings.P_TILE_SIZE) / 2);
		}
		if (CoreSettings.musicVol == 1) {
			sliderMusicX = (CoreSettings.P_WIDTH - sliderMusic.getWidth()) / 2;
		}
		if (CoreSettings.musicVol == 1.5) {
			sliderMusicX = ((CoreSettings.P_WIDTH - sliderMusic.getWidth()) / 2) + ((CoreSettings.P_TILE_SIZE) / 2);
		}
		if (CoreSettings.musicVol == 2) {
			sliderMusicX = ((CoreSettings.P_WIDTH - sliderMusic.getWidth()) / 2) + ((CoreSettings.P_TILE_SIZE * 2) / 2);
		}

		largeFont = assetManager.get("fonts/Track-30.fnt", BitmapFont.class);
		smallFont = assetManager.get("fonts/Track-14.fnt", BitmapFont.class);
		layout = new GlyphLayout();

		title = CoreSettings.TITLE;
		menuOptions = new String[] { "New Game", "Continue Game", "Settings", "Help", "Credits", "Quit" };
		currentMenuOption = 0;

		showSaveSlots = false;
		saveSlots = "Choose a save slot...";
		saveSlotOptions = new String[] { level1 + dateStamp1, level2 + dateStamp2, level3 + dateStamp3, "Cancel" };
		currentSaveSlotOption = 0;

		showOverwrite = false;
		overwrite = ("This will overwrite the saved\ngame. Are you sure you want\nto continue?");
		overwriteOptions = new String[] { "Continue", "Cancel" };
		currentOverwriteOption = 0;

		showNoSaveFile = false;
		noSaveFile = "No saved game found.";
		noSaveFileOK = "OK";

		showNoSaving = false;
		noSaving = "Saved games not available\nin web browser version.";
		noSavingOK = "OK";

		showSettings = false;
		settingsOptions = new String[] { "Sound Volume", "Music Volume", "OK" };
		currentSettingsOption = 0;

		newGame = false;
		continueGame = false;
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
		batch.draw(menu, (CoreSettings.P_WIDTH - menu.getWidth()) / 2, (CoreSettings.P_HEIGHT - menu.getHeight()) / 2);
		batch.end();

		batch.begin();
		layout.setText(largeFont, title);
		largeFont.setColor(Color.valueOf("582000"));
		largeFont.draw(batch, title, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 4));
		batch.end();

		batch.begin();
		for (int i = 0; i < menuOptions.length; i++) {
			layout.setText(smallFont, menuOptions[i]);
			if (currentMenuOption == i) {
				smallFont.setColor(Color.valueOf("ff0000"));
			} else {
				smallFont.setColor(Color.valueOf("582000"));
			}
			smallFont.draw(batch, menuOptions[i], (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2)
							- (CoreSettings.P_TILE_SIZE * i));
		}
		batch.end();

		if (showSaveSlots) {
			batch.begin();
			batch.draw(message, (CoreSettings.P_WIDTH - message.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - message.getHeight()) / 2);
			batch.end();

			batch.begin();
			layout.setText(smallFont, saveSlots);
			smallFont.setColor(Color.valueOf("582000"));
			smallFont.draw(batch, saveSlots, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
			for (int i = 0; i < saveSlotOptions.length; i++) {
				layout.setText(smallFont, saveSlotOptions[i]);
				if (currentSaveSlotOption == i) {
					smallFont.setColor(Color.valueOf("ff0000"));
				} else {
					smallFont.setColor(Color.valueOf("582000"));
				}
				smallFont.draw(batch, saveSlotOptions[i], (CoreSettings.P_WIDTH - layout.width) / 2,
						(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE)
								- (CoreSettings.P_TILE_SIZE * i));
			}
			batch.end();
		}

		if (showOverwrite) {
			batch.begin();
			batch.draw(message, (CoreSettings.P_WIDTH - message.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - message.getHeight()) / 2);
			batch.end();

			batch.begin();
			layout.setText(smallFont, overwrite);
			smallFont.setColor(Color.valueOf("582000"));
			smallFont.draw(batch, overwrite, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
			for (int i = 0; i < overwriteOptions.length; i++) {
				layout.setText(smallFont, overwriteOptions[i]);
				if (currentOverwriteOption == i) {
					smallFont.setColor(Color.valueOf("ff0000"));
				} else {
					smallFont.setColor(Color.valueOf("582000"));
				}
				smallFont.draw(batch, overwriteOptions[i],
						(CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 2)
								- ((CoreSettings.P_TILE_SIZE * 4) * i),
						(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 2));
			}
			batch.end();
		}

		if (showNoSaveFile) {
			batch.begin();
			batch.draw(message, (CoreSettings.P_WIDTH - message.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - message.getHeight()) / 2);
			batch.end();

			batch.begin();
			layout.setText(smallFont, noSaveFile);
			smallFont.setColor(Color.valueOf("582000"));
			smallFont.draw(batch, noSaveFile, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
			layout.setText(smallFont, noSaveFileOK);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, noSaveFileOK, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 2));
			batch.end();
		}

		if (showNoSaving) {
			batch.begin();
			batch.draw(message, (CoreSettings.P_WIDTH - message.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - message.getHeight()) / 2);
			batch.end();

			batch.begin();
			layout.setText(smallFont, noSaving);
			smallFont.setColor(Color.valueOf("582000"));
			smallFont.draw(batch, noSaving, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
			layout.setText(smallFont, noSavingOK);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, noSavingOK, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 2));
			batch.end();
		}

		if (showSettings) {
			batch.begin();
			batch.draw(message, (CoreSettings.P_WIDTH - message.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - message.getHeight()) / 2);
			batch.end();

			batch.begin();
			batch.draw(socketSound, (CoreSettings.P_WIDTH - socketSound.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - socketSound.getHeight()) / 2 + CoreSettings.P_TILE_SIZE);
			batch.draw(sliderSound, sliderSoundX,
					(CoreSettings.P_HEIGHT - sliderSound.getHeight()) / 2 + CoreSettings.P_TILE_SIZE);
			batch.end();

			batch.begin();
			batch.draw(socketMusic, (CoreSettings.P_WIDTH - socketMusic.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - socketMusic.getHeight()) / 2 - CoreSettings.P_TILE_SIZE);
			batch.draw(sliderMusic, sliderMusicX,
					(CoreSettings.P_HEIGHT - sliderMusic.getHeight()) / 2 - CoreSettings.P_TILE_SIZE);
			batch.end();

			batch.begin();
			for (int i = 0; i < settingsOptions.length; i++) {
				layout.setText(smallFont, settingsOptions[i]);
				if (currentSettingsOption == i) {
					smallFont.setColor(Color.valueOf("ff0000"));
				} else {
					smallFont.setColor(Color.valueOf("582000"));
				}
				smallFont.draw(batch, settingsOptions[i], (CoreSettings.P_WIDTH - layout.width) / 2,
						(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2)
								- ((CoreSettings.P_TILE_SIZE * 2) * i));
			}
			batch.end();
		}

		batch.begin();
		layout.setText(smallFont, CoreSettings.VERSION);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, CoreSettings.VERSION, (CoreSettings.P_WIDTH - layout.width) - CoreSettings.P_TILE_SIZE,
				CoreSettings.P_TILE_SIZE - layout.height);
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
		if (showSaveSlots) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
				if (currentSaveSlotOption > 0) {
					currentSaveSlotOption--;
				} else {
					currentSaveSlotOption = saveSlotOptions.length - 1;
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
				if (currentSaveSlotOption < saveSlotOptions.length - 1) {
					currentSaveSlotOption++;
				} else {
					currentSaveSlotOption = 0;
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				selectSaveSlot();
			}
		}

		else if (showOverwrite) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
				if (currentOverwriteOption > 0) {
					currentOverwriteOption--;
				} else {
					currentOverwriteOption = overwriteOptions.length - 1;
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
				if (currentOverwriteOption < overwriteOptions.length - 1) {
					currentOverwriteOption++;
				} else {
					currentOverwriteOption = 0;
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				selectOverwriteOption();
			}
		}

		else if (showNoSaveFile) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				showNoSaveFile = false;
			}
		}

		else if (showNoSaving) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				showNoSaving = false;
			}
		}

		else if (showSettings) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
				if (currentSettingsOption > 0) {
					currentSettingsOption--;
				} else {
					currentSettingsOption = overwriteOptions.length - 1;
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
				if (currentSettingsOption < settingsOptions.length - 1) {
					currentSettingsOption++;
				} else {
					currentSettingsOption = 0;
				}
			}

			if (currentSettingsOption == 0) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
					sliderSoundX -= CoreSettings.P_TILE_SIZE / 2;
					CoreSettings.sfxVol -= 0.5f;

					if (sliderSoundX < 472) {
						sliderSoundX = 472;
					}
					if (CoreSettings.sfxVol < 0) {
						CoreSettings.sfxVol = 0;
					}
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
					sliderSoundX += CoreSettings.P_TILE_SIZE / 2;
					CoreSettings.sfxVol += 0.5f;

					if (sliderSoundX > 536) {
						sliderSoundX = 536;
					}
					if (CoreSettings.sfxVol > 2) {
						CoreSettings.sfxVol = 2;
					}
				}
			}
			if (currentSettingsOption == 1) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
					sliderMusicX -= CoreSettings.P_TILE_SIZE / 2;
					CoreSettings.musicVol -= 0.5f;

					if (sliderMusicX < 472) {
						sliderMusicX = 472;
					}
					if (CoreSettings.musicVol < 0) {
						CoreSettings.musicVol = 0;
					}
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
					sliderMusicX += CoreSettings.P_TILE_SIZE / 2;
					CoreSettings.musicVol += 0.5f;

					if (sliderMusicX > 536) {
						sliderMusicX = 536;
					}
					if (CoreSettings.musicVol > 2) {
						CoreSettings.musicVol = 2;
					}
				}
			}
			if (currentSettingsOption == 2) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
					showSettings = false;
					if (Gdx.files.isExternalStorageAvailable()) {
						CoreSettings.save();
					}
				}
			}
		}

		else {
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
				if (currentMenuOption > 0) {
					currentMenuOption--;
				} else {
					currentMenuOption = menuOptions.length - 1;
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
				if (currentMenuOption < menuOptions.length - 1) {
					currentMenuOption++;
				} else {
					currentMenuOption = 0;
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				selectMenuOption();
			}
		}
	}

	private void selectMenuOption() {
		switch (currentMenuOption) {
		case 0:
			if (Gdx.files.isExternalStorageAvailable()) {
				showSaveSlots = true;
				newGame = true;
				continueGame = false;
			} else {
				stateFinished = true;
				nextStateID = StateManager.PROLOGUE_STATE;
			}
			break;
		case 1:
			if (Gdx.files.isExternalStorageAvailable()) {
				showSaveSlots = true;
				newGame = false;
				continueGame = true;
			} else {
				showNoSaving = true;
			}
			break;
		case 2:
			showSettings = true;
			break;
		case 3:
			stateFinished = true;
			nextStateID = StateManager.HELP_STATE;
			break;
		case 4:
			stateFinished = true;
			nextStateID = StateManager.CREDITS_STATE;
			break;
		case 5:
			Gdx.app.exit();
			break;
		default:
			Gdx.app.exit();
			break;
		}
	}

	private void selectSaveSlot() {
		if (newGame) {
			switch (currentSaveSlotOption) {
			case 0:
				CoreSettings.saveSlot = 1;
				if (saveFile1.exists()) {
					showSaveSlots = false;
					showOverwrite = true;
				} else {
					stateFinished = true;
					if (CoreSettings.customLevel) {
						nextStateID = StateManager.PLAY_STATE;
					} else {
						nextStateID = StateManager.PROLOGUE_STATE;
					}
				}
				break;
			case 1:
				CoreSettings.saveSlot = 2;
				if (saveFile2.exists()) {
					showSaveSlots = false;
					showOverwrite = true;
				} else {
					stateFinished = true;
					if (CoreSettings.customLevel) {
						nextStateID = StateManager.PLAY_STATE;
					} else {
						nextStateID = StateManager.PROLOGUE_STATE;
					}
				}
				break;
			case 2:
				CoreSettings.saveSlot = 3;
				if (saveFile3.exists()) {
					showSaveSlots = false;
					showOverwrite = true;
				} else {
					stateFinished = true;
					if (CoreSettings.customLevel) {
						nextStateID = StateManager.PLAY_STATE;
					} else {
						nextStateID = StateManager.PROLOGUE_STATE;
					}
				}
				break;
			case 3:
				showSaveSlots = false;
				break;
			default:
				showSaveSlots = false;
				break;
			}
		}

		if (continueGame) {
			switch (currentSaveSlotOption) {
			case 0:
				CoreSettings.saveSlot = 1;
				if (saveFile1.exists()) {
					stateFinished = true;
					nextStateID = StateManager.CONTINUE_PLAY_STATE;
				} else {
					showSaveSlots = false;
					showNoSaveFile = true;
				}
				break;
			case 1:
				CoreSettings.saveSlot = 2;
				if (saveFile2.exists()) {
					stateFinished = true;
					nextStateID = StateManager.CONTINUE_PLAY_STATE;
				} else {
					showSaveSlots = false;
					showNoSaveFile = true;
				}
				break;
			case 2:
				CoreSettings.saveSlot = 3;
				if (saveFile3.exists()) {
					stateFinished = true;
					nextStateID = StateManager.CONTINUE_PLAY_STATE;
				} else {
					showSaveSlots = false;
					showNoSaveFile = true;
				}
				break;
			case 3:
				showSaveSlots = false;
				break;
			default:
				showSaveSlots = false;
				break;
			}
		}
	}

	private void selectOverwriteOption() {
		switch (currentOverwriteOption) {
		case 0:
			stateFinished = true;
			if (CoreSettings.customLevel) {
				nextStateID = StateManager.PLAY_STATE;
			} else {
				nextStateID = StateManager.PROLOGUE_STATE;
			}
			break;
		case 1:
			showOverwrite = false;
			break;
		default:
			showOverwrite = false;
			break;
		}
	}

}
