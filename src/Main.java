import java.util.List;

public class Main {
    public static void main(String[] args){
    	SerializacionGuardado saves = new SerializacionGuardado();
    	Usuario user = new Usuario();
    	List<Usuario> lista = saves.deserializacion("test", user);
    }
}
