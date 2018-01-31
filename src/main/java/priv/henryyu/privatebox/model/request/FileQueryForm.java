package priv.henryyu.privatebox.model.request;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Id;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月30日下午5:13:13
 * @version 1.0.0
 */
public class FileQueryForm extends PaginationForm {
	
	private Timestamp createTime;
	private String originalName;
	private String extension;
	
	

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

	public Timestamp getCreateTime() {
		return createTime;
	}
}
 

