import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Paciente extends Usuario implements Menu{

	private List<Tratamiento> tratamientos = new ArrayList<>();

	public Paciente() {
	}

	public Paciente(Enfermedad e, String profesionalEncargado, String userName, String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		tratamientos.add(new Tratamiento(profesionalEncargado, e));
	}

	public void ingresoDatosDeControl()
	{
		crearUser(this);
		System.out.println("Ingrese la enfermedad:");
		this.tratamientos.get(0).getPlan().getEnfermedad().setName(ScannerSingleton.getInstance().nextLine());    //en el get el "0" habría que cambiarlo
		
	}

	@Override
	public void menu() {
		int choice = 0;

		do{
			try{
				System.out.println("1. Ingreso de Datos de Control.\n"
						         + "0. Salir.\n");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				if(choice == 1){
					ingresoDatosDeControl();
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);
		
	}

	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	@Override
	public String toString() {
		return "PACIENTE | " + super.toString() +
				"\nTratamientos del Paciente:\n" + tratamientos +
				'}';
	}
}
