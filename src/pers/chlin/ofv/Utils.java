package pers.chlin.ofv;

import java.text.NumberFormat;

/**
 * @author CHLin
 * @version 1.0
 * @since JDK 1.6
 */
public class Utils {
	
	/**
	 * 檢查字串是否為null或空值
	 * @param str
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String str)
	{
		return str == null || str.trim().isEmpty();
	}
	
	/**
	 * 將fileLength轉為人眼可辨識的格式 , ex:1GB or 1MB etc..
	 * @param fileLength
	 * @return String
	 */
	public static String getFileSize(long fileLength)
	{
		long kb = 1024;
		long mb = kb*1024;
		long gb = mb*1024;
		float leftSize = 0;
		float retSize;
		
		NumberFormat nf = NumberFormat.getInstance();
		if (fileLength < kb) {
			return 1 + " KB";
		} else if(fileLength > kb && fileLength < mb) {
			nf.setMaximumFractionDigits(0);
			retSize = fileLength / kb;
			leftSize = fileLength % kb;
			if (leftSize > 0) {
				retSize += 1;
			}
			return nf.format(retSize) + " KB";
		} else if(fileLength > mb && fileLength < gb) {
			nf.setMaximumFractionDigits(1);
			
			retSize = fileLength / mb;
			leftSize = (float) (fileLength % mb) / mb;
			retSize = retSize + Float.valueOf(nf.format(leftSize));
			return retSize + " MB";
		} else {
			nf.setMaximumFractionDigits(1);
			
			retSize = fileLength / gb;
			leftSize = ( float ) (fileLength % mb) / mb;
			retSize = retSize + Float.valueOf(nf.format(leftSize));
			return retSize + " GB";
		}
	}
}
