package com.hack;

/**
 * Created by joydeep.paul on 23/05/15.
 */
public class CommandLine {
    private String assemblyString;
    private CommandType cType;

    public CommandLine(){
        assemblyString = "";
    }

    public CommandLine(String as_string){
        this.assemblyString = as_string;
        identifyCommandType();
    }

    public String getAssemblyString(){
        return this.assemblyString;
    }

    public void setAssemblyString(String as_string){
        this.assemblyString = as_string;
        identifyCommandType();
    }

    /**
     * Identify command type based on the first character
     */
    private void identifyCommandType(){
        if (this.assemblyString.startsWith("//")){
            this.cType = CommandType.COMMENT;
        }else if(this.assemblyString.startsWith("@")){
            this.cType = CommandType.A_COMMAND;
        }else if(this.assemblyString.startsWith("(")){
            this.cType = CommandType.L_COMMAND;
        }else if(this.assemblyString.isEmpty()){
            this.cType = CommandType.WHITESPACE;
        }else{
            this.cType = CommandType.C_COMMAND;
        }
    }

    /**
     * @return command type - A_COMMAND, C_COMMAND, L_COMMAND, COMMENT, WHITESPACE
     */
    public CommandType getCommandType(){
        return this.cType;
    }

    /**
     * @return the symbol part of the A_COMMAND or L_COMMAND
     * for A_COMMAND - @Xxx, Xxx is the symbol
     * for L_COMMAND - (Xxx), Xxx is the symbol
     */
    public String getSymbol(){
        String symbol = "";

        if (this.cType == CommandType.A_COMMAND || this.cType == CommandType.L_COMMAND){
            symbol = this.assemblyString.substring(1);
            if (this.cType == CommandType.L_COMMAND){
                symbol = symbol.substring(0,symbol.length()-1);
            }
        }
        return symbol;
    }

    /**
     * @return the destination part of the C_COMMAND
     * e.g. for D=D+1, the method will return 'D'
     */
    public String getDestMnemonic(){
        String dest = "";

        if (this.cType == CommandType.C_COMMAND && this.assemblyString.contains("=") ){
            dest = this.assemblyString.substring(0,this.assemblyString.indexOf("="));
        }
        return dest;
    }

    /**
     * @return the computation part of the C_COMMAND
     * e.g. for D=D+1, the method will return 'D+1'
     */
    public String getCompMnemonic(){
        String comp = "";
        if (this.cType == CommandType.C_COMMAND){
            if (this.assemblyString.contains("=")){
                if(this.assemblyString.contains(";")){
                    comp = this.assemblyString.substring(this.assemblyString.indexOf("=")+1,this.assemblyString.indexOf(";"));
                }else{
                    comp = this.assemblyString.substring(this.assemblyString.indexOf("=")+1);
                }
            }else{
                if(this.assemblyString.contains(";")){
                    comp = this.assemblyString.substring(0,this.assemblyString.indexOf(";"));
                }else{
                    comp = this.assemblyString;
                }
            }
        }
        return comp;
    }

    /**
     * @return the jump part of the C_COMMAND
     * e.g. for D=D+1;JGE, the method will return 'JGE'
     */
    public String getJumpMnemonic(){
        String jump = "";
        if(this.cType == CommandType.C_COMMAND){
            if(this.assemblyString.contains(";")){
                jump = this.assemblyString.substring(this.assemblyString.indexOf(";")+1);
            }
        }
        return jump;
    }
}
