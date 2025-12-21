/**
 * 
 */
package sim.application;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import sim.graphics.SColor;
import sim.graphics.camera.SCamera;
import sim.graphics.camera.SVectorCamera;
import sim.listener.SListenerCamera;
import sim.math.SVector3d;
import sim.opengl.SGLDisplay;
import sim.opengl.SGLUtil;
import sim.opengl.SSimulationThread;

/**
 * L'Application <b>SIMIntroOpenGL</b> représente une introduction à l'usage de la librairie graphique OpenGL en JAVA étant JOGL.
 * 
 * @author Simon Vezina
 * @version 2022-03-18
 * @since 2022-10-04
 */
public class SIMIntroOpenGL extends JFrame implements GLEventListener {

  /**
   * Code généré aléatoirement. 
   */
  private static final long serialVersionUID = 4331828525838021538L;
  
  /**
   * La constante <b>TIME_STEP</b> représente le temps associé à la simulation.
   */
  private static final double TIME_STEP = 0.01; 
  
  /**
   * La camera qui pourra être déplacée si elle est connecté au MouseEvent du JFrame.
   */
  private SListenerCamera camera;
  
  /**
   * La position de la source de lumière.
   */
  private SVector3d light_position = new SVector3d(0.0, 0.0, 5.0);
  
  /**
   * La constante <b> représente le module de la vitesse de la source de lumière.
   */
  private static final double MODULUS_LIGHT_SPEED = 2.0;
  
  /**
   * La vitesse de la source de lumière.
   */
  private SVector3d light_speed = new SVector3d(0.0, MODULUS_LIGHT_SPEED, 0.0);
  
  /**
   * La variable <b>fps</b> représente l'utilitaire pour faire la gestion du rythme de l'affichage.
   */
  private final FPSAnimator fps;
  
  /**
   * La variable <b>simulator</b> représente l'utilisaire pour faire la gestion de l'animation (déplacement des objets dans le temps).
   */
  private final SSimulationThread simulator;
  
