package priv.henryyu.privatebox.siglton;

import java.util.Map;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import priv.henryyu.privatebox.model.RegisterEmailEntity;

public enum Siglton {
	INSTANCE;
	private ExpiringMap<String, RegisterEmailEntity> registerMap = ExpiringMap.builder()
			  .variableExpiration()
			  .build();

	public ExpiringMap<String, RegisterEmailEntity> getRegisterExpiringMap() {
		return registerMap;
	}
	
}
