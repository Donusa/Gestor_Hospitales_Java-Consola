import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class SerializacionGuardado implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	public <T> void serializacion(String saveName, List<T> dataSave)
	{
		try {
			
			Writer myWriter = Files.newBufferedWriter(Paths.get("src/"+saveName+".json"));
			
			Gson gson = new Gson();
			gson.toJson(dataSave, myWriter);
			
			
		}
		catch(IOException e)
		{
			e.getMessage();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> deserializacion(String saveName, T dataType)
	{
		List<T> data = new ArrayList<T>();
		try {
			
			Reader myReader = Files.newBufferedReader(Paths.get("src/"+saveName+".json"));
			Gson gson = new Gson();
			
			data.add((T)gson.fromJson(myReader,Object.class));
			
		}
		catch(FileNotFoundException e)
		{
			e.getMessage();
		}
		catch(ClassCastException e)
		{
			e.getMessage();
		}
		catch(IOException e)
		{
			e.getMessage();
		}
		return data;
	}
	
}
