package pers.chlin.ofv.servlets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.chlin.ofv.FileInfo;
import pers.chlin.ofv.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author CHLin
 * @version 1.0
 * @since JDK 1.6
 */
@WebServlet("/FolderViewerServlet")
public class FolderViewerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FolderViewerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException 
	{
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException 
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		String targetPath = request.getParameter("targetPath");
		
		if (Utils.isNullOrEmpty(targetPath)) {
			returnFailData(response, "路徑為空值");
			return;
		}
		
		File targetFolder = new File(targetPath);
		if (!targetFolder.exists()) {
			this.returnFailData(response, "目標目錄不存在");
			return;
		}
		
		if (!targetFolder.isDirectory()) {
			this.returnFailData(response, "目標非目錄");
			return;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		File[] children = targetFolder.listFiles();
		List<FileInfo> fileInfos = new ArrayList<FileInfo>(children == null ? 0 : children.length);
		for (File child : children) {
			fileInfos.add(new FileInfo(child.getAbsolutePath(), child.getName(),
									   dateFormat.format(child.lastModified()),
									   child.isDirectory(), 
									   child.length()));
		}
		
		this.returnSuccessData(response, fileInfos);
	}
	
	private void returnFailData(HttpServletResponse response, String reason) 
	throws IOException
	{
		Map<String, Object> dataMap = new HashMap<String, Object>(2);
		dataMap.put("RESULT", "FAIL");
		dataMap.put("REASON", reason);
		this.returnData(response, dataMap);
	}
	
	private void returnSuccessData(HttpServletResponse response, Object data) 
	throws IOException
	{
		Map<String, Object> dataMap = new HashMap<String, Object>(2);
		dataMap.put("RESULT", "SUCCESS");
		dataMap.put("DATA", data);
		this.returnData(response, dataMap);
	}
	
	private void returnData(HttpServletResponse response, Map<String, Object> dataMap) 
	throws IOException
	{
		try {
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().print(mapper.writeValueAsString(dataMap));
		} catch(Exception e) {
			response.getWriter().print("ERROR->轉換JSON格式時發生錯誤。" + e.getMessage());
		} finally {
			if (dataMap != null) {
				dataMap.clear();
			}
		}
	}

}