  /**
   * Méthode main().
   * 
   * @param args
   */
  public static void main(String[] args)
  {
   // Instruction pour retirer, si possible, des messages de type WARNING en rouge lors de l'exécution.
    SGLUtil.removeAccessWarning();
        
    // Lancement de l'application.
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          SIMIntroOpenGL app = new SIMIntroOpenGL();
            app.setVisible(true);
        }
    });
  }
  
  /**
   * Constructeur de l'application. 
   */
  public SIMIntroOpenGL() 
  {
    super("Application de base en OpenGL");
    setSize(800,600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    GLProfile glProfile = GLProfile.getDefault();
    GLCapabilities glCapabilities = new GLCapabilities(glProfile);
    glCapabilities.setDoubleBuffered(true);
    GLJPanel glCanvas = new GLJPanel(glCapabilities);
    
    // Construction de la caméra.
    camera = new SListenerCamera(new SVectorCamera());
        
    // Connecter le glCanvas au événement de type GL (repaint et compagnie).
    glCanvas.addGLEventListener(this);
    
    // Connecter la camera au événement de souris sur le glCanvas.
    glCanvas.addMouseMotionListener(camera);
    glCanvas.addMouseListener(camera);
    
    // Ajouter le glCanvas au JFrame.
    add(glCanvas);
    
    //----------------------------------------------------------
    // Gestionnaire de l'affichage 
    //
    // C'est cet objet qui permet de relancer la méthode display
    // afin de faire le rafraichissement de l'écran
    //----------------------------------------------------------
    fps = new FPSAnimator(glCanvas, 30, true); // 30 frames/sec
    fps.start();
    
    //----------------------------------------------------------
    // Gestionnaire de la simulation
    //
    // C'est cet objet qui va faire la mise à jour de la scène,
    // afin de réaliser des simulations.
    //----------------------------------------------------------
    simulator = new SSimulationThread(this::simulation);
    simulator.setTimeStep(TIME_STEP);
    simulator.start();
  }
  
  /**
   * Méthode pour réaliser les simulations dans la scene.
   * 
   * @param time_step Le temps écoulé à chaque itération de la simulation.
   */
  private void simulation(final double time_step)
  {
    // Dans ce exemple, on modifie la position d'une source de lumière avec une vitesse constante.
    // Lorsque la source de lumière atteint une certaine position, on change le sens de la vitesse,
    // afin de créer un mouvement de va-et-vient
    
    // Modification de l'orientation de la source de lumière si requis.
    if(light_position.getY() > 5.0)
      light_speed = new SVector3d(0.0, -1*MODULUS_LIGHT_SPEED, 0.0);
    
    if(light_position.getY() < -5.0)
      light_speed = new SVector3d(0.0, MODULUS_LIGHT_SPEED, 0.0);
    
    // Modification de la position de la source de lumière.
    light_position = light_position.add(light_speed.multiply(TIME_STEP));
  }
  
  @Override
  public void display(GLAutoDrawable drawable)
  {
    // Accès au fonctionnalité de OpenGL.
    GL2 gl = drawable.getGL().getGL2();
    
    // Effacer le buffer de couleur et de profondeur.
    gl.glClearColor(0,0,0,0);
    gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
    
    //------------------------------------------------------------------------------------
    // Modification de la matrice du model view à partir des informations de la caméra. //
    //------------------------------------------------------------------------------------
    SCamera cam = this.camera.getCamera();
        
    // Informer OpenGL que c'est la matrice de model view qui sera modifiée.
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Effacer la matrice précédente.
    gl.glLoadIdentity();
    
    // Charger la version des fonctionnalités de GLU
    final GLU glu = new GLU();
    
    // Modifier la matrice du model view avec une séquence d'instruction openGL préprogrammé dans GLU (GL_UTILITAIRE)
    SVector3d position = cam.getPosition();
    SVector3d lookAt = cam.getLookAt();
    SVector3d up = cam.getUp();
    
    glu.gluLookAt(position.getX(), position.getY(), position.getZ(),
                    lookAt.getX(),   lookAt.getY(),   lookAt.getZ(),
                        up.getX(),       up.getY(),       up.getZ());
    
    //--------------------------------------------------------------------------------------
    // Positionnement des sources de lumière (car elles peuvent bouger dans l'animation). //
    //--------------------------------------------------------------------------------------
    
    // Définir la position de la source de lumière #0.
    float[] lightPos = { (float)light_position.getX(), (float)light_position.getY(), (float)light_position.getZ(), 1}; 
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);  // le zéro représente d'index de la source de lumière
    
    //----------------------------------------------------------
    // Dessiner la forme de la sourc de lumière en mouvement. //
    //----------------------------------------------------------
    
    // Dessiner une forme géométrique à partir d'une librairie pour visualiser la position de la source de lumière (une sphère blanche).
    SGLDisplay.displaySphere(gl, new SColor(1.0, 1.0, 1.0), new SVector3d(0.2, 0.2, 0.2)  , SVector3d.ZERO, light_position);
        
    //-------------------------------------
    // Dessiner des formes géométriques. //
    //-------------------------------------
    
    // Dessiner une forme géométrique à partir d'une librairie (un cube rouge).
    SGLDisplay.displayCube(gl, SColor.RED, new SVector3d(1.0, 1.0, 1.0), SVector3d.ZERO, SVector3d.ORIGIN);
    
    // Dessiner une forme géométrique à partir d'une librairie (un cube vert).
    SGLDisplay.displayCube(gl, SColor.GREEN, new SVector3d(2.0, 2.0, 2.0), SVector3d.ZERO, new SVector3d(0.0, 2.0, 0.0));
  
    // Dessiner une forme géométrique à partir d'une librairie (un cube bleu).
    SGLDisplay.displayCube(gl, SColor.BLUE, new SVector3d(2.0, 2.0, 2.0), SVector3d.ZERO, new SVector3d(0.0, -2.0, -1.0));
    
    // Dessiner une forme géométrique à partir d'une librairie (une sphère jaune).
    SGLDisplay.displaySphere(gl, new SColor(1.0, 1.0, 0.0), new SVector3d(0.5, 0.5, 0.5), SVector3d.ZERO, new SVector3d(-2.0, 2.0, 3.0));
    
    // Dessiner une forme géométrique à partir d'une librairie (un cylindre orange).
    SGLDisplay.displayCylinder(gl, new SColor(1.0, 0.7, 0.0), new SVector3d(0.5, 0.5, 4.0), SVector3d.ZERO, new SVector3d(-2.0, -2.0, 0.0));
    
    // Dessiner une forme géométrique à partir d'une librairie (un cône vert pâle).
    SGLDisplay.displayCone(gl, new SColor(0.635, 0.0, 1.0), new SVector3d(0.5, 0.5, 2.0), SVector3d.ZERO, new SVector3d(2.0, 2.0, -1.0));
  }

  @Override
  public void dispose(GLAutoDrawable arg0)
  {
    // il n'y a rien à faire ...
  }

  @Override
  public void init(GLAutoDrawable drawable) 
  {
    // Accès au fonctionnalité de OpenGL.
    GL2 gl = drawable.getGL().getGL2();
    
    // Effacer les couleurs initiale.
    gl.glClearColor(0, 0, 0, 0);
    
    //-----------------------------------------------------------
    // ACTIVATION DE DIFFÉRENTE ALGORITHME DE CALCUL GRAPHIQUE //
    //-----------------------------------------------------------
    
    // Activer le test de profondeur pour afficher les triangles près de la caméra (pas dans l'ordre d'affichage).
    gl.glEnable(GL.GL_DEPTH_TEST);
    
    // Activer le mode d'interpolation des couleurs sur les surface des triangles.
    gl.glShadeModel(GL2.GL_SMOOTH);  // version avec normale à la surface interpolée.
    
    // Activer le mode éclairage pour l'algorithme d'illumination.
    gl.glEnable(GL2.GL_LIGHTING);
    
    // Activer le mode couleur des matériaux pour l'algorithme d'illumination.
    gl.glEnable(GL2.GL_COLOR_MATERIAL);
    
    // Activer le mode "normalize" (que je ne sais pas pourquoi pour l'instant ...)
    gl.glEnable(GL2.GL_NORMALIZE);
    
    //-----------------------------------------------------------
    // ACTIVATION ET DÉFINITION DES PROPRIÉTÉS DE LA SOURCE #0 //
    //-----------------------------------------------------------
    
    // Activer la source de lumière #0.
    gl.glEnable(GL2.GL_LIGHT0);
    
    // Définir la position par défaut de la source de lumière #0 (si elle ne bougeait pas).
    float[] lightPos = { 0, 0, 5, 1};             
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
    
    // Ajustement des couleurs de la source de lumière #0.
    
    // Ajustement de l'éclairage ambiant.
    float[] ambient = { 0.2f, 0.2f, 0.2f, 1f }; 
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,  ambient, 0);
    
    // Ajustement de l'éclairage diffuse.
    float[] diffuse = { 1f, 1f, 1f, 1f };        
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE,  diffuse, 0); 
  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height )
  {
    // Avoir accès au fonctionnalité de OpenGL.
    final GL2 gl = drawable.getGL().getGL2();
    
    // Définir la taille de la fenête de vue.
    final float h = ( float ) width / ( float ) height;
    gl.glViewport( 0, 0, width, height );
    
    // Mentionner à OpenGL que c'est la matrice de projection qui sera modifiée.
    gl.glMatrixMode( GL2.GL_PROJECTION );
    
    // Définir la pyramide de vue avec l'angle d'ouverture à 60 degré.
    GLU glu = new GLU();
    
    // Effacer la matrice précédente.
    gl.glLoadIdentity();
    
    // Utiliser les fonctionnalités de GLU pour modifier la matrice de projection avec les effets de perspective.
    SCamera c = this.camera.getCamera();
    glu.gluPerspective(c.getViewAngle(), h, c.getZNear(), c.getZFar());
  }
  
}
