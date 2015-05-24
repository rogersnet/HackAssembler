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
        identify_command_type();
    }

    public void setAssemblyString(String as_string){
        this.assemblyString = as_string;
        identify_command_type();
    }

    public String getAssemblyString(){
        return this.assemblyString;
    }

    private void identify_command_type(){
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

    public CommandType getCommandType(){
        return this.cType;
    }

    public String getSymbol(){
        String symbol = "";

        if (this.cType == CommandType.A_COMMAND || this.cType == CommandType.L_COMMAND){
            symbol = this.assemblyString.substring(1);
            if (this.cType == CommandType.L_COMMAND){
                symbol = symbol.substring(0,symbol.length()-2);
            }
        }
        return symbol;
    }

    public String getDestMnemonic(){
        String dest = "";

        if (this.cType == CommandType.C_COMMAND && this.assemblyString.contains("=") ){
            dest = this.assemblyString.substring(0,this.assemblyString.indexOf("="));
        }
        return dest;
    }

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
