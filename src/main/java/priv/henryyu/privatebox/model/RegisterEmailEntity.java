package priv.henryyu.privatebox.model;

import java.util.UUID;

public class RegisterEmailEntity {
	private long lastRequestTimeMillis;
	private int totalRequestTimes;
	private String activationCode;
	public RegisterEmailEntity() {
		this.lastRequestTimeMillis = System.currentTimeMillis();
		this.totalRequestTimes = 1;
		this.activationCode = UUID.randomUUID().toString();
	}
	public long getLastRequestTimeMillis() {
		return lastRequestTimeMillis;
	}
	public void setLastRequestTimeMillis(long lastRequestTimeMillis) {
		this.lastRequestTimeMillis = lastRequestTimeMillis;
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
