/**
 * 
 */
package sim.physics;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.exception.SConstructorException;
import sim.math.SVector3d;
import sim.util.SAbstractReadableWriteable;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SParticle</b> représente une particule physique.
 * 
 * @author Simon Vézina
 * @since 2016-02-01
 * @version 2017-06-05
 */
public class SParticle extends SAbstractReadableWriteable {

  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = { SKeyWordDecoder.KW_POSITION, SKeyWordDecoder.KW_ELECTRIC_CHARGE };
  
  /**
   * La constante <b>DEFAULT_POSITION</b> représente la position par défaut d'une particule étant à l'origine.
   */
  private static final SVector3d DEFAULT_POSITION = SVector3d.ORIGIN;
  
  /**
   * La constante <b>DEFAULT_ELECTRIC_CHARGE</b> représente la charge électrique par défaut d'une particule étant égale à {@value}.
   */
  private static final double DEFAULT_ELECTRIC_CHARGE = 0.0;
  
  //------------
  // VARIABLE //
  //------------
  
  /**
   * La variable <b>position</b> représente la position d'une particule.
   */
  private SVector3d position;
  
  /**
   * La variable <b>e_charge</b> représente la charge électrique d'une particule.
   */
  private double electric_charge;
  
  //----------------
  // CONSTRUCTEUR //
  //----------------
  
  /**
   * Constructeur d'une particule.
   * 
   * @param q La charge de la particule.
   * @param position La position d'une particule.
   */
  public SParticle(double q, SVector3d position)
  {
    this.electric_charge = q;
    this.position = position;
  }

  /**
   * Constructeur d'une particule avec lecture.
   * 
   * @param sbr Le buffer de lecture.
   * @throws IOException Si une erreur de de type I/O est lancée.
   * @throws SConstructorException Si une ereur est survenue lors de la construction de la particule.
   */
  public SParticle(SBufferedReader sbr) throws IOException, SConstructorException
  {
    this(DEFAULT_ELECTRIC_CHARGE, DEFAULT_POSITION);
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SConstructorException("Erreur SParticle 001 : Une erreur d'initialisation est survenue." + SStringUtil.END_LINE_CARACTER + "\t" + e.getMessage(), e);
    }
  }
  
  //------------
  // MÉTHODES //
  //------------
  
  /**
   * Méthode pour obtenir la position de la particule.
   * 
   * @return La position de la particule.
   */
  public SVector3d getPosition()
  {
    return position;
  }
  
  /**
   * Méthode pour obtenir la charge électrique d'une particule.
   * 
   * @return La charge électrique.
   */
  public double getElectricCharge()
  {
    return electric_charge;
  }

  @Override
  public void write(BufferedWriter bw) throws IOException 
  {
    bw.write(SKeyWordDecoder.KW_PARTICLE);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    //Écrire les propriétés de la classe SSphereGeometry et ses paramètres hérités
    writeSParticleParameter(bw);
    
    bw.write(SKeyWordDecoder.KW_END);
    bw.write(SStringUtil.END_LINE_CARACTER);
    bw.write(SStringUtil.END_LINE_CARACTER);
  }

  /**
   * Méthode pour écrire les paramètres associés à la classe SParticle et ses paramètres hérités.
   * @param bw Le BufferedWriter écrivant l'information dans un fichier txt.
   * @throws IOException Si une erreur I/O s'est produite.
   * @see IOException
   */
  protected void writeSParticleParameter(BufferedWriter bw) throws IOException
  {
    bw.write(SKeyWordDecoder.KW_POSITION);
    bw.write("\t");
    position.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_ELECTRIC_CHARGE);
    bw.write("\t\t");
    bw.write(Double.toString(electric_charge));
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  
  @Override
  public String getReadableName() 
  {
    return SKeyWordDecoder.KW_PARTICLE;
  }

  @Override
  public String[] getReadableParameterName()
  {
    String[] other_parameters = super.getReadableParameterName();
    
    return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
  }
  
  @Override
  protected void readingInitialization() throws SInitializationException
  {
   
  }

  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException, IOException 
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_POSITION :        position = new SVector3d(remaining_line); return true;
                          
      case SKeyWordDecoder.CODE_ELECTRIC_CHARGE : electric_charge = readDouble(remaining_line, SKeyWordDecoder.KW_ELECTRIC_CHARGE); return true;
          
      default : return false;
    }
  }
  
  @Override
  public String toString() 
  {
    return "SParticle [position=" + position + ", electric_charge=" + electric_charge + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(electric_charge);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((position == null) ? 0 : position.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof SParticle)) {
      return false;
    }
    SParticle other = (SParticle) obj;
    if (Double.doubleToLongBits(electric_charge) != Double.doubleToLongBits(other.electric_charge)) {
      return false;
    }
    if (position == null) {
      if (other.position != null) {
        return false;
      }
    } else if (!position.equals(other.position)) {
      return false;
    }
    return true;
  }
  
}//fin de la classe SParticle
