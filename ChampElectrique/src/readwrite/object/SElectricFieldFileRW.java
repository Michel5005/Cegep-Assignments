/**
 * 
 */
package sim.readwrite.object;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sim.readwrite.SAbstractRW;
import sim.readwrite.object.field.SFieldSetupRW;
import sim.readwrite.object.field.SFiniteRodUniformlyChargedRW;
import sim.readwrite.object.field.SInfinitePlateUniformlyChargedRW;
import sim.readwrite.object.field.SInfiniteRodUniformlyChargedRW;
import sim.readwrite.object.field.SSphereUniformlyChargedRW;
import sim.readwrite.object.field.SVectorFieldRW;
import sim.util.SBufferedReader;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SFileObjectRW</b> représente un fichier contenant des informations 
 * décrivant des objets pouvant être lu et écrit par l'interface SRW.
 * 
 * @author Simon Vezina
 * @since 2022-02-01
 * @version 2022-03-04
 */
public class SElectricFieldFileRW extends SAbstractRW {

  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = {
      SKeyWordDecoder.KW_CAMERA,
      SKeyWordDecoder.KW_FIELD_SETUP,
      SKeyWordDecoder.KW_SPHERE_UNIFORMLY_CHARGED,
      SKeyWordDecoder.KW_INFINITE_PLATE_UNIFORMLY_CHARGED,
      SKeyWordDecoder.KW_PPIUC,
      SKeyWordDecoder.KW_FINITE_ROD_UNIFORMLY_CHARGED,
      SKeyWordDecoder.KW_INFINITE_ROD_UNIFORMLY_CHARGED
  };
  
  /**
   * ...
   */
  protected SFieldSetupRW field_setup;
  
  /**
   * ...
   */
  private final List<SCameraRW> camera_list; 
    
  /**
   * ...
   */
  private final List<SVectorFieldRW> vector_field_list;
  
  /**
   * ...
   */
  public SElectricFieldFileRW()
  {
    super();
    
    this.field_setup = new SFieldSetupRW();
    this.camera_list = new ArrayList<SCameraRW>();
    this.vector_field_list = new ArrayList<SVectorFieldRW>();
  }
  
  /**
   * ...
   * 
   * @param file_name
   */
  public SElectricFieldFileRW(String file_name) 
  {
    super(file_name);
    
    this.field_setup = new SFieldSetupRW();
    this.camera_list = new ArrayList<SCameraRW>();
    this.vector_field_list = new ArrayList<SVectorFieldRW>();
  }

  /**
   * ...
   * 
   * @return
   */
  public SFieldSetupRW getFieldSetup()
  {
    return this.field_setup;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public List<SCameraRW> getCameraRWList()
  {
    return this.camera_list;
  }
  
  /**
   * ...
   * 
   * @return
   */
  public List<SVectorFieldRW> getVectorFieldRWList()
  {
    return this.vector_field_list;
  }
  
  @Override
  public String getRWName() 
  {
    return SKeyWordDecoder.KW_FILE;
  }

  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    // Écrire l'ensemble des cameras.
    for(SCameraRW c : this.camera_list)
      c.write(bw);
    
    // Écriture de la configuration du champ.
    this.field_setup.write();
    
    // Écrire l'ensemble des champs vectoriels.
    for(SVectorFieldRW f : this.vector_field_list)
      f.write(bw);
  }
  
  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_CAMERA : this.camera_list.add(new SCameraRW(sbr)); return true; 
    
      case SKeyWordDecoder.CODE_FIELD_SETUP : this.field_setup = new SFieldSetupRW(sbr); return true;
      
      case SKeyWordDecoder.CODE_SPHERE_UNIFORMLY_CHARGED : this.vector_field_list.add(new SSphereUniformlyChargedRW(sbr)); return true;
      case SKeyWordDecoder.CODE_INFINITE_PLATE_UNIFORMLY_CHARGED : this.vector_field_list.add(new SInfinitePlateUniformlyChargedRW(sbr)); return true;
      case SKeyWordDecoder.CODE_FINITE_ROD_UNIFORMLY_CHARGED : this.vector_field_list.add(new SFiniteRodUniformlyChargedRW(sbr)); return true;
      case SKeyWordDecoder.CODE_INFINITE_ROD_UNIFORMLY_CHARGED : this.vector_field_list.add(new SInfiniteRodUniformlyChargedRW(sbr)); return true;
      
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
