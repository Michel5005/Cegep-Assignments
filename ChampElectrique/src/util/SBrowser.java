/**
 * 
 */
package sim.util;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * La classe <b>SBrowser</b> représente un explorateur de répertoire et de fichier.
 * 
 * @author Simon Vézina
 * @since 2018-02-26
 * @version 2022-04-24
 */
public class SBrowser {

  /**
   * La constante <b>DEFAULT_MAX_DEPTH</b> représente le niveau de profondeur maximal de recherche récursive dans les sous-répertoire.
   */
  private final int DEFAULT_MAX_DEPTH = 8;
  
  /**
   * La variable <b>file</b> représente l'emplacement courant de l'explorateur.
   */
  private File file;
  
  //----------------
  // CONSTRUCTEUR //
  //----------------
  
  /**
   * Constructeur d'un explorateur de répertoire.
   */
  public SBrowser()
  {
    this.file = new File(System.getProperty("user.dir"));
  }
  
  //------------
  // MÉTHODES //
  //------------
  
  /**
   * Méthode pour obtenir le nom du répertoire où est situé l'explorateur de répertoire.
   * 
   * @return Le nom du répertoire.
   */
  public String getDirectoryName()
  {
    return this.file.getName();
  }
  
  /**
   * Méthode pour obtenir le chemin où est situé l'explorateur de répertoire.
   * 
   * @return Le chemin de localisation de l'explorateur.
   */
  public String getPath()
  {
    return this.file.getPath();
  }
  
  /**
   * Méthode qui retourne une liste d'accès au fichier visible par la localisation courant de l'explorateur.
   * 
   * @return Un liste d'accès aux fichiers.
   */
  public List<File> getFileList()
  {
    List<File> list = new ArrayList<File>();
    
    for(File f : file.listFiles())
      if(f.isFile())
        list.add(f);
    
    return list;
  }
  
  /**
   * Méthode qui retourne une liste d'accès au répertoire visible par la localisation courant de l'explorateur.
   * 
   * @return Un liste d'accès aux répertoires.
   */
  public List<File> getDirectoryList()
  {
    List<File> list = new ArrayList<File>();
    
    for(File f : this.file.listFiles())
      if(f.isDirectory())
        list.add(f);
    
    return list;
  }
  
  /**
   * Méthode pour déteminer si le browser est situé dans un répertoire vide.
   * 
   * @return <b>true</b> si le répertoire est vide et <b>false</b> sinon.
   */
  public boolean isEmpty()
  {
    return this.file.listFiles().length == 0;
  }
  
  /**
   * Méthode pour entrer dans un répertoire à partir de la position local du browser.
   * 
   * @param directory_name Le nom du répertoire dans lequel on veut entrer.
   * @throws FileNotFoundException Si le nom du répertoire n'a pas été trouvé.
   */
  public void forward(String directory_name) throws FileNotFoundException, SManyFilesFoundException
  {
    goTo(directory_name, 1);
    
    /*
    List<File> list = getDirectoryList();
    List<File> found_list = new ArrayList<File>();
    
    for(File f : list)
      if(f.getName().equalsIgnoreCase(directory_name))
        found_list.add(f);
    
    if(found_list.isEmpty())
      throw new FileNotFoundException("Erreur SBrowser 001 : Le répertoire '" + directory_name + "' n'a pas été trouvé à partir de l'emplacement initiale '" + getPath() + "'.");
    
    if(found_list.size() > 1)
      throw new SManyFilesFoundException("Erreur SBrowser 002 : Le répertoire '" + directory_name + "' a été trouvé à plusieurs reprise à partir de l'emplacement initiale '" + getPath() + "'. Voici la liste : " + found_list);
    
    // Affectation de la nouvelle position de l'explorateur.
    this.file = found_list.get(0);
    */
  }
  
  /**
   * Méthode pour sortir du répertoire courant du browser.
   */
  public void back()
  {
    this.file = new File(file.getParent());
  }
  
  /**
   * Méthode pour construire un fichier (objet de type File) à partir de l'emplacement du browser.
   * 
   * @param file_name Le nom du fichier (avec l'extension).
   * @return Le fichier.
   */
  public File createFile(String file_name)
  {
    // Générer le nom du fichier avec le bon sous-répertoire.
    String text_name = this.getPath() + SStringUtil.FILE_SEPARATOR_CARACTER + file_name;
   
    return new File(text_name);
  }
  
