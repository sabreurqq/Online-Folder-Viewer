package pers.chlin.ofv;

/**
 * @author CHLin
 * @version 1.0
 * @since JDK 1.6
 */
public class FileInfo {

	/** 檔案路徑 **/
	public String path;
	/** 檔案名稱 **/
	public String name;
	/** 檔案最後修改日期 **/
	public String lastModified;
	/** 檔案大小(顯示用 ex: 1GB, 256MB..etc) **/
	public String size;
	/** 真實檔案大小 **/
	public long realSize;
	/** 是否為目錄 **/
	public boolean isDir;

	public FileInfo(String path, String name, String lastModified, boolean isDir, long size)
	{
		this.path = path;
		this.name = name;
		this.lastModified = lastModified;
		this.realSize = size;
		this.size = Utils.getFileSize(size);
		this.isDir = isDir;
	}
}
