package priv.henryyu.privatebox.siglton;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
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
	public ExpiringMap<String, RegisterEmailEntity> getRegisterExpiringMap() {
		return registerMap;
	}
	public ExpiringMap<String, Integer> getIpMap() {
		return ipMap;
	}
	
}
