package at.deppn.H2toGraph.DB;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

import at.deppn.H2toGraph.types.Config;


public class JsonReader {
	public Config readConfig(Path path) {
		Gson gson = new Gson();
		Reader reader;
		try {
			reader = Files.newBufferedReader(path);
			
			Config config = gson.fromJson(reader, Config.class);
			
			return config;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
