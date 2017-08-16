package com.hexensemble.mildred.system;

import com.badlogic.gdx.utils.Array;

/**
 * Represents the music playlist.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.0
 * @since 1.0.0
 */
public class PlayList {

	private static Array<String> queuedTracks;
	private static int trackNumber;
	private static int lastTrackNumber;

	private PlayList() {

	}

	/**
	 * Initialize.
	 * 
	 */
	public static void init() {
		queuedTracks = new Array<String>();
		queuedTracks.add("music/01.ogg");
		queuedTracks.add("music/02.ogg");
		queuedTracks.add("music/03.ogg");
		queuedTracks.add("music/04.ogg");
		queuedTracks.add("music/05.ogg");

		trackNumber = 0;
		lastTrackNumber = queuedTracks.size;
	}

	/**
	 * Gets the track filename.
	 * 
	 * @return Track filename
	 */
	public static String getTrack() {
		if (trackNumber == lastTrackNumber) {
			trackNumber++;
		}
		if (trackNumber >= queuedTracks.size) {
			trackNumber = 0;
		}

		lastTrackNumber = trackNumber;

		return queuedTracks.get(trackNumber);
	}

}
