package priv.henryyu.privatebox.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月12日上午9:59:04
 * @version 1.0.0
 */
@Entity
public class UniqueFile implements Serializable{

	@Id
	private String encryptName;
	private String path;
	private Timestamp createTime;
	
	public UniqueFile() {
		createTime=new Timestamp(System.currentTimeMillis());
	}

	public String getEncryptName() {
		return encryptName;
	}

	public void setEncryptName(String encryptName) {
		this.encryptName = encryptName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}
	
	
	
}
 

