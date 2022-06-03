import java.io.Serializable;

import com.google.gson.Gson;

public class SerializacionGuardado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public <T> void serializacionGuardado(String saveName, T saveData)
	{
		Gson gson = new Gson();
		String json = gson.toJson(saveData);
	}
	
}
