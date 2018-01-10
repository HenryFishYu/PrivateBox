package priv.henryyu.privatebox.model.request;

import java.sql.Timestamp;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import priv.henryyu.privatebox.entity.User;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月8日下午3:35:53
 * @version 1.0.0
 */
public class InvitationCodeQueryForm extends PaginationForm{
	private String code;
	private Boolean used;
	private Timestamp createTime;
	private Timestamp usedTime;
	private User createUser;
	private User usedUser;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getUsed() {
		return used;
	}
	public void setUsed(Boolean used) {
		this.used = used;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(Timestamp usedTime) {
		this.usedTime = usedTime;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public User getUsedUser() {
		return usedUser;
	}
	public void setUsedUser(User usedUser) {
		this.usedUser = usedUser;
	}
	
}
 

