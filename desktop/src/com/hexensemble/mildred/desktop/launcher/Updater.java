package com.hexensemble.mildred.desktop.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * Retrieves latest version and changes from web site.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class Updater {

	/**
	 * Initialize.
	 */
	public Updater() {

	}

	/**
	 * Checks if the update server is reachable.
	 * 
	 * @return Update server reachable true/false.
	 */
	public static boolean isReachable() {
		try {
			Socket socket = new Socket();
			InetSocketAddress address = new InetSocketAddress(DesktopSettings.UPDATE_SITE, 80);
			socket.connect(address, 3000);
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Gets the latest version.
	 * 
	 * @throws MalformedURLException
	 *             MalformedURLException.
	 * @throws IOException
	 *             IOException.
	 * @return Latest version.
	 */
	public static String getVersion() throws MalformedURLException, IOException {
		String data = getData(DesktopSettings.UPDATE_URL);

		return data.substring(data.indexOf("[version]") + 9, data.indexOf("[/version]"));
	}

	/**
	 * Gets the latest date.
	 * 
	 * @throws MalformedURLException
	 *             MalformedURLException.
	 * @throws IOException
	 *             IOException.
	 * @return Latest date.
	 */
	public static String getDate() throws MalformedURLException, IOException {
		String data = getData(DesktopSettings.UPDATE_URL);

		return data.substring(data.indexOf("[date]") + 6, data.indexOf("[/date]"));
	}

	/**
	 * Gets the latest changes.
	 * 
	 * @throws MalformedURLException
	 *             MalformedURLException.
	 * @throws IOException
	 *             IOException.
	 * @return Latest changes.
	 */
	public static String getChanges() throws MalformedURLException, IOException {
		String data = getData(DesktopSettings.UPDATE_URL);

		return data.substring(data.indexOf("[changes]") + 9, data.indexOf("[/changes]"));
	}

	private static String getData(String address) throws MalformedURLException, IOException {
		URL url = new URL(address);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
		InputStream html = connection.getInputStream();

		StringBuffer buffer = new StringBuffer("");
		int c = 0;
		while (c != -1) {
			c = html.read();
			buffer.append((char) c);
		}

		return buffer.toString();
	}

}
