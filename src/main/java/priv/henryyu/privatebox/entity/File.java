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
@Entity
public class File implements Serializable{

	@Id
	private String id;
	private Timestamp createTime;
	private String originalName;
	private String extension;
	private boolean deleted;
	private String username;
	private String encryptName;
	
	public File() {
		id=UUID.randomUUID().toString();
		createTime=new Timestamp(System.currentTimeMillis());
	}

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEncryptName() {
		return encryptName;
	}


	public void setEncryptName(String encryptName) {
		this.encryptName = encryptName;
	}


	public String getId() {
		return id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}
	
	
	
	
}
 

