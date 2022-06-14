import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Paciente extends Usuario implements Menu{
	//---Atributos------------------------------------------------------------------------------------------------------
	private List<Tratamiento> tratamientos = new ArrayList<>();
	//------------------------------------------------------------------------------------------------------------------


	//---Constructores--------------------------------------------------------------------------------------------------
	public Paciente() {
	}

	public Paciente(Enfermedad e, String profesionalEncargado, String userName, String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		tratamientos.add(new Tratamiento(profesionalEncargado, e));
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Getters y Setters----------------------------------------------------------------------------------------------
	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Metodos--------------------------------------------------------------------------------------------------------
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
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PACIENTE | " + super.toString() +
				"\nTratamientos del Paciente:\n");
		sb.append("---------------------------------------------------------------------------------------\n");
		for (int i = 0; i < tratamientos.size(); i++) {
			sb.append((i+1) + ") " + tratamientos.get(i));
		}
		return sb.toString();
	}
	//------------------------------------------------------------------------------------------------------------------
}
