package com.hack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joydeep.paul on 23/05/15.
 */
public class SymbolTable {
    private Map<String,Integer> symbolTable;

    public SymbolTable(){
        symbolTable = new HashMap<String,Integer>();
    }

    public void addEntry(String symbol,Integer address){
        symbolTable.put(symbol,address);
    }

    public int getAddress(String symbol){
        return symbolTable.get(symbol);
    }

    public boolean containsSymbol(String symbol){
        return symbolTable.containsKey(symbol);
    }
}
