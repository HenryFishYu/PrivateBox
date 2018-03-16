package priv.henryyu.privatebox.siglton;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.entity.User;
import priv.henryyu.privatebox.model.RegisterEmailEntity;

public enum Siglton {
	INSTANCE;
	private ExpiringMap<String, RegisterEmailEntity> registerMap = ExpiringMap.builder()
			  .variableExpiration()
			  .build();
	private ExpiringMap<String, Integer> ipMap = ExpiringMap.builder()
			  .expiration(1, TimeUnit.MINUTES)
			  .expirationPolicy(ExpirationPolicy.CREATED)
			  .build();
	private ConcurrentMap<String, InvitationCode> invitationCodeMap=new ConcurrentHashMap<String, InvitationCode>();
	private ConcurrentMap<String, User> userUUIDMap=new ConcurrentHashMap<String, User>();
	public ExpiringMap<String, RegisterEmailEntity> getRegisterMap() {
		return registerMap;
	}
	
	public ConcurrentMap<String, InvitationCode> getInvitationCodeMap() {
		return invitationCodeMap;
	}
	
	public ConcurrentMap<String, User> getUserUUIDMapMap() {
		return userUUIDMap;
	}

	public ExpiringMap<String, RegisterEmailEntity> getRegisterExpiringMap() {
		return registerMap;
	}
	public ExpiringMap<String, Integer> getIpMap() {
		return ipMap;
	}
	
}
