package at.deppn.H2toGraph;

import java.nio.file.Paths;
import java.util.List;

import at.deppn.H2toGraph.DB.JsonReader;
import at.deppn.H2toGraph.DB.Select;
import at.deppn.H2toGraph.types.Config;
import at.deppn.H2toGraph.types.Point;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	JsonReader jr = new JsonReader();
		Config dbconfig = jr.readConfig(Paths.get("./vendor/config.json"));
		Select sel = new Select(dbconfig);
		
		List<Point> list = sel.getPointList();
		
		
		
    }
}
