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
		System.out.println("Ingrese e-mail del nuevo usuario");
		u.setEmail(scan.nextLine());
		System.out.println("Ingrese nombre del nuevo usuario");
		u.setUserName(scan.nextLine());
		System.out.println("Ingrese contraseña del nuevo usuario");
		u.setPassword(scan.nextLine());
		System.out.println("Ingrese celular del nuevo usuario");
		u.setUserCel(scan.nextLine());
		System.out.println("Ingrese DNI del nuevo usuario");
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
}
