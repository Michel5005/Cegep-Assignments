/**
 * 
 */
package sim.readwrite.object.field;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.math.SImpossibleNormalizationException;
import sim.math.SVector3d;
import sim.math.field.SVectorField;
import sim.physics.field.SInfiniteRodUniformlyCharged;
import sim.readwrite.SAbstractRW;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SInfiniteRodUniformlyChargedRW</b> représente une tige infinie uniformément chargée pouvant être lu et écrit dans un fichier.
 * 
 * @author Simon Vezina
 * @since 2022-03-04
 * @version 2022-03-04
 */
public class SInfiniteRodUniformlyChargedRW extends SAbstractRW implements SVectorFieldRW {

  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = {
    SKeyWordDecoder.KW_POSITION, 
    SKeyWordDecoder.KW_ORIENTATION,
    SKeyWordDecoder.KW_LINEAR_CHARGE_DENSITY
  };
  
  private final static SVector3d DEFAULT_POSITION = SVector3d.ORIGIN;
  
  private final static SVector3d DEFAULT_ORIENTATION = SVector3d.UNITARY_Z;
  
  private final static double DEFAULT_LINEAR_CHARGE_DENSITY = 1e-6;
  
  private SVector3d position;
  
  private SVector3d orientation;
  
  private double linear_charge_density;
  
  /**
   * 
   */
  public SInfiniteRodUniformlyChargedRW()
  {
    this.position = DEFAULT_POSITION;
    this.orientation = DEFAULT_ORIENTATION;
    this.linear_charge_density = DEFAULT_LINEAR_CHARGE_DENSITY;
  }

  /**
   * ...
   * 
   * @param sbr
   * @throws SReadingException
   */
  public SInfiniteRodUniformlyChargedRW(SBufferedReader sbr) throws SReadingException
  {
    this();   
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SReadingException("Erreur SInfiniteRodUniformlyChargedRW 001 : Une erreur d'initialisation est survenue.", e);
    }
  }

  /**
   * Méthode pour convertir cet objet en <b>SInfiniteRodUniformlyChargedRW</b> étant de type <b>SVectorField</b>.
   * 
   * @return La plaque uniformément chargée.
   */
  @Override
  public SVectorField toVectorField() 
  {
    return new SInfiniteRodUniformlyCharged(this.position, this.orientation, this.linear_charge_density);
  }
  
  @Override
  public String getRWName()
  {
    return SKeyWordDecoder.KW_INFINITE_ROD_UNIFORMLY_CHARGED;
  }

 
  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    bw.write(SKeyWordDecoder.KW_POSITION);
    bw.write("\t");
    this.position.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_ORIENTATION);
    bw.write("\t");
    this.orientation.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_LINEAR_CHARGE_DENSITY);
    bw.write("\t\t\t");
    bw.write(Double.toString(this.linear_charge_density));
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  
  @Override
  protected void initialization() throws SInitializationException
  {
    try {
      this.orientation = orientation.normalize();
    }catch(SImpossibleNormalizationException e) {
      throw new SInitializationException("Erreur SInfiniteRodUniformlyChargedRW 002 : L'orientation de la tige ne peut pas être normalisée.", e);
    }
  }
  
  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_POSITION : this.position = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_POSITION); return true;
      case SKeyWordDecoder.CODE_ORIENTATION : this.orientation = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_ORIENTATION); return true;
      case SKeyWordDecoder.CODE_LINEAR_CHARGE_DENSITY : this.linear_charge_density = this.readDouble(remaining_line,SKeyWordDecoder.KW_SURFACE_CHARGE_DENSITY); return true;
      
      default : return super.read(sbr, code, remaining_line);
    }
  }
  
  @Override
  public String[] getRWParameterName()
  {
    String[] other_parameters = super.getRWParameterName();
    
    return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
  }
  
}
