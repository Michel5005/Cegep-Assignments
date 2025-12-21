package sim.application;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import sim.application.util.SConfiguration;
import sim.application.util.SJFrameListenerCameraSetup;
import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.graphics.SColor;
import sim.graphics.camera.SCamera;
import sim.graphics.camera.SVectorCamera;
import sim.listener.SListenerCamera;
import sim.math.SMath;
import sim.math.SVector3d;
import sim.math.field.SListVectorField;
import sim.math.field.SUndefinedFieldException;
import sim.math.field.SVectorField;
import sim.opengl.SGLDisplay;
import sim.opengl.SGLFieldDisplay;
import sim.opengl.SGLUtil;
import sim.opengl.SSimulationThread;
import sim.physics.field.SZeroElectricField;
import sim.readwrite.object.SCameraRW;
import sim.readwrite.object.SElectricFieldFileRW;
import sim.readwrite.object.field.SFieldSetupRW;
import sim.readwrite.object.field.SVectorFieldRW;
import sim.sound.SSoundUtil;
import sim.util.SLog;
import sim.util.SReadingException;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;

/**
 * L'application <b>SIMFieldViewer</b> est une application permettant de visualiser en 3d des champs physique.
 * 
 * @author Simon Vezina
 * @since 2022-01-28
 * @version 2022-10-06
 */
public class SIMElectricFieldViewer extends JFrame implements GLEventListener {

  /**
   * 
   */
  private static final long serialVersionUID = 3216419169134881245L;
  
  /**
   * La constante <b>WINDOWS_BUILDER_MODE</b> permet de configurer l'application afin de déterminer si l'application pourra activer ou non
   * les fonctionnalité de l'application intern windows builder.
   */
  private static final boolean WINDOWS_BUILDER_MODE = false;
  
  private static final String CONFIGURATION_FILE_NAME = "field_configuration.cfg";
  
  private static final SColor ARROW_COLOR = SColor.GREEN;
  
  private static final int FIELD_DISPLAY_MODE_ALL = 0;
  private static final int FIELD_DISPLAY_MODE_XY = 1;
  private static final int FIELD_DISPLAY_MODE_XZ = 2;
  private static final int FIELD_DISPLAY_MODE_YZ = 3;
  private static final int FIELD_DISPLAY_MODE_NONE = 4;
    
  /**
   * La variable <b>camera</b> représente la caméra qui pourra être déplacée lorqu'elle est connectée au MouseEvent du JFrame.
   */
  private SListenerCamera camera;
  
  /**
   * ...
   */
  final FPSAnimator fps;
  
  /**
   * ...
   */
  final SSimulationThread simulator;
  
  /**
   * ...
   */
  private SVectorField field;
  
  /**
   * ...
   */
  private int display_field_mode;
  
  /**
   * ...
   */
  SVector3d field_position;
  
  /**
   * ...
   */
  SVector3d vector_position;
  
  /**
   * ...
   */
  double size;
  
  /**
   * ...
   */
  int N;
  
  /**
   * ...
   */
  double modulus;
  
  /**
   * La variable <b>modulus_factor</b> détermine le module maximal pouvant être utilisé pour dessiner un vecteur.
   */
  double modulus_factor;
  
  private JPanel contentPane;
  
  private JTextField textFieldScene;
  private JTextField textFieldPositionX;
  private JTextField textFieldPositionY;
  private JTextField textFieldPositionZ;
  private JTextField textFieldSize;
  private JTextField textFieldN;
  
  
  private JSlider sliderModulus1to10;
  private JSlider sliderModulusPower;
  private JTextField textFieldVectorPositionX;
  private JTextField textFieldVectorPositionY;
  private JTextField textFieldVectorPositionZ;
  
  private JLabel lblModulus1to10;
  private JLabel lblModulusPower;
  private JLabel lblModulus;
  
  private JLabel lblEx;
  private JLabel lblEy;
  private JLabel lblEz;
  
