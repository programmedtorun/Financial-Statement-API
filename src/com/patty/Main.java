package com.patty;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

//        System.out.println("keyboard args: " + args[0]);


        try {
            qCall("AAPL");
        } catch (IOException e){
            e.printStackTrace();
        }


    }

    // returns eps and revenue for most recent 8 quarters
    static void qCall(String ticker) throws IOException {
        ticker = ticker.toUpperCase();
        if(ticker.length() > 5 || ticker.length() < 3 || !ticker.matches("[A-Za-z]+")){
            System.out.println("Symbol entered must be letters only, and between 3 and 5 characters inclusive");
        }
        URL url = new URL("https://financialmodelingprep.com/api/v3/income-statement/"
                + ticker + "?period=quarter&apikey=aa9b189f0b8c5b59b5802e95b9d1bcee");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                sb.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        NumberFormat numberFormat = NumberFormat.getInstance();

        String json = sb.toString();
        Gson gson = new Gson();
        QRetriever retriever = gson.fromJson(json, QRetriever.class);
        System.out.println();
        System.out.println();
        System.out.println("************************************");
        for(int i = 0; i < (retriever.size() - (retriever.size() - 8)); i++){
            System.out.println("Q-Date: " + retriever.get(i).get("date"));
            System.out.println("\tEPS: " + retriever.get(i).get("eps"));
            System.out.println("\tRevenue: $" + retriever.get(i).get("revenue"));
            System.out.println("--------------------------");
        }
    }

    // yearly call code (currently not in use)
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
