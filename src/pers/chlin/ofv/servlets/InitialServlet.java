package pers.chlin.ofv.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import pers.chlin.ofv.Res;
import pers.chlin.ofv.Utils;

/**
 * @author CHLin
 * @version 1.0
 * @since JDK 1.6
 */
public class InitialServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config)
	{
		String defaultPath = config.getInitParameter("DefaultPath");
		if (Utils.isNullOrEmpty(defaultPath)) {
			defaultPath = "D:/";
		}
		Res.setDefaultPath(defaultPath);
		Res.setShowdataURL("http://127.0.0.1:8082/OnlineFolderViewer/FolderViewerServlet");
		Res.setDownloadURL("http://127.0.0.1:8082/OnlineFolderViewer/DownloadServlet");
	}
}
