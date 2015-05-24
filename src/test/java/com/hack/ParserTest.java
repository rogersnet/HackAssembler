package com.hack;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by joydeep.paul on 23/05/15.
 */
public class ParserTest {

    private Parser jparser;

    @Before
    public void setUp(){
        try{
            jparser = new Parser("src/test/resources/sample.asm");
        }catch (IOException ex){
            assert 1 == 2;
        }
    }

    @Test
    public void createAndCheckObject() {
        assertNotNull(jparser);
    }

    @Test
    public void createAndCheckAddressInstruction(){
        //there should be more commands to analyse
        assertEquals(true, jparser.hasMoreCommands());

        //read the first command, assert the type and symbol
        jparser.advance();
        CommandLine cLine = jparser.getCurrentCommand();

        assertEquals(CommandType.A_COMMAND,cLine.getCommandType());
        assertEquals("R0", cLine.getSymbol());
    }

    @Test
    public void createAndCheckCInstruction(){
        //there should be more commands to analyse
        assertEquals(true, jparser.hasMoreCommands());

        //read the second command, assert the type and symbol
        jparser.advance();
        jparser.advance();

        CommandLine cLine = jparser.getCurrentCommand();

        assertEquals(CommandType.C_COMMAND,cLine.getCommandType());
        assertEquals("D",cLine.getDestMnemonic());
        assertEquals("M",cLine.getCompMnemonic());
        assertEquals("",cLine.getJumpMnemonic());
    }

    @Test
    public void createAndCheckEOF(){

        //move the pointer to the end of the file
        jparser.advance();
        jparser.advance();

        assertEquals(false,jparser.hasMoreCommands());
    }
}
