
public class Usuario {
	
	
	protected String userName;
	protected String email;
	protected String password;
	protected String userDni;
	protected String userCel;

	public Usuario() {
	}

	public Usuario(String userName, String email, String password, String userDni, String userCel) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.userDni = userDni;
		this.userCel = userCel;
	}
	
}
