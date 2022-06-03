import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class SerializacionGuardado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public <T> void serializacion(String saveName, T saveData)
	{
		
	}
	
	public <T> List<T> deserializacion(String saveName, T saveData)
	{
		List<T> data = new ArrayList<T>();
		try {
			Reader myReader = Files.newBufferedReader(Paths.get("src/"+saveName+".json"));
			Gson gson = new Gson();
			data.add((T)gson.fromJson(myReader,Object.class));
			System.out.println(data);
		}
		catch(ClassCastException e)
		{
			System.out.println("cast error");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
}
