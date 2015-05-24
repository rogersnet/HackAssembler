package com.hack;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by joydeep.paul on 24/05/15.
 */
class HackOutputArrayStream implements MachineCodeStreamOut {
    private List<String> outHack;
    public HackOutputArrayStream(){
        outHack = new ArrayList<String>();
    }

    public void initialize(String path){
        //dont do anything
    }

    public void write(String machineCode){
        outHack.add(machineCode);
    }

    public void close(){
        //dont do anything
    }

    public void delete(){
        outHack.clear();
    }
}

public class AssemblerMainTest {
    private MachineCodeStreamOut arrayStreamOut;
    private AssemblerMain jackasm;

    @Before
    public void setUp(){
        arrayStreamOut = new HackOutputArrayStream();
        jackasm = new AssemblerMain("src/test/resources/sample.asm");
    }

    @Test
    public void createAndCheckObject(){
        assertNotNull(jackasm);
    }

}
