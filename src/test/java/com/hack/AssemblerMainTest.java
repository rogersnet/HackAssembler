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

    public List<String> getFinalConvCode(){
        return this.outHack;
    }
}

public class AssemblerMainTest {
    private MachineCodeStreamOut arrayStreamOut;
    private AssemblerMain jackasm;

    @Before
    public void setUp(){
        arrayStreamOut = new HackOutputArrayStream();
        jackasm = new AssemblerMain("src/test/resources/sample.asm", arrayStreamOut);
    }

    @Test
    public void createAndCheckObject(){
        assertNotNull(jackasm);
    }

    @Test
    public void checkConvertedCode(){
        //build expected list
        List<String> expOut = new ArrayList<String>();
        expOut.add("0000000000000000");
        expOut.add("1111110000010000");

        jackasm.asmToBinary();

        HackOutputArrayStream arrayOut = (HackOutputArrayStream) arrayStreamOut;
        List<String> actualOut = arrayOut.getFinalConvCode();

        assertArrayEquals(expOut.toArray(), actualOut.toArray());
    }

    @Test
    public void createAndCheckAdditionOfTwoNumbers(){
        jackasm = new AssemblerMain("src/test/resources/add.asm", arrayStreamOut);

        //build expected output
        List<String> expOut = new ArrayList<String>();
        expOut.add("0000000000000000");
        expOut.add("1111110000010000");
        expOut.add("0000000000000001");
        expOut.add("1111000010010000");
        expOut.add("0000000000000010");
        expOut.add("1110001100001000");
        expOut.add("0000000000000110");
        expOut.add("1110101010000111");

        jackasm.asmToBinary();

        HackOutputArrayStream arrayOut = (HackOutputArrayStream) arrayStreamOut;
        List<String> actualOut = arrayOut.getFinalConvCode();

        assertArrayEquals(expOut.toArray(), actualOut.toArray());
    }

}
