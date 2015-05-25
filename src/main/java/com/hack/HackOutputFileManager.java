package com.hack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by joydeep.paul on 24/05/15.
 */
public class HackOutputFileManager implements MachineCodeStreamOut {
    private File outH;
    private BufferedWriter bufferedWr;

    public HackOutputFileManager(){}

    /**
     * Initialize FileWriter and BufferedWriter for the file path provided
     * @param path
     */
    public void initialize(String path) {
        try{
            outH = new File(path);

            //create the file if not already exists
            if(!outH.exists()){
                outH.createNewFile();
            }

            bufferedWr = new BufferedWriter(new FileWriter(outH));
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Write the machine code to the output file
     * @param machineCode
     * @throws ProcessingException
     */
    public void write(String machineCode) throws ProcessingException {
        try {
            this.bufferedWr.write(machineCode);
            this.bufferedWr.newLine();
        }catch (IOException ex){
            throw new ProcessingException(ex.getMessage());
        }
    }

    /**
     * Close all open buffered writer streams
     */
    public void close(){
        try {
            if (bufferedWr != null)
                bufferedWr.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Delete the file incase of any exception, should not generated partially assembled files.
     */
    public void delete() {
        if(outH != null){
            outH.delete();
        }
    }
}
