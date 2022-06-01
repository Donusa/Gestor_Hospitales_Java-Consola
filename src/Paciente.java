import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Paciente extends Usuario implements Menu{
	
	List<Tratamiento> tratamientos = new ArrayList<>();

	public Paciente(Enfermedad e, Profesional p, String userName,
			String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		tratamientos.add(new Tratamiento(p, e));
	}

	public Paciente() {
	}

	public void ingresoDatosDeControl()
	{
		Scanner scan= new Scanner(System.in);
		System.out.println("Ingrese tu nombre:");
		this.userName = scan.nextLine();

	}

	@Override
	public void menu() {
		int choice =0;
		do {
			try
			{
				ingresoDatosDeControl();
				choice = 1;
			}catch (InputMismatchException e){
				System.out.println("Ingrese un dato valido");
			}
		}while (choice !=0);
	}
}
