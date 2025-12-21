/**
 * 
 */
package sim.opengl;

import sim.graphics.SColor;
import sim.math.SQuaternion;
import sim.math.SVector3d;
import sim.math.SVectorUV;

import com.jogamp.opengl.GL2;

/**
 * La classe <b>SGLDisplay</b> représente une classe utilitaire pour faire l'affichage
 * de forme géométrique à l'aide de l'interface OpenGL sous l'usage de la librairie JOGL.
 *  
 * @author Simon Vézina
 * @since 2016-06-12
 * @version 2022-04-12
 */
public class SGLDisplay {

  /**
   * ...
   */
  public static final int DEFAULT_SPHERE_DETAIL = 20;
  
  /**
   * ...
   */
  public static final int DEFAULT_DISK_DETAIL = 20;
  
  /**
   * ...
   */
  public static final int DEFAULT_TUBE_DETAIL = 20;
  
  /**
   * ...
   */
  public static final int DEFAULT_CONE_DETAIL = 15;
  
  /**
   * <p>
   * Méthode permettant de dessiner via l'interface OpenGL un <b>cube unitaire</b> centré à l'origine incluant
   * (1) les coordonnées de texture, (2) la normale à la surface des points et (3) la coordonnée
   * des points formant les carrées du cube. 
   * </p>
   * 
   * <p>
   * Puisque la texture doit préalablement avoir été activée si l'on désire y dessiner un cube texturé,
   * les 6 faces auront la même référence à la texture (les 6 côté seront identiques).
   * </p>
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   */
  public static void displayCube(final GL2 gl)
  {
    // Start Drawing The Cube
    gl.glBegin(GL2.GL_QUADS); 
         
    // Front Face
    gl.glTexCoord2f(0.0f, 0.0f); gl.glNormal3f(0.0f, 0.0f, 1.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 0.0f); gl.glNormal3f(0.0f, 0.0f, 1.0f); gl.glVertex3f( 0.5f, -0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glNormal3f(0.0f, 0.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glNormal3f(0.0f, 0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);

    // Back Face
    gl.glTexCoord2f(1.0f, 0.0f); gl.glNormal3f(0.0f, 0.0f, -1.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glNormal3f(0.0f, 0.0f, -1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glNormal3f(0.0f, 0.0f, -1.0f); gl.glVertex3f( 0.5f, 0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 0.0f); gl.glNormal3f(0.0f, 0.0f, -1.0f); gl.glVertex3f( 0.5f, -0.5f, -0.5f);

    // Top Face
    gl.glTexCoord2f(0.0f, 1.0f); gl.glNormal3f(0.0f, 1.0f, 0.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 0.0f); gl.glNormal3f(0.0f, 1.0f, 0.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 0.0f); gl.glNormal3f(0.0f, 1.0f, 0.0f); gl.glVertex3f( 0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glNormal3f(0.0f, 1.0f, 0.0f); gl.glVertex3f( 0.5f, 0.5f, -0.5f);

    // Bottom Face
    gl.glTexCoord2f(1.0f, 1.0f); gl.glNormal3f(0.0f, -1.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glNormal3f(0.0f, -1.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 0.0f); gl.glNormal3f(0.0f, -1.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 0.0f); gl.glNormal3f(0.0f, -1.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);

    // Right face
    gl.glTexCoord2f(1.0f, 0.0f); gl.glNormal3f(1.0f, 0.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glNormal3f(1.0f, 0.0f, 0.0f); gl.glVertex3f( 0.5f, 0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glNormal3f(1.0f, 0.0f, 0.0f); gl.glVertex3f( 0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(0.0f, 0.0f); gl.glNormal3f(1.0f, 0.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, 0.5f);

    // Left Face
    gl.glTexCoord2f(0.0f, 0.0f); gl.glNormal3f(-1.0f, 0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(1.0f, 0.0f); gl.glNormal3f(-1.0f, 0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glNormal3f(-1.0f, 0.0f, 0.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glNormal3f(-1.0f, 0.0f, 0.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);
    
    gl.glEnd();   // fin GL_QUADS
    
    gl.glFlush(); // envoyer le tout au pipeline 
  }
  
  /**
   * Méthode permettant de dessiner via l'interface OpenGL un <b>cube unitaire</b> avec coordonnée de texture.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param gl_texture_id Le tableau des numéros d'accès aux textures à appliquer sur les 6 faces du cube.
   * @throws IllegalArgumentException Si le tableau d'accès au numéro de texture n'est pas de taille 6.
   */
  public static void displayCube(final GL2 gl, int[] gl_texture_id) throws IllegalArgumentException
  {
    // Validation.
    if(gl_texture_id.length != 6)
      throw new IllegalArgumentException("Erreur SGLGeometry 001 : Le tableau d'accès au numéro de texture est de taille égal à " + gl_texture_id.length + " ce qui n'est pas égal à 6.");
    
    // Affectation de couleur de fond blanc pour l'addition avec la couleur de la texture.
    gl.glColor3d(1.0f, 1.0f, 1.0f);
       
    // Activation des texture 2d.
    gl.glEnable(GL2.GL_TEXTURE_2D);
    
    // Accès à la texture #1.
    gl.glBindTexture(GL2.GL_TEXTURE_2D, gl_texture_id[0]);   
    
    // Start Drawing The Cube
    gl.glBegin(GL2.GL_QUADS); 
    
    // Front Face
    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);

    gl.glEnd();
    gl.glFlush();
    
    // Accès à la texture #2.
    gl.glBindTexture(GL2.GL_TEXTURE_2D, gl_texture_id[1]);
    
    gl.glBegin(GL2.GL_QUADS); 
    
    // Back Face
    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, -0.5f);

    gl.glEnd();
    gl.glFlush();
    
    // Accès à la texture #3.
    gl.glBindTexture(GL2.GL_TEXTURE_2D, gl_texture_id[2]);
    
    gl.glBegin(GL2.GL_QUADS); 
    
    // Top Face
    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, -0.5f);

    gl.glEnd();
    gl.glFlush();
    
    // Accès à la texture #4.
    gl.glBindTexture(GL2.GL_TEXTURE_2D, gl_texture_id[3]);
    
    gl.glBegin(GL2.GL_QUADS); 
    
    // Bottom Face
    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);

    gl.glEnd();
    gl.glFlush();
    
    // Accès à la texture #5.
    gl.glBindTexture(GL2.GL_TEXTURE_2D, gl_texture_id[4]);
    
    gl.glBegin(GL2.GL_QUADS);
    
    // Right face
    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, -0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, 0.5f);

    gl.glEnd();
    gl.glFlush();
    
    // Accès à la texture #6.
    gl.glBindTexture(GL2.GL_TEXTURE_2D, gl_texture_id[5]);
    
    gl.glBegin(GL2.GL_QUADS);
    
    // Left Face
    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);
    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);
    
    gl.glEnd();
    gl.glFlush();
    
    // Désactivation des textures.
    gl.glDisable(GL2.GL_TEXTURE_2D);
  }
    
  /**
   * Méthode permettant de dessiner une sphere positionnée en son centre.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param detail Le niveau de détail de la sphère influencant le nombre de carrés nécessaire pour dessiner la sphère.
   */
  public static void displaySphere(final GL2 gl, int detail)
  {
    if(detail < DEFAULT_SPHERE_DETAIL)
      detail = DEFAULT_SPHERE_DETAIL;
    
    float x,y,z;
    float theta1, theta2, theta3;

    final float PI_VALUE = (float) Math.PI;
        
    for(int j=0; j <= detail/2 - 1; j++)
    {
      theta1 = j*2.0f*PI_VALUE/detail - PI_VALUE/2.0f;
      theta2 = (j+1)*2.0f*PI_VALUE/detail - PI_VALUE/2.0f;

      gl.glBegin(GL2.GL_QUAD_STRIP);
        for(int i=0; i<=detail; i++)
        {
          theta3 = i*2.0f*PI_VALUE/detail;
          x = (float) (Math.cos(theta2)*Math.cos(theta3));
          y = (float) Math.sin(theta2);
          z = (float) (Math.cos(theta2)*Math.sin(theta3));

          gl.glNormal3f(x,y,z);  
          gl.glTexCoord2f(1.0f-(float)i/detail, 2.0f*(float)(j+1)/detail);     
          gl.glVertex3f(x,y,z);

          x = (float) (Math.cos(theta1)*Math.cos(theta3));
          y = (float) Math.sin(theta1);
          z = (float) (Math.cos(theta1)*Math.sin(theta3));

          gl.glNormal3f(x,y,z);  
          gl.glTexCoord2f(1.0f-(float)i/detail, 2.0f*(float)j/detail);         
          gl.glVertex3f(x,y,z);
        }
      gl.glEnd();
    }

    gl.glFlush();
  }
  
  /**
   * Méthode pour dessiner un disque dans le plan xy centré à l'origine.
   * 
   * @param gl
   * @param detail
   */
  public static void displayDisk(GL2 gl, int detail, double r, boolean normal_up)
  {
    if(r < 0.0)
      r*= -1.0;
    
    // Définir le sens de la normale.
    double n_z;
    
    if(normal_up)
      n_z = 1.0;
    else
      n_z = -1.0;
    
    if(detail < DEFAULT_DISK_DETAIL)
      detail = DEFAULT_DISK_DETAIL;
    
    double dtheta = 2.0*Math.PI / detail;
    double du = 1.0 / detail;
    double  x, y,
            u = 0.0, theta = 0.0;

    gl.glBegin(GL2.GL_TRIANGLE_FAN);
      gl.glNormal3d(0.0, 0.0, n_z);  gl.glTexCoord2d(u, 1.0);  gl.glVertex3d(0.0, 0.0, 0.0);
      
    // Itération sur l'ensemble des rectangles qui vont définir le tube.
    for(int i = 0; i <= detail; i++)
    {
      theta = dtheta*i;
      u = du*i;
     
      x = r*Math.cos(theta);
      y = r*Math.sin(theta);
     
      gl.glNormal3d(0.0, 0.0, n_z);  gl.glTexCoord2d(u, 1.0);  gl.glVertex3d(x, y, 0.0);
            
    }
    
    gl.glEnd();
  }
  
  /**
   * Méthode pour dessiner un tube unitaire aligné le long de l'axe z.
   * 
   * @param gl
   * @param detail
   */
  public static void displayTube(GL2 gl, int detail)
  {
    if(detail < DEFAULT_TUBE_DETAIL)
      detail = DEFAULT_TUBE_DETAIL;
    
    double dtheta = 2.0*Math.PI / detail;
    double du = 1.0 / detail;
    double  x0 = 0.0, y0 = 0.0,
            x1 = 0.0, y1 = 0.0,
            u0 = 0.0, u1 = 0.0,
            theta0 = 0.0, theta1 = 0.0;

    // Itération sur l'ensemble des rectangles qui vont définir le tube.
    for(int i = 0; i < detail; i++)
    {
      theta0 = theta1;
      u0 = u1;
      theta1 += dtheta;
      u1 += du;

      x0 = Math.cos(theta0);
      y0 = Math.sin(theta0);
      x1 = Math.cos(theta1);
      y1 = Math.sin(theta1);

      //  Dessiner un petit rectangle avec un section de la texture //
      gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3d(x0,y0,0.0);  gl.glTexCoord2d(u0, 1.0);  gl.glVertex3d(x0, y0, 1.0);
        gl.glNormal3d(x0,y0,0.0);  gl.glTexCoord2d(u0, 0.0);  gl.glVertex3d(x0, y0, 0.0);
        gl.glNormal3d(x1,y1,0.0);  gl.glTexCoord2d(u1, 0.0);  gl.glVertex3d(x1, y1, 0.0);
        gl.glNormal3d(x1,y1,0.0);  gl.glTexCoord2d(u1, 1.0);  gl.glVertex3d(x1, y1, 1.0);
      gl.glEnd();
    }
  }
  
  /**
   * Méthode permettant de dessiner un cylindre.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param color La couleur du cylindre.
   * @param scale La taille du cylindre.
   * @param rotation L'état de rotation Rxyz du cylindre.
   * @param translation La position du cylindre.
   */
  public static void displayCylinder(final GL2 gl, SColor color, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Initialiser la couleur du cube
    SGLUtil.setColor(gl, color);
    
    // Dessiner le cube avec les transformations appropriées
    displayCylinder(gl, scale, rotation, translation);
  }
  
  /**
   * Méthode permettant de dessiner un cone.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param color La couleur du cone.
   * @param scale La taille du cone.
   * @param rotation L'état de rotation Rxyz du cone.
   * @param translation La position du cone.
   */
  public static void displayCone(final GL2 gl, SColor color, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Initialiser la couleur du cube
    SGLUtil.setColor(gl, color);
    
    // Dessiner le cube avec les transformations appropriées
    displayCone(gl, scale, rotation, translation);
  }
  
  /**
   * Méthode pour dessiner un <b>cylindre unitaire aligné sur l'axe z transformé</b> à l'aide de transformation linéaire.
   * Il faut préalablement établir la couleur ou le matériel pour obtenir la couleur désirée.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param scale La taille du cylindre.
   * @param rotation L'état de rotation Rxyz du cylindre.
   * @param translation La position du cylindre.
   */
  public static void displayCylinder(final GL2 gl, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Transformer le futur cube
    SGLUtil.pushTransformationMatrix(gl, scale, rotation, translation);
    
    // Dessiner le cube unitaire
    displayCylinder(gl, SGLDisplay.DEFAULT_TUBE_DETAIL, SGLDisplay.DEFAULT_DISK_DETAIL);
    
    // Remettre les matrices de transformation d'origine
    SGLUtil.popTransformationMatrix(gl);
  }
  
  /**
   * Méthode pour dessiner un <b>cone unitaire aligné sur l'axe z transformé</b> à l'aide de transformation linéaire.
   * Il faut préalablement établir la couleur ou le matériel pour obtenir la couleur désirée.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param scale La taille du cone.
   * @param rotation L'état de rotation Rxyz du cone.
   * @param translation La position du cone.
   */
  public static void displayCone(final GL2 gl, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Transformer le futur cube
    SGLUtil.pushTransformationMatrix(gl, scale, rotation, translation);
    
    // Dessiner le cube unitaire
    displayCone(gl, SGLDisplay.DEFAULT_TUBE_DETAIL, SGLDisplay.DEFAULT_DISK_DETAIL);
    
    // Remettre les matrices de transformation d'origine
    SGLUtil.popTransformationMatrix(gl);
  }
  
  /**
   * Méthode pour dessiner un cylindre unitaire aligné sur l'axe z dont l'une extrémité sera à l'origine.
   * 
   * @param gl
   * @param detail_tube
   * @param detail_disk
   */
  public static void displayCylinder(GL2 gl, int detail_tube, int detail_disk)
  {
    // Dessiner le tube.
    displayTube(gl, detail_tube);
    
    // Dessiner le disque du bas.
    displayDisk(gl, detail_disk, 1.0, false);
    
    // Dessiner le disque du haut.
    gl.glPushMatrix();
    
    gl.glTranslated(0.0, 0.0, 1.0);
    displayDisk(gl, detail_disk, 1.0, true);
    
    gl.glPopMatrix();
  }
  
  /**
   * ...
   * 
   * @param gl
   * @param detail
   */
  public static void displayCone(GL2 gl, int detail)
  {
    double dtheta = 2.0*Math.PI / detail;
    double du = 1.0 / detail;
    double  x0 = 0.0, y0 = 0.0,
            x1 = 0.0, y1 = 0.0,
            u0 = 0.0, u1 = 0.0,
            theta0 = 0.0, theta1 = 0.0;

    // Itération sur l'ensemble des rectangles qui vont définir le tube.
    for(int i = 0; i < detail; i++)
    {
      theta0 = theta1;
      u0 = u1;
      theta1 += dtheta;
      u1 += du;

      x0 = Math.cos(theta0);
      y0 = Math.sin(theta0);
      x1 = Math.cos(theta1);
      y1 = Math.sin(theta1);

      //  Dessiner un petit rectangle avec un section de la texture //
      gl.glBegin(GL2.GL_TRIANGLES);
        gl.glNormal3d(x0,y0,0.0);  gl.glTexCoord2d(u0, 1.0);  gl.glVertex3d(0.0, 0.0, 1.0);
        gl.glNormal3d(x0,y0,0.0);  gl.glTexCoord2d(u0, 0.0);  gl.glVertex3d(x0, y0, 0.0);
        gl.glNormal3d(x1,y1,0.0);  gl.glTexCoord2d(u1, 0.0);  gl.glVertex3d(x1, y1, 0.0);
      gl.glEnd();
    }
  }
  
  /**
   * Méthode pour dessiner un cone unitaire aligné sur l'axe z dont l'une extrémité sera à l'origine.
   * 
   * @param gl
   * @param detail_cone
   * @param detail_disk
   */
  public static void displayCone(GL2 gl, int detail_cone, int detail_disk)
  {
    // Dessiner le tube.
    displayCone(gl, detail_cone);
    
    // Dessiner le disque du bas.
    displayDisk(gl, detail_disk, 1.0, false);
  }
    
  /**
   * Méthode pour dessier en OpenGL une flèche (formée d'un cylindre et d'un cône).
   * 
   * @param gl L'objet openGL.
   * @param color La couleur de la flèche.
   * @param position La position de la flèche (la queue)
   * @param orientation L'orientation de la flèche
   * @param cylinder_length La longueur du cylindre.
   * @param cylinder_ray Le rayon du cylindre.
   * @param cone_length La longueur du cône.
   * @param cone_ray Le rayon du cône.
   * @param detail Le niveau de détail des primitives.
   */
  public static void displayArrow(GL2 gl, SColor color, SVector3d position, SVector3d orientation, double cylinder_length, double cylinder_ray, double cone_length, double cone_ray, int detail)
  {
    // Initialiser la couleur du cube
    SGLUtil.setColor(gl, color);
    
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Définir la rotation du tube et cône avec un quaternion.
    SQuaternion q = SQuaternion.toRotationQuaternion(SVector3d.UNITARY_Z, orientation);
    
    //----------------------
    // Dessiner le cylindre.
    //----------------------
    SGLUtil.pushTransformationMatrix(gl, new SVector3d(cylinder_ray, cylinder_ray, cylinder_length), q, position);
    displayCylinder(gl, detail, detail);
    SGLUtil.popTransformationMatrix(gl);
    
       
    //------------------
    // Dessiner le cône.
    //------------------
    
    // Préparation des matrices de transformation.
    gl.glPushMatrix();
            
    // Translation de la position finale.
    gl.glTranslated(position.getX(), position.getY(), position.getZ() ); 
    
    // Rotation. 
    SVector3d axis = q.rotationAxis();
    gl.glRotated(Math.toDegrees(q.rotationAngle()), axis.getX(), axis.getY(), axis.getZ()); 
    
    // Déplacer la base du cône à la point du cylindre.
    gl.glTranslated(0.0, 0.0, cylinder_length);   
    
    // Scale du cône.
    gl.glScaled(cone_ray, cone_ray, cone_length);  
    
    // Dessiner la primitive du cône (parallèle à l'axe z).
    displayCone(gl, detail);
    
    // Retirer l'ensemble des matrices de transformation.
    SGLUtil.popTransformationMatrix(gl);
  }
  
  /**
   * Méthode pour dessiner une collection de segment à partir d'une collection de position.
   * Un segment sera dessiné pour l'ensemble des couples de positions.
   * 
   * @param gl
   * @param positions
   */
  public static void displayEdgesAllCombinations(GL2 gl, SVector3d[] positions)
  {
    gl.glBegin(GL2.GL_LINES);
    
    for(int i = 0; i < positions.length; i++)
      for(int j = i+1; j < positions.length; j++)
      {
        gl.glVertex3d(positions[i].getX(), positions[i].getY(), positions[i].getZ());
        gl.glVertex3d(positions[j].getX(), positions[j].getY(), positions[j].getZ());    
      }
    
    gl.glEnd();
    gl.glFlush();
  }
  
  /**
   * ...
   * 
   * @param gl
   * @param list
   */
  public static void displayLineStrip(GL2 gl, SVector3d[] positions)
  {
    gl.glBegin(GL2.GL_LINE_STRIP);
      
    for(int i = 0; i < positions.length; i++)
      gl.glVertex3d(positions[i].getX(), positions[i].getY(), positions[i].getZ());
    
    gl.glEnd();
    gl.glFlush();
  }
   
  /**
   * ...
   * 
   * @param gl
   * @param p1
   * @param p2
   * @param p3
   * @param n
   */
  public static void displayTriangle(GL2 gl, SVector3d p1, SVector3d p2, SVector3d p3, SVector3d n)
  {
    gl.glBegin(GL2.GL_TRIANGLES);
    
    gl.glNormal3d(n.getX(), n.getY(), n.getZ()); gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
    gl.glNormal3d(n.getX(), n.getY(), n.getZ()); gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
    gl.glNormal3d(n.getX(), n.getY(), n.getZ()); gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());
    
    gl.glEnd();
    gl.glFlush();
  }
  
  /**
   * ...
   * 
   * @param gl
   * @param p1
   * @param p2
   * @param p3
   * @param uv1
   * @param uv2
   * @param uv3
   * @param n1
   * @param n2
   * @param n3
   */
  public static void displayTriangle(GL2 gl, SVector3d p1, SVector3d p2, SVector3d p3, SVectorUV uv1, SVectorUV uv2, SVectorUV uv3, SVector3d n1, SVector3d n2, SVector3d n3)
  {
    gl.glBegin(GL2.GL_TRIANGLES);
    
    gl.glTexCoord2d(uv1.getU(), uv1.getV()); gl.glNormal3d(n1.getX(), n1.getY(), n1.getZ()); gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
    gl.glTexCoord2d(uv2.getU(), uv2.getV()); gl.glNormal3d(n2.getX(), n2.getY(), n2.getZ()); gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
    gl.glTexCoord2d(uv3.getU(), uv3.getV()); gl.glNormal3d(n3.getX(), n3.getY(), n3.getZ()); gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());
    
    gl.glEnd();
    gl.glFlush();
  }
  
  /**
   * ...
   * 
   * @param gl
   * @param p1
   * @param p2
   * @param p3
   * @param uv1
   * @param uv2
   * @param uv3
   * @param gl_texture_id
   */
  public static void displayTriangle(GL2 gl, SVector3d p1, SVector3d p2, SVector3d p3, SVectorUV uv1, SVectorUV uv2, SVectorUV uv3, int gl_texture_id)
  {
    // Activation des texture 2d.
    gl.glEnable(GL2.GL_TEXTURE_2D);
    
    // Accès à la texture #1.
    gl.glBindTexture(GL2.GL_TEXTURE_2D, gl_texture_id);   
    
    gl.glBegin(GL2.GL_TRIANGLES);
    
    gl.glTexCoord2d(uv1.getU(), uv1.getV()); gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
    gl.glTexCoord2d(uv2.getU(), uv2.getV()); gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
    gl.glTexCoord2d(uv3.getU(), uv3.getV()); gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());
    
    gl.glEnd();
    gl.glFlush();
  }
   
  /**
   * Méthode pour dessiner un <b>cube unitaire transformé</b> à l'aide de transformation linéaire.
   * Il faut préalablement établir la couleur ou le matériel pour obtenir la couleur désirée.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param scale La taille du cube.
   * @param rotation L'état de rotation Rxyz du cube.
   * @param translation La position du cube.
   */
  public static void displayCube(final GL2 gl, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Transformer le futur cube
    SGLUtil.pushTransformationMatrix(gl, scale, rotation, translation);
    
    // Dessiner le cube unitaire
    displayCube(gl);
    
    // Remettre les matrices de transformation d'origine
    SGLUtil.popTransformationMatrix(gl);
  }
  
  /**
   * Méthode pour dessiner un <b>cylindre unitaire aligné sur l'axe z transformé</b> à l'aide de transformation linéaire.
   * Il faut préalablement établir la couleur ou le matériel pour obtenir la couleur désirée.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param scale La taille du cylindre.
   * @param rotation L'état de rotation Rxyz du cylindre.
   * @param translation La position du cylindre.
   */
  /*
  public static void displayCylinder(final GL2 gl, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Transformer le futur cube
    SGLUtil.pushTransformationMatrix(gl, scale, rotation, translation);
    
    // Dessiner le cube unitaire
    displayCylinder(gl, SGLDisplay.DEFAULT_TUBE_DETAIL);
    
    // Remettre les matrices de transformation d'origine
    SGLUtil.popTransformationMatrix(gl);
  }
  */
  
  /**
   * ...
   * 
   * @param gl
   * @param scale
   * @param rotation
   * @param translation
   */
  public static void displayCube(final GL2 gl, SVector3d scale, SQuaternion rotation, SVector3d translation)
  {
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Transformer le futur cube
    SGLUtil.pushTransformationMatrix(gl, scale, rotation, translation);
    
    // Dessiner le cube unitaire
    displayCube(gl);
    
    // Remettre les matrices de transformation d'origine
    SGLUtil.popTransformationMatrix(gl);
  }
    
  /**
   * ...
   * 
   * @param gl
   * @param scale
   * @param rotation
   * @param translation
   */
  public static void displayCube(final GL2 gl, int[] gl_texture_id, SVector3d scale, SQuaternion rotation, SVector3d translation)
  {
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Transformer le futur cube
    SGLUtil.pushTransformationMatrix(gl, scale, rotation, translation);
    
    // Dessiner le cube unitaire
    displayCube(gl, gl_texture_id);
    
    // Remettre les matrices de transformation d'origine
    SGLUtil.popTransformationMatrix(gl);
  }
  
  /**
   * Méthode permettant de dessiner une sphere positionnée en son centre.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param scale La taille de la sphère (elle peut être déformé par le scale).
   * @param rotation La rotation Rxyz de la sphère.
   * @param translation La position de la sphère.
   */
  public static void displaySphere(final GL2 gl, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Transformer la futur sphère.
    SGLUtil.pushTransformationMatrix(gl, scale, rotation, translation);
    
    // Dessiner le cube unitaire
    displaySphere(gl, DEFAULT_SPHERE_DETAIL);
    
    // Remettre les matrices de transformation d'origine
    SGLUtil.popTransformationMatrix(gl);
  }
  
  /**
   * ...
   * 
   * @param gl
   * @param positions
   * @param scale
   * @param rotation
   * @param translation
   */
  public static void displayEdgesAllCombinations(GL2 gl, SVector3d[] positions, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Transformer la futur sphère.
    SGLUtil.pushTransformationMatrix(gl, scale, rotation, translation);
    
    // Dessiner le cube unitaire
    displayEdgesAllCombinations(gl, positions);
    
    // Remettre les matrices de transformation d'origine
    SGLUtil.popTransformationMatrix(gl);
  }
  
  /**
   * ...
   * @param gl
   * @param list
   * @param scale
   * @param rotation
   * @param translation
   */
  public static void displayLineStrip(GL2 gl, SVector3d[] positions, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Définir les matrices de transformation de modèle
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Transformer la futur sphère.
    SGLUtil.pushTransformationMatrix(gl, scale, rotation, translation);
    
    // Dessiner le cube unitaire
    displayLineStrip(gl, positions);
    
    // Remettre les matrices de transformation d'origine
    SGLUtil.popTransformationMatrix(gl);
  }
    
  /**
   * Méthode permettant de dessiner un cube.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param color La couleur du cube.
   * @param scale La taille du cube.
   * @param rotation L'état de rotation Rxyz du cube.
   * @param translation La position du cube.
   */
  public static void displayCube(final GL2 gl, SColor color, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Initialiser la couleur du cube
    SGLUtil.setColor(gl, color);
    
    // Dessiner le cube avec les transformations appropriées
    displayCube(gl, scale, rotation, translation);
  }
  
  /**
   * Méthode permettant de dessiner un cube.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param color La couleur du cube.
   * @param scale La taille du cube.
   * @param rotation Le quaternion de rotation du cube.
   * @param translation La position du cube.
   */
  public static void displayCube(final GL2 gl, SColor color, SVector3d scale, SQuaternion rotation, SVector3d translation)
  {
    // Initialiser la couleur du cube
    SGLUtil.setColor(gl, color);
    
    // Dessiner le cube avec les transformations appropriées
    displayCube(gl, scale, rotation, translation);
  }
  
  /**
   * Méthode permettant de dessiner une sphere positionnée en son centre.
   * 
   * @param gl Objet en référence au fonctionnalité de openGL.
   * @param color La couleur de la sphère.
   * @param scale La taille de la sphère (elle peut être déformé par le scale).
   * @param rotation Lae rotation Rxyz de la sphère.
   * @param translation La position de la sphère.
   */
  public static void displaySphere(final GL2 gl, SColor color, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Initialiser la couleur du cube
    SGLUtil.setColor(gl, color);
    
    // Dessiner le cube avec les transformations appropriées
    displaySphere(gl, scale, rotation, translation);
  }
  
  /**
   * ...
   * 
   * @param gl
   * @param color
   * @param positions
   * @param scale
   * @param rotation
   * @param translation
   */
  public static void displayEdgesAllCombinations(GL2 gl, SColor color, SVector3d[] positions, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Initialiser la couleur du cube
    SGLUtil.setColor(gl, color);
    
    // Dessiner avec les transformations appropriées
    displayEdgesAllCombinations(gl, positions, scale, rotation, translation);
  }
  
  /**
   * ...
   * 
   * @param gl
   * @param color
   * @param list
   * @param scale
   * @param rotation
   * @param translation
   */
  public static void displayLineStrip(GL2 gl, SColor color, SVector3d[] positions, SVector3d scale, SVector3d rotation, SVector3d translation)
  {
    // Initialiser la couleur du cube
    SGLUtil.setColor(gl, color);
    
    // Dessiner avec les transformations appropriées
    displayLineStrip(gl, positions, scale, rotation, translation);
  }
     
  /**
   * Méthode pour dessiner un vecteur à l'aide d'une flèche.
   * 
   * @param gl L'objet OpenGL.
   * @param position La position de la queue de la flèche.
   * @param color La couleur de la flèche.
   * @param max_vector_modulus Le module maximal du vecteur.
   * @param division Le nombre de sous-division du module maximal du vecteur.
   * @param max_arrow_size La longueur maximale de la flèche.
   * @param vector Le vecteur à dessiner.
   * @throws IllegalArgumentException S'il y a un paramètre illégal.
   */
  public static void displayVector(GL2 gl, SVector3d position, SColor color, double max_vector_modulus, int division, double max_arrow_size, SVector3d vector) throws IllegalArgumentException
  {
    displayVector(gl, position, color, SColor.YELLOW, SColor.ORANGE, max_vector_modulus, division, max_arrow_size, vector);
  }
  
  
  /**
   * Méthode pour dessiner un vecteur à l'aide d'une flèche.
   * 
   * @param gl L'objet OpenGL.
   * @param position La position de la queue de la flèche
   * @param color La couleur du vecteur.
   * @param color_saturation La couleur de saturation.
   * @param color_minimal La couleur de taille minimale.
   * @param max_vector_modulus Le module maximal du vecteur.
   * @param division Le nombre de sous-division du module maximal du vecteur.
   * @param max_arrow_size La longueur maximale de la flèche.
   * @param vector Le vecteur à dessiner.
   * @throws IllegalArgumentException S'il y a un paramètre illégal.
   */
  public static void displayVector(GL2 gl, SVector3d position, SColor color, SColor color_saturation, SColor color_minimal, double max_vector_modulus, int division, double max_arrow_size, SVector3d vector) throws IllegalArgumentException
  {
    if(max_vector_modulus <= 0.0)
      throw new IllegalArgumentException("Erreur SGLDisplay 001 : Le module unitaire de référence m = " + max_vector_modulus + " doit être supérieur à 0.");
       
    double nb_unit_length = vector.modulus() / max_vector_modulus;
    
    double division_length = max_arrow_size / (double)division;
    
    // Dessiner la flèche désigant le champ s'il n'est pas de longueur zéro.
    if(nb_unit_length  != 0.0)
    {
      // Nous aurons deux versions de flèche :
      // 1) flèche trop longue (jaune)
      // 2) trop courte (orange)
      // 3) flèche normale (couleur normale)
      
      double cylinder_length;
      SColor c;
      
      if(nb_unit_length > 1) // trop longue
      {
        cylinder_length =  max_arrow_size-division_length*0.7;
        c = color_saturation;
      }
      else
        if(nb_unit_length < 1.0/(double)division) // trop courte
        {
          cylinder_length = division_length - division_length*0.7;
          c = color_minimal;
        }
        else
        {
          cylinder_length = nb_unit_length*max_arrow_size - division_length*0.7;
          c = color;
        }
      
      // Dessiner la flèche.
      displayArrow(gl, c, position, vector.normalize(), cylinder_length, division_length*0.3, division_length*0.7, division_length*0.6, DEFAULT_CONE_DETAIL);
    }
  }
  
}