  /**
   * Méthode pour construire un fichier (objet de type File) à partir de l'emplacement du browser.
   * 
   * @param file_name_without_extension Le nom du fichier (sans l'extension).
   * @param extension Le nom de l'extension du fichier.
   * @return Le fichier.
   */
  public File createFile(String file_name_without_extension, String extension)
  {
    // Générer le nom du fichier avec le bon sous-répertoire.
    String file_name = this.getPath() + SStringUtil.FILE_SEPARATOR_CARACTER + file_name_without_extension + "." + extension;
   
    return new File(file_name);
  }
  
  /**
   * Méthode pour construire un répertoire à partir de l'emplacement du browser.
   * 
   * @param directory_name Le nom du répertoire à créer.
   * @return <b>true</b> si le répertoire a été créé et <b>false</b> sinon.
   */
  public boolean createDirectory(String directory_name)
  {
    File new_directory = new File(this.file.getPath() + SStringUtil.FILE_SEPARATOR_CARACTER + directory_name);
    
    return new_directory.mkdirs();
  }
  
  /**
   * Méthode pour effacer un répertoire ainsi que l'ensemble de son contenu (répertoire et fichier).
   * Le browser doit être situé à l'emplacement du répertoire à effacer.
   * Il n'y aura pas de recherche récursive du nom du répertoire à effacer.
   * 
   * @param directory_name Le nom du répertoire à effacer (ainsi que son contenu).
   */
  public void deleteDirectory(String directory_name)
  {
    File directory = new File(this.file.getPath() + SStringUtil.FILE_SEPARATOR_CARACTER + directory_name);
    
    deleteDirectory(directory);
  }
   
  /**
   * Méthode pour déplacer l'explorateur à l'emplacement d'un répertoire à partir du répertoire courant de l'explorateur.
   * 
   * @param directory_name Le nom du répertoire vers lequel l'explorateur se dirigera.
   * @throws FileNotFoundException Si le nom du répertoire n'a pas été trouvé.
   * @throws SManyFilesFoundException Si plusieurs répertoires ayant le même nom a été trouvés.
   */
  public void goTo(String directory_name) throws FileNotFoundException, SManyFilesFoundException
  {
    goTo(directory_name, DEFAULT_MAX_DEPTH);
  }
  
  /**
   * Méthode pour déplacer l'explorateur à l'emplacement d'un répertoire à partir du répertoire courant de l'explorateur.
   * 
   * @param directory_name Le nom du répertoire vers lequel l'explorateur se dirigera.
   * @throws FileNotFoundException Si le nom du répertoire n'a pas été trouvé.
   * @throws SManyFilesFoundException Si plusieurs répertoires ayant le même nom a été trouvés.
   */
  public void goTo(String directory_name, int max_depth) throws IllegalArgumentException, FileNotFoundException, SManyFilesFoundException
  {
    // Vérification que le niveau de profondeur maximal est adéquat.
    if(max_depth < 1)
      throw new IllegalArgumentException("Erreur SBrowser 003 : La recherche en profondeur ne peut pas être effectué avec une profondeur maximale de " + max_depth + ".");
    
    List<File> list = new ArrayList<File>();
    
    searchDirectories(directory_name, file, 0, max_depth, list);
    
    if(list.isEmpty())
      throw new FileNotFoundException("Erreur SBrowser 004 : Le répertoire '" + directory_name + "' n'a pas été trouvé à partir de l'emplacement initial '" + getPath() + "'.");
    
    if(list.size() > 1)
      throw new SManyFilesFoundException("Erreur SBrowser 005 : Le répertoire '" + directory_name + "' a été trouvé à plusieurs reprise à partir de l'emplacement initial '" + getPath() + "'. Voici la liste : " + list);
    
    // Affectation de la nouvelle position de l'explorateur.
    this.file = list.get(0);
  }
  
  /**
   * Méthode pour obtenir une liste d'accès à un fichier correspondant à un nom particulier.
   * 
   * @param file_name Le nom du fichier en recherche (avec extension).
   * @return Une liste de fichiers ayant le nom désiré.
   */
  public List<File> searchFiles(String file_name)
  {
    List<File> list = new ArrayList<File>();
    
    searchFiles(file_name, file, 0, DEFAULT_MAX_DEPTH, list);
    
    return list;
  }
  
