package sim.application.util;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sim.graphics.camera.SVectorCamera;
import sim.listener.SListenerCamera;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * L'application <b>SJFrameListenerCameraSetup</b> permet la configuration d'une camera à l'aide d'un JFrame.
 * 
 * @author Simon Vezina
 * @since 2022-03-27
 * @version 2022-06-05
 */
public class SJFrameListenerCameraSetup extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * La variable <b>camera</b> représente la camera qui sera configurée par ce JFrame.
   */
  private final SListenerCamera camera;
  
  private JPanel contentPane;

  private JSlider sliderMovementSensibility;
  private JSlider sliderDeltaMovement;
  private JSlider sliderRotationSensibility;
  
  private JLabel lblMovementSensibilityValue;
  private JLabel lblDeltaMovementValue;
  private JPanel panelRotationSensibility;
  private JLabel lblRotationSensibility;
  private JLabel lblRotationSensibilityValue;
  private JPanel panelDeltaRotation;
  private JLabel lblDeltaRotation;
  private JLabel lblDeltaRotationValue;
  private JSlider sliderDeltaRotation;
  private JPanel panelViewAngle;
  private JLabel lblViewAngle;
  private JLabel lblViewAngleValue;
  private JSlider sliderViewAngle;
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          
          SListenerCamera cam = new SListenerCamera(new SVectorCamera());
          SJFrameListenerCameraSetup frame = new SJFrameListenerCameraSetup(cam);
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
  public SJFrameListenerCameraSetup(SListenerCamera camera)
  {
    // Demander au frame de se fermer seul.
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    setTitle("Camera setup");
    this.camera = camera;
        
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(new GridLayout(5, 0, 0, 0));
    
    JPanel panelMovementSensibility = new JPanel();
    contentPane.add(panelMovementSensibility);
    panelMovementSensibility.setLayout(new BorderLayout(0, 0));
    
    JLabel lblMovementSensibility = new JLabel("Movement sensibility");
    lblMovementSensibility.setHorizontalAlignment(SwingConstants.CENTER);
    panelMovementSensibility.add(lblMovementSensibility, BorderLayout.NORTH);
    
    lblMovementSensibilityValue = new JLabel("0");
    lblMovementSensibilityValue.setHorizontalAlignment(SwingConstants.CENTER);
    panelMovementSensibility.add(lblMovementSensibilityValue, BorderLayout.SOUTH);
    
    sliderMovementSensibility = new JSlider();
    sliderMovementSensibility.setMinimum(1);
    sliderMovementSensibility.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) 
      {
        actionSliderMovementSensibility();
      }
    });
    
    panelMovementSensibility.add(sliderMovementSensibility, BorderLayout.CENTER);
    
    JPanel panelDeltaMovement = new JPanel();
    contentPane.add(panelDeltaMovement);
    panelDeltaMovement.setLayout(new BorderLayout(0, 0));
    
    JLabel lblDeltaMovement = new JLabel("Delta movement");
    lblDeltaMovement.setHorizontalAlignment(SwingConstants.CENTER);
    panelDeltaMovement.add(lblDeltaMovement, BorderLayout.NORTH);
    
    lblDeltaMovementValue = new JLabel("0");
    lblDeltaMovementValue.setHorizontalAlignment(SwingConstants.CENTER);
    panelDeltaMovement.add(lblDeltaMovementValue, BorderLayout.SOUTH);
    
    sliderDeltaMovement = new JSlider();
    sliderDeltaMovement.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e)
      {
        actionSliderMovementSensibility();
      }
    });
    sliderDeltaMovement.setValue(11);
    sliderDeltaMovement.setMaximum(21);
    sliderDeltaMovement.setMinimum(1);
    panelDeltaMovement.add(sliderDeltaMovement, BorderLayout.CENTER);
    
    panelRotationSensibility = new JPanel();
    contentPane.add(panelRotationSensibility);
    panelRotationSensibility.setLayout(new BorderLayout(0, 0));
    
    lblRotationSensibility = new JLabel("Rotation sensibility");
    lblRotationSensibility.setHorizontalAlignment(SwingConstants.CENTER);
    panelRotationSensibility.add(lblRotationSensibility, BorderLayout.NORTH);
    
    lblRotationSensibilityValue = new JLabel("0");
    lblRotationSensibilityValue.setHorizontalAlignment(SwingConstants.CENTER);
    panelRotationSensibility.add(lblRotationSensibilityValue, BorderLayout.SOUTH);
    
    sliderRotationSensibility = new JSlider();
    sliderRotationSensibility.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) 
      {
        actionSliderRotationSensibility();
      }
    });
    sliderRotationSensibility.setValue(5);
    sliderRotationSensibility.setMaximum(10);
    sliderRotationSensibility.setMinimum(1);
    panelRotationSensibility.add(sliderRotationSensibility, BorderLayout.CENTER);
    
    panelDeltaRotation = new JPanel();
    contentPane.add(panelDeltaRotation);
    panelDeltaRotation.setLayout(new BorderLayout(0, 0));
    
    lblDeltaRotation = new JLabel("Delta rotation");
    lblDeltaRotation.setHorizontalAlignment(SwingConstants.CENTER);
    panelDeltaRotation.add(lblDeltaRotation, BorderLayout.NORTH);
    
    lblDeltaRotationValue = new JLabel("0");
    lblDeltaRotationValue.setHorizontalAlignment(SwingConstants.CENTER);
    panelDeltaRotation.add(lblDeltaRotationValue, BorderLayout.SOUTH);
    
    sliderDeltaRotation = new JSlider();
    sliderDeltaRotation.setMaximum(200);
    sliderDeltaRotation.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) 
      {
        actionSliderRotationSensibility();
      }
    });
    sliderDeltaRotation.setMinimum(1);
    panelDeltaRotation.add(sliderDeltaRotation, BorderLayout.CENTER);
    
    panelViewAngle = new JPanel();
    contentPane.add(panelViewAngle);
    panelViewAngle.setLayout(new BorderLayout(0, 0));
    
    lblViewAngle = new JLabel("View angle");
    lblViewAngle.setHorizontalAlignment(SwingConstants.CENTER);
    panelViewAngle.add(lblViewAngle, BorderLayout.NORTH);
    
    lblViewAngleValue = new JLabel("0");
    lblViewAngleValue.setHorizontalAlignment(SwingConstants.CENTER);
    panelViewAngle.add(lblViewAngleValue, BorderLayout.SOUTH);
    
    sliderViewAngle = new JSlider();
    sliderViewAngle.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e)
      {
        actionSliderViewAngle();
      }
    });
    sliderViewAngle.setValue(60);
    sliderViewAngle.setMinimum(10);
    sliderViewAngle.setMaximum(170);
    panelViewAngle.add(sliderViewAngle, BorderLayout.CENTER);
  }

  /**
   * Méthode pour mettre à jour la sensibilité du mouvement de la camera.
   */
  private void actionSliderMovementSensibility()
  {
    // Capture de l'information.
    int value = 1;
        
    if(sliderMovementSensibility != null)
      value = sliderMovementSensibility.getValue();
    
    // Affichage de l'information.
    if(lblMovementSensibilityValue != null)
      lblMovementSensibilityValue.setText(Double.toString(value/100.0));
    
    // Capture de l'information.
    int power = 0;
    
    // Puisque la valeur générée sera entre 1 et 21, la valeur centrale sera 10.
    if(sliderDeltaMovement != null)
      power = this.sliderDeltaMovement.getValue() - 11;
        
    double delta = Math.pow(10.0, power);
    
    // Affichage de l'information.
    if(lblDeltaMovementValue != null)
      lblDeltaMovementValue.setText(Double.toString(delta) + " m");
    
    // Affectation de la modification à la camera.
    this.camera.setMovementSensibility(value/100.0 * delta);
  }
    
  /**
   * ...
   */
  private void actionSliderRotationSensibility()
  {
    // Captude de l'information.
    int value = 1;
        
    if(sliderRotationSensibility != null)
      value = sliderRotationSensibility.getValue();
    
    // Affichage de l'information.
    if(lblRotationSensibilityValue != null)
      lblRotationSensibilityValue.setText(Double.toString(value/10.0));
    
    // Captude de l'information.
    int delta = 1;
        
    if(sliderDeltaRotation != null)
      delta = sliderDeltaRotation.getValue();
    
    // Affichage de l'information.
    if(lblDeltaRotationValue != null)
      lblDeltaRotationValue.setText(Double.toString(delta/100.0) + " degrees");
    
    // REMARQUE : Nous cherchons une valeur moyenne où 1 pixel sera une rotation de 0.2 degré.
    
    // Affectation de la modification à la camera.
    this.camera.setRotationSensibility(value/10.0 * delta/100.0);
  }
    
  /**
   * ...
   */
  private void actionSliderViewAngle()
  {
    // Captude de l'information.
    int value = 60;
        
    if(sliderViewAngle != null)
      value = sliderViewAngle.getValue();
    
    // Affichage de l'information.
    lblViewAngleValue.setText(Integer.toString(value) + " degrees");
    
    // Affectation de la modification à la camera.
    this.camera.getCamera().setViewAngle(value);
  }
  
}
