package priv.henryyu.privatebox.tools;

import priv.henryyu.privatebox.entity.File;
import priv.henryyu.privatebox.entity.UniqueFile;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月30日上午11:06:00
 * @version 1.0.0
 */
public class FileUtil {
	public static File getFileByUniqueFileAndOriginalFilename(UniqueFile uniqueFile,String originalFilename,Long size,String username) {
		File file=new File();
		file.setDeleted(false);
		file.setEncryptName(uniqueFile.getEncryptName());
		file.setUsername(username);
		file.setSize(size);
		int pointPosition=originalFilename.lastIndexOf('.');
		if(pointPosition<0) {
			file.setOriginalName(originalFilename);
			file.setExtension("");
			return file;
		}
		
		file.setOriginalName(originalFilename.substring(0, pointPosition-1));
		file.setExtension(originalFilename.substring(pointPosition, originalFilename.length()));
		return file;
	}
}
 