  /**
   * Launch the application.
   */
  public static void main(String[] args)
  {
    // Instruction pour retirer, si possible, des messages de type WARNING en rouge lors de l'exécution.
    SGLUtil.removeAccessWarning();
        
    // Lancement de l'application.
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          SIMElectricFieldViewer frame = new SIMElectricFieldViewer();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public SIMElectricFieldViewer() 
  {
    super("Application : SIMFieldViewer, visualisateur de champ électrique");
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 800, 600);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);
    
    GLProfile glProfile = GLProfile.getDefault();
    GLCapabilities glCapabilities = new GLCapabilities(glProfile);
    glCapabilities.setDoubleBuffered(true);
    GLJPanel glCanvas = new GLJPanel(glCapabilities);
    
    // Construction de la caméra.
    this.camera = new SListenerCamera(new SVectorCamera());
    
    
    // Connecter le glCanvas au événement de type GL (repaint et compagnie).
    glCanvas.addGLEventListener(this);
    
    // Connecter la camera au événement de souris sur le glCanvas.
    glCanvas.addMouseMotionListener(this.camera);
    glCanvas.addMouseListener(this.camera);
    
    //--------------------------------------------------------------------------
    // Structuce de code pour permettre au plugin Design de fonctionner.
    //--------------------------------------------------------------------------
    if(WINDOWS_BUILDER_MODE)
    {
      JPanel panelCenterToChangeForGLCanvas = new JPanel();
      contentPane.add(panelCenterToChangeForGLCanvas, BorderLayout.CENTER);
    }
    else
    {
      contentPane.add(glCanvas, BorderLayout.CENTER);
    }
        
    JPanel panelSouth = new JPanel();
    contentPane.add(panelSouth, BorderLayout.SOUTH);
    panelSouth.setLayout(new GridLayout(4, 0, 0, 0));
    
    JPanel panelPosition = new JPanel();
    panelSouth.add(panelPosition);
    panelPosition.setLayout(new GridLayout(0, 3, 0, 0));
    
    JPanel panelPositionX = new JPanel();
    panelPosition.add(panelPositionX);
    
    JLabel lblPositionX = new JLabel("x : ");
    panelPositionX.add(lblPositionX);
    
    textFieldPositionX = new JTextField();
    textFieldPositionX.setText("0");
    panelPositionX.add(textFieldPositionX);
    textFieldPositionX.setColumns(10);
    
    JPanel panelPositionY = new JPanel();
    panelPosition.add(panelPositionY);
    
    JLabel lblPositionY = new JLabel("y : ");
    panelPositionY.add(lblPositionY);
    
    textFieldPositionY = new JTextField();
    textFieldPositionY.setText("0");
    panelPositionY.add(textFieldPositionY);
    textFieldPositionY.setColumns(10);
    
    JPanel panelPositionZ = new JPanel();
    panelPosition.add(panelPositionZ);
    
    JLabel lblPositionZ = new JLabel("z : ");
    panelPositionZ.add(lblPositionZ);
    
    textFieldPositionZ = new JTextField();
    textFieldPositionZ.setText("0");
    panelPositionZ.add(textFieldPositionZ);
    textFieldPositionZ.setColumns(10);
    
    JPanel panelFieldSize = new JPanel();
    panelSouth.add(panelFieldSize);
    panelFieldSize.setLayout(new GridLayout(1, 3, 0, 0));
    
    JPanel panelSize = new JPanel();
    panelFieldSize.add(panelSize);
    
    JLabel lblSize = new JLabel("Size : ");
    panelSize.add(lblSize);
    
    textFieldSize = new JTextField();
    textFieldSize.setText("5");
    panelSize.add(textFieldSize);
    textFieldSize.setColumns(10);
    
    JPanel panelN = new JPanel();
    panelFieldSize.add(panelN);
    
    JLabel lblN = new JLabel("N : ");
    panelN.add(lblN);
    
    textFieldN = new JTextField();
    textFieldN.setText("10");
    panelN.add(textFieldN);
    textFieldN.setColumns(10);
    
    JPanel panelActivation = new JPanel();
    panelFieldSize.add(panelActivation);
    
