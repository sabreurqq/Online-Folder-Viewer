package pers.chlin.ofv;

/**
 * @author CHLin
 * @version 1.0
 * @since JDK 1.6
 */
public class Res {

	private static String defaultPath;
	
	private static String DownloadURL;
	
	private static String ShowdataURL;

	public static String getDefaultPath() {
		return defaultPath;
	}

	public static void setDefaultPath(String defaultPath) {
		Res.defaultPath = defaultPath;
	}

	public static String getDownloadURL() {
		return DownloadURL;
	}

	public static void setDownloadURL(String downloadURL) {
		DownloadURL = downloadURL;
	}

	public static String getShowdataURL() {
		return ShowdataURL;
	}

	public static void setShowdataURL(String showdataURL) {
		ShowdataURL = showdataURL;
	}
	
}
