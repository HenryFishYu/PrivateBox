package priv.henryyu.privatebox.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * InvitationCode class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */
@Entity
public class InvitationCode implements Serializable{
	@Id
	private String code;
	private boolean used;
	private Timestamp createTime;
	private Timestamp usedTime;
	private String createUsername;
	private String usedUsername;
	
	public InvitationCode() {
		this.createTime=new Timestamp(System.currentTimeMillis());
		this.code=UUID.randomUUID().toString();
		used=false;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}
	public Timestamp getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(Timestamp usedTime) {
		this.usedTime = usedTime;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	public String getUsedUsername() {
		return usedUsername;
	}
	public void setUsedUsername(String usedUsername) {
		this.usedUsername = usedUsername;
	}
	
	
}
