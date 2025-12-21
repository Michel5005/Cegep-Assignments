/**
 * 
 */
package sim.math.field;

import sim.math.SVector3d;

/**
 * L'interface <b>SVectorField</b> représente un champ vectoriel où plusieurs opération mathématique est supportée.
 * 
 * @author Simon Vezina
 * @since 2022-01-24
 * @version 2022-01-24
 */
public interface SVectorField {

  /**
   * Méthode pour obtenir une composante vectorielle appartenant à la coordonnée r du champ vectoriel.
   * 
   * @param r La position où sera évalué le champ vectoriel.
   * @return Une composante du champ vectoriel
   * @throws SUndefinedFieldException Si le champ n'est pas défini à la coordonnée r.
   */
  public SVector3d get(SVector3d r) throws SUndefinedFieldException;
  
  /**
   * Méthode pour réaliser l'addition d'un champ vectoriel avec le champ vectoriel réalisant l'appel de cette méthode.
   * Un nouveau champ vectoriel sera retourné après la réalisation de cette addition.
   * 
   * @param f Le champ vectoriel à additionner.
   * @return L'addition d'un champ vectoriel.
   */
  public default SVectorField add(SVectorField f)
  {
    // Par défaut, il faut transformer le champ vectoriel courant en champ vectoriel par liste ave son contenu.
    SListVectorField field = new SListVectorField(this);
    
    // Retourner le champ vectoriel par liste avec l'ajout de f.
    return field.add(f);
  }
  
}
