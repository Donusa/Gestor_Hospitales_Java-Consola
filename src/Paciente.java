import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Paciente extends Usuario implements Menu{

	private List<Tratamiento> tratamientos;

	public Paciente() {
		tratamientos = new ArrayList<>();
	}

	public Paciente(Enfermedad e, Profesional p, String userName,
			String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		tratamientos.add(new Tratamiento(p.getUserName(), e));
	}

	public void ingresoDatosDeControl()
	{
		Scanner scan= new Scanner(System.in);
		crearUser(this);
		System.out.println("Ingrese la enfermedad:");
		this.tratamientos.get(0).plan.enfermedad.setName(scan.nextLine());    //en el get el "0" habria que cambiarlo
		scan.close();
	}

	@Override
	public void menu() {
		int choice = 0;
		Scanner scan =  new Scanner(System.in);

		do{
			try{
				System.out.println("1. Ingreso de Datos de Control.\n"
						         + "0. Salir.\n");
				choice = scan.nextInt();
				if(choice == 1){
					ingresoDatosDeControl();
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);
		scan.close();
	}

	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}
	
}
