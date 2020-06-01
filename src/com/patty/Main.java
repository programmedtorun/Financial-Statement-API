package com.patty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println(call("AAPL5"));

        } catch (IOException e){
            e.printStackTrace();

        }
    }
//    static String t(String ticker){
//        ticker = ticker.toUpperCase();
//        if(ticker.length() > 5 || ticker.length() < 3){
//            return "Symbol entered must be between 3 and 5 characters inclusive";
//        }
//
//        if(){
//            return "Symbol must be alphabet characters";
//        }
//        return ticker;
//    }

    static String call(String ticker) throws IOException {
        ticker = ticker.toUpperCase();
        if(ticker.length() > 5 || ticker.length() < 3 || !ticker.matches("[A-Za-z]+")){
            return "Symbol entered must be letters only, and between 3 and 5 characters inclusive";
        }

        URL url = new URL("https://financialmodelingprep.com/api/v3/financials/income-statement/" + ticker + "?apikey=aa9b189f0b8c5b59b5802e95b9d1bcee");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                System.out.println();
                System.out.println(line);
                System.out.println();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return ticker;
    }
}
