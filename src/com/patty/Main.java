package com.patty;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println ("No ticker provided! Exiting program...");
            System.exit(0);
        } else {
            try {
                qCall(args[0]);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    // returns eps and revenue for most recent 8 quarters
    static void qCall(String ticker) throws IOException {
        ticker = ticker.toUpperCase();
        if(ticker.length() > 5 || ticker.length() < 3 || !ticker.matches("[A-Za-z]+")){
            System.err.println("TICKER symbol entered must be letters only, and between 3 and 5 characters inclusive");
            System.exit(0);
        }
        URL url = new URL("https://financialmodelingprep.com/api/v3/income-statement/"
                + ticker + "?period=quarter&apikey=aa9b189f0b8c5b59b5802e95b9d1bcee");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                sb.append(line);
            }
        } catch (IOException e){
            System.err.println("Error: Could not open API Stream...");
            e.printStackTrace();
            System.exit(-1);
        }
        // Searalization to POJO using GSON
        String json = sb.toString();
        Gson gson = new Gson();
        QRetriever retriever = gson.fromJson(json, QRetriever.class);
        System.out.println();
        System.out.println();
        System.out.println("************************************");
        System.out.println("Getting Information for " + ticker);

        // Printing info needed
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
