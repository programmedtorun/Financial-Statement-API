package com.patty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try {
            call();

        } catch (IOException e){
            e.printStackTrace();

        }
    }

    static void call() throws IOException {
        URL url = new URL("https://financialmodelingprep.com/api/v3/financials/income-statement/AAPL?apikey=aa9b189f0b8c5b59b5802e95b9d1bcee");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
