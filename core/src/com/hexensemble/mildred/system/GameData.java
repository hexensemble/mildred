package com.hexensemble.mildred.system;

/**
 * Static variables for game data.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 2.1.0
 */
public class GameData {

	public static String version;
	public static String dateStamp;
	public static Boolean customLevel;
	public static int levelIndex;
	public static int xp;
	public static int xpLevel;
	public static int xpLevelCheck;
	public static int health;
	public static int energy;

	public static float totalSecrets;
	public static int secrets;
	public static int totalEnemies;
	public static int kills;
	public static String time;

	/**
	 * Initialize.
	 * 
	 * @deprecated
	 */
	@Deprecated
	public GameData() {

	}

	/**
	 * Passes in variables to be saved.
	 * 
	 * @param version
	 *            Application version.
	 * @param dateStamp
	 *            File date stamp.
	 * @param customLevel
	 *            Custom level true/false.
	 * @param levelIndex
	 *            Level index.
	 * @param xp
	 *            XP value.
	 * @param xpLevel
	 *            XP level.
	 * @param xpLevelCheck
	 *            XP level check value.
	 * @param health
	 *            Health value.
	 * @param energy
	 *            Energy value.
	 * @param totalSecrets
	 *            Total number of secrets.
	 * @param secrets
	 *            Number of secrets found.
	 * @param totalEnemies
	 *            Total number of enemies.
	 * @param kills
	 *            Number of enemies killed.
	 * @param time
	 *            Level completion time.
	 */
	public static void save(String version, String dateStamp, boolean customLevel, int levelIndex, int xp, int xpLevel,
			int xpLevelCheck, int health, int energy, float totalSecrets, int secrets, int totalEnemies, int kills,
			String time) {
		GameData.version = version;
		GameData.dateStamp = dateStamp;
		GameData.customLevel = customLevel;
		GameData.levelIndex = levelIndex;
		GameData.xp = xp;
		GameData.xpLevel = xpLevel;
		GameData.xpLevelCheck = xpLevelCheck;
		GameData.health = health;
		GameData.energy = energy;

		GameData.totalSecrets = totalSecrets;
		GameData.secrets = secrets;
		GameData.totalEnemies = totalEnemies;
		GameData.kills = kills;
		GameData.time = time;
	}

}
