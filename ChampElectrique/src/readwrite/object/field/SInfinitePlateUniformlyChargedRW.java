/**
 * 
 */
package sim.readwrite.object.field;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.math.SImpossibleNormalizationException;
import sim.math.SVector3d;
import sim.math.field.SVectorField;
import sim.physics.field.SInfinitePlateUniformlyCharged;
import sim.readwrite.SAbstractRW;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SInfinitePlateUniformlyChargedRW</b> représente une plaque infinie uniformément chargée pouvant être lu et écrit dans un fichier.
 * 
 * @author Simon Vezina
 * @since 2022-02-03
 * @version 2022-02-28
 */
public class SInfinitePlateUniformlyChargedRW extends SAbstractRW implements SVectorFieldRW {

  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = {
    SKeyWordDecoder.KW_POSITION, 
    SKeyWordDecoder.KW_NORMAL, 
    SKeyWordDecoder.KW_SURFACE_CHARGE_DENSITY
  };
  
  private final static SVector3d DEFAULT_POSITION = SVector3d.ORIGIN;
  private final static SVector3d DEFAULT_NORMAL = SVector3d.UNITARY_Z;
  private final static double DEFAULT_SURFACE_CHARGE_DENSITY = 1e-9;
  
  private SVector3d position;
  private SVector3d normal;
  private double surface_charge_density;
  
  /**
   * 
   */
  public SInfinitePlateUniformlyChargedRW() 
  {
    this.position = DEFAULT_POSITION;
    this.normal = DEFAULT_NORMAL;
    this.surface_charge_density = DEFAULT_SURFACE_CHARGE_DENSITY;
  }

  /**
   * ...
   * 
   * @param sbr
   * @throws SReadingException
   */
  public SInfinitePlateUniformlyChargedRW(SBufferedReader sbr) throws SReadingException
  {
    this();   
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SReadingException("Erreur SInfinitePlateUniformlyChargedRW 001 : Une erreur d'initialisation est survenue.", e);
    }
  }

  @Override
  public String getRWName() 
  {
    return SKeyWordDecoder.KW_INFINITE_PLATE_UNIFORMLY_CHARGED;
  }

  /**
   * Méthode pour convertir cet objet en <b>SInfinitePlateUniformlyCharged</b> étant de type <b>SVectorField</b>.
   * 
   * @return La plaque uniformément chargée.
   */
  @Override
  public SVectorField toVectorField() 
  {
    return new SInfinitePlateUniformlyCharged(this.position, this.normal, this.surface_charge_density);
  }

  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    bw.write(SKeyWordDecoder.KW_POSITION);
    bw.write("\t");
    this.position.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_NORMAL);
    bw.write("\t");
    this.normal.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_SURFACE_CHARGE_DENSITY);
    bw.write("\t\t\t");
    bw.write(Double.toString(this.surface_charge_density));
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  
  @Override
  protected void initialization() throws SInitializationException
  {
    try {
      this.normal = normal.normalize();
    }catch(SImpossibleNormalizationException e) {
      throw new SInitializationException("Erreur SInfinitePlateUniformlyChargedRW 002 : La normale à la surface ne peut pas être normalisée.", e);
    }
  }
  
  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_POSITION : this.position = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_POSITION); return true;
      case SKeyWordDecoder.CODE_NORMAL : this.normal = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_NORMAL); return true;
      case SKeyWordDecoder.CODE_SURFACE_CHARGE_DENSITY : this.surface_charge_density = this.readDouble(remaining_line,SKeyWordDecoder.KW_SURFACE_CHARGE_DENSITY); return true;
      
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
