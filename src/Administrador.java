import java.util.InputMismatchException;
import java.util.Scanner;

public class Administrador extends Usuario implements CrearPlan, Menu{
	

	
	public Administrador(String userName ,String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		
	}

	
	public Paciente ingresoPacientes()
	{
		Paciente p = new Paciente();
		Usuario.crearUser(p);
		return p;
	}
	
	public Profesional ingresoProfesionales()
	{
		Profesional p = new Profesional();
		Usuario.crearUser(p);
		return p;
	}
	
	public void administracionEnfermedades()
	{
		
	}
	
	public void administracionTareasDeControl()
	{
		
	}



	@Override
	public void crearNuevoPlan() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void menu() {
		int choice = 0;
		Scanner scan =  new Scanner(System.in);

		do{
			try{
				System.out.println("1. Ingreso de Pacientes.\n"
								 + "2. Ingreso de Profesionales.\n"
								 + "3. Administracion de Enfermedades.\n"
								 + "4. Administracion de Tareas de Control.\n"
								 + "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice){
					case 1:	ingresoPacientes();
							break;
					case 2:	ingresoProfesionales();
							break;
					case 3:	administracionEnfermedades();
							break;
					case 4: administracionTareasDeControl();
							break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);
		scan.close();
	}
}
