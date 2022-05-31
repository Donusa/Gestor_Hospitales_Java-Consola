import java.util.ArrayList;
import java.util.List;

public class Profesional extends Usuario implements CrearPlan{
	
	List<Paciente> pacientes = new ArrayList<>();
	
	public Profesional(String userName ,String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		
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

	@Override
	public void crearNuevoPlan() {
		// TODO Auto-generated method stub
		
	}
	
}
