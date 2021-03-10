package pt.i9.template.Integration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.i9.template.Data.CovidStatusResponse;

import java.io.IOException;
import java.util.Objects;

public class CovidStatusService {

    private static final Logger log = LoggerFactory.getLogger(CovidStatusService.class);

    //Variables
    public static String HEADER_KEY_PROPERTY= "x-rapidapi-key";
    public static String HEADER_HOST_PROPERTY= "x-rapidapi-host";
    public static String NOT_AVAILABLE= "N/A";
    public static String RESPONSE= "response";
    public static String CASES= "cases";
    public static String NEW= "new";

    //Messages
    public static String ERROR_ON_RESPONSE="Response is null or something went wrong... see next log statement: ";
    public static String ERROR_ON_PARSE="Covid data unable to be parsed. See next log statement: ";
    public static String ERROR_ON_NEW_CASES="It was not possible to determine the number of new cases for: ";

    //Configs
    public static String URL_DESTINATION= "https://covid-193.p.rapidapi.com/statistics?country=Portugal";
    public static String HEADER_KEY= "cc884b6befmsh8d27a474c7d2d29p1273c6jsn2a6b7896a229";
    public static String HEADER_HOST= "covid-193.p.rapidapi.com";


    public CovidStatusResponse fetchStatus(){
        CovidStatusResponse data = new CovidStatusResponse();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_DESTINATION)
                .get()
                .addHeader(HEADER_KEY_PROPERTY, HEADER_KEY)
                .addHeader(HEADER_HOST_PROPERTY, HEADER_HOST)
                .build();
        try{
            Response response = client.newCall(request).execute();
            if(Objects.nonNull(response)){
                return decodeMessage(response);
            }else{
                throw new IOException();
            }
        }catch (IOException e){
            log.info(ERROR_ON_RESPONSE);
            log.info(e.getMessage());
        }

        return data;
    }

    public CovidStatusResponse decodeMessage(Response response){

        CovidStatusResponse data = new CovidStatusResponse();

        try{
            Gson gson = new Gson();
            ResponseBody responseBody = response.body();
            String responseString = responseBody.string();
            data = gson.fromJson(responseString, CovidStatusResponse.class);
            extractNewCases(data, responseString);
        }catch (IOException e){
            log.info(ERROR_ON_PARSE);
            log.info(e.getMessage());
        }

        return data;
    }

    public void extractNewCases(CovidStatusResponse data, String payload){
        try {
            JsonParser parser = new JsonParser();
            JsonObject obj = parser.parse(payload).getAsJsonObject();
            String newCases = obj.get(RESPONSE).getAsJsonArray().get(0).getAsJsonObject().get(CASES).getAsJsonObject().get(NEW).getAsString();
            data.getResponse().get(0).getCases().setNewCases(newCases);
        }catch (Exception e){
            data.getResponse().get(0).getCases().setNewCases(NOT_AVAILABLE);
            log.info(ERROR_ON_NEW_CASES+data.response.get(0).day);
            log.info(e.getMessage());
        }
    }
}
