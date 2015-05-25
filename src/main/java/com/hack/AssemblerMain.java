package com.hack;

import java.io.IOException;

/**
 * Created by joydeep.paul on 24/05/15.
 */
public class AssemblerMain {
    private SymbolTable symTable;        //Symbol table
    private CodeMap cMap;                //Comp, Destination and Jump instructions map
    private PredefinedSymbolMap pMap;    //Predefined symbol map
    private String asmFile;              //Input Assembly File
    private MachineCodeStreamOut streamOut;
    private int currentAvailRAMAddress;  //Current Available RAM Address for variable allocation

    public AssemblerMain(String file){
        this.asmFile   = file;
        this.cMap     = CodeMap.getInstance();
        this.pMap     = PredefinedSymbolMap.getInstance();
        this.symTable = new SymbolTable();
        this.currentAvailRAMAddress = 16;
        this.streamOut = new HackOutputFileManager();
        this.streamOut.initialize(this.asmFile.replace(".asm", ".hack"));
    }

    public AssemblerMain(String file, MachineCodeStreamOut outStream){
        this.asmFile  = file;
        this.cMap     = CodeMap.getInstance();
        this.pMap     = PredefinedSymbolMap.getInstance();
        this.symTable = new SymbolTable();
        this.currentAvailRAMAddress = 16;
        this.streamOut = outStream;
        this.streamOut.initialize(file);
    }

    public static void main(String[] args){
        AssemblerMain jasm = new AssemblerMain(args[0]);
        jasm.asmToBinary();
    }

    /**
     * Processes the given input file in two passes
     * In the first pass, it parses each line and
     * identifies the labels that are necessary and
     * loads them into the Symbol table. In the second
     * pass, it parses again each line, but this time it
     * also handles variables and predefined symbols
     * and converts each line to its corresponding machine
     * code.
     */
    public void asmToBinary(){
        try{
            processPassFirst();
            processPassSecond();
        }catch (IOException ex){
            System.out.println("An exception occurred while parsing input file.");
        }catch (ProcessingException ex){
            System.out.println("An exception occurred while processing input file.");
        }
        System.out.println("Asm file " + this.asmFile + " successfully assembled.");
    }

    /*
     *  First pass: Focus on labels
     */
    private void processPassFirst() throws IOException{
        Parser hParser = new Parser(this.asmFile);

        int rowNumber = 0;
        while(hParser.hasMoreCommands()){
            hParser.advance();
            CommandLine instr = hParser.getCurrentCommand();

            switch (instr.getCommandType()){
                case A_COMMAND:
                case C_COMMAND:
                    rowNumber++;
                    break;
                case L_COMMAND:
                    symTable.addEntry(instr.getSymbol(),rowNumber);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Second Pass: Focus on variables and convert each instruction to corresponding machine code
     * @throws ProcessingException
     */
    private void processPassSecond() throws ProcessingException {
        try {
            Parser hParser = new Parser(this.asmFile);
            while (hParser.hasMoreCommands()) {
                //move the parser one line
                hParser.advance();

                //read the current command line instruction
                CommandLine instr = hParser.getCurrentCommand();

                String machineCode = "";
                switch (instr.getCommandType()) {
                    case A_COMMAND:
                        machineCode = generateAInsMc(instr);
                        break;
                    case C_COMMAND:
                        machineCode = generateCInsMc(instr);
                        break;
                    case L_COMMAND:
                    case COMMENT:
                    case WHITESPACE:
                        //ignore
                        break;
                }

                //once the machine code is generated, write it to the output stream
                if(!machineCode.isEmpty()){
                    streamOut.write(machineCode);
                }
            }
            streamOut.close();
        }catch(IOException ex){
            streamOut.delete();
            throw new ProcessingException(ex.getMessage());
        }
    }

    /**
     * @param instr - Hack assembly A instruction
     * @return - corresponding machine language equivalent
     */
    private String generateAInsMc(CommandLine instr){
        String symbol  = instr.getSymbol();
        String macCode = "0";

        //check if this is a integer constant
        boolean isConstant = true;

        try{
            macCode = intTo16BitBinary(symbol);
        }catch(NumberFormatException e){
            isConstant = false;
        }

        if(!isConstant){
            //check if this is a predefined symbol
            if (pMap.isPredefined(symbol)){
                macCode = intTo16BitBinary(pMap.getMemoryAddressForSymbol(symbol));
            }else{
                //this is a symbol
                if(symTable.containsSymbol(symbol)){
                    macCode = intTo16BitBinary(Integer.toString((Integer) symTable.getAddress(symbol)));
                }else{
                    macCode = intTo16BitBinary(Integer.toString(currentAvailRAMAddress));
                    symTable.addEntry(symbol,currentAvailRAMAddress);
                    currentAvailRAMAddress++;
                }
            }
        }

        return macCode;
    }

    /**
     * @param instr - Hack assembly C instruction
     * @return - corresponding machine language equivalent
     */
    private String generateCInsMc(CommandLine instr){
        return "111" + cMap.getCompCode(instr.getCompMnemonic()) +
                cMap.getDestCode(instr.getDestMnemonic()) +
                cMap.getJumpCode(instr.getJumpMnemonic());
    }

    /**
     * @param number - a string representing a number
     * @return - a binary equivalent of the number padded with zeros to ensure the length is 16
     * @throws NumberFormatException
     */
    private String intTo16BitBinary(String number) throws NumberFormatException{
        Integer iconst   = Integer.parseInt(number);
        String binString = Integer.toBinaryString(iconst);
        return String.format("%16s",binString).replace(" ","0");
    }

}
