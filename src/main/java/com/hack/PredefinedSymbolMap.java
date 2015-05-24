package com.hack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joydeep.paul on 23/05/15.
 */
public class PredefinedSymbolMap {
    private final Map<String,String> predSymMap = new HashMap<String,String>(){
                                                        {
                                                            put("SP","0");
                                                            put("LCL","1");
                                                            put("ARG","2");
                                                            put("THIS","3");
                                                            put("THAT","4");
                                                            put("R0","0");
                                                            put("R1","1");
                                                            put("R2","2");
                                                            put("R3","3");
                                                            put("R4","4");
                                                            put("R5","5");
                                                            put("R6","6");
                                                            put("R7","7");
                                                            put("R8","8");
                                                            put("R9","9");
                                                            put("R10","10");
                                                            put("R11","11");
                                                            put("R12","12");
                                                            put("R13","13");
                                                            put("R14","14");
                                                            put("R15","15");
                                                            put("SCREEN","16384");
                                                            put("KBD","24756");
                                                        }
                                                  };

    private static PredefinedSymbolMap instance = null;

    private PredefinedSymbolMap(){}

    public static PredefinedSymbolMap getInstance(){
        if(instance == null){
            instance = new PredefinedSymbolMap();
        }
        return instance;
    }

    public boolean isPredefined(String sym){
        return predSymMap.containsKey(sym);
    }

    public String getMemoryAddressForSymbol(String sym){
        return predSymMap.get(sym);
    }
}
