package httpRequestJSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.*;//i dont know why this doesnt work.
//import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class readingJSON {

	private static HttpURLConnection connections;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

	      //Method 1 to call HTTP in Java: java.net.HttpURLConnection. This is a method i found online

	      URL url = new URL("https://api.exchangeratesapi.io/latest"); //get rates from API
	      connections = (HttpURLConnection) url.openConnection();

	      //request setup

	      connections.setRequestMethod("GET");//APi needs a get method to get the data, can also use other methods, but exchangeratesapi uses GET.
	      connections.setConnectTimeout(5000);//timeout after 5 seconds of not receiving a respond from the server
	      connections.setReadTimeout(5000);

	      int status = connections.getResponseCode();
	      System.out.println(status);

	      if (status > 299) {
	    	  reader = new BufferedReader(new InputStreamReader(connections.getErrorStream())); //BuffeReader if the server is not accessible
	    	  while ((line = reader.readLine()) != null) {
	    		  responseContent.append(line);
	    	  }
	    	  reader.close();
	      } else {
	    	  reader = new BufferedReader(new InputStreamReader(connections.getInputStream())); //server is accessbile get data by reading the line of respond
	    	  while ((line = reader.readLine()) != null) { //while loop to read all lines that are best sent
	    	  	responseContent.append(line);

	      }
	    	  reader.close();
	      }
	      System.out.println(responseContent.toString()); //print the respond received by the server



	      JSONParser parser = new JSONParser(); //parser that allows to take the JSON string and create a javascript object that can be used to manipulate

	      JSONObject objFromRequest; //since the respond from the server is an Object, we create a JSON object that we can parse in the string to


		try { //I DONT KNOW WHAT THIS DOES
			objFromRequest = (JSONObject) parser.parse(responseContent.toString()); //i dont know why this needs a try. Basically passing the String into the JSON Obj
																					//called objFromRequest. FOr some reason you need to Cast it. I dont know why.

		      System.out.printf("Base Currency pair is %s\n", objFromRequest.get("base").toString()); //prints out just the Base currency that we received from the API

		      JSONObject pairs = (JSONObject) objFromRequest.get("rates"); //get the Rates that the server sends and save it in pairs

		    	  double CAD = (double) pairs.get("CAD");  //I dont know how to get all the pairs without writing 10 lines of code :(
		    	  System.out.printf("Currency Pair EUR/CAD = %f\n", CAD);//Prints the EUR/* value after getting it from the API

		    	  double USD = (double) pairs.get("USD");
		    	  System.out.printf("Currency Pair EUR/USD = %f\n", USD);

		    	  double AUD = (double) pairs.get("AUD");
		    	  System.out.printf("Currency Pair EUR/AUD = %f\n", AUD);

		    	  double GBP = (double) pairs.get("GBP");
		    	  System.out.printf("Currency Pair EUR/GBP = %f\n", GBP);

		    	  double JPY = (double) pairs.get("JPY");
		    	  System.out.printf("Currency Pair EUR/JPY = %f\n", JPY);

		    	  double NZD = (double) pairs.get("NZD");
		    	  System.out.printf("Currency Pair EUR/NZD = %f\n", NZD);

		    	  //There has be an easier way of doing this rather than doing it individually.
		    	  System.out.println("THERE HAS TO BE AN EASIER WAY OF DOING THIS BUT IT WORKS FOR NOW");


		} catch (ParseException e1) { //I dont know what this does
			// TODO Auto-generated catch block
			System.out.println();
		}



	}

}
