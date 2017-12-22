package priv.henryyu.privatebox.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	private Timestamp useTime;
	@ManyToOne
	private User createUser;
	@OneToOne
	private User useUser;
	
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
	public Timestamp getUseTime() {
		return useTime;
	}
	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public User getUseUser() {
		return useUser;
	}
	public void setUseUser(User useUser) {
		this.useUser = useUser;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	
}
