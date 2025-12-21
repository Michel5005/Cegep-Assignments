/**
 * 
 */
package sim.readwrite.object.field;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.math.SVector3d;
import sim.math.field.SVectorField;
import sim.physics.field.SSphereUniformlyCharged;
import sim.readwrite.SAbstractRW;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SSphereUniformlyChargedRW</b> représente ...
 * 
 * @author Simon Vezina
 * @since 2022-02-01
 * @version 2022-02-28
 */
public class SSphereUniformlyChargedRW extends SAbstractRW implements SVectorFieldRW {

  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = {
    SKeyWordDecoder.KW_POSITION, 
    SKeyWordDecoder.KW_RAY, 
    SKeyWordDecoder.KW_ELECTRIC_CHARGE
  };
  
  private final static SVector3d DEFAULT_POSITION = SVector3d.ORIGIN;
  private final static double DEFAULT_RAY = 1.0;
  private final static double DEFAULT_ELECTRIC_CHARGE = 1e-9;
  
  /**
   * ...
   */
  private SVector3d position;
  
  /**
   * ...
   */
  private double ray;
  
  /**
   * ...
   */
  private double electric_charge;
  
  /**
   * ...
   */
  public SSphereUniformlyChargedRW()
  {
    super();
    
    this.position = DEFAULT_POSITION;
    this.ray = DEFAULT_RAY;
    this.electric_charge = DEFAULT_ELECTRIC_CHARGE;
  }
  
  /**
   * ...
   * 
   * @param sbr
   * @throws SReadingException
   */
  public SSphereUniformlyChargedRW(SBufferedReader sbr) throws SReadingException
  {
    this();   
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SReadingException("Erreur SSphereUniformlyChargedRW 001 : Une erreur d'initialisation est survenue.", e);
    }
  }
  
  /**
   * Méthode pour convertir cet objet en <b>SSphereUniformlyCharged</b> étant de type <b>SVectorField</b>.
   * 
   * @return La sphère uniformément chargée.
   */
  @Override
  public SVectorField toVectorField()
  {
    return new SSphereUniformlyCharged(this.position, this.ray, this.electric_charge);
  }
  
  @Override
  public String getRWName() 
  {
    return SKeyWordDecoder.KW_SPHERE_UNIFORMLY_CHARGED;
  }

  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    bw.write(SKeyWordDecoder.KW_POSITION);
    bw.write("\t");
    this.position.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_RAY);
    bw.write("\t\t\t");
    bw.write(Double.toString(this.ray));
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_ELECTRIC_CHARGE);
    bw.write("\t\t\t");
    bw.write(Double.toString(this.electric_charge));
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
    
  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_POSITION : this.position = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_POSITION); return true;
      case SKeyWordDecoder.CODE_RAY : this.ray = this.readDoubleGreaterThanZero(remaining_line, SKeyWordDecoder.KW_RAY); return true;
      case SKeyWordDecoder.CODE_ELECTRIC_CHARGE : this.electric_charge = this.readDouble(remaining_line,SKeyWordDecoder.KW_ELECTRIC_CHARGE); return true;
      
      default : return super.read(sbr, code, remaining_line);
    }
  }
  
  @Override
  public String toString() {
    return "SSphereUniformlyChargedRW [position=" + position + ", ray=" + ray + ", electric_charge=" + electric_charge
        + "]";
  }

  @Override
  public String[] getRWParameterName()
  {
    String[] other_parameters = super.getRWParameterName();
    
    return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
  }
  
}
