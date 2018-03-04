package priv.henryyu.privatebox.siglton;

import java.util.Map;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

public enum Siglton {
	INSTANCE;
	private ExpiringMap<String, Object> registerMap = ExpiringMap.builder()
			  .variableExpiration()
			  .build();

	public Map getRegisterMap() {
		return registerMap;
	}
	
}
