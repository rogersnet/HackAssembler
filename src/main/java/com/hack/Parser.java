package com.hack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by joydeep.paul on 23/05/15.
 */
public class Parser {
    private String fileName;
    private String nextLine;
    private CommandLine currentLine;
    private FileReader fileH;
    private BufferedReader buffR;
    private boolean EOFMarker;
    private CommandType cType;

    public Parser(){
        fileName = "";
    }

    public Parser(String fileName) throws IOException{
        this.fileName = fileName;
        initFileBufferStream();
    }

    public void setFileName(String filename) throws IOException{
        this.fileName = filename;
        initFileBufferStream();
    }

    private void initFileBufferStream() throws IOException{
        this.fileH  = new FileReader(this.fileName);
        this.buffR  = new BufferedReader(this.fileH);
        this.currentLine = new CommandLine();
        moveFilePointer();
    }

    private void moveFilePointer() throws IOException{
        this.nextLine = this.buffR.readLine();
        if (this.nextLine != null) {
            this.EOFMarker = false;
        }else{
            this.EOFMarker = true;
            this.buffR.close();
        }
    }

    public boolean hasMoreCommands(){
        return !this.EOFMarker;
    }

    public void advance(){
        this.currentLine.setAssemblyString(this.nextLine.trim());
        try {
            moveFilePointer();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public CommandLine getCurrentCommand(){
        return this.currentLine;
    }

    protected void finalize() throws Throwable{
        this.buffR.close();
    }
}
