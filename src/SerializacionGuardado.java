import java.io.BufferedWriter;
import java.io.File;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class SerializacionGuardado implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	public static <T> void serializacion(String saveName, List<T> dataSave)
	{
		try {
			File file = new File("src/"+saveName+".json");
			if(!file.exists())
			{
				try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){}
				catch(IOException e)
				{
					e.getMessage();
				}
			}
			JsonParser parser = new JsonParser();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			String json = gson.toJson(dataSave);;
			JsonElement el = parser.parse(json);
			json = gson.toJson(el);
			FileWriter f = new FileWriter(file);
			f.write(json);
			f.close();
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}
	}
	
	public static <T> List<T> deserializacion(String saveName, T dataType)
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
