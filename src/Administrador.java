import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Administrador extends Usuario implements CrearPlan, Menu{

	public Administrador() {
	}

	public Administrador(String userName , String email, String password, String userDni, String userCel) {
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
		int choice = 0;
		Scanner scan =  new Scanner(System.in);
		Tarea tarea = null;

		do{
			try{
				System.out.println("1. Agregar tarea.\n"
								 + "2. Modificar tarea.\n"
								 + "3. Eliminar tarea.\n"
								 + "4. Ver lista de tareas.\n"
								 + "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice){
					case 1:	tarea = nuevaTarea();
							break;
					case 2:	tarea = modificarTarea(tarea);
							break;
					case 3:	eliminarTarea();
							break;
					case 4: 
							break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);
		scan.close();
	}
	
	public List<Tarea> verListaTareas()
	{
		//llamar merge de lista de sistema para unir las listas y printear la salida
		return null;
	}
	
	public void eliminarTarea()
	{
		//llamar lista de tareas de sistema
		
		//borrar del archivo
		Sistema.users.remove(0);//El 0 deberia ser cambiado por la forma de busqueda del usuario . 
		//Pasar por parametro el usuario? o solo dni?
	}
	
	public Tarea modificarTarea(Tarea tareaAModificar)
	{
		System.out.println("Nombre : "+tareaAModificar.getTaskName());
		if(tareaAModificar instanceof TareaAlfanumerica)
		{
			System.out.println(((TareaAlfanumerica) tareaAModificar).getInfo());
		}
		if(tareaAModificar instanceof TareaSiNo)
		{
			System.out.println(((TareaSiNo) tareaAModificar).getDecision());
		}
		if(tareaAModificar instanceof TareaNumerica)
		{
			System.out.println(((TareaNumerica) tareaAModificar).getNumero());
		}
		return tareaAModificar;
	}
	
	public Tarea nuevaTarea()
	{
		int choice = 0;
		String taskName = "";
		Tarea nuevaTarea = null;
		Scanner scan = new Scanner(System.in);
		do{
			try{
				System.out.println("1. Tarea de Si/No\n"
								 + "2. Tarea con respuesta Alfanumerica.\n"
								 + "3. Tarea con respuesta Numerica\n"
								 + "4. Tarea simple de solo Check");
				choice = scan.nextInt();
				switch (choice){
					case 1:
						nuevaTarea = new TareaSiNo(taskName);
						break;
					case 2:	
						nuevaTarea = new TareaAlfanumerica(taskName);
						break;
					case 3:
						nuevaTarea = new TareaNumerica(taskName);
						break;
					case 4:
						nuevaTarea = new Tarea(taskName);
						break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (nuevaTarea == null);
		scan.close();
		//agregar tarea al archivo
		return nuevaTarea;
	}


	@Override
	public Plan crearNuevoPlan(Enfermedad e) {          /// Falta que agregue el nuevo plan al archivo de planes
		Scanner scan= new Scanner(System.in);
		Plan p= new Plan(e);
		System.out.println("Ingrese la duracion del Plan:");
		p.setDuracion(scan.nextInt());
		int rta =2;
		do {
			try {
				p.agregarTarea();
				System.out.println("Desea agregar otra tarea?\n"+
						"1. Si\n"+
						"2. No");
				rta=scan.nextInt();

			}catch (InputMismatchException exception){
				System.out.println("Ingrese una opcion valida.\n");
			}
		}while (rta == 1);
		scan.close();
		return p;
	}

	@Override
	public Plan modificarPlan(Plan p){             /// Falta que modifique el plan en el archivo de planes
		int choice = 0;
		Scanner scan =  new Scanner(System.in);

		do{
			try{
				System.out.println("1. Modificar duracion.\n"
						+ "2. Agregar tarea.\n"
						+ "3. Borrar tarea.\n"
						+ "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice) {
					case 1 :
						System.out.println("Ingrese la nueva duracion:");
						p.setDuracion(scan.nextInt());
						break;
					case 2 :
						p.agregarTarea();
						break;
					case 3 :
						p.mostrarTareas();
						System.out.println("Ingrese el numero de tarea que quieras eliminar");
						p.tasks.remove(scan.nextInt()-1);
						break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);

		scan.close();
		return p;
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
