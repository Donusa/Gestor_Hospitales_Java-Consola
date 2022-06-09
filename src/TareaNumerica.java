
public class TareaNumerica extends Tarea{
	
	private int numero;

	public TareaNumerica() {
	}

	public TareaNumerica(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return super.toString() +
				"Numero: " + numero;
	}
	
}
