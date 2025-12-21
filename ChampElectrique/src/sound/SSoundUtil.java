/**
 * 
 */
package sim.sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sim.util.SBrowser;
import sim.util.SManyFilesFoundException;
import sim.util.SStringUtil;

/**
 * La classe <b>SSoundUtil</b> représente une classe utilitaire pour faire la gestion de fichier de son.
 * 
 * Référence : https://stackoverflow.com/questions/5210147/reading-wav-file-in-java
 * Référence : https://fr.wikipedia.org/wiki/Note_de_musique
 * 
 * @author Simon Vezina
 * @since 2019-12-23
 * @version 2022-03-17
 */
public class SSoundUtil {
  
  /**
   * ...
   */
  private static final String WAV_EXTENSION = "wav";
  
  /**
   * La constante <b>SAMPLE_RATE</b> représente le nombre de d'élément discret de son (<i>frame</i>) par seconde.
   */
  private static final int SAMPLE_RATE = 44100;
  
  /**
   * ...
   */
  private static final int VALID_BITS = 16;
  
  /**
   * ...
   */
  private static final String[] NOTES = { "do", "mi", "sol", "la" };
  
  /**
   * La constante <b>DO</b> représente les fréquences (en Hz) du son "do" de l'octave #0 à #8.
   */
  private static final double[] DO = { 32.70,  65.41, 130.81,  261.63,  523.25,  1046.50, 2093.00, 4186.01, 8372.02 }; 
    
  /**
   * La constante <b>MI</b> représente les fréquences (en Hz) du son "mi" de l'octave #0 à #8.
   */
  private static final double[] MI = { 41.20,  82.41, 164.81,  329.63,  659.26,  1318.51, 2637.02, 5274.04, 10548.08 }; 
   
  /**
   * La constante <b>LA</b> représente les fréquences (en Hz) du son "sol" de l'octave #0 à #8.
   */
  private static final double[] SOL = { 49.0, 98.0, 196.0, 392.0, 783.99, 1567.98, 3135.96, 6271.93, 12543.86 }; 
 
  /**
   * La constante <b>LA</b> représente les fréquences (en Hz) du son "la" de l'octave #0 à #8.
   */
  private static final double[] LA = { 55.0, 110.0, 220.0, 440.0, 880.0, 1760.0, 3520.0, 7400.0, 14080.0 }; 
  
  /**
   * Méthode pour faire jouer un fichier sonore de type "wav".
   * 
   * @param file_name Le nom du fichier.
   * @param wait Si l'exécution de la thread doit attendre la fin de l'extrait sonore avant de continuer son exécution.
   * @throws IllegalArgumentException S'il y a eu un argument invalide.
   * @throws RuntimeException S'il y a eu une erreur avec la librairie de lecture du fichier.
   */
  public static void playWaveFile(String file_name, boolean wait) throws IllegalArgumentException, RuntimeException
  {
    playWaveFile(new SBrowser(), file_name, wait);
  }
  
