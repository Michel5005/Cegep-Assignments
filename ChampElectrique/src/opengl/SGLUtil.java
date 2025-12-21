/**
 * 
 */
package sim.opengl;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import sim.graphics.SColor;
import sim.math.SQuaternion;
import sim.math.SVector3d;
import sim.util.SBrowser;
import sim.util.SManyFilesFoundException;
import sun.misc.Unsafe;

/**
 * La classe <b>SGLUtil</b> représente une classe utilitaire en lien avec des fonctionnalités de la librairie opengl de JOGL.
 * 
 * @author Simon Vézina
 * @since 2016-06-12
 * @version 2022-10-04 (Version labo : Le champ électrique)
 */
public class SGLUtil {

  /**
   * Méthode pour retirer des <i>warning access</i> (message en rouge) engendrés par la librairie de JOGL.
   */
  public static void removeAccessWarning()
  {
    int jdk_version = Integer.parseInt(System.getProperty("java.specification.version"));
    
    if(jdk_version < 13)
      removeAccessWarningJDK11andLess();
    else
      System.out.println("Message GLUTIL --- La version de JDK est " + jdk_version + " et présentement, il n'est pas clair que l'on peut y retirer les messages de type WARNING en ROUGE.");      
  }
  
  /**
   * <p>
   * Méthode pour retirer des <i>warning access</i> engendrés par la librairie de JOGL sous JDK-11 et moins.
   * Il est à noté que cette méthode n'a pas encore été testé pour des versions plus à jour que JDK-11.
   * </p>
   * 
   * <p>
   * Pour le fonctionnement de cette méthode, l'importation de <u>import sun.misc.Unsafe;</u> est requis.
   * </p>
   */
  public static void removeAccessWarningJDK11andLess()
  {
    try {
    	
      final java.lang.reflect.Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
      theUnsafe.setAccessible(true);
      final Unsafe unsafe = (Unsafe) theUnsafe.get(null);

      final Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
      final java.lang.reflect.Field logger = cls.getDeclaredField("logger");
      unsafe.putObjectVolatile(cls, unsafe.staticFieldOffset(logger), null);
      
      }catch(NoSuchFieldException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }catch(IllegalAccessException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }catch(ClassNotFoundException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
  }
  
  /**
   * Méthode permettant de convertir un <b>SVector3d</b> en <b>FloatBuffer</b>.
   * 
   * @param v Le vecteur à convertir.
   * @return Le buffer converti.
   */
	public static FloatBuffer SVector3dToFloatBuffer(SVector3d v)
	{
	  float[] tab = { (float)v.getX(), (float)v.getY(), (float)v.getZ(), 0.0f };
	  
    return FloatBuffer.wrap(tab);
	}
	
	/**
	 * ...
	 * 
	 * @param c
	 * @return
	 */
	public static FloatBuffer SColorToFloatBuffer(SColor c)
	{
	  float[] tab = { (float)c.getRed(), (float)c.getGreen(), (float)c.getBlue(), 1.0f };
    
    return FloatBuffer.wrap(tab);
	}
	
	/**
	 * ...
	 * 
	 * @param c
	 * @param transparency
	 * @return
	 */
	public static FloatBuffer SColorToFloatBuffer(SColor c, float transparency)
  {
    float[] tab = { (float)c.getRed(), (float)c.getGreen(), (float)c.getBlue(), 1.0f - transparency };
    
    return FloatBuffer.wrap(tab);
  }
	
	/**
	 * ...
	 * 
	 * @param c
	 * @return
	 */
	public static float[] SColorToFloatArray(SColor c)
	{
	  float[] tab = { (float)c.getRed(), (float)c.getGreen(), (float)c.getBlue() };
    
    return tab;
	}
	/**
	 * ...
	 * 
	 * @param gl
	 * @param scale
	 * @param rotation
	 * @param translation
	 */
	public static void pushTransformationMatrix(final GL2 gl, SVector3d scale, SVector3d rotation, SVector3d translation)
	{
	  // Informer OpenGL qu'il y aura une nouvelle matrice sur la pile
	  gl.glPushMatrix();
	  
	  // Appliquer les matrices dans l'ordre inverse de l'exécution.
	  // L'ordre d'exécution désiré à appliquer par multiplication au vecteur est :
	  // 1) scale
	  // 2) rotation x
	  // 3) rotation y
	  // 4) rotation z
	  // 5) translation
	  
	  gl.glTranslated(translation.getX(), translation.getY(), translation.getZ() ); // translation
    
    gl.glRotated(rotation.getZ(), 0.0, 0.0, 1.0); // rotation z
    gl.glRotated(rotation.getY(), 0.0, 1.0, 0.0); // rotation y
    gl.glRotated(rotation.getX(), 1.0, 0.0, 0.0); // rotation x
    
    gl.glScaled(scale.getX(), scale.getY(), scale.getZ());  // scale
	}
	
	/**
	 * Méthode pour pousser sur la pile des matrices de transformation les matrice de <i>scalse</i>, de rotation par l'usage d'un quaternion et de translation.
	 * 
	 * @param gl
	 * @param scale
	 * @param q
	 * @param translation
	 */
	public static void pushTransformationMatrix(final GL2 gl, SVector3d scale, SQuaternion q, SVector3d translation)
	{
	  // Informer OpenGL qu'il y aura une nouvelle matrice sur la pile
    gl.glPushMatrix();
    
    // Appliquer les matrices dans l'ordre inverse de l'exécution.
    // L'ordre d'exécution désiré à appliquer par multiplication au vecteur est :
    // 1) scale
    // 2) rotation du quaternion
    // 3) translation
    
    gl.glTranslated(translation.getX(), translation.getY(), translation.getZ() ); // translation
    
    SVector3d axis = q.rotationAxis();
    gl.glRotated(Math.toDegrees(q.rotationAngle()), axis.getX(), axis.getY(), axis.getZ()); // rotation 
        
    gl.glScaled(scale.getX(), scale.getY(), scale.getZ());  // scale
	}
		
	/**
	 * ...
	 * 
	 * @param gl
	 */
	public static void popTransformationMatrix(final GL2 gl)
	{
	  gl.glPopMatrix();
	}
	
	/**
	 * Méthode pour définir la couleur des prochains vertexs envoyés au <i>pipeline</i>.
	 * 
	 * @param gl
	 * @param color
	 */
	public static void setColor(final GL2 gl, SColor color)
	{
	  gl.glColor3d(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	/**
	 * Méthode pour attribuer une texture aux prochains vertexs envoyés au <i>pipeline</i>.
	 * 
	 * @param gl
	 * @param gl_texture_id
	 */
	public static void bindTexture(final GL2 gl, int gl_texture_id)
	{
	  gl.glBindTexture(GL2.GL_TEXTURE_2D, gl_texture_id);
	}
	
	/**
   * ...
   * 
   * @param gl
   * @param texture_file_name
   */
  public static void bindTexture(final GL2 gl, String texture_file_name) throws IllegalArgumentException
  {
    // Accéder au numéro d'identifiant OpenGL de la texture.
    int id = SGLTextureHandler.getTextureID(gl, texture_file_name);
   
    // Vérification que la texture possède un numéro d'identification dans OpenGL.
    if(id == SGLTextureHandler.NO_TEXTURE_ID)
      throw new IllegalArgumentException("Erreur SGLUtil 001 : La texture '" + texture_file_name + "' n'a pas de id de référence.");
   
    // Lier la texture.
    bindTexture(gl, id);      
  }
  
	/**
	 * Méthode pour charger une texture en mémoire.
	 * 
	 * @param gl La librairie OpenGL 2. 
	 * @param file_name Le nom du fichier à charger.
	 * @return Le code d'accès OpenGL en mémoire de la texture chargée. 
	 * @throws SManyFilesFoundException S'il y a eu plusieurs fichiers de trouvé avec ce même nom.
	 * @throws IOException Une erreur de type I/O est survenue lors du chargement de la texture.
	 * @throws GLException Une erreur de type GL est survenue lors du chargement de la texture.
	 */
	public static int loadTexture(final GL2 gl, String file_name) throws SManyFilesFoundException, GLException, IOException
	{
	  SBrowser browser = new SBrowser();
    File file = browser.findFile(file_name);
	    	    
    Texture texture = TextureIO.newTexture(file, true);
      
    return texture.getTextureObject(gl);
	}
	
	/**
	 * Méthode pour générer à l'aide d'opengl la matrice de transformation associé au positionnement de la caméra et son orientation.
	 * 
	 * @param position
	 * @param look_at
	 * @param up
	 */
	public static void lookAtPushMatrix(SVector3d position, SVector3d look_at, SVector3d up)
	{
	  // Charger la version des fonctionnalités de GLU
    final GLU glu = new GLU();
    
    // Utiliser les fonctionnalités de la librairie GLU pour faire la transformation de l'orientation de la caméra
    glu.gluLookAt(position.getX(), position.getY(), position.getZ(),
                  look_at.getX(), look_at.getY(), look_at.getZ(),
                  up.getX(), up.getY(), up.getZ());
	}
	
}
