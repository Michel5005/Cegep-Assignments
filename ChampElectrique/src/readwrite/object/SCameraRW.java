/**
 * 
 */
package sim.readwrite.object;

import java.io.BufferedWriter;
import java.io.IOException;

import sim.graphics.camera.SCamera;
import sim.graphics.camera.SVectorCamera;
import sim.math.SImpossibleNormalizationException;
import sim.math.SVector3d;
import sim.readwrite.SAbstractRW;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SCameraRW</b> représente une camera initialisée lors d'une lecture en fichier et pouvant être écrite dans un fichier texte.
 * 
 * @author Simon Vézina
 * @since 2017-11-16
 * @version 2022-06-02
 */
public class SCameraRW extends SAbstractRW {

  /**
   * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
   * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
   */
  private static final String[] KEYWORD_PARAMETER = {
    SKeyWordDecoder.KW_POSITION,
    SKeyWordDecoder.KW_UP, 
    SKeyWordDecoder.KW_LOOK_AT,
    SKeyWordDecoder.KW_ANGLE, 
    SKeyWordDecoder.KW_NEAR_CLIPPING_PLANE, 
    SKeyWordDecoder.KW_FAR_CLIPPING_PLANE 
  };
  
  /**
   * La constante <b>DEFAULT_POSITION</b> correspond à la position de la caméra par défaut.
   */
  private static final SVector3d DEFAULT_POSITION = new SVector3d(0.0, 0.0, 0.0);
  
  /**
   * La constante <b>DEFAULT_LOOK_AT</b> correspond à la direction vers où la caméra regarde par défaut.
   */
  private static final SVector3d DEFAULT_LOOK_AT = new SVector3d(0.0, 0.0, 1.0);
  
  /**
   * La constante <b>DEFAULT_UP</b> correspond à la direction verticale (le haut) de la caméra par défaut.
   */
  private static final SVector3d DEFAULT_UP = new SVector3d(0.0, 1.0, 0.0);
  
  /**
   * La constante <b>DEFAULT_VIEW_ANGLE</b> correspond à l'angle d'ouverture vertical de la caméra par défaut étant égal à {@value} degrés.
   */
  private static final double DEFAULT_VIEW_ANGLE = 60.0;
  
  /**
   * <p>
   * La constante <b>DEFAULT_ZNEAR</b> correspond à distance à partir de laquelle la caméra <b>peut voir</b> par défaut étant égale à {@value}.
   * </p>
   * <p>
   * <b>REMARQUE</b> : Il est important de déclarer que le z-near, ne peut pas être une valeur infinitésimal. Le système semble être non fonctionnel lorsque z-near < 0.00001.
   * Il est donc important de ne pas avoir une valeur trop faible.
   * </p>
   * <p>
   * Référence : https://www.khronos.org/opengl/wiki/Depth_Buffer_Precision
   * <ul>
   * You may have configured your zNear and zFar clipping planes in a way that severely limits your depth buffer precision. 
   * Generally, this is caused by a zNear clipping plane value that's too close to 0.0. As the zNear clipping plane is set increasingly closer to 0.0, the effective precision of the depth buffer decreases dramatically. 
   * Moving the zFar clipping plane further away from the eye always has a negative impact on depth buffer precision, but it's not one as dramatic as moving the zNear clipping plane.
   * </ul>
   * </p>
   */
  protected static final double DEFAULT_ZNEAR = 0.01;
  
  /**
   * La constante <b>DEFAULT_ZFAR</b> correspond à distance à partir de laquelle la caméra <b>ne peut plus voir</b> par défaut étant égale à {@value}.
   */
  protected static final double DEFAULT_ZFAR = 1000.0;

  /**
   * La constante <b>MINIMUM_CAMERA_ANGLE</b> correspond à l'angle minimal d'ouverture d'une camera étant égal à {@value} degrés.
   */
  private static final double MINIMUM_CAMERA_ANGLE = 10;
  
  /**
   * La constante <b>MAXIMUM_CAMERA_ANGLE</b> correspond à l'angle maximal d'ouverture d'une camera étant égal à {@value} degrés.
   */
  private static final double MAXIMUM_CAMERA_ANGLE = 170;
  
  //-------------
  // VARIABLES //
  //-------------
  
  /**
   * La variable <b>position</b> représente la position de la caméra.
   */
  private SVector3d position; 
  
  /**
   * La variable <b>look_at</b> représente la position où la caméra regarde.
   * Cette information permet de déterminer dans quelle direction la caméra pointe.
   */
  private SVector3d look_at;  
    
  /**
   * La variable <b>up</b> correspond à la définition du haut pour la caméra.
   */
  private SVector3d up;   

  /**
   * La variable <b>view_angle</b> correspond à l'angle d'ouverture de la caméra dans la direction verticale (up).
   * Cet élément se retrouve entre autre dans la fonction gluPerspective (paramètre <i>fovy</i>)de la librairie OpenGl pour faire la construction de la pyramide de vue.
   * Cette valeur doit être en <b>degré</b>.
   */
  private double view_angle;  
  
