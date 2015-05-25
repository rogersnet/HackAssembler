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

    /**
     * Set the current reader stream to the beginning
     * @throws IOException
     */
    private void initFileBufferStream() throws IOException{
        this.fileH  = new FileReader(this.fileName);
        this.buffR  = new BufferedReader(this.fileH);
        this.currentLine = new CommandLine();
        moveFilePointer();
    }

    /**
     * Move the File pointer to the next line.
     * Set EOFMarker based on if there is more data to be read.
     * @throws IOException
     */
    private void moveFilePointer() throws IOException{
        this.nextLine = this.buffR.readLine();
        if (this.nextLine != null) {
            this.EOFMarker = false;
        }else{
            this.EOFMarker = true;
            this.buffR.close();
        }
    }

    /**
     * @return True/False based on the value of EOFMarker
     */
    public boolean hasMoreCommands(){
        return !this.EOFMarker;
    }

    /**
     * Move the file pointer to the next line.
     * Set the current line to the one previously read.
     * Also remove any inline comments
     */
    public void advance(){
        String currentCommand = this.nextLine.trim();

        //check if the current command contains some inline comment
        if(currentCommand.indexOf("//") > 0){
            currentCommand = currentCommand.substring(0,currentCommand.indexOf("//"));
            currentCommand = currentCommand.trim();
        }

        //remove all whitespaces from the string
        currentCommand = currentCommand.replaceAll("\\s","");
        this.currentLine.setAssemblyString(currentCommand);

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
