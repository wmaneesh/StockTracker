package httpRequestJSON;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

//import org.json.simple.JSONObject;

public class readingJSON {

    private static HttpURLConnection connections;

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        Scanner input = new Scanner(System.in);

        // Getting base and quote currency
        System.out.print("Enter base pair: ");
        String base = input.next();

        System.out.print("Enter quote pair: ");
        String quote = input.next();


        //Method 1 to call HTTP in Java: java.net.HttpURLConnection. This is a method i found online
        URL url = new URL("https://api.exchangeratesapi.io/latest?base=EUR&symbols=CAD,USD,AUD,GBP,JPY,NZD"); //get rates from API. I have no idea what the SPEC means
        URL EUR_USD = new URL("https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol=" + base + "&to_symbol=" + quote + "&interval=5min&apikey=S1T571A43RNAAHXQ");
        connections = (HttpURLConnection) EUR_USD.openConnection();

        //request setup using HTTP

        connections.setRequestMethod("GET");//APi needs a get method to get the data, can also use other methods, but exchangeratesapi uses GET.
        connections.setConnectTimeout(5000);//timeout after 5 seconds of not receiving a respond from the server
        connections.setReadTimeout(5000);

        int status = connections.getResponseCode(); //sending a GET response to the server. The server responds with a status code
        System.out.println(status);

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connections.getErrorStream())); //BuffeReader if the server is not accessible.
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(connections.getInputStream())); //server is accessbile get data by reading the line of respond
            while ((line = reader.readLine()) != null) { //while loop to read all lines that are being sent. I STILL DONT UNDERSTAND HOW THIS WORKS EITHER
                responseContent.append(line);

            }
            reader.close();
        }
        System.out.println(responseContent.toString()); //print the respond received by the server as a String

        JSONParser parser = new JSONParser(); //parser that allows to take the string received by the server and putting it into JSON format
        JSONObject objFromRequest; //since the respond from the server is an Object, we create a JSON object that we can parse in the string to

        try { 
            objFromRequest = (JSONObject) parser.parse(responseContent.toString()); //i dont know why this needs a try. Basically youre passing the String into the JSON Obj
            //called objFromRequest.

            JSONObject metaData = (JSONObject) objFromRequest.get("Meta Data"); //create object to pull fields from "Meta Data"
            String timeStamp = (String) metaData.get("4. Last Refreshed"); //pull the timestamp field under Meta Data object"
            System.out.printf("Time Stamp (UTC): %s\n ", timeStamp); //pulling the latest TimeStamp

            JSONObject pairInfo = (JSONObject) objFromRequest.get("Time Series FX (5min)"); //creating an object to pull fields from "Time series" Object
            JSONObject closingValue = (JSONObject) pairInfo.get(timeStamp); //pull the closing value from the Time series given the timeStamp.

            String value = (String) closingValue.get("4. close");
            System.out.printf("Closing value of " + base + "/" + quote + ": %s\n", value);

            //There has be an easier way of doing this rather than doing it individually.

        } catch (ParseException e1) { //
            // TODO Auto-generated catch block
            System.out.println(e1.toString());
        }

    }

}