  /**
   * Méthode pour obtenir une liste d'accès à des répertoire correspondant à un nom particulier.
   * 
   * @param directory_name Le nom du répertoire en recherche.
   * @return Une liste de répertoires ayant le nom désiré.
   */
  public List<File> searchDirectories(String directory_name)
  {
    List<File> list = new ArrayList<File>();
    
    searchDirectories(directory_name, this.file, 0, this.DEFAULT_MAX_DEPTH, list);
    
    return list;
  }
  
  /**
   * Méthode pour obtenir un accès à un fichier correspondant à un nom particulier à partir de l'emplacemenet de l'explorateur et des sous-répertoires.
   * 
   * @param file_name Le nom du fichier en recherche (avec extension).
   * @return Un accès à un fichier ayant le nom désiré.
   * @throws FileNotFoundException Si le fichier n'a pas été trouvé.
   * @throws SManyFilesFoundException Si plusieurs fichiers ayant le même nom a été trouvés.
   */
  public File findFile(String file_name) throws FileNotFoundException, SManyFilesFoundException
  {
    List<File> list = searchFiles(file_name);
    
    if(list.isEmpty())
      throw new FileNotFoundException("Erreur SBrowser 006 : Le fichier '" + file_name + "' n'a pas été trouvé à partir de l'emplacement initiale '" + getPath() + "'.");
    
    if(list.size() > 1)
      throw new SManyFilesFoundException("Erreur SBrowser 007 : Le fichier '" + file_name + "' a été trouvé à pluseurs reprise à partir de l'emplacement initiale '" + getPath() + "'.");
    
    return list.get(0);
  }
  
  /**
   * Méthode pour localiser un répertoire à partir de l'emplacement du browser. 
   * La recherche sera effectuée récursivement dans l'ensemble de l'arborésance des répertoires.
   * Si le nom du répertoire est localisé à plusieurs endroits, une exception sera lancée.
   * Si le nom du répertoire n'est pas localisé, une exception sera lancée.
   * 
   * @param directory_name Le nom du répertoire.
   * @return L'accès au répertoire.
   * @throws FileNotFoundException Si le répertoire n'a pas été trouvé.
   * @throws SManyFilesFoundException Si le répertoire a été trouvé à plusieurs endroits.
   */
  public File findDirectory(String directory_name) throws FileNotFoundException, SManyFilesFoundException
  {
    List<File> list = searchDirectories(directory_name);
    
    if(list.isEmpty())
      throw new FileNotFoundException("Erreur SBrowser 008 : Le répertoire '" + directory_name + "' n'a pas été trouvé à partir de l'emplacement initiale '" + getPath() + "'.");
    
    if(list.size() > 1)
      throw new SManyFilesFoundException("Erreur SBrowser 009 : Le répertoire '" + directory_name + "' a été trouvé à pluseurs reprise à partir de l'emplacement initiale '" + getPath() + "'.");
    
    return list.get(0);
  }
  
  /**
   * <p>
   * Méthode pour déplacer une fichier dans un autre répertoire.
   * Le nom du fichier sera cherché à partir de l'emplacement du browser
   * et sera déplacé dans le répertoire désiré à partir de l'emplacement du browser. 
   * </p>
   * <p>
   * Puisque cette méthode effectue une recherche du fichier <u>sans duplicata du nom admissible</u>,
   * il n'y a pas de risque que le fichier déplacé à un autre endroit remplace un autre fichier existant ayant le même nom.
   * </p>
   * 
   * @param file_name Le nom du fichier à déplacer.
   * @param directory_name Le nom du répertoire où sera déplacé le fichier.
   * @throws FileNotFoundException Si le fichier ou le répertoire n'a pas été trouvé.
   * @throws SManyFilesFoundException Si le nom du fichier ou répertoire a été trouvé à plusieurs endroits.
   */
  public void moveFile(String file_name, String directory_name) throws FileNotFoundException, SManyFilesFoundException
  {
    // Rechercher le fichier à déplacer.
    File file = findFile(file_name);
    
    // Obtenir la localisation du répertoire vers où le fichier sera déplacé. 
    File directory = findDirectory(directory_name);
    
    // Copier le fichier et effacer s'il y a eu copie.
    if(file.renameTo(new File(directory.getPath() + SStringUtil.FILE_SEPARATOR_CARACTER + file_name)))
      file.delete(); 
  }
    
  /**
   * Méthode pour écrire un fichier texte à l'emplacement du browser.
   * 
   * @param file_name Le nom du fichier. 
   * @param extension L'extension du fichier.
   * @param text Le texte à écrire dans le fichier.
   * @throws IOException S'il y a eu des erreur de type I/O.
   */
  public void writeTextFile(String file_name, String extension, String text) throws IOException
  {
    FileWriter fw = new FileWriter(this.createFile(file_name, extension));
    BufferedWriter bw = new BufferedWriter(fw);
    
    bw.write(text);
    bw.flush();
    
    bw.close();     
    fw.close();
  }
  
