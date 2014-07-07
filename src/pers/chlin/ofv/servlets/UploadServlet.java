package pers.chlin.ofv.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import pers.chlin.ofv.FileInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author CHLin
 * @version 1.0
 * @since JDK 1.6
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
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
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			
			String path = URLDecoder.decode(request.getParameter("Path"), "UTF-8");
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8"); 
			
			try {
				List<FileItem> items = upload.parseRequest(request);
				List<FileInfo> infos = new ArrayList<FileInfo>(items.size());
				for (FileItem item : items) {

					if (item.isFormField()) {
						// 其他欄位
					} else {
						
						File uploadFolder = new File(path);
						File uploadFile = new File(uploadFolder, item.getName());
						
						BufferedInputStream bis = null;
						FileOutputStream fos = null;
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						try {
							bis = new BufferedInputStream(item.getInputStream());
							fos = new FileOutputStream(uploadFile);
							byte[] myByte = new byte[102400];
							int read = 0;
							while ((read = bis.read(myByte)) != -1) {
								fos.write(myByte, 0, read);
							}
							
							infos.add(new FileInfo(uploadFile.getAbsolutePath(), uploadFile.getName(),
												   dateFormat.format(uploadFile.lastModified()),
												   uploadFile.isDirectory(), 
												   uploadFile.length()));
						} finally {
							if (bis != null) {
								try {
									bis.close();
								} catch (Exception e) {
									//ignore
								}
							}
							
							if (fos != null) {
								try {
									fos.close();
								} catch (Exception e) {
									//ignore
								}
							}
						}

					}
				}
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/json; charset=UTF-8");
				 
		        ObjectMapper mapper = new ObjectMapper();
		 
		        mapper.writeValue(response.getOutputStream(), infos);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}
	}

}
