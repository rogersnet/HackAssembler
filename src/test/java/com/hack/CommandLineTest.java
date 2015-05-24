package com.hack;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by joydeep.paul on 23/05/15.
 */
public class CommandLineTest {

    @Test
    public void createAndCheckObjectWithEmptyString(){
        CommandLine cLine = new CommandLine();
        assertNotNull(cLine);
    }

    @Test
    public void createAndCheckObjectWithString(){
        CommandLine cLine = new CommandLine("D=D+1");
        assertEquals("D=D+1", cLine.getAssemblyString());
    }

    @Test
    public void setAndCheckCommentCommandType(){
        CommandLine cLine = new CommandLine();
        cLine.setAssemblyString("//This is a comment");
        assertEquals(CommandType.COMMENT,cLine.getCommandType());
    }

    @Test
    public void setAndCheckAddressCommandType(){
        CommandLine cLine = new CommandLine("@R0");
        assertEquals(CommandType.A_COMMAND,cLine.getCommandType());
    }

    @Test
    public void setAndCheckAddressSymbol(){
        CommandLine cLine = new CommandLine("@R0");
        assertEquals("R0",cLine.getSymbol());
    }

    @Test
    public void setAndCheckCInstructionCommandType(){
        CommandLine cLine = new CommandLine("D=D+1");
        assertEquals(CommandType.C_COMMAND,cLine.getCommandType());
    }

    @Test
    public void setAndCheckCInsDest_001(){
        CommandLine cLine = new CommandLine("D=D+1");
        assertEquals("D",cLine.getDestMnemonic());
    }

    @Test
    public void setAndCheckCInsDest_002(){
        CommandLine cLine = new CommandLine("D;JMP");
        assertEquals("",cLine.getDestMnemonic());
    }

    @Test
    public void setAndCheckCInsDest_003(){
        CommandLine cLine = new CommandLine("MD=D+1;JMP");
        assertEquals("MD",cLine.getDestMnemonic());
    }

    @Test
    public void setAndCheckCInsJump_001(){
        CommandLine cLine = new CommandLine("MD=D+1;JMP");
        assertEquals("JMP",cLine.getJumpMnemonic());
    }

    @Test
    public void setAndCheckCInsJump_002(){
        CommandLine cLine = new CommandLine("MD=D+1;");
        assertEquals("",cLine.getJumpMnemonic());
    }

    @Test
    public void setAndCheckCInsComp_001(){
        CommandLine cLine = new CommandLine("MD=D+1;JMP");
        assertEquals("D+1",cLine.getCompMnemonic());
    }

    @Test
    public void setAndCheckCInsComp_002(){
        CommandLine cLine = new CommandLine("D;JMP");
        assertEquals("D",cLine.getCompMnemonic());
    }

    @Test
    public void setAndCheckCInsComp_003(){
        CommandLine cLine = new CommandLine("D=D+1");
        assertEquals("D+1",cLine.getCompMnemonic());
    }
}
