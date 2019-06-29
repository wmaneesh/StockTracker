import org.json.*;

public class readingJSON {

  public static void main(String[] arg){

System.out.println("Reading JSON Testing Complier");

    JSONParser parser = new JSONParser();

      Object obj = new parser.parse(new FileReader("myJSON.json"));

      JSONObject jsonObject = (JSONObject) obj;


  }
}
