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
		int choice = 0;
		for(Tratamiento t :this.getTratamientos())
		{
			for(Tarea tarea : t.getPlan().getTasks())
			{
				if(!tarea.isTaskDone()) {
					System.out.println(tarea.getTaskName());
					System.out.println("Realizo la tarea?");
					System.out.println("1. Si");
					System.out.println("2. No");
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
					if (choice == 1)
						tarea.setTaskDone(true);
					else
						tarea.setTaskDone(false);
				}
				if(tarea instanceof TareaNumerica)   ///ingreso de informacion adicional dependiendo el tipo de tarea
				{
					System.out.println("Ingrese valor numerico correspondiente a la tarea");
					((TareaNumerica) tarea).setNumero(Integer.parseInt(ScannerSingleton.getInstance().nextLine()));
				}
				else if(tarea instanceof TareaAlfanumerica)
				{
					System.out.println("Ingrese la informacion adicional requerida");
					((TareaAlfanumerica) tarea).setInfo(ScannerSingleton.getInstance().nextLine());
				}
			}
		}
	}
	

	private void notificacionesTareasSinRealizar() {
		Boolean flag = false;
		System.out.println("Tareas sin realizar : ");
		for(Tratamiento t :this.getTratamientos())
		{
			for(int i = 0 ; i < t.getPlan().getTasks().size() ; i++) {
				if(!t.getPlan().getTasks().get(i).isTaskDone())
				{
					flag = true;
					System.out.println(t.getPlan().getTasks().get(i).getTaskName());
				}
			}
		}
		if(!flag)
		{
			System.out.println("No tiene tareas sin realizar!");
		}
	}
	
	@Override
	public void menu() {
		int choice = -1;
		notificacionesTareasSinRealizar();
		do{
			try{
				System.out.println("1. Ingreso de Datos de Control.\n"
								 + "2. Mostrar Tareas\n"
						         + "0. Salir.\n");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				
				switch(choice)
				{
				case 1:
					ingresoDatosDeControl();
					break;
				case 2:
					for(Tratamiento t : this.getTratamientos())
					{
						System.out.println(t);//printea los tratamientos con sus planes y tareas
					}
					break;
				case 0: break;
				default: System.out.println("Opcion invalida");
				break;
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
