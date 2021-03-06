package priv.henryyu.privatebox.model;

import java.io.Serializable;
import java.util.UUID;

public class RegisterEmailEntity implements Serializable{
	private int totalRequestTimes;
	private String activationCode;
	private String registerUsername;
	private boolean send;
	public RegisterEmailEntity() {
		this.send=false;
		this.totalRequestTimes = 1;
		this.activationCode = UUID.randomUUID().toString();
	}
	
	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public String getRegisterUsername() {
		return registerUsername;
	}

	public void setRegisterUsername(String registerUsername) {
		this.registerUsername = registerUsername;
	}

	public int getTotalRequestTimes() {
		return totalRequestTimes;
	}
	public void setTotalRequestTimes(int totalRequestTimes) {
		this.totalRequestTimes = totalRequestTimes;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	
}
