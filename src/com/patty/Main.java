package com.patty;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println(call("AAPL"));

        } catch (IOException e){
            e.printStackTrace();

        }
    }

    static String call(String ticker) throws IOException {
        ticker = ticker.toUpperCase();
        if(ticker.length() > 5 || ticker.length() < 3 || !ticker.matches("[A-Za-z]+")){
            return "Symbol entered must be letters only, and between 3 and 5 characters inclusive";
        }

        URL url = new URL("https://financialmodelingprep.com/api/v3/financials/income-statement/"
                               + ticker + "?apikey=aa9b189f0b8c5b59b5802e95b9d1bcee");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                sb.append(line);
                System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        String json = sb.toString();
        Gson gson = new Gson();
        Retriever retriever = gson.fromJson(json, Retriever.class);
        System.out.println("SYMBOL IS......" + retriever.getSymbol());
        System.out.println("financials are....");
        for(HashMap<String, String> map : retriever.getFinancials()){
            System.out.println("date is: " + map.get("date"));
        }

        return "blah";
//        return ticker;
    }
}
