/**
 * 
 */
package sim.readwrite.object.field;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.math.SVector3d;
import sim.readwrite.SAbstractRW;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SFieldSetupRW</b> représente les caractéristiques pour décrire un champ pouvant être lu dans un fichier texte.
 * 
 * @author Simon Vezina
 * @since 2022-04-02
 * @version 2022-04-02
 */
public class SFieldSetupRW extends SAbstractRW {

  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = {
    SKeyWordDecoder.KW_POSITION,
    SKeyWordDecoder.KW_SIZE,
    SKeyWordDecoder.KW_NB,
    SKeyWordDecoder.KW_MULTIPLICATOR_OF_TEN,
    SKeyWordDecoder.KW_MULTIPLICATOR_OF_POWER
  };
  
  private final SVector3d DEFAULT_POSITION = SVector3d.ORIGIN;
  private final double DEFAULT_SIZE = 5.0;
  private final int DEFAULT_NB = 10;
  private final double DEFAULT_MULTIPLICATOR_OF_TEN = 1.0;
  private final int DEFAULT_MULTIPLICATOR_OF_POWER = 0;
  
  private SVector3d position;
  private double size;
  private int nb;
  private double multiplicator_of_ten;
  private int multiplicator_of_power;
  
  /**
   * 
   */
  public SFieldSetupRW()
  {
    super();
    
    this.position = DEFAULT_POSITION;
    this.size = DEFAULT_SIZE;
    this.nb = DEFAULT_NB;
    this.multiplicator_of_ten = DEFAULT_MULTIPLICATOR_OF_TEN;
    this.multiplicator_of_power = DEFAULT_MULTIPLICATOR_OF_POWER;
  }

  /**
   * ...
   * 
   * @param sbr
   * @throws SReadingException
   */
  public SFieldSetupRW(SBufferedReader sbr) throws SReadingException
  {
    this();   
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SReadingException("Erreur SFieldSetupRW 001 : Une erreur d'initialisation est survenue.", e);
    }
  }
  
  /**
   * ...
   * 
   * @return
   */
  public SVector3d getPosition()
  {
    return this.position;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getSize()
  {
    return this.size;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public int getNb()
  {
    return this.nb;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getMultiplicatorOfTen()
  {
    return this.multiplicator_of_ten;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public int getMultiplicatorOfPower()
  {
    return this.multiplicator_of_power;
  }
  
  @Override
  public String getRWName() 
  {
    return SKeyWordDecoder.KW_FIELD_SETUP;
  }

  @Override
  public String[] getRWParameterName()
  {
    String[] other_parameters = super.getRWParameterName();
    
    return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
  }
  
  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    bw.write(SKeyWordDecoder.KW_POSITION);
    bw.write("\t");
    this.position.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_SIZE);
    bw.write("\t");
    bw.write(Double.toString(this.size));
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_NB);
    bw.write("\t");
    bw.write(Integer.toString(this.nb));
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_MULTIPLICATOR_OF_TEN);
    bw.write("\t");
    bw.write(Double.toString(this.multiplicator_of_ten));
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_MULTIPLICATOR_OF_POWER);
    bw.write("\t");
    bw.write(Integer.toString(this.multiplicator_of_power));
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  
  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_POSITION : this.position = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_POSITION); return true;
      case SKeyWordDecoder.CODE_SIZE : this.size = this.readDoubleGreaterThanZero(remaining_line, SKeyWordDecoder.KW_SIZE); return true;
      case SKeyWordDecoder.CODE_NB : this.nb = this.readIntEqualOrGreaterThanValue(remaining_line, 2, SKeyWordDecoder.KW_NB); return true;
      case SKeyWordDecoder.CODE_MULTIPLICATOR_OF_TEN : this.multiplicator_of_ten = this.readDoubleEqualOrGreaterThanValueAndEqualOrSmallerThanValue(remaining_line, 0.0, 10.0, SKeyWordDecoder.KW_MULTIPLICATOR_OF_TEN); return true;
      case SKeyWordDecoder.CODE_MULTIPLICATOR_OF_POWER : this.multiplicator_of_power = this.readIntEqualOrGreaterThanValueAndEqualOrSmallerThanValue(remaining_line, -10, 10, SKeyWordDecoder.KW_MULTIPLICATOR_OF_TEN); return true; 
        
      default : return super.read(sbr, code, remaining_line);
    }
    
  }
}
