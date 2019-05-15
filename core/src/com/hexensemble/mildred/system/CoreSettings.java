package com.hexensemble.mildred.system;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;

/**
 * Core settings.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.1
 * @since Alpha 2.0.0
 */
public class CoreSettings {

	/**
	 * Title.
	 */
	public static final String TITLE = "Mildred";

	/**
	 * Author.
	 */
	public static final String AUTHOR = "HexEnsemble";

	/**
	 * Version.
	 */
	public static final String VERSION = "Version 1.0.0";

	/**
	 * Version date.
	 */
	public static final String DATE = "19-APR-2016";

	/**
	 * Copyright.
	 */
	public static final String COPYRIGHT = "\u00a9 Copyright 2016 HexEnsemble. Published under licence by Hot Baked Goods.";

	/**
	 * Settings file path.
	 */
	public static final String SETTINGS_FILE = "/.mildred/settings.xml";

	/**
	 * Save file 1 path.
	 */
	public static final String SAVE_FILE_1 = "/.mildred/savegame1.json";

	/**
	 * Save file 2 path.
	 */
	public static final String SAVE_FILE_2 = "/.mildred/savegame2.json";

	/**
	 * Save file 3 path.
	 */
	public static final String SAVE_FILE_3 = "/.mildred/savegame3.json";

	/**
	 * Virtual tile size.
	 */
	public static final float V_TILE_SIZE = 1;

	/**
	 * Virtual screen width.
	 */
	public static final float V_WIDTH = 32;

	/**
	 * Virtual screen height.
	 */
	public static final float V_HEIGHT = 24;

	/**
	 * Virtual tile grid width.
	 */
	public static final float V_TILE_GRID_WIDTH = 64;

	/**
	 * Virtual tile grid height.
	 */
	public static final float V_TILE_GRID_HEIGHT = 48;

	/**
	 * Pixel tile size.
	 */
	public static final float P_TILE_SIZE = 32;

	/**
	 * Pixel screen width.
	 */
	public static final float P_WIDTH = V_WIDTH * P_TILE_SIZE;

	/**
	 * Pixel screen height.
	 */
	public static final float P_HEIGHT = V_HEIGHT * P_TILE_SIZE;

	/**
	 * HTML screen width.
	 */
	public static final int HTML_WIDTH = 800;

	/**
	 * HTML screen height.
	 */
	public static final int HTML_HEIGHT = 600;

	/**
	 * Screen width.
	 */
	public static int width;

	/**
	 * Screen height.
	 */
	public static int height;

	/**
	 * Fullscreen on/off.
	 */
	public static boolean fullscreen;

	/**
	 * vSync on/off.
	 */
	public static boolean vSync;

	/**
	 * Show FPS on/off.
	 */
	public static boolean fps;

	/**
	 * Sound volume.
	 */
	public static float sfxVol;

	/**
	 * Music volume.
	 */
	public static float musicVol;

	/**
	 * Active save slot.
	 */
	public static int saveSlot;

	/**
	 * Use custom level(s).
	 */
	public static boolean customLevel;

	/**
	 * Custom level list.
	 */
	public static ArrayList<String> customLevelList;

	/**
	 * Initialize.
	 * 
	 */
	public CoreSettings() {

	}

	public static void load() {
		if (Gdx.files.isExternalStorageAvailable()) {
			FileHandle settingsFile = Gdx.files.external(SETTINGS_FILE);
			XmlReader reader = new XmlReader();

			if (!settingsFile.exists()) {
				width = 800;
				height = 600;
				fullscreen = false;
				vSync = false;
				fps = false;
				sfxVol = 1;
				musicVol = 1;
				save();
			} else {
				for (int i = 0; i < reader.parse(settingsFile).getChildCount(); i++) {
					if (reader.parse(settingsFile).getChild(i).getAttribute("key").contains("width")) {
						width = Integer.parseInt((reader.parse(settingsFile).getChild(i).getText()));
					}
					if (reader.parse(settingsFile).getChild(i).getAttribute("key").contains("height")) {
						height = Integer.parseInt((reader.parse(settingsFile).getChild(i).getText()));
					}
					if (reader.parse(settingsFile).getChild(i).getAttribute("key").contains("fullscreen")) {
						fullscreen = Boolean.parseBoolean((reader.parse(settingsFile).getChild(i).getText()));
					}
					if (reader.parse(settingsFile).getChild(i).getAttribute("key").contains("vsync")) {
						vSync = Boolean.parseBoolean((reader.parse(settingsFile).getChild(i).getText()));
					}
					if (reader.parse(settingsFile).getChild(i).getAttribute("key").contains("fps")) {
						fps = Boolean.parseBoolean((reader.parse(settingsFile).getChild(i).getText()));
					}
					if (reader.parse(settingsFile).getChild(i).getAttribute("key").contains("sfx")) {
						sfxVol = Float.parseFloat((reader.parse(settingsFile).getChild(i).getText()));
					}
					if (reader.parse(settingsFile).getChild(i).getAttribute("key").contains("music")) {
						musicVol = Float.parseFloat((reader.parse(settingsFile).getChild(i).getText()));
					}
				}

				boolean soundSetting = false;
				boolean musicSetting = false;
				for (int i = 0; i < reader.parse(settingsFile).getChildCount(); i++) {
					if (reader.parse(settingsFile).getChild(i).getAttribute("key").contains("sfx")) {
						soundSetting = true;
					}
					if (reader.parse(settingsFile).getChild(i).getAttribute("key").contains("music")) {
						musicSetting = true;
					}
				}
				if (!soundSetting) {
					sfxVol = 1;
					save();
				}
				if (!musicSetting) {
					musicVol = 1;
					save();
				}
			}
		} else {
			width = HTML_WIDTH;
			height = HTML_HEIGHT;
			fullscreen = false;
			vSync = false;
			fps = false;
			sfxVol = 1;
			musicVol = 1;
		}
	}

	public static void save() {
		FileHandle settingsFile = Gdx.files.external(SETTINGS_FILE);
		XmlWriter writer = new XmlWriter(settingsFile.writer(false));

		try {
			writer.text("<?xml version=" + "\"1.0\"" + " encoding=" + "\"UTF-8\"" + " standalone=" + "\"no\"" + "?>");
			writer.text("<!DOCTYPE properties SYSTEM " + "\"http://java.sun.com/dtd/properties.dtd\"" + ">");
			writer.element("properties").element("entry").attribute("key", "width").text(width).pop().element("entry")
					.attribute("key", "height").text(height).pop().element("entry").attribute("key", "fullscreen")
					.text(fullscreen).pop().element("entry").attribute("key", "vsync").text(vSync).pop()
					.element("entry").attribute("key", "fps").text(fps).pop().element("entry").attribute("key", "sfx")
					.text(sfxVol).pop().element("entry").attribute("key", "music").text(musicVol).pop().pop();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}