package priv.henryyu.privatebox.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月10日下午3:49:57
 * @version 1.0.0
 */
import javax.persistence.ManyToOne;
@Entity
public class File implements Serializable{

	@Id
	private String id;
	private Timestamp createTime;
	private String originalName;
	private String fileType;
	private Boolean deleted;
	@ManyToOne
	private User user;
	@ManyToOne
	private UniqueFile uniqueFile;
	
	public File() {
		id=UUID.randomUUID().toString();
		createTime=new Timestamp(System.currentTimeMillis());
	}

	
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}


	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UniqueFile getUniqueFile() {
		return uniqueFile;
	}

	public void setUniqueFile(UniqueFile uniqueFile) {
		this.uniqueFile = uniqueFile;
	}

	public String getId() {
		return id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}
	
	
	
	
}
 

