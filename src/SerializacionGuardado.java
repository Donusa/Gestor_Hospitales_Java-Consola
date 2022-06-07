import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

public class SerializacionGuardado implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	public <T> void serializacion(String saveName, List<T> dataSave)
	{
		try {
			
			//Writer myWriter = Files.newBufferedWriter(Paths.get("src/"+saveName+".json"));
			
			Gson gson = new Gson();
			gson.toJson(dataSave, new FileWriter("src/"+saveName+".json"));
			
			
		}
		catch(IOException e)
		{
			e.getMessage();
		}
	}
	
	public <T> List<T> deserializacion(String saveName, T dataType)
	{
		List<T> data = new ArrayList<T>();
		try {
			Type listType = TypeToken.getParameterized(ArrayList.class, dataType.getClass()).getType();	
			data = new Gson().fromJson(new FileReader("src/"+saveName+".json"), listType);
		}
		catch(FileNotFoundException e)
		{
			e.getMessage();
		}
		catch(ClassCastException e)
		{
			e.getMessage();
		}
		return data;
	}
}
