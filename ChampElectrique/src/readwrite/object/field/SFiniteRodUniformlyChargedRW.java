/**
 * 
 */
package sim.readwrite.object.field;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.math.SVector3d;
import sim.math.field.SVectorField;
import sim.physics.field.SFiniteRodUniformlyCharged;
import sim.readwrite.SAbstractRW;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SFiniteRodUniformlyChargedRW</b> représente une tige finie uniformément chargée pouvant être lu et écrite dans un fichier.
 * 
 * @author Simon Vezina
 * @since 2022-02-03
 * @version 2022-04-13
 */
public class SFiniteRodUniformlyChargedRW extends SAbstractRW implements SVectorFieldRW {

  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = {
    SKeyWordDecoder.KW_POSITION, 
    SKeyWordDecoder.KW_ELECTRIC_CHARGE
  };
  
  /**
   * La constante <b>DEFAULT_P1</b> correspond à la position du début du tube par défaut.
   */
  private static final SVector3d DEFAULT_P1 = new SVector3d(0.0, 0.0, 0.0);   
  
  /**
   * La constante <b>DEFAULT_P1</b> correspond à la position de fin du tube par défaut.
   */
  private static final SVector3d DEFAULT_P2 = new SVector3d(0.0, 0.0, 1.0);   
  
  /**
   * ...
   */
  private static final double DEFAULT_ELECTRIC_CHARGE = 1e-6;
  
  /**
   * La variable <b>p1</b> correspond à la position du début de la tige.
   */
  private SVector3d p1; 
  
  /**
   * La variable <b>p1</b> correspond à la position de fin de la tige.
   */
  private SVector3d p2; 
  
  /**
   * ...
   */
  private double electric_charge;
  
  /**
   * La variable <b>reading_point</b> correspond au numéro du point qui sera en lecture.
   */
  private int reading_point;
  
  /**
   * 
   */
  public SFiniteRodUniformlyChargedRW() 
  {
    this.p1 = DEFAULT_P1;
    this.p2 = DEFAULT_P2;
    this.electric_charge = DEFAULT_ELECTRIC_CHARGE;
    
    this.reading_point = 0;
  }

  /**
   * ...
   * 
   * @param sbr
   * @throws SReadingException
   */
  public SFiniteRodUniformlyChargedRW(SBufferedReader sbr) throws SReadingException
  {
    this();   
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SReadingException("Erreur SFiniteRodUniformlyChargedRW 001 : Une erreur d'initialisation est survenue.", e);
    }
  }

  @Override
  public String getRWName()
  {
    return SKeyWordDecoder.KW_FINITE_ROD_UNIFORMLY_CHARGED;
  }

  /**
   * Méthode pour convertir cet objet en <b>SFiniteRodUniformlyChargedRW</b> étant de type <b>SVectorField</b>.
   * 
   * @return La tige finie uniformément chargée.
   */
  @Override
  public SVectorField toVectorField()
  {
    return new SFiniteRodUniformlyCharged(this.p1, this.p2, this.electric_charge);
  }

  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    bw.write(SKeyWordDecoder.KW_POSITION);
    bw.write("\t");
    this.p1.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_POSITION);
    bw.write("\t");
    this.p2.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_ELECTRIC_CHARGE);
    bw.write("\t");
    bw.write(Double.toString(this.electric_charge));
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
    
  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_POINT :
      case SKeyWordDecoder.CODE_POSITION : readPosition(remaining_line); return true;
     
      case SKeyWordDecoder.CODE_ELECTRIC_CHARGE : this.electric_charge = this.readDouble(remaining_line,SKeyWordDecoder.KW_ELECTRIC_CHARGE); return true;
      
      default : return super.read(sbr, code, remaining_line);
    }
  }
  
  @Override
  public String[] getRWParameterName()
  {
    String[] other_parameters = super.getRWParameterName();
    
    return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
  }
  
  /**
   * ...
   * 
   * @param remaining_line
   * @throws SReadingException
   */
  private void readPosition(String remaining_line) throws SReadingException
  {
    switch(this.reading_point)
    {
      case 0 :  this.p1 = new SVector3d(remaining_line); break;
      
      case 1 :  SVector3d v = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_POSITION); 
      
                if(v.equals(this.p1))
                  throw new SReadingException("Erreur SFiniteRodUniformlyChargedRW 004 : Le point p2 = " + v + " est identique au point p1 ce qui n'est pas acceptable.");
                
                this.p2 = v; 
                break;
      
      default : throw new SReadingException("Erreur SFiniteRodUniformlyChargedRW 005 : Il y a déjà 2 points de défini.");
    }
    
    reading_point++;      
  }
  
}
