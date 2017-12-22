package priv.henryyu.privatebox.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * BaseUser class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */
@Entity
public class User implements Serializable,UserDetails{
	@Id
	private String username;
	private String password;
	private Timestamp createTime;
	@ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<Role> roles=new LinkedList<Role>();
	@OneToOne(fetch=FetchType.EAGER)
	private InvitationCode usedInvitationCode;
	@OneToMany(fetch=FetchType.EAGER)
	private Set<InvitationCode> generatedInvoInvitationCodes=new HashSet<InvitationCode>();
	
	
	public InvitationCode getUsedInvitationCode() {
		return usedInvitationCode;		
	}



	public void setUsedInvitationCode(InvitationCode usedInvitationCode) {
		this.usedInvitationCode = usedInvitationCode;
		usedInvitationCode.setUseTime(new Timestamp(System.currentTimeMillis()));
		usedInvitationCode.setUseUser(this);
		usedInvitationCode.setUsed(true);
	}



	public Set<InvitationCode> getGeneratedInvoInvitationCodes() {
		return generatedInvoInvitationCodes;
	}



	public void addGeneratedInvoInvitationCode(InvitationCode generatedInvitationCode) {
		generatedInvitationCode.setCreateUser(this);
		this.generatedInvoInvitationCodes.add(generatedInvitationCode);
	}

	public void addGeneratedInvoInvitationCodes(Set<InvitationCode> generatedInvitationCodes) {
		for(InvitationCode invitationCode:generatedInvitationCodes) {
			addGeneratedInvoInvitationCode(invitationCode);
		}
	}

	public User() {
		this.createTime=new Timestamp(System.currentTimeMillis());
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities=new LinkedList<GrantedAuthority>();
		for(Role role:roles){
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return grantedAuthorities;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
}
