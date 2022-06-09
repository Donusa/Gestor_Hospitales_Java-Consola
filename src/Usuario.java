import java.util.Scanner;

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
	
	public static void crearUser(Usuario u)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Ingrese el e-mail:");
		u.setEmail(scan.nextLine());
		System.out.println("Ingrese el nombre:");
		u.setUserName(scan.nextLine());
		System.out.println("Ingrese la contraseña:");
		u.setPassword(scan.nextLine());
		System.out.println("Ingrese el celular:");
		u.setUserCel(scan.nextLine());
		System.out.println("Ingrese el DNI:");
		u.setUserDni(scan.nextLine());
		scan.close();
	}

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

	@Override
	public String toString() {
		return "userName=" + userName + ", email=" + email + ", password=" + password + ", userDni=" + userDni
				+ ", userCel=" + userCel + "\n";
	}
	
	
	
}
