package com.patty;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        if (args.length == 0) {
//            System.err.println ("No ticker provided! Exiting program...");
//            System.exit(0);
//        } else {
            try {
                QRetriever retriever = qCall("GOOG");
                printQData(retriever, "GOOG");
            } catch (IOException e){
                e.printStackTrace();
            }
//        }
//        System.out.print("some initial stuff");
//        System.out.println("beginning part should NOT have red...."+ ANSI_RED + "text has Red background" + ANSI_RESET + "some more stuff");
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    static void printQData(QRetriever retriever, String ticker){
        System.out.println();
        System.out.println();

        // If more than 8 quarters will change iterable to 8
        int rSize = retriever.size();
        if (retriever.size() >= 8){
            rSize = retriever.size() - (retriever.size() - 8);
        }
        // logic to stop % different calculation if there is no data 4 quarters prior
        int sizeDiff = retriever.size();
       if(sizeDiff <= 12 ){
            sizeDiff = retriever.size() - 4;
        }
        System.out.println("*******************************************************************************************************");
        System.out.println("Getting Information for " + ticker);
        System.out.println();
        System.out.println("\tDATE\t\t\t\tEPS\t\t\t\tEPS(%Diff)\t\t\t\tRevenue\t\t\t\tRevenue(%Diff)");
        System.out.println("-------------------------------------------------------------------------------------------------------");

        // Printing info needed
        for(int i = 0; i < rSize; i++){
            String epsCurr = retriever.get(i).get("eps");
            double epsC = Double.parseDouble(epsCurr);
            double epsCRnd = round(epsC, 2);
            epsCurr = Double.toString(epsCRnd);
            String revCurr = retriever.get(i).get("revenue");
            String date = retriever.get(i).get("date");
            String epsDiff = "no data";
            String revDiff = "no data";
            String ANSI_RED_EPS = "";
            String ANSI_RED_REV = "";
            String ANSI_RESET = "\u001B[0m";


            if(sizeDiff >= 1){
                String epsLast = retriever.get(i + 4).get("eps");
                double epsL = Double.parseDouble(epsLast);
                double epsD = ((epsC - epsL) / Math.abs(epsL)) * 100;
                double epsDRnd = round(epsD, 2);
                if(epsDRnd >= 25){
                    ANSI_RED_EPS = "\u001B[31m";
                }
                epsDiff = Double.toString(epsDRnd);

                String revLast = retriever.get(i + 4).get("revenue");
                double revC = Double.parseDouble(revCurr);
                double revL = Double.parseDouble(revLast);
                double revD = ((revC - revL) / Math.abs(revL)) * 100;
                double revDRnd = round(revD, 2);
                if(revDRnd >= 25){
                    ANSI_RED_REV = "\u001B[31m";
                }
                revDiff = Double.toString(revDRnd);
            }
            sizeDiff--;
            if(!epsDiff.equals("no data")){
                epsDiff = "%" + epsDiff;
            }
            if(!revDiff.equals("no data")){
                revDiff = "%" + revDiff;
            }
            System.out.println("\t" + date + "\t\t\t" + epsCurr + "\t\t\t" + ANSI_RED_EPS + epsDiff + ANSI_RESET + "\t\t\t\t" + revCurr + "\t\t\t\t" + ANSI_RED_REV + revDiff  +ANSI_RESET);
        }
    }

    // returns eps and revenue for most recent 8 quarters
    static QRetriever qCall(String ticker) throws IOException {
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
        return retriever;
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
