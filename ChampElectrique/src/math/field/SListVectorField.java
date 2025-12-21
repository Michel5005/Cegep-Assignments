/**
 * 
 */
package sim.math.field;

import java.util.ArrayList;
import java.util.List;

import sim.math.SVector3d;

/**
 * La classe <b>SListVectorlField</b> représente un champ vectoriel contenant une liste de champs vectoriels superposés.  
 *
 * @author Simon Vezina
 * @since 2022-01-24
 * @version 2022-10-06
 */
public class SListVectorField implements SVectorField {

  /**
   * La variable <b>field_list</b> représente la liste de tous les champs vectoriel de cette superposition.
   */
  protected List<SVectorField> field_list;
  
  /**
   * Constructeur d'un champ vectoriel vide.
   */
  public SListVectorField()
  {
    this.field_list = new ArrayList<SVectorField>();
  }
  
  /**
   * Constructeur d'un champ vectoriel à partir d'un autre champ vectoriel.
   * 
   * @param f Le champ vectoriel.
   */
  public SListVectorField(SVectorField f)
  {
    this.field_list = new ArrayList<SVectorField>();
    
    // Ajouter l'ensemble des champ de f à la liste courante. 
    if(f instanceof SListVectorField)
    {
      SListVectorField l = (SListVectorField)f;
      
      for(SVectorField v : l.field_list)
        this.field_list.add(v);
    }
    else
      this.field_list.add(f);   // Ajouter seulement le champ f.
  }
  
  
  @Override
  public SListVectorField clone()
  {
    SListVectorField new_field = new SListVectorField();
    
    for(SVectorField f : this.field_list)
      new_field.field_list.add(f);
    
    return new_field;
  }
  
  @Override
  public SVector3d get(SVector3d r) throws SUndefinedFieldException
  {
    // Vérification que la liste n'est pas vide.
    if(this.field_list.isEmpty())
      throw new SUndefinedFieldException("Erreur SListVectorField 001 : Puisque la liste interne des champs vectoriels est vide, le champ est indéfini.", r);
    
    // Calcul de la superposition des champs vectoriels.
    SVector3d v = SVector3d.ZERO;
    
    for(SVectorField f : this.field_list)
      v = v.add(f.get(r));
     
    return v;
  }

  @Override
  public SVectorField add(SVectorField f)
  {
    SListVectorField field = new SListVectorField(f);
    
    for(SVectorField v : this.field_list)
      field.field_list.add(v);
    
    return field;
  }
  
  @Override
  public String toString()
  {
    return "SListVectorField [NB_field=" + this.field_list.size() + "]";
  }
  
}
