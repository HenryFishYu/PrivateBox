package priv.henryyu.privatebox.model.request; 
/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月31日下午2:52:42
 * @version 1.0.0
 */
public class FileDownloadForm {
	private String encryptName;
	private String originalName;
	private String extension;
	public String getEncryptName() {
		return encryptName;
	}
	public void setEncryptName(String encryptName) {
		this.encryptName = encryptName;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	
}
 

