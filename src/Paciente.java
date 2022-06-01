import java.util.ArrayList;
import java.util.List;

public class Paciente extends Usuario implements Menu{
	
	private List<Tratamiento> tratamientos = new ArrayList<>();

	public Paciente(Enfermedad e, Profesional p, String userName,
			String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		tratamientos.add(new Tratamiento(p, e));
	}
	
	public void ingresoDatosDeControl()
	{
		
	}

	@Override
	public void menu() {
		// TODO Auto-generated method stub
		
	}
	
}
