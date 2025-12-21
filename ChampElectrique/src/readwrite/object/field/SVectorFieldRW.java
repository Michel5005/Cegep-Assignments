/**
 * 
 */
package sim.readwrite.object.field;

import sim.math.field.SVectorField;
import sim.readwrite.SRW;

/**
 * L'interface <b>SVectorFieldRW</b> représente un champ de vecteur pouvant être lu et écrit grâce à l'interface <b>SRW</b>.
 * 
 * @author Simon Vezina
 * @since 2022-02-01
 * @version 2022-02-01
 */
public interface SVectorFieldRW extends SRW {

  /**
   * Méthode pour obtenir le champ vectoriel associé à l'objet en lecture/écriture.
   * 
   * @return Le champ vectoriel d'association.
   */
  public SVectorField toVectorField();
  
}
