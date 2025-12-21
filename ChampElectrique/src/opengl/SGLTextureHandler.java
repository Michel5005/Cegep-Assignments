/**
 * 
 */
package sim.opengl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;

import sim.util.SLog;
import sim.util.SManyFilesFoundException;

/**
 * ...
 * 
 * @author Simon Vezina
 * @since 2020-06-25
 * @version 2020-06-25
 */
public class SGLTextureHandler {

  /**
   * ...
   */
  public final static Integer NO_TEXTURE_ID = -1;
  
  /**
   * ...
   */
  private final static Map<String, Integer> opengl_texture_id_map = new HashMap<String, Integer>();
  
  /**
   * ...
   * 
   * @param gl
   * @param file_name
   * @return
   */
  public static int getTextureID(GL2 gl, String file_name)
  {
    // Cas particulier : Si le nom du fichier est null.
    if(file_name == null)
      return NO_TEXTURE_ID;
    
    // Obtenir le ID de la texture si elle a été chargé (avec ou sans succès).
    if(opengl_texture_id_map.containsKey(file_name))
      return opengl_texture_id_map.get(file_name).intValue();
    else
    {
      // Chargement de la texture en mémoire pour OpenGL.
      int id;
      
      try {
        // Chargement de la texture en mémoire pour Opengl.
        id = SGLUtil.loadTexture(gl, file_name);
      } catch (SManyFilesFoundException | GLException | IOException e) {
        // Erreur dans le chargement, donc code sans texture.
        SLog.logWriteLine("Message SGLTextureHandler - La texture '" + file_name + "' n'a pas été chargé dans OpenGL.");
        id = NO_TEXTURE_ID;
      }
      
      // Mettre le ID de la texture dans la map (même si le code est sans texture).
      opengl_texture_id_map.put(file_name, id);
      
      return id;
    }    
  }
  
}
