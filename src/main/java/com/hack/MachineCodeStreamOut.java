package com.hack;

/**
 * Created by joydeep.paul on 24/05/15.
 */
public interface MachineCodeStreamOut {
    public void initialize(String path);
    public void write(String machineCode) throws ProcessingException;
    public void close();
    public void delete();
}
