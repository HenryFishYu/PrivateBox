package priv.henryyu.privatebox.model.request; 
/**
 * Role class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */
public class RegisterUser {
	private String username;
	private String sha512Password;
	private String invitationCode;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSha512Password() {
		return sha512Password;
	}
	public void setSha512Password(String sha512Password) {
		this.sha512Password = sha512Password;
	}
	public String getInvitationCode() {
		return invitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	
	
}
 

