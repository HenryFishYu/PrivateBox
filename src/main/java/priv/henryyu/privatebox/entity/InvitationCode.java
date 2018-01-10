package priv.henryyu.privatebox.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	private Boolean used;
	private Timestamp createTime;
	private Timestamp usedTime;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private User createUser;
	@OneToOne(fetch=FetchType.EAGER)
	private User usedUser;
	
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
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public Boolean getUsed() {
		return used;
	}
	public void setUsed(Boolean used) {
		this.used = used;
	}
	public Timestamp getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(Timestamp usedTime) {
		this.usedTime = usedTime;
	}
	public User getUsedUser() {
		return usedUser;
	}
	public void setUsedUser(User usedUser) {
		this.usedUser = usedUser;
	}
	
}
