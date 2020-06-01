package com.patty;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by patrickskelley on Jun, 2020
 */
public class YearRetriever {
    private String symbol;
    private ArrayList<HashMap<String, String>> financials;

    public String getSymbol() {
        return symbol;
    }
    public ArrayList<HashMap<String, String>> getFinancials() {
        return financials;
    }
}