  /**
   * La variable <b>z_near</b> correspond à la distance <b>la plus près</b> pouvant être observée par la caméra.
   * Cette valeur doit toujours être <b>positive</b>. Elle correspond également la distance entre la caméra et le <i>near clipping plane</i> de la pyramide de vue.
   */
  private double z_near;      
  
  /**
   * Specifies the distance from the viewer to the far clipping plane (always positive). 
   */
  private double z_far;
  
  /**
   * 
   */
  public SCameraRW() 
  {
    this.position = DEFAULT_POSITION;
    this.look_at = DEFAULT_LOOK_AT;
    this.up = DEFAULT_UP;
    this.view_angle = DEFAULT_VIEW_ANGLE;
    this.z_near = DEFAULT_ZNEAR;
    this.z_far = DEFAULT_ZFAR;
  }
  
  /**
   * ...
   * 
   * @param sbr
   * @throws SReadingExceltion
   */
  public SCameraRW(SBufferedReader sbr) throws SReadingException
  {
    this();   
    
    try{
      read(sbr);
    }catch(SInitializationException e){
      throw new SReadingException("Erreur SCameraRW 001 : Une erreur d'initialisation est survenue.", e);
    }
  }

  /**
   * ...
   * 
   * @return
   */
  public SCamera toCamera()
  {
    return new SCamera(this.position, this.look_at, this.up, this.view_angle, this.z_near, this.z_far);
  }
  
  /**
   * ...
   * 
   * @return
   */
  public SVectorCamera toVectorCamera()
  {
    return new SVectorCamera(this.position, this.look_at, this.up, this.view_angle, this.z_near, this.z_far);
  }
    
  @Override
  public String getRWName()
  {
    return SKeyWordDecoder.KW_CAMERA;
  }

  @Override
  public void writeInformation(BufferedWriter bw) throws IOException
  {
    bw.write(SKeyWordDecoder.KW_POSITION);
    bw.write("\t");
    position.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_LOOK_AT);
    bw.write("\t\t");
    look_at.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_UP);
    bw.write("\t\t");
    up.write(bw);
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_ANGLE);
    bw.write("\t\t\t");
    bw.write(Double.toString(view_angle));
    bw.write(" degrees");
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_NEAR_CLIPPING_PLANE);
    bw.write("\t");
    bw.write(Double.toString(z_near));
    bw.write(SStringUtil.END_LINE_CARACTER);
    
    bw.write(SKeyWordDecoder.KW_FAR_CLIPPING_PLANE);
    bw.write("\t");
    bw.write(Double.toString(z_far));
    bw.write(SStringUtil.END_LINE_CARACTER);
  }
  
  @Override
  protected void initialization() throws SInitializationException
  {
    SVector3d front;
    
    try{
      
      // Construction du vecteur "front = devant" qui doivent être normalisés
      front = (look_at.substract(position)).normalize();
      
    }catch(SImpossibleNormalizationException e){
      throw new SInitializationException("Erreur SCameraRW 002 : Le vecteur look_at = " + look_at + " est le vecteur position = " + position + " ne permet pas de construire un vecteur désignant l'orientation avant de la camera normalisé.", e);
    }
    
    try {
      
      // Construction du vecteur "up = haut" à partir des informations fournies
      // afin de le rendre purement perpendiculaire au vecteur front (tout en étant le le plan initial et gardant le même sens)
      up = front.cross(up).cross(front).normalize();
      
    }catch(SImpossibleNormalizationException e){
      throw new SInitializationException("Erreur SCameraRW 003 : Un vecteur up n'a pas pu être normalisé.", e);
    }
    
    //Vérifier que l'écran de fond est plus loin que l'écran de face
    if(z_far < z_near)
      throw new SInitializationException("Erreur SCameraRW 004 : La distance à l'écran de fond '" + z_far + "' ne peut pas être plus près que la distance à l'écran de face '" + z_near + "'.");  
  }
  
  @Override
  protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException
  {
    switch(code)
    {
      case SKeyWordDecoder.CODE_POSITION : position = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_POSITION); return true;
      
      case SKeyWordDecoder.CODE_UP :       up = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_UP); return true;
      
      case SKeyWordDecoder.CODE_LOOK_AT :  look_at = this.readSVector3d(remaining_line, SKeyWordDecoder.KW_LOOK_AT); return true;
      
      case SKeyWordDecoder.CODE_ANGLE : view_angle = this.readDoubleEqualOrGreaterThanValueAndEqualOrSmallerThanValue(remaining_line, MINIMUM_CAMERA_ANGLE, MAXIMUM_CAMERA_ANGLE, SKeyWordDecoder.KW_ANGLE); return true;
      
      case SKeyWordDecoder.CODE_NEAR_CLIPPING_PLANE : z_near = readDoubleGreaterThanZero(remaining_line, SKeyWordDecoder.KW_NEAR_CLIPPING_PLANE); return true;
      
      case SKeyWordDecoder.CODE_FAR_CLIPPING_PLANE : z_far = readDoubleGreaterThanZero(remaining_line, SKeyWordDecoder.KW_FAR_CLIPPING_PLANE); return true;
      
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
