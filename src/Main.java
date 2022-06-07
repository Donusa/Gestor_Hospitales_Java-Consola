import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
    	SerializacionGuardado saves = new SerializacionGuardado();
    	Usuario user = new Usuario();
    	List<Usuario> lista = (ArrayList<Usuario>) saves.deserializacion("test", user);
    	System.out.println(lista.get(0).getUserName());
    	Usuario user2 = new Usuario("Jorge","email@email.com","pass1234","42069666","2230000000");
    	lista.add(user2);
    	System.out.println(lista);
    	/*saves.serializacion("test", lista);
    	System.out.println(saves.deserializacion("test", user));*/
    }
}
