package com.hexensemble.mildred.desktop.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Saves and loads application settings.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class DesktopSettings {

	/**
	 * Working directory.
	 */
	public static final String WORK_DIR = System.getProperty("user.home") + "/.mildred";

	/**
	 * Settings file.
	 */
	public static final String SETTINGS_FILE = WORK_DIR + "/settings.xml";

	/**
	 * Default width setting.
	 */
	public static final int WIDTH_DEFAULT = 800;

	/**
	 * Default height setting.
	 */
	public static final int HEIGHT_DEFAULT = 600;

	/**
	 * Default resolution setting.
	 */
	public static final String RESOLUTION_DEFAULT = WIDTH_DEFAULT + "x" + HEIGHT_DEFAULT;

	/**
	 * Default fullscreen setting.
	 */
	public static final boolean FULLSCREEN_DEFAULT = false;

	/**
	 * Default vSync setting.
	 */
	public static final boolean VSYNC_DEFAULT = false;

	/**
	 * Default show FPS setting.
	 */
	public static final boolean FPS_DEFAULT = false;

	/**
	 * Default sound setting.
	 */
	public static final String SFX_DEFAULT = "1.0";

	/**
	 * Default music setting.
	 */
	public static final String MUSIC_DEFAULT = "1.0";

	/**
	 * Width setting.
	 */
	public static int width;

	/**
	 * Height setting.
	 */
	public static int height;

	/**
	 * Fullscreen setting.
	 */
	public static boolean fullscreen;

	/**
	 * vSync setting.
	 */
	public static boolean vsync;

	/**
	 * Update site.
	 */
	public static final String UPDATE_SITE = "www.hexensemble.com";

	/**
	 * Update URL.
	 */
	public static final String UPDATE_URL = "http://www.hexensemble.com/mildred/update.html";

	/**
	 * Initialize.
	 */
	public DesktopSettings() {

	}

	/**
	 * Saves a setting.
	 * 
	 * @param properties
	 *            Properties list.
	 * @param key
	 *            Setting key.
	 * @param value
	 *            Setting value.
	 * @throws Exception
	 *             Exception.
	 */
	public static void save(Properties properties, String key, String value) throws Exception {
		File file = new File(SETTINGS_FILE);

		if (Files.notExists(Paths.get(WORK_DIR))) {
			Files.createDirectory(Paths.get(WORK_DIR));
		}
		if (!file.exists()) {
			file.createNewFile();
		}

		OutputStream write = new FileOutputStream(SETTINGS_FILE);
		properties.setProperty(key, value);
		properties.storeToXML(write, null);
	}

	/**
	 * Loads a setting.
	 * 
	 * @param key
	 *            Setting key.
	 * @throws Exception
	 *             Exception.
	 * @return Setting value.
	 */
	public static String load(String key) throws Exception {
		File file = new File(SETTINGS_FILE);
		Properties properties = new Properties();

		if (file.exists()) {
			InputStream read = new FileInputStream(SETTINGS_FILE);
			properties.loadFromXML(read);
			return properties.getProperty(key);
		} else {
			return null;
		}
	}

	/**
	 * Loads screen settings.
	 * 
	 */
	public static void loadScreenSettings() {
		File settingsFile = new File(DesktopSettings.SETTINGS_FILE);

		if (settingsFile.exists()) {
			try {
				width = Integer.parseInt(load("width"));
				height = Integer.parseInt(load("height"));
				fullscreen = Boolean.parseBoolean(load("fullscreen"));
				vsync = Boolean.parseBoolean(load("vsync"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			width = WIDTH_DEFAULT;
			height = HEIGHT_DEFAULT;
			fullscreen = FULLSCREEN_DEFAULT;
			vsync = VSYNC_DEFAULT;
		}
	}

}
