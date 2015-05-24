package com.hack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joydeep.paul on 23/05/15.
 */
public class CodeMap {
    /**
     * aluCompMap stores the ALU computation code
     */
    private final Map<String,String> aluCompMap = new HashMap<String, String>(){
                                                        {
                                                            put("0", "0101010");
                                                            put("1", "0111111");
                                                            put("-1", "0111010");
                                                            put("D", "0001100");
                                                            put("A", "0110000");
                                                            put("M", "1110000");
                                                            put("!D", "0001101");
                                                            put("!A", "0110001");
                                                            put("!M", "1110001");
                                                            put("-D", "0001111");
                                                            put("-A", "0110011");
                                                            put("-M", "1110011");
                                                            put("D+1", "0011111");
                                                            put("A+1", "0110111");
                                                            put("M+1", "1110111");
                                                            put("D-1", "0001110");
                                                            put("A-1", "0110010");
                                                            put("M-1", "1110010");
                                                            put("D+A", "0000010");
                                                            put("D+M", "1000010");
                                                            put("D-A", "0010011");
                                                            put("D-M", "1010011");
                                                            put("A-D", "0000111");
                                                            put("M-D", "1000111");
                                                            put("D&A", "0000000");
                                                            put("D&M", "1000000");
                                                            put("D|A", "0010101");
                                                            put("D|M", "1010101");
                                                        }
                                                  };

    /**
     * destMap stores the destination of computation or memory addressing
     */
    private final Map<String,String> destMap = new HashMap<String, String>(){
                                                    {
                                                        put("","000");
                                                        put("M","001");
                                                        put("D","010");
                                                        put("MD","011");
                                                        put("A","100");
                                                        put("AM","101");
                                                        put("AD","110");
                                                        put("AMD","111");
                                                    }
                                                };

    /**
     * jumpMap stores machine code sequences for jump instructions
     */
    private final Map<String,String> jumpMap = new HashMap<String, String>(){
                                                    {
                                                        put("","000");
                                                        put("JGT","001");
                                                        put("JEQ","010");
                                                        put("JGE","011");
                                                        put("JLT","100");
                                                        put("JNE","101");
                                                        put("JLE","110");
                                                        put("JMP","111");
                                                    }
                                               };
    private static CodeMap instance = null;

    private CodeMap(){}

    /**
     * @return a singleton instance of CodeMap
     */
    public static CodeMap getInstance(){
        if(instance == null){
            instance = new CodeMap();
        }
        return instance;
    }

    /**
     * @param codeseg - string in Hack assembly code representing an alu instruction
     * @return - binary equivalent of the alu comp instruction
     */
    public String getCompCode(String codeseg){
        return aluCompMap.get(codeseg);
    }

    /**
     * @param destseg - string representing destination for storing results of computation
     * @return - binary equivalent of the destination mnemonic
     */
    public String getDestCode(String destseg){
        return destMap.get(destseg);
    }

    /**
     * @param jumpseg - string representing the jump mnemonic, e.g. JMP, JGE etc.
     * @return - binary equivalent of the jump mnemonic
     */
    public String getJumpCode(String jumpseg){
        return jumpMap.get(jumpseg);
    }
}