  /**
   * Méthode permettant d'écrire un BufferedImage en fichier image à l'emplacement du browser.
   *
   * @param file_name Le nom du fichier (sans l'extension).
   * @param extension Le nom de l'extension du fichier (format de l'image généré). 
   * @param image L'image.
   * @throws IOException Si l'image n'a pas pu être convertie en fichier.
   */
  public void writeImageFile(String file_name, String extension, BufferedImage image) throws IOException
  {
    // Générer le fichier image
    File image_file = this.createFile(file_name, extension);
     
    // Écriture de l'image.
    ImageIO.write(image, extension, image_file);
  }
  
  /**
   * Méthode permettant de lire un fichier image afin d'en générer un BufferedImage.
   * 
   * @param file_name Le nom du fichier (avec extension).
   * @return L'image contenu dans le fichier.
   * @throws FileNotFoundException
   * @throws SManyFilesFoundException
   * @throws IOException
   */
  public BufferedImage readImageFile(String file_name) throws FileNotFoundException, SManyFilesFoundException, IOException
  {
    File file = this.findFile(file_name);
      
    return ImageIO.read(file);
  }
  
  /**
   * Méthode pour faire la recherche d'un fichier désiré de façon récursive. À chaque fois que le fichier sera trouvé,
   * l'adresse du fichier sera sauvegardé dans la liste.
   * 
   * @param file_name Le nom du fichier en recherche (avec extension).
   * @param current_file Le répertoire où la recherche est rendue.
   * @param depth Le niveau de profondeur courant de la récursivité.
   * @param max_depth Le niveau de profoncteur maximal de la récursivité.
   * @param file_list La liste des fichies trouvés.
   */
  private void searchFiles(final String file_name, final File current_file, final int depth, final int max_depth, final List<File> file_list)
  {
    // Vérification du niveau de profondeur maximal du fichier en recherche
    if(depth <= max_depth)
    {
      // Vérification si l'analyse se fait sur un répertoire et s'il est accessible.
      if(current_file.isDirectory() && current_file.canRead())                            
      {
        // Itérer sur l'ensemble du contenu du répertoire
        for(File temp : current_file.listFiles())                                 
        {
          // Vérifier si le fichier itéré est un répertoire. Dans ce cas, on continue la recherche récursivement.  
          if(temp.isDirectory())                                         
            searchFiles(file_name, temp, depth + 1, max_depth, file_list); 
          else 
            if(temp.getName().equalsIgnoreCase(file_name))                // Si le fichier est trouvé ! 
                file_list.add(temp);    
        }
      } 
    }
  }
  
  /**
   * Méthode pour faire la recherche du répertoire désiré de façon récursive. À chaque fois que le répertoire sera trouvé,
   * l'adresse du répertoire sera sauvegardé dans la liste.
   * 
   * @param file Le répertoire où la recherche est rendue.
   * @param depth Le niveau de profondeur de la récursivité.
   */
  private void searchDirectories(final String directory_name, final File current_file, final int depth, final int max_depth, final List<File> directory_list)
  {
    // Vérification du niveau de profondeur maximal du fichier en recherche
    if(depth <= max_depth)
    {
      // Vérification si l'analyse se fait sur un répertoire et l'accès au répertoire est possible.
      if(current_file.isDirectory() && current_file.canRead())                                                  
      {                                            
        // Vérifier si le répertoire courant porte le nom recherché.
        if(directory_name.equalsIgnoreCase(current_file.getName()))
          directory_list.add(current_file);
        
        // Itérer sur l'ensemble du contenu du répertoire et itérer récursivement dans le cas d'un répertoire.
        for(File temp : current_file.listFiles())   
          if(temp.isDirectory())            
            searchDirectories(directory_name, temp, depth + 1, max_depth, directory_list);        
      }
    }
  }
  
  /**
   * Méthode pour effacer recursivement un répertoire et son contenu.
   * 
   * @param directory Le répertoire à effacer et son contenu.
   */
  private void deleteDirectory(File directory)
  {
    for(File f : directory.listFiles())
      if(f.isDirectory())
        deleteDirectory(f);
      else
        f.delete();
    
    directory.delete();
  }
  
}