    JButton btnActivation = new JButton("Activation");
    btnActivation.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionButtonActivation();
      }
    });
    panelActivation.add(btnActivation);
    
    JPanel panelModulus = new JPanel();
    panelSouth.add(panelModulus);
    panelModulus.setLayout(new GridLayout(0, 3, 0, 0));
    
    JPanel panelModulusValue = new JPanel();
    panelModulus.add(panelModulusValue);
    panelModulusValue.setLayout(new GridLayout(0, 1, 0, 0));
    
    JLabel lblModulusValue = new JLabel("Module unitaire (N/C) :");
    lblModulusValue.setHorizontalAlignment(SwingConstants.CENTER);
    panelModulusValue.add(lblModulusValue);
    
    lblModulus = new JLabel("...");
    lblModulus.setHorizontalAlignment(SwingConstants.CENTER);
    panelModulusValue.add(lblModulus);
    
    JPanel panelModulus1to10 = new JPanel();
    panelModulus.add(panelModulus1to10);
    panelModulus1to10.setLayout(new BorderLayout(0, 0));
    
    this.lblModulus1to10 = new JLabel("Multiplicateur 1 \u00E0 10");
    lblModulus1to10.setHorizontalAlignment(SwingConstants.CENTER);
    panelModulus1to10.add(lblModulus1to10, BorderLayout.NORTH);
    
    this.sliderModulus1to10 = new JSlider();
    sliderModulus1to10.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        actionSliderModulus();
      }
    });
    sliderModulus1to10.setMinimum(10);
    sliderModulus1to10.setValue(10);
    panelModulus1to10.add(sliderModulus1to10, BorderLayout.CENTER);
    
    JPanel panelModulusPower = new JPanel();
    panelModulus.add(panelModulusPower);
    panelModulusPower.setLayout(new BorderLayout(0, 0));
    
    this.lblModulusPower = new JLabel("Multiplicateur en puissance de 10");
    lblModulusPower.setHorizontalAlignment(SwingConstants.CENTER);
    panelModulusPower.add(lblModulusPower, BorderLayout.NORTH);
    
    this.sliderModulusPower = new JSlider();
    sliderModulusPower.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        actionSliderModulus();
      }
    });
    sliderModulusPower.setValue(0);
    sliderModulusPower.setMaximum(10);
    sliderModulusPower.setMinimum(-10);
    panelModulusPower.add(sliderModulusPower, BorderLayout.CENTER);
    
    JPanel panelPlane = new JPanel();
    panelSouth.add(panelPlane);
    panelPlane.setLayout(new GridLayout(0, 5, 0, 0));
    
    JButton btnPlaneAll = new JButton("All");
    btnPlaneAll.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionButtonPlaneAll();
      }
    });
    panelPlane.add(btnPlaneAll);
    
    JButton btnPlaneXY = new JButton("XY");
    btnPlaneXY.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionButtonPlaneXY();
      }
    });
    panelPlane.add(btnPlaneXY);
    
    JButton btnPlaneXZ = new JButton("XZ");
    btnPlaneXZ.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionButtonPlaneXZ();
      }
    });
    panelPlane.add(btnPlaneXZ);
    
    JButton btnPlaneYZ = new JButton("YZ");
    btnPlaneYZ.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionButtonPlaneYZ();
      }
    });
    panelPlane.add(btnPlaneYZ);
    
    JButton btnPlaneNone = new JButton("Aucun");
    btnPlaneNone.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionButtonPlaneNone();
      }
    });
    panelPlane.add(btnPlaneNone);
    
    JPanel panelNorth = new JPanel();
    contentPane.add(panelNorth, BorderLayout.NORTH);
    panelNorth.setLayout(new GridLayout(3, 1, 0, 0));
    
    JPanel panelScene = new JPanel();
    FlowLayout flowLayout = (FlowLayout) panelScene.getLayout();
    flowLayout.setAlignment(FlowLayout.LEFT);
    panelNorth.add(panelScene);
    
    JLabel lblScene = new JLabel("Scene :");
    panelScene.add(lblScene);
    
    textFieldScene = new JTextField();
    panelScene.add(textFieldScene);
    textFieldScene.setColumns(30);
        
    JButton btnBuildScene = new JButton("Chargement");
    panelScene.add(btnBuildScene);
    btnBuildScene.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionButtonBuildScene();
      }
    });
    
    JPanel panelVectorPosition = new JPanel();
    panelNorth.add(panelVectorPosition);
    panelVectorPosition.setLayout(new GridLayout(0, 4, 0, 0));
    
    JLabel lblVectorPosition = new JLabel("Position d'un vecteur champ :");
    lblVectorPosition.setHorizontalAlignment(SwingConstants.CENTER);
    panelVectorPosition.add(lblVectorPosition);
    
    JPanel panelVectorPositionX = new JPanel();
    panelVectorPosition.add(panelVectorPositionX);
    
    JLabel lblVectorPositionX = new JLabel("x :");
    panelVectorPositionX.add(lblVectorPositionX);
    
    textFieldVectorPositionX = new JTextField();
    textFieldVectorPositionX.setText("0");
    panelVectorPositionX.add(textFieldVectorPositionX);
    textFieldVectorPositionX.setColumns(10);
    
    JPanel panelVectorPositionY = new JPanel();
    panelVectorPosition.add(panelVectorPositionY);
    
    JLabel lblVectorPositionY = new JLabel("y :");
    panelVectorPositionY.add(lblVectorPositionY);
    
    textFieldVectorPositionY = new JTextField();
    textFieldVectorPositionY.setText("0");
    panelVectorPositionY.add(textFieldVectorPositionY);
    textFieldVectorPositionY.setColumns(10);
    
    JPanel panelVectorPositionZ = new JPanel();
    panelVectorPosition.add(panelVectorPositionZ);
    
    JLabel lblVectorPositionZ = new JLabel("z :");
    panelVectorPositionZ.add(lblVectorPositionZ);
    
    textFieldVectorPositionZ = new JTextField();
    textFieldVectorPositionZ.setText("0");
    panelVectorPositionZ.add(textFieldVectorPositionZ);
    textFieldVectorPositionZ.setColumns(10);
    
    JPanel panelVector = new JPanel();
    panelNorth.add(panelVector);
    panelVector.setLayout(new GridLayout(0, 4, 0, 0));
    
    JButton btnVector = new JButton("Calculer le champ E");
    btnVector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionButtonVector();
      }
    });
    panelVector.add(btnVector);
    
    JPanel panelEx = new JPanel();
    panelVector.add(panelEx);
    
    this.lblEx = new JLabel("Ex = ... N/C");
    panelEx.add(lblEx);
    
    JPanel panelEy = new JPanel();
    panelVector.add(panelEy);
    
    this.lblEy = new JLabel("Ey = ... N/C");
    panelEy.add(lblEy);
    
    JPanel panelEz = new JPanel();
    panelVector.add(panelEz);
    
    this.lblEz = new JLabel("Ez = ... N/C");
    panelEz.add(lblEz);
    
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
    simulator.setTimeStep(0.001);
    simulator.start();
    
    // Sélection du mode d'affichage initial.
    this.display_field_mode = FIELD_DISPLAY_MODE_ALL;
    
    // Initialisation du fichier de configuration et détermination du nom du fichier de scène.
    try {
      
      SConfiguration config = new SConfiguration(CONFIGURATION_FILE_NAME);
      
      this.textFieldScene.setText(config.getReadDataFileName());
      
      JButton btnBrowser = new JButton("Browser");
      btnBrowser.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          actionBrowser();          
        }
      });
      panelScene.add(btnBrowser);
      
      JButton btnCameraSetup = new JButton("Camera Setup");
      btnCameraSetup.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) 
        {
          actionCameraSetup();
        }
      });
      panelScene.add(btnCameraSetup);
      
    }catch(IOException | SConstructorException e) {
      SLog.logWriteLine("Message SIMFieldViewer --- Une erreur est survenue lors de la lecture du fichier de configuration. Un champ par défaut sera construit.");
    }
    
    // Chargement de la scène par défaut.
    buildEmptyScene();
    
    // Chargement des paramètres pour l'affichage du champ.
    actionButtonActivation();
   
    // Chargement des paramètre de l'affichage du vecteur.
    actionButtonVector();
    
    // Chargement des paramètres du slider de module.
    actionSliderModulus();
    
  }

  /**
   * Méthode pour réaliser les simulations dans la scene.
   * 
   * @param time_step
   */
  private void simulation(final double time_step)
  {
    
  }
  
  @Override
  public void display(GLAutoDrawable drawable)
  {
    // Accès à la camera et à ses propriétés géométriques.
    SCamera c = this.camera.getCamera();
        
    SVector3d position = c.getPosition();
    SVector3d lookAt = c.getLookAt();
    SVector3d up = c.getUp();
    
    //--------------------------------
    // Effacer le buffer de OpenGL. //
    //--------------------------------
    
    // Accès au fonctionnalité de OpenGL.
    GL2 gl = drawable.getGL().getGL2();
    
    // Effacer le buffer de couleur et de profondeur.
    gl.glClearColor(0,0,0,0);
    gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
    
    // Définir la pyramide de vue avec l'angle d'ouverture à 60 degré.
    GLU glu = new GLU();
    
    //----------------------------
    // Définir la pyramide de vue.
    //----------------------------
    int[] m_viewport = new int[4];
    gl.glGetIntegerv( GL2.GL_VIEWPORT, m_viewport, 0);
    final float h = ( float ) m_viewport[2] / ( float ) m_viewport[3];
    
    // Mentionner à OpenGL que c'est la matrice de projection qui sera modifiée.
    gl.glMatrixMode( GL2.GL_PROJECTION );
       
    // Effacer la matrice précédente.
    gl.glLoadIdentity();
    
    // Définir la matrice de pyramide de vue.
    glu.gluPerspective(c.getViewAngle(), h, c.getZNear(), c.getZFar());
    
    //------------------------------------------------------------------------------------
    // Modification de la matrice du model view à partir des informations de la caméra. //
    //------------------------------------------------------------------------------------
       
    // Informer OpenGL que c'est la matrice de model view qui sera modifiée.
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    
    // Effacer la matrice précédente.
    gl.glLoadIdentity();
        
    glu.gluLookAt(position.getX(), position.getY(), position.getZ(),
                    lookAt.getX(),   lookAt.getY(),   lookAt.getZ(),
                        up.getX(),       up.getY(),       up.getZ());
    
    //---------------------------------------------------------------------------------
    // Mettre la source de lumière #0 au-dessus de la camera avec 4 unité au-dessus. //
    //---------------------------------------------------------------------------------
    SVector3d light_position = position.add(up.multiply(4.0));
    
    float[] lightPos = {(float)light_position.getX() , (float)light_position.getY(), (float)light_position.getZ(), 1};             
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
          
    //------------------------
    // Dessin des vecteurs. //
    //------------------------
    
    // Dessin du champ électrique.
    switch(this.display_field_mode) 
    {
      case FIELD_DISPLAY_MODE_ALL : SGLFieldDisplay.displayVectorField(gl, this.field, ARROW_COLOR, this.modulus_factor, this.field_position, this.size, this.N); break;
      case FIELD_DISPLAY_MODE_XY :  SGLFieldDisplay.displayVectorFieldXY(gl, this.field, ARROW_COLOR, this.modulus_factor, this.field_position, this.size, this.N); break;
      case FIELD_DISPLAY_MODE_XZ :  SGLFieldDisplay.displayVectorFieldXZ(gl, this.field, ARROW_COLOR, this.modulus_factor, this.field_position, this.size, this.N); break;
      case FIELD_DISPLAY_MODE_YZ :  SGLFieldDisplay.displayVectorFieldYZ(gl, this.field, ARROW_COLOR, this.modulus_factor, this.field_position, this.size, this.N); break;
      case FIELD_DISPLAY_MODE_NONE : break;
      
      default : throw new IllegalArgumentException("Erreur SIMElectricFieldViewer 001 : Le mode d'affichage '" + this.display_field_mode + "' n'est pas valide pour le champ électrique.");
    }
    
    // Dessier un vecteur du champ total en une position de l'espace.
    SVector3d vector = this.field.get(this.vector_position);
      
    // Dessiner un vecteur si la longueur n'est pas nulle et dessiner une sphère si c'est nulle.
    if(!SMath.nearlyZero(vector.modulus()))
      SGLDisplay.displayVector(gl, this.vector_position, SColor.RED, SColor.RED, SColor.RED, this.modulus_factor, SGLFieldDisplay.DEFAULT_MODULUS_DIVISION, this.size / (double)this.N, vector);
    else
    {
      double ray = 0.1*this.size / (double) this.N;
      SGLDisplay.displaySphere(gl, SColor.RED, new SVector3d(ray, ray, ray), SVector3d.ZERO, this.vector_position);
    }
        
    // Dessiner une petite sphère à l'origine.
    double ray = 0.1*this.size / (double) this.N;
    SGLDisplay.displaySphere(gl, SColor.BLUE, new SVector3d(ray, ray, ray), SVector3d.ZERO, SVector3d.ZERO);
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
    //gl.glShadeModel(GL2.GL_FLAT);  // version avec normale à la surface unique.
        
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
       
    // Ajustement de l'éclairage ambiant de lumière #0.
    float[] ambient = { 0.2f, 0.2f, 0.2f, 1f }; 
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,  ambient, 0);
    
    // Ajustement de l'éclairage diffuse de lumière #0.
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
  
  /**
   * Méthode pour construire une scène vide.
   */
  private void buildEmptyScene()
  {
    // Construction d'un nouveau champ électrique vide.
    this.field = new SZeroElectricField();
    
    // Définition de la camera amovible.
    this.camera.setCamera(new SVectorCamera());
    
    // Définition par défaut des boîte de texte.    
    this.textFieldPositionX.setText(Double.toString(0.0));
    this.textFieldPositionY.setText(Double.toString(0.0));
    this.textFieldPositionZ.setText(Double.toString(0.0));
    this.textFieldSize.setText(Double.toString(2));
    this.textFieldN.setText(Integer.toString(10));
    this.sliderModulus1to10.setValue((int)(1*10.0));
    this.sliderModulusPower.setValue(1);
    
    // Affichage d'un message signalant le chargement de la scène vide.
    SLog.logWriteLine("Message SIMFieldViewer --- Une scène vide a été initialisée.");
  }
  
  /**
   * Action permettant le chargement d'une scène.
   */
  private void actionButtonBuildScene()
  {
    try {
      
      // Lecture de la scène.
      SElectricFieldFileRW reader = new SElectricFieldFileRW(this.textFieldScene.getText());
      reader.read();
            
      // Définition des champs.
      List<SVectorFieldRW> field_list = reader.getVectorFieldRWList();
      
      // Construction d'un nouveau champ électrique.
      SVectorField new_field = new SListVectorField();
      
      // Remplir le champ électrique.
      for(SVectorFieldRW f : field_list)
        new_field = new_field.add(f.toVectorField());
      
      // Affectation du résultat à l'objet this.field de la classe.
      this.field = new_field;
            
      // Modification de la camera.
      List<SCameraRW> camera_list = reader.getCameraRWList();
      
      if(!camera_list.isEmpty())
        //this.camera.setCamera(camera_list.get(0).toQuaternionCamera());
        this.camera.setCamera(camera_list.get(0).toVectorCamera());
      
      // Modification de la configuration du champ.
      SFieldSetupRW setup = reader.getFieldSetup();
      
      SVector3d position = setup.getPosition();
      this.textFieldPositionX.setText(Double.toString(position.getX()));
      this.textFieldPositionY.setText(Double.toString(position.getY()));
      this.textFieldPositionZ.setText(Double.toString(position.getZ()));
      this.textFieldSize.setText(Double.toString(setup.getSize()));
      this.textFieldN.setText(Integer.toString(setup.getNb()));
      this.sliderModulus1to10.setValue((int)(setup.getMultiplicatorOfTen()*10.0));
      this.sliderModulusPower.setValue(setup.getMultiplicatorOfPower());
      
      // Affichage d'un message de réussite de la procédure de lecture.
      SLog.logWriteLine("Message SIMFieldViewer --- La scène '" + reader.getFileName() + "' a été lue et exécutée avec succès.");
      
      // Activation des paramètes de description du champ.
      actionButtonActivation();
      actionButtonVector();
      SLog.logWriteLine("Message SIMFieldViewer --- Il y a activation des paramètres du champ.");
      
      // Production d'un son signifiant l'activation de la scène.
      SSoundUtil.playWaveFile("load_scene.wav", false);
      
    }catch(SReadingException e){
      
      // Gestion du fichier en lecture erroné.
      SLog.logWriteLine("Message SIMFieldViewer --- Une erreur est survenue lors de la lecture de la scène '" + this.textFieldScene.getText() + "'. Une scène par défaut sera construite.");
      buildEmptyScene();
      
      // Production d'un son signifiant que ça n'a pas fonctionné.
      SSoundUtil.playWaveFile("chord.wav", false);
      
    }catch(SNoImplementationException e) {
    
      // Gestion du fichier en lecture erroné.
      SLog.logWriteLine("Message SIMFieldViewer --- Une erreur est survenue avec la scène '" + this.textFieldScene.getText() + "', car une méthode requise n'a pas été implémentée. Une scène par défaut sera construite.");
      buildEmptyScene();
    
      // Affichage du stack trace.
      e.printStackTrace();
      
      // Production d'un son signifiant que ça n'a pas fonctionné.
      SSoundUtil.playWaveFile("chord.wav", false);
    }
  }
  
  /**
   * ...
   */
  private void actionButtonPlaneAll()
  {
    this.display_field_mode = FIELD_DISPLAY_MODE_ALL;
  }
  
  /**
   * ...
   */
  private void actionButtonPlaneXY()
  {
    this.display_field_mode = FIELD_DISPLAY_MODE_XY;
  }
  
  /**
   * ...
   */
  private void actionButtonPlaneXZ()
  {
    this.display_field_mode = FIELD_DISPLAY_MODE_XZ;
  }
  
  /**
   * ...
   */
  private void actionButtonPlaneYZ()
  {
    this.display_field_mode = FIELD_DISPLAY_MODE_YZ;
  }
  
  private void actionButtonPlaneNone()
  {
    this.display_field_mode = FIELD_DISPLAY_MODE_NONE;
  }
  
  /**
   * Action permettant d'enregistrer les données des textField.
   */
  private void actionButtonActivation()
  {
    this.field_position = this.getFieldPositionWithTextField();
    this.size = this.getSizeWithTextField();
    this.N = this.getNWithTextField();
  }
  
  /**
   * Action permettant d'enregistrer les donnée des textField permettant l'affichage du vecteur particulier calculé.
   */
  private void actionButtonVector()
  {
    this.vector_position = getVectorPositionWithTextField();
    
    try {
      
      SVector3d vector = this.field.get(this.vector_position);
      
      this.lblEx.setText("Ex = " + Double.toString(vector.getX()) + " N/C");
      this.lblEy.setText("Ey = " + Double.toString(vector.getY()) + " N/C");
      this.lblEz.setText("Ez = " + Double.toString(vector.getZ()) + " N/C");
      
    }catch(SUndefinedFieldException e) {
      this.lblEx.setText("Ex = ??? N/C");
      this.lblEy.setText("Ey = ??? N/C");
      this.lblEz.setText("Ez = ??? N/C");
    }
  }
  
  /**
   * Action permettant d'enregistrer les données des slider de module.
   */
  private void actionSliderModulus()
  {
    int power = 0;
    double modulus_1to_10 = 1.0;
    
    // La valeur générée sera entre -10 et 10.
    if(this.sliderModulusPower != null)
      power = this.sliderModulusPower.getValue();
        
    double modulus_power = Math.pow(10.0, power);
    
    // Puisque la valeur générée sera entre 10 et 100, il faudra diviser par 10.
    if(this.sliderModulus1to10 != null)
      modulus_1to_10 = this.sliderModulus1to10.getValue() / 10.0;
    
    // Détermination du facteur de modification du module unitaire.
    this.modulus_factor = modulus_1to_10 * modulus_power;    
    
    // Affichage des informations dans les labels.
    if(this.lblModulus1to10 != null)
      this.lblModulus1to10.setText("Mutiplicateur de 10 ( " + Double.toString(modulus_1to_10) + " )");
    
    if(this.lblModulusPower != null)
      this.lblModulusPower.setText("Multiplicateur de puissance de 10 ( 10e" + Integer.toString(power) + " )");
    
    // Affichage de l'information du module maximal.
    if(this.lblModulus != null)
      this.lblModulus.setText(Double.toString(this.modulus_factor));
  }
  
  /**
   * Action permettant de définir le nom du fichier en lecture.
   */
  private void actionBrowser()
  {
    JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));

    int returnValue = jfc.showOpenDialog(null);
    
    if (returnValue == JFileChooser.APPROVE_OPTION) 
    {
      File selectedFile = jfc.getSelectedFile();
      this.textFieldScene.setText(selectedFile.getName());  
    }
  }
  
  /**
   * ...
   */
  private void actionCameraSetup()
  {
    SJFrameListenerCameraSetup frame = new SJFrameListenerCameraSetup(this.camera);
    frame.setVisible(true);
  }
       
  /**
   * ...
   * 
   * @return
   */
  private SVector3d getFieldPositionWithTextField()
  {
    try {
      
      double x = Double.parseDouble(textFieldPositionX.getText());
      double y = Double.parseDouble(textFieldPositionY.getText());
      double z = Double.parseDouble(textFieldPositionZ.getText());
      
      return new SVector3d(x, y, z);
      
    }catch(NullPointerException | NumberFormatException e) {
      
      this.textFieldPositionX.setText("0.0");
      this.textFieldPositionY.setText("0.0");
      this.textFieldPositionZ.setText("0.0");
      
      return SVector3d.ORIGIN;
    }
  }
  
  /**
   * ...
   * 
   * @return
   */
  private SVector3d getVectorPositionWithTextField()
  {
    try {
      
      double x = Double.parseDouble(this.textFieldVectorPositionX.getText());
      double y = Double.parseDouble(this.textFieldVectorPositionY.getText());
      double z = Double.parseDouble(this.textFieldVectorPositionZ.getText());
      
      return new SVector3d(x, y, z);
      
    }catch(NullPointerException | NumberFormatException e) {
      
      this.textFieldVectorPositionX.setText("0.0");
      this.textFieldVectorPositionY.setText("0.0");
      this.textFieldVectorPositionZ.setText("0.0");
      
      return SVector3d.ORIGIN;
    }
  }
  
  /**
   * ...
   * 
   * @return
   */
  private double getSizeWithTextField()
  {
    try {
      
      double size = Double.parseDouble(textFieldSize.getText());
            
      // Validation de la valeur numérique de size.
      if(size < 0)
        throw new NumberFormatException();
      
      return size;
      
    }catch(NullPointerException | NumberFormatException e) {
      
      // Valeur par défaut si texte invalide.
      this.textFieldSize.setText("10.0");
      return 10.0;
    }
  }
  
  /**
   * ...
   * 
   * @return
   */
  private int getNWithTextField()
  {
    try {
      
      int N = Integer.parseInt(textFieldN.getText());
      
      // Validation de la valeur numérique de N.
      if(N < 2)
        throw new NumberFormatException();
      
      return N;
      
    }catch(NullPointerException | NumberFormatException e) {
      
      // Valeur par défaut si texte invalide.
      this.textFieldN.setText("8");
      return 8; 
    }
  }
    
}
