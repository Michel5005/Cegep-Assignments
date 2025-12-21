package sim.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import sim.exception.SRuntimeException;

/**
 * JUnit test permettant de valider les fonctionnalités de la classe <b>SChronometer</b>.
 * 
 * @author Simon Vézina
 * @since 2021-09-14
 * @version 2021-09-14
 */
public class SChronometerTest {

  @Test
  public void test1()
  {
    try {
      SLog.setLogDirectory("test");
      SLog.setConsoleLog(false);
      SLog.setLogFileName("test_SChronometer_test1.txt");
    } catch (SRuntimeException | IOException e1) {
      e1.printStackTrace();
      fail();
    }
    
    SChronometer c = new SChronometer();
    c.start();
    
    //Écriture du temps écoulé (sera en exception).
    SLog.logWriteLine(String.valueOf(c.getTime()) + " s");
        
    c.stop();
    
    //Écriture du temps écoulé (sera en exception).
    try {
      Thread.sleep(10);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
      fail();
    }
    
    SLog.logWriteLine(String.valueOf(c.getTime()) + " s");
    
    try{
      SLog.closeLogFile();
    }catch(IOException e){
      fail("FAIL - " + e.getMessage());
    }
    
  }

}
