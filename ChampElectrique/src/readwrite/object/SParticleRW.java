/**
 * 
 */
package sim.readwrite.object;

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
 * La classe <b>SParticleRW</b> représente une particule pouvant être lue dans un fichier texte.
 * 
 * @author Simon Vezina
 * @since 2020-07-29
 * @version 2022-04-02
 */
public class SParticleRW extends SAbstractRW {

  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = { 
      SKeyWordDecoder.KW_POSITION, 
      SKeyWordDecoder.KW_SPEED, 
      SKeyWordDecoder.KW_ELECTRIC_CHARGE
  };
  
  /**
   * La constante <b>DEFAULT_POSITION</b> représente la position par défaut d'une particule étant à l'origine.
   */
  private static final SVector3d DEFAULT_POSITION = SVector3d.ORIGIN;
  
  /**
   * La constante <b>DEFAULT_SPEED</b> représente la vitesse par défaut d'une particule étant nulle.
   */
  private static final SVector3d DEFAULT_SPEED = SVector3d.ZERO;
  
  /**
   * La constante <b>DEFAULT_ELECTRIC_CHARGE</b> représente la charge électrique par défaut d'une particule étant égale à {@value}.
   */
  private static final double DEFAULT_ELECTRIC_CHARGE = 0.0;
  
  /**
   * ...
   */
  private static final double DEFAULT_MASS = 1.0;
  
  //------------
  // VARIABLE //
  //------------
  
  /**
   * La variable <b>position</b> représente la position d'une particule.
   */
  private SVector3d position;
  
  /**
   * La variable <b>speed</b> représente la vitesse de la particule.
   */
  private SVector3d speed;
  
  /**
   * ...
   */
  private double mass;
  
  /**
   * La variable <b>e_charge</b> représente la charge électrique d'une particule.
   */
  private double electric_charge;
  
  /**
   * 
   */
  public SParticleRW() 
  {
    this(DEFAULT_MASS, DEFAULT_ELECTRIC_CHARGE, DEFAULT_POSITION);
  }

  /**
   * ...
   * 
   * @param m
   * @param q
   * @param position
   */
  public SParticleRW(double m, double q, SVector3d position)
  {
    this(m, q, position, DEFAULT_SPEED);
  }
  
  /**
   * ...
   * 
   * @param m
   * @param q
   * @param position
   * @param speed
   */
  public SParticleRW(double m, double q, SVector3d position, SVector3d speed)
  {
    this.mass = m;
    this.electric_charge = q;
    this.position = position;
    this.speed = speed;
  }
  /**
   * Constructeur d'une particule à partir d'information lue dans un fichier de format txt.
   * 
   * @param sbr Le BufferedReader cherchant l'information dans le fichier txt.
   * @throws SReadingException Si une erreur est survenue à la construction.
   * @see SBufferedReader
   */
  public SParticleRW(SBufferedReader sbr) throws SReadingException
  {
    this();   
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SReadingException("Erreur SParticleRW 001 : Une erreur d'initialisation est survenue.", e);
    }  
  }

  /**
   * Méthode pour obtenir la position de la particule.
   * 
   * @return La position de la particule.
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
  public SVector3d getSpeed()
  {
    return this.speed;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public double getMass()
  {
    return this.mass;
  }
  
  /**
   * Méthode pour obtenir la charge électrique d'une particule.
   * 
   * @return La charge électrique.
   */
  public double getElectricCharge()
  {
    return this.electric_charge;
  }
  
  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    bw.write(SKeyWordDecoder.KW_MASS);
    bw.write("\t\t");
    bw.write(Double.toString(mass));
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_ELECTRIC_CHARGE);
    bw.write("\t\t");
    bw.write(Double.toString(electric_charge));
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_POSITION);
    bw.write("\t");
    position.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_SPEED);
    bw.write("\t");
    speed.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  
  @Override
  public String getRWName() 
  {
    return SKeyWordDecoder.KW_PARTICLE;
  }

  @Override
  public String[] getRWParameterName()
  {
    String[] other_parameters = super.getRWParameterName();
    
    return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
  }
  
  @Override
  protected void initialization() throws SInitializationException
  {
   
  }

  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException 
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_POSITION :        this.position = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_POSITION); return true;
                       
      case SKeyWordDecoder.CODE_SPEED :           this.speed = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_SPEED); return true;
            
      case SKeyWordDecoder.CODE_MASS :            this.mass = readDouble(remaining_line, SKeyWordDecoder.KW_MASS); return true;
            
      case SKeyWordDecoder.CODE_ELECTRIC_CHARGE : this.electric_charge = readDouble(remaining_line, SKeyWordDecoder.KW_ELECTRIC_CHARGE); return true;
          
      default : return super.read(sbr, code, remaining_line);
    }
  }
  
}
