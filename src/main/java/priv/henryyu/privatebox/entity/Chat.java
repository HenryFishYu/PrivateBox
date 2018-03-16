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
 * @date 2018年3月15日下午3:43:46
 * @version 1.0.0
 */
@Entity
public class Chat implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9169116358635481889L;
	@Id
	private String uuid;
	private String ip;
	private String message;
	private String fromUser;
	private String toUser;
	private String behavior;
	private Timestamp createTime;
	
	public Chat() {
		super();
	}
	public Chat(String ip, String message, String fromUser, String toUser, String behavior) {
		this.uuid=UUID.randomUUID().toString();
		this.ip = ip;
		this.message = message;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.behavior = behavior;
		this.createTime = new Timestamp(System.currentTimeMillis());
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getBehavior() {
		return behavior;
	}
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
}
 