  /**
   * Méthode pour faire jouer un fichier sonore de type "wav".
   * 
   * @prama browser Le browser à partir de où la recherche du fichier sera effectuée.
   * @param file_name Le nom du fichier.
   * @param wait Si l'exécution de la thread doit attendre la fin de l'extrait sonore avant de continuer son exécution.
   * @throws IllegalArgumentException S'il y a eu un argument invalide.
   * @throws RuntimeException S'il y a eu une erreur avec la librairie de lecture du fichier.
   */
  public static void playWaveFile(SBrowser browser, String file_name, boolean wait) throws IllegalArgumentException, RuntimeException
  {
    try {
      
      // Localiser le fichier.
      File file = browser.findFile(file_name);
      
      // Ouverture du clip.
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(file));
      
      // Débuter le clip.
      clip.start();
      
      // Attendre la fin du clip avant de continuer l'exécution de la thread.
      if(wait)
        Thread.sleep(clip.getMicrosecondLength()/1000);
        
    }catch(FileNotFoundException e) {
      throw new IllegalArgumentException("Erreur SSoundUtil 002 : Le fichier '" + file_name + "' n'a pas été trouvé.", e);
    }catch(SManyFilesFoundException e) {
      throw new IllegalArgumentException("Erreur SSoundUtil 003 : Le fichier '" + file_name + "' a été trouvé en plusieurs exemplaires", e);
    }catch(LineUnavailableException e) {
      throw new RuntimeException("Erreur SSoundUtil 004 : Une erreur est survenue à l'ouverture du clip du fichier '" + file_name + "'.", e);
    }catch(IOException e) {
      throw new RuntimeException("Erreur SSoundUtil 005 : Une erreur de type I/O est survenue à l'ouverture du fichier '" + file_name + "'.", e);
    }catch(UnsupportedAudioFileException e) {
      throw new RuntimeException("Erreur SSoundUtil 006 : Le fichier '" + file_name + "' n'est pas supporté en lecture par cette librairie", e);
    }catch(InterruptedException e) {
      throw new RuntimeException("Erreur SSoundUtil 007 : Une erreur d'interruption est survenue lors de l'exécution du fichier '" + file_name + "'.", e);
    }
    
  }
    
  /**
   * Méthode pour produire un son wave à fréquence unique.
   * 
   * @param file_name Le nom du fichier (sans l'extension).
   * @param frequency La fréquence.
   * @param duration La durée de l'enregistrement.
   */
  public static void writeWaveFile(String file_name, double frequency_ch1, double frequency_ch2, double duration)
  {
    SBrowser browser = new SBrowser();
       
    try
    {
       int sampleRate = 44100;    // Samples per second
      
       // Calculate the number of frames required for specified duration
       long numFrames = (long)(duration * sampleRate);

       // Retirer l'extension du fichier, s'il y en a.
       file_name = SStringUtil.getFileNameWithoutExtension(file_name);
       
       // Create a wav file with the name specified as the first argument
       WavFile wavFile = WavFile.newWavFile(browser.createFile(file_name, WAV_EXTENSION), 2, numFrames, 16, sampleRate);

       // Create a buffer of 100 frames
       double[][] buffer = new double[2][100];

       // Initialise a local frame counter
       long frameCounter = 0;

       // Loop until all frames written
       while (frameCounter < numFrames)
       {
          // Determine how many frames to write, up to a maximum of the buffer size
          long remaining = wavFile.getFramesRemaining();
          int toWrite = (remaining > 100) ? 100 : (int) remaining;

          // Fill the buffer, one tone per channel
          for (int s=0 ; s<toWrite ; s++, frameCounter++)
          {
             buffer[0][s] = Math.sin(2.0 * Math.PI * frequency_ch1 * frameCounter / sampleRate);
             buffer[1][s] = Math.sin(2.0 * Math.PI * frequency_ch2 * frameCounter / sampleRate);
          }

          // Write the buffer
          wavFile.writeFrames(buffer, toWrite);
       }

       // Close the wavFile
       wavFile.close();
    }
    catch (Exception e)
    {
       System.err.println(e);
    }

  }

  /**
   * Méthode pour écrire le contenu d'un fichier wave dans un fichier texte.
   * 
   * @param wave_file_name
   * @param txt_file_name
   * @throws SManyFilesFoundException
   * @throws IOException
   * @throws WavFileException
   */
  public static void writeDataWaveFile(String wave_file_name, String txt_file_name) throws SManyFilesFoundException, IOException, WavFileException
  {
    SBrowser browser = new SBrowser();
    
    WavFile wavFile = WavFile.openWavFile(browser.findFile(wave_file_name));
    
    StringBuffer s = new StringBuffer();
    byte[] buffer = wavFile.getBuffer();
    
    for(int i = 0; i < buffer.length; i++)
    { 
      s.append(Byte.toString(buffer[i]));
      s.append(SStringUtil.END_LINE_CARACTER);
    }
    
    browser.writeTextFile(txt_file_name, "txt", s.toString());
  }
  
  /**
   * ...
   * 
   * @param file_name Le nom du fichier (sans l'extension).
   * @param channel_number
   * @param duration
   * @return
   * @throws IOException
   * @throws WavFileException
   */
  public static WavFile buildWavFile(String file_name, int channel_number, double duration) throws IOException, WavFileException
  {
    SBrowser browser = new SBrowser();
    
    // Calculate the number of frames required for specified duration
    long numFrames = (long)(duration * SAMPLE_RATE);

    // Retirer l'extension du fichier, s'il y en a.
    file_name = SStringUtil.getFileNameWithoutExtension(file_name);
    
    
    // Create a wav file with the name specified as the first argument
    return WavFile.newWavFile(browser.createFile(file_name, WAV_EXTENSION), channel_number, numFrames, VALID_BITS, SAMPLE_RATE);
  }
  
  /**
   * ...
   * 
   * @param wavFile
   * @param duration
   * @param frequencies
   * @return
   * @throws WavFileException 
   * @throws IOException 
   */
  public static boolean fillWavFile(WavFile wavFile, double duration, double... frequencies) throws IllegalArgumentException, IOException, WavFileException
  {
    if(frequencies.length != wavFile.getNumChannels())
      throw new IllegalArgumentException("Erreur SSoundUtil 001 : Le nombre de fréquence à injecter dans le fichier wav est " + frequencies.length + " mais le fichier wav doit en contenir " + wavFile.getNumChannels() + ".");
    
    if(wavFile.getFramesRemaining() < 0)
      return false;
    
    //------------------------------------------------
    // REMPLIR LE FICHIER WAVE AVEC LA DURÉE DEMANDÉE.
    //------------------------------------------------
    
    // Calculate the number of frames required for specified duration
    long numFrames = (long)(duration * (double)SAMPLE_RATE);

    // Create a buffer of 100 frames
    double[][] buffer = new double[frequencies.length][100];

    // Initialise a local frame counter
    long frameCounter = 0;

    // Loop until all frames written
    while (frameCounter < numFrames && wavFile.getFramesRemaining() > 0 )
    {
       // Determine how many frames to write, up to a maximum of the buffer size
       long remaining = wavFile.getFramesRemaining();
       int toWrite = (remaining > 100) ? 100 : (int) remaining;

       // Fill the buffer, one tone per channel
       for (int s=0 ; s<toWrite ; s++, frameCounter++)
         for(int f = 0; f < frequencies.length; f++)
           buffer[f][s] = Math.sin(2.0 * Math.PI * frequencies[f] * (double)frameCounter / (double)SAMPLE_RATE);
           
       // Write the buffer
       wavFile.writeFrames(buffer, toWrite);
    }
    
    return true;
  }
  
  /**
   * ...
   * 
   * @param note
   * @param octave
   * @return
   * @throws IllegalArgumentException
   */
  public static double frequency(String note, int octave) throws IllegalArgumentException
  {
    if(octave < 0 || octave > 8)
      throw new IllegalArgumentException("Erreur SSoundUtil 002 : L'octave " + octave + " n'est pas supportée. Il doit être situé entre 0 et 8 inclusivement.");
    
    note = note.toUpperCase();
    
    switch (note) {
    
      case "DO" : return DO[octave];
      case "MI" : return MI[octave];
      case "SOL" : return SOL[octave];
      case "LA" : return LA[octave];
      
      default : throw new IllegalArgumentException("Erreur SSoundUtil 003 : La note " + note + " n'est pas supportée. Les notes reconnues sont les suivantes : " + SStringUtil.join(NOTES, ", "));
    }
  }
  
  
}
