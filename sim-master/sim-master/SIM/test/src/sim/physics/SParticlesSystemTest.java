package sim.physics;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import sim.exception.SNoImplementationException;
import sim.math.SMath;
import sim.math.SVector3d;
import sim.util.SLog;

/**
 * JUnit test permettant de valider les fonctionnalit�s de la classe <b>SParticlesSystem</b>.
 * 
 * @author Simon V�zina
 * @since 2017-06-05
 * @version 2020-09-21
 */
public class SParticlesSystemTest {

  /**
   * JUnit Test de la m�thode <b>evaluateForce</b> dans le cas le plus simple o� le syst�me contient uniquement 1 seule particule.
   */
  @Test
  public void evaluateForceTest1() 
  {
    try{
      
    SVector3d r_Q = new SVector3d(-4.0, -2.0, 3.0);
    double Q = 1e-6;
    
    SParticle p0 = new SParticle(Q, r_Q);
    SParticlesSystem sys = new SParticlesSystem(p0);
    Assert.assertEquals(SVector3d.ZERO, sys.evaluateForce(0)); 
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SParticlesSystemTest ---> Test non effectu� : public void evaluateForceTest1()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>evaluateForce</b> dans le cas le plus simple o� il n'y a que 2 charges dans le systeme.
   */
  @Test
  public void evaluateForceTest2() 
  {
    try{
    
    SVector3d r_Q = new SVector3d(-4.0, -2.0, 3.0);
    double Q = 1e-6;
    
    SVector3d r_q = new SVector3d(2.0, 3.0, 4.0);
    double q = 3e-6;
    
    SParticle p0 = new SParticle(Q, r_Q);
    SParticle p1 = new SParticle(q, r_q);
    
    SParticlesSystem sys = new SParticlesSystem(p0, p1);
    
    Assert.assertEquals(SElectrostatics.coulombLaw(Q, r_Q, q, r_q), sys.evaluateForce(1));  // force sur p1
    Assert.assertEquals(SElectrostatics.coulombLaw(q, r_q, Q, r_Q), sys.evaluateForce(0));  // force sur p0
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SParticlesSystemTest ---> Test non effectu� : public void evaluateForceTest2()");
    }
  }
 
  /**
   * JUnit Test de la m�thode <b>evaluateForce</b> dans un cas quelconque � quatre particules (<i>back coverage test</i>).
   */
  @Test
  public void evaluateForceTest3() 
  {
    try{
    
      SParticle p0 = new SParticle(1e-6, new SVector3d(4.5, 7.8, 3.4));
      SParticle p1 = new SParticle(2e-6, new SVector3d(7.5, -4.8, 2.7));
      SParticle p2 = new SParticle(-3e-6, new SVector3d(-2.3, -5.3, 4.9));
      SParticle p3 = new SParticle(-4e-6, new SVector3d(-0.3, -2.9, -1.1));
      
      SParticlesSystem sys = new SParticlesSystem(p0, p1, p2, p3);
      
      // Solution attendue
      SVector3d expected_solution = new SVector3d(-1.2104051719612912e-4, -7.015085301823657e-4, 0.001961435883977761);
      
      Assert.assertEquals(expected_solution, sys.evaluateForce(2));  // force sur p2
        
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SParticlesSystemTest ---> Test non effectué : public void evaluateForceTest3()");
    }
  }
    
  /**
   * JUnit Test de la méthode <b>evaluateForce</b> afin de vérifier que le système de particule n'a pas été modifié durant le calcul de l'évaluation d'une force électrique.
   * Ce test sera réalisé avec un système à 6 particules.
   */
  @Test
  public void evaluateForceTest4a() 
  {
    try{
    
      SParticle p0 = new SParticle(1e-6, new SVector3d(4.5, 7.8, 3.4));
      SParticle p1 = new SParticle(2e-6, new SVector3d(7.5, -4.8, 2.7));
      SParticle p2 = new SParticle(-3e-6, new SVector3d(-2.3, -5.3, 4.9));
      SParticle p3 = new SParticle(-4e-6, new SVector3d(-0.3, 2.9, -2.1));
      SParticle p4 = new SParticle(-4e-7, new SVector3d(-1.3, -5.9, -1.1));
      SParticle p5 = new SParticle(-4e-8, new SVector3d(3.3, -2.9, -8.1));
      
      SParticlesSystem sys = new SParticlesSystem(p0, p1, p2, p3, p4, p5);
      
      // �valuer la force sur l'�l�ment 0 du syst�me.
      sys.evaluateForce(0);
      
      // Validation de la taille du syst�me.
      if(sys.getNbParticles() != 6)
        fail("Ce test est en �chec, car le syst�me contenait 6 particules et apr�s le calcul de la force �lectrique, il ne reste plus que " + sys.getNbParticles() + " particules.");
         
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SParticlesSystemTest ---> Test non effectu� : public void evaluateForceTest4a()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>evaluateForce</b> afin de v�rifier que le syst�me de particule n'a pas �t� modifi� durant le calcul de l'�valuation d'une force �lectrique.
   * Ce test sera r�alis� avec un syst�me � 6 particules et toutes les particules seront test�es.
   */
  @Test
  public void evaluateForceTest4b() 
  {
    try{
    
    	SParticle p0 = new SParticle(1e-6, new SVector3d(4.5, 7.8, 3.4));
        SParticle p1 = new SParticle(2e-6, new SVector3d(7.5, -4.8, 2.7));
        SParticle p2 = new SParticle(-3e-6, new SVector3d(-2.3, -5.3, 4.9));
        SParticle p3 = new SParticle(-4e-6, new SVector3d(-0.3, 2.9, -2.1));
        SParticle p4 = new SParticle(-4e-7, new SVector3d(-1.3, -5.9, -1.1));
        SParticle p5 = new SParticle(-4e-8, new SVector3d(3.3, -2.9, -8.1));
      
      SParticlesSystem sys = new SParticlesSystem(p0, p1, p2, p3, p4, p5);
      
      // �valuer la force sur toutes les particules. 
      for(int i = 0 ; i < 6; i++)
        sys.evaluateForce(i);
      
      // Validation de la taille du syst�me.
      if(sys.getNbParticles() != 6)
        fail("Ce test est en �chec, car le syst�me contenait 6 particules et apr�s le calcul de la force �lectrique, il ne reste plus que " + sys.getNbParticles() + " particules.");
         
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SParticlesSystemTest ---> Test non effectu� : public void evaluateForceTest4b()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>evaluateForce</b> afin de v�rifier que le syst�me de particule n'a pas �t� modifi� durant le calcul de l'�valuation d'une force �lectrique.
   * Ce test sera r�alis� avec un syst�me � 6 particules et v�rifie que les particules dans le syst�me n'a pas �t� modifi� ni changer d'ordre dans la base de donn�e.
   */
  @Test
  public void evaluateForceTest4c() 
  {
    try{
    
    	SParticle p0 = new SParticle(1e-6, new SVector3d(4.5, 7.8, 3.4));
        SParticle p1 = new SParticle(2e-6, new SVector3d(7.5, -4.8, 2.7));
        SParticle p2 = new SParticle(-3e-6, new SVector3d(-2.3, -5.3, 4.9));
        SParticle p3 = new SParticle(-4e-6, new SVector3d(-0.3, 2.9, -2.1));
        SParticle p4 = new SParticle(-4e-7, new SVector3d(-1.3, -5.9, -1.1));
        SParticle p5 = new SParticle(-4e-8, new SVector3d(3.3, -2.9, -8.1));
      
      SParticlesSystem sys = new SParticlesSystem(p0, p1, p2, p3, p4, p5);
            
      // �valuer la force sur toutes les particules. 
      for(int i = 0 ; i < 6; i++)
        sys.evaluateForce(i);
      
      // Validation de la taille du syst�me.
      if(sys.getNbParticles() != 6)
        fail("Ce test est en �chec, car le syst�me contenait 6 particules et apr�s le calcul de la force �lectrique, il ne reste plus que " + sys.getNbParticles() + " particules.");
             
      // Forcer l'acc�s au champ de la liste.
      try {
       
        // Acc�s au champ protected.
        Field list_input = SParticlesSystem.class.getDeclaredField("particles_list");
        list_input.setAccessible(true);
        
        // Affectation forc�e de l'input � la fonction d'agr�gation.
        @SuppressWarnings("unchecked")
        List<SParticle> list = (List<SParticle>)list_input.get(sys);  
            
        // Validation de la positions des particules dans la base de donn�e qui est une liste.
        // Puisque les objets sont les m�mes, une validation de "alias" sera suffisant puisque l'objet est unmutable.
        if(list.get(0) != p0)
          fail("Ce test est en �chec, car la particule 0 a �t� chang�e de place dans la liste.");
             
        if(list.get(1) != p1)
          fail("Ce test est en �chec, car la particule 1 a �t� chang�e de place dans la liste.");
           
        if(list.get(2) != p2)
          fail("Ce test est en �chec, car la particule 2 a �t� chang�e de place dans la liste.");
           
        if(list.get(3) != p3)
          fail("Ce test est en �chec, car la particule 3 a �t� chang�e de place dans la liste.");
           
        if(list.get(4) != p4)
          fail("Ce test est en �chec, car la particule 4 a �t� chang�e de place dans la liste.");
           
        if(list.get(5) != p5)
          fail("Ce test est en �chec, car la particule 5 a �t� chang�e de place dans la liste.");
                  
      } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
            
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SParticlesSystemTest ---> Test non effectu� : public void evaluateForceTest4c()");
    }
  }
  
  /**
   * JUnit Test de la m�thode <b>buildSystemTest</b> dans le cas o� l'on test uniquement la charge individuelle des particules du syst�me.
   */
  @Test
  public void buildSystemTest1()
  {
    try{
    
    double Q = 5.0;   // charge total
    List<SVector3d> list = new ArrayList<SVector3d>();
    
    // Ajouter des vecteurs � la liste, la valeur n'a pas d'importance pour le test.
    list.add(SVector3d.ZERO);
    list.add(SVector3d.ZERO);
    list.add(SVector3d.ZERO);
    list.add(SVector3d.ZERO);
    list.add(SVector3d.ZERO);
    list.add(SVector3d.ZERO);
    list.add(SVector3d.ZERO);
    
    SParticlesSystem sys = SParticlesSystem.buildSystem(Q, list);
    
    Assert.assertEquals(Q / (double)sys.getNbParticles(), sys.removeLast().getElectricCharge(), SMath.EPSILON);
    
    }catch(SNoImplementationException e){
      SLog.logWriteLine("SParticlesSystemTest ---> Test non effectu� : public void buildSystemTest1()");
    }
  }
    
}
