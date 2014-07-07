package pers.chlin.ofv.servlets;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author CHLin
 * @version 1.0
 * @since JDK 1.6
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException 
	{
		response.setContentType("text/json; charset=UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String,String> retData = new HashMap<String,String>();

		boolean _done = false;
		FileInputStream fis = null;
		try {
			File targetLog = null;
			String targetPath = URLDecoder.decode(request.getParameter("TargetPath"),"UTF-8");
			if (targetPath != null && targetPath.length() > 0) {
				targetLog = new File(targetPath);
			} else {
				retData.put("RESULT", "FAIL");
				retData.put("REASON", "指定的目標路徑為空值");
			}

			if (targetLog != null && targetLog.exists()) {
				int length = 0;
				ServletOutputStream outStream = response.getOutputStream();
				ServletContext context  = getServletConfig().getServletContext();
				String mimetype = context.getMimeType(targetLog.getAbsolutePath());
				response.setContentType(mimetype);
				response.setContentLength((int) targetLog.length());
				response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(targetLog.getName(), "UTF-8") + "\"");

				byte[] _byte = new byte[102400];
				fis = new FileInputStream(targetLog);
				DataInputStream in = new DataInputStream(fis);

				while ((in != null) && ((length = in.read(_byte)) != -1 )) {
					outStream.write(_byte, 0, length);
				}

				in.close();
				outStream.close();
				_done = true;
			} else {
				retData.put("RESULT", "FAIL");
				retData.put("REASON", "目標檔案不存在");
			}


		} catch(Exception e) {
			retData.put("RESULT", "FAIL");
			retData.put("REASON", e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch(Exception e) {
					// ignore
				}
			}
		}

		if (!_done) {
			String jsonStr = "";
			try {
				jsonStr = mapper.writeValueAsString(retData);
				response.getWriter().print(jsonStr);
			} catch(Exception e) {
				response.getWriter().print("ERROR->轉換JSON格式時發生錯誤。" + e.getMessage());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException 
	{
		
	}

}
