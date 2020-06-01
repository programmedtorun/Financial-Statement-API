package com.patty;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
//        if (args.length == 0) {
//            System.err.println ("No arguments!");
//            System.exit(0);
//        } else {
//            try {
//                System.out.println(call(args[0]));
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }
        try {
//            System.out.println(yearCall("AAPL"));
            System.out.println(quarterCall("AAPL"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    static String quarterCall(String ticker) throws IOException {
        ticker = ticker.toUpperCase();
        if(ticker.length() > 5 || ticker.length() < 3 || !ticker.matches("[A-Za-z]+")){
            return "Symbol entered must be letters only, and between 3 and 5 characters inclusive";
        }
        URL url = new URL("https://financialmodelingprep.com/api/v3/income-statement/"
                + ticker + "?period=quarter&apikey=aa9b189f0b8c5b59b5802e95b9d1bcee");
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
        QuarterRetriever retriever = gson.fromJson(json, QuarterRetriever.class);

        for(HashMap<String, String> map : retriever.getQuarters()){
            System.out.println("quarters: " + map.get("date"));
        }
//        for(HashMap<String, String> map : retriever.getQuarters()){
//            System.out.println("quarter date is: " + map.get("date"));
//        }

        return "blah";
//      return ticker;
    }

    static String yearCall(String ticker) throws IOException {
        ticker = ticker.toUpperCase();
        if(ticker.length() > 5 || ticker.length() < 3 || !ticker.matches("[A-Za-z]+")){
            return "Symbol entered must be letters only, and between 3 and 5 characters inclusive";
        }
        URL url = new URL("https://financialmodelingprep.com/api/v3/income-statement/"
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
        YearRetriever retriever = gson.fromJson(json, YearRetriever.class);
        System.out.println("SYMBOL IS......" + retriever.getSymbol());
        System.out.println("financials are....");
        for(HashMap<String, String> map : retriever.getFinancials()){
            System.out.println("date is: " + map.get("date"));
        }

        return "blah";
//        return ticker;
    }
}
