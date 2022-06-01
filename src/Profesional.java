import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Profesional extends Usuario implements CrearPlan, Menu{
	
	private List<Paciente> pacientes = new ArrayList<>();

	public Profesional() {
	}

	public Profesional(String userName , String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		
	}

	@Override
	public void menu() {
		int choice = 0;
		Scanner scan =  new Scanner(System.in);

		do{
			try{
				System.out.println("1. Asignacion de Planes de Control.\n"
								 + "2. Control de los Registros de los Pacientes.\n"
								 + "3. Finalizacion de Planes de Control.\n"
								 + "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice){
					case 1:	asignacionPlanesDeControl();
							break;
					case 2:	controlRegistrosDePacientes();
							break;
					case 3:	finalizacionPlanesDeControl();
							break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);
	}

	public void asignacionPlanesDeControl()
	{
		
	}
	
	public void controlRegistrosDePacientes()
	{
		
	}
	
	public void finalizacionPlanesDeControl()
	{
		
	}
	
	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	@Override
	public void crearNuevoPlan() {
		// TODO Auto-generated method stub
		
	}
	
}
