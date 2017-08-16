package com.hexensemble.mildred.system;

/**
 * Saved gamed data.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 2.1.0
 */
public class SavedData {

	public String version;
	public String dateStamp;
	public Boolean customLevel;
	public int levelIndex;
	public int xp;
	public int xpLevel;
	public int xpLevelCheck;
	public int health;
	public int energy;

	/**
	 * Initialize.
	 * 
	 */
	public SavedData() {

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
	 */
	public void save(String version, String dateStamp, boolean customLevel, int levelIndex, int xp, int xpLevel,
			int xpLevelCheck, int health, int energy) {
		this.version = version;
		this.dateStamp = dateStamp;
		this.customLevel = customLevel;
		this.levelIndex = levelIndex;
		this.xp = xp;
		this.xpLevel = xpLevel;
		this.xpLevelCheck = xpLevelCheck;
		this.health = health;
		this.energy = energy;
	}

}
