package com.hexensemble.mildred.desktop;

import java.io.File;

import com.hexensemble.mildred.desktop.launcher.MenuState;
import com.hexensemble.mildred.desktop.launcher.DesktopSettings;
import com.hexensemble.mildred.desktop.launcher.SettingsState;
import com.hexensemble.mildred.desktop.launcher.UpdateState;
import com.hexensemble.mildred.desktop.launcher.Updater;
import com.hexensemble.mildred.system.CoreSettings;

/**
 * Desktop launcher.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class DesktopLauncher {

	/**
	 * Launcher title.
	 */
	public static final String TITLE = "Mildred Launcher";

	/**
	 * Launcher width.
	 */
	public static final int WIDTH = 640;

	/**
	 * Launcher height.
	 */
	public static final int HEIGHT = 480;

	/**
	 * Main method.
	 * 
	 * @param args
	 *            String[] args
	 */
	public static void main(String[] args) {
		try {
			if (Updater.isReachable()) {
				if (Updater.getVersion().equals(CoreSettings.VERSION)) {
					File settingsFile = new File(DesktopSettings.SETTINGS_FILE);
					if (settingsFile.exists()) {
						new MenuState();
					} else {
						new SettingsState();
					}
				} else {
					new UpdateState();
				}
			} else {
				File settingsFile = new File(DesktopSettings.SETTINGS_FILE);
				if (settingsFile.exists()) {
					new MenuState();
				} else {
					new SettingsState();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
