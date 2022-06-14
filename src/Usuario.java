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
	public static void crearUser(Usuario u) {
		String email;
		String dni;

		System.out.println("Ingrese el DNI:");
		dni = ScannerSingleton.getInstance().nextLine();
		if(!isDNIUsed(dni)){
			u.setUserDni(dni);

			System.out.println("Ingrese el nombre:");
			u.setUserName(ScannerSingleton.getInstance().nextLine());

			System.out.println("Ingrese el e-mail:");
			email = ScannerSingleton.getInstance().nextLine();
			while (isEmailUsed(email)){
				System.out.println("El e-mail ingresado ya esta en uso. Ingrese otro e-mail:");
				email = ScannerSingleton.getInstance().nextLine();
			}
			u.setEmail(email);

			System.out.println("Ingrese el celular:");
			u.setUserCel(ScannerSingleton.getInstance().nextLine());

			System.out.println("Ingrese la contraseña:");
			u.setPassword(ScannerSingleton.getInstance().nextLine());
		}
		else{
			System.out.println("Error: El usuario DNI " + dni + " ya existe.\n");
			u=null;
		}
	}

	private static boolean isEmailUsed(String email){
		if(!Sistema.users.isEmpty()) {
			int i = 0;
			while (i < Sistema.users.size() && !email.equals(Sistema.users.get(i).getEmail())) {
				i++;
			}
			if (i < Sistema.users.size()) {
				return true;
			} else {
				return false;
			}
		}
		else{
			return false;
		}
	}

	private static boolean isDNIUsed(String dni){
		int i = 0;
		while(i < Sistema.users.size() && !dni.equals(Sistema.users.get(i).getUserDni())){
			i++;
		}
		if(i < Sistema.users.size()){
			return true;
		}
		else{
			return false;
		}
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "Nombre: " + userName + " | DNI: " + userDni +
				"\nEmail: " + email + " | Celular: " + userCel + " | Contraseña: " + password;
	}
	//------------------------------------------------------------------------------------------------------------------
}
