package pt.i9.template.Data;

import java.util.ArrayList;

public class CovidStatusResponse {

    public ArrayList<CovidData> response;

    public ArrayList<CovidData> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<CovidData> response) {
        this.response = response;
    }

}
