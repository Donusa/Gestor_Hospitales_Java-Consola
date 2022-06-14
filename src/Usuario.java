public class Usuario {
	//---Atributos------------------------------------------------------------------------------------------------------
	protected String userName;
	protected String email;
	protected String password;
	protected String userDni;
	protected String userCel;
	//------------------------------------------------------------------------------------------------------------------


	//---Constructores--------------------------------------------------------------------------------------------------
	public Usuario() {
	}

	public Usuario(String userName, String email, String password, String userDni, String userCel) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.userDni = userDni;
		this.userCel = userCel;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Getters y Setters----------------------------------------------------------------------------------------------
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserDni() {
		return userDni;
	}

	public void setUserDni(String userDni) {
		this.userDni = userDni;
	}

	public String getUserCel() {
		return userCel;
	}

	public void setUserCel(String userCel) {
		this.userCel = userCel;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Metodos--------------------------------------------------------------------------------------------------------
	public static void crearUser(Usuario u)
	{
		System.out.println("Ingrese el e-mail:");
		u.setEmail(ScannerSingleton.getInstance().nextLine());
		System.out.println("Ingrese el nombre:");
		u.setUserName(ScannerSingleton.getInstance().nextLine());
		System.out.println("Ingrese la contraseña:");
		u.setPassword(ScannerSingleton.getInstance().nextLine());
		System.out.println("Ingrese el celular:");
		u.setUserCel(ScannerSingleton.getInstance().nextLine());
		System.out.println("Ingrese el DNI:");
		u.setUserDni(ScannerSingleton.getInstance().nextLine());
		
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "Nombre: " + userName + " | DNI: " + userDni +
				"\nEmail: " + email +
				"\nCelular: " + userCel +
				"\nContraseña: " + password;
	}
	//------------------------------------------------------------------------------------------------------------------
}
