import java.util.ArrayList;
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
					case 4: System.out.println(verListaTareas());
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
		List<Tarea> l = new ArrayList<>();
		Sistema.mergeListas(SerializacionGuardado.deserializacion(nombreArchivos.TAREASBASICAS.getName(), new Tarea()), l);
		Sistema.mergeListas(SerializacionGuardado.deserializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), new Tarea()), l);
		Sistema.mergeListas(SerializacionGuardado.deserializacion(nombreArchivos.TAREASNUMERICAS.getName(), new Tarea()), l);
		Sistema.mergeListas(SerializacionGuardado.deserializacion(nombreArchivos.TAREASSINO.getName(), new Tarea()), l);
		return l;
	}
	
	public void eliminarTarea()
	{
		List<Tarea> l = verListaTareas();
		List<TareaSiNo> saveSiNo = new ArrayList<>();
		List<TareaAlfanumerica> saveAlfanumerica = new ArrayList<>();
		List<TareaNumerica> saveNumerica = new ArrayList<>();
		Tarea aux = new Tarea();
		Scanner scan = new Scanner(System.in);
		int choice = -1, remover;
		for(int i = 0 ; i < l.size() ; i++)
		{
			System.out.println((i+1)+"."+l.get(i).getTaskName());
		}
		do
		{
			try
			{
				choice = scan.nextInt();
				
			}
			catch (InputMismatchException e) {
				System.out.println("Solo valores numericos");
			}	
		}while(choice>= l.size() && choice>0);
		aux = l.get(choice-1);
		l.remove(choice-1);
		for(Tarea t: l)
		{
			remover = l.indexOf(t);
			if(t instanceof TareaSiNo)
			{
				saveSiNo.add((TareaSiNo) t);
				l.remove(remover);
			}
			else if(t instanceof TareaAlfanumerica)
			{
				saveAlfanumerica.add((TareaAlfanumerica) t);
				l.remove(remover);
			}
			else if(t instanceof TareaNumerica)
			{
				saveNumerica.add((TareaNumerica) t);
				l.remove(remover);
			}
		}
		SerializacionGuardado.serializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), saveAlfanumerica);
		SerializacionGuardado.serializacion(nombreArchivos.TAREASNUMERICAS.getName(), saveNumerica);
		SerializacionGuardado.serializacion(nombreArchivos.TAREASSINO.getName(), saveSiNo);
		SerializacionGuardado.serializacion(nombreArchivos.TAREASBASICAS.getName(), l);
		scan.close();
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
						p.getTasks().remove(scan.nextInt()-1);
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

	@Override
	public String toString() {
		return "ADMINISTRADOR | " + super.toString();
	}
}
