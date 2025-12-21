/**
 * 
 */
package sim.physics;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sim.exception.SConstructorException;
import sim.exception.SNoImplementationException;
import sim.exception.SRuntimeException;
import sim.geometry.SGeometricDiscretization;
import sim.math.SVector3d;
import sim.util.SAbstractReadableWriteable;
import sim.util.SBufferedReader;
import sim.util.SInitializationException;
import sim.util.SKeyWordDecoder;
import sim.util.SReadingException;
import sim.util.SStringUtil;

/**
 * La classe <b>SParticlesSystem</b> représente un système de particle.
 * 
 * @author Simon Vézina
 * @since 2017-05-31
 * @version 2017-09-20 (labo - La loi de Coulomb v1.1)
 */
public class SParticlesSystem extends SAbstractReadableWriteable {

	/**
	 * La constante <b>KEYWORD_PARAMETER</b> correspond à un tableau contenant l'ensemble des mots clés 
	 * à utiliser reconnus lors de la définition de l'objet par une lecture en fichier.
	 */
	private static final String[] KEYWORD_PARAMETER = { SKeyWordDecoder.KW_PARTICLE };

	/**
	 * La variable <b>particlesLi</b> représente la liste des particules du système.
	 */
	private final List<SParticle> particles_list;

	//----------------
	// CONSTRUCTEUR //
	//----------------

	/**
	 * Constructeur d'un système de particule à partir d'un tableau de particule.
	 * 
	 * @param particles La tableau des particles.
	 */
	public SParticlesSystem(SParticle ... particles)
	{
		// Créer la liste interne des particules du système. 
		particles_list = new ArrayList<SParticle>();

		// Remplir la liste des particules.
		for(SParticle p : particles)
			particles_list.add(p);
	}

	/**
	 * Constructeur d'un système de particule é partir d'une liste de particule.
	 * 
	 * @param list La liste des particules.
	 */
	public SParticlesSystem(List<SParticle> list)
	{
		// Créer la liste interne des particules du système. 
		particles_list = new ArrayList<SParticle>();

		// Remplir la liste des particules.
		for(SParticle p : list)
			particles_list.add(p);
	}


	/**
	 * Constructeur d'un système de particule é partir de la lecture d'un fichier txt décrivant une lise de particule.
	 * 
	 * @param file_name Le nom du fichier.
	 * @throws SConstructorException S'il y a eu une erreur le de la construction de l'objet.
	 */
	public SParticlesSystem(String file_name) throws SConstructorException
	{
		// Créer la liste interne des particules du système. 
		particles_list = new ArrayList<SParticle>();

		try{
			read(file_name);
		}catch(FileNotFoundException e){
			throw new SConstructorException("Erreur SParticlesSystem 001 : Le fichier " + file_name + " n'a pas été trouvé.", e);
		}catch(IOException e){
			throw new SConstructorException("Erreur SParticlesSystem 002 : Une erreur de type I/O s'est produite lors de l'analyse du fichier " + file_name + ".", e);
		}
	}

	//-----------
	// MéTHODE //
	//-----------

	/**
	 * Méthode pour obtenir le nombre de particules dans le système.
	 * 
	 * @return Le nombre de particules dans le système.
	 */
	public int getNbParticles()
	{
		return particles_list.size();
	}

	/**
	 * Méthode pour ajouter une particule au système. Cette particule aura ajoutée à la fin de la liste des particule du système (index maximal).
	 * 
	 * @param p La particule ajoutée au système.
	 */
	public void add(SParticle p)
	{
		particles_list.add(p);
	}

	/**
	 * Méthode pour retirer la derniére particule inserée dans le système.
	 * 
	 * @return La derniére particule inserée dans le système et qui fut retirée.
	 * @throws UnsupportedOperationException Si le système ne posséde pas de particule.
	 */
	public SParticle removeLast() throws UnsupportedOperationException
	{
		// Vérifier que la liste n'est pas vide.
		if(particles_list.size() == 0)
			throw new UnsupportedOperationException("Erreur SParticlesSystem 003 : La liste de particule est vide.");

		return particles_list.remove(particles_list.size()-1);
	}

	/**
	 * Méthode pour obtenir la force électrique appliquée sur une particule par les autres charges du système.
	 * 
	 * @param index L'indice de la particule qui va subir la force électrique.
	 * @return La force électrique appliquée sur la particule.
	 * @throws IndexOutOfBoundsException Si l'indice ne concorde pas avec une particule du système.
	 */
	public SVector3d evaluateForce(int index) {

		SVector3d forceER = new SVector3d(0, 0, 0);

		for (int i = 0; i< this.getNbParticles() ; i++) {
			if(i != index) {

				forceER = forceER.add(SElectrostatics.coulombLaw(particles_list.get(i).getElectricCharge(), particles_list.get(i).getPosition() ,particles_list.get(index).getElectricCharge() , particles_list.get(index).getPosition()));
			}
		}



		return forceER;
	}

	/**
	 * Méthode pour construire un système de particule à partir d'une liste de positionnment des particules composant le système.
	 * 
	 * @param Q La charge totale.
	 * @param list La liste des particules.
	 * @return Le système de particules.
	 */
	public static SParticlesSystem buildSystem(double Q, List<SVector3d> list)
	{
		// Allocation de la mémoire au système.
		SParticlesSystem system = new SParticlesSystem();

		// Déterminer la charge de chaque particule.
		double Q_particle = Q / (double)list.size();

		// Construire les particules.
		for(SVector3d v : list)
			system.add(new SParticle(Q_particle, v));

		return system;
	}

	/**
	 * Méthode pour construire un système de particules situé le long d'une ligne alignée sur l'axe x positif.
	 * 
	 * @param Q La charge totale.
	 * @param size La taille de la ligne.
	 * @param N Le nombre de particules le long de la ligne (la discrétisation).
	 * @return Le système de particles.
	 * @throws SRuntimeException S'il y a eu une erreur dans les paramètres.
	 */
	public static SParticlesSystem buildLineX(double Q, double size, int N) throws SRuntimeException
	{
		return buildSystem(Q, SGeometricDiscretization.positiveLineXDiscretization(size, N));
	}

	/**
	 * Méthode pour construire un système de particules situé sur un carré situé dans le plan XY positif.
	 * 
	 * @param Q La charge totale.
	 * @param size La taille des cétés du carré.
	 * @param N Le nombre de particules sur le carré dans le plan XY (la discrétisation).
	 * @return Le système de particles.
	 * @throws SRuntimeException S'il y a eu une erreur dans les paramètres.
	 */
	public static SParticlesSystem buildSquareXY(double Q, double size, int N) throws SRuntimeException
	{
		return buildSystem(Q, SGeometricDiscretization.positiveSquareXYDiscretization(size, (int)Math.sqrt(N)));
	}

	@Override
	public void write(BufferedWriter bw) throws IOException 
	{
		bw.write(SKeyWordDecoder.KW_PARTICLES_SYSTEM);
		bw.write(SStringUtil.END_LINE_CARACTER);

		//Écrire les propriétés de la classe SSphereGeometry et ses paramètres hérités
		writeSParticlesSystemParameter(bw);

		bw.write(SKeyWordDecoder.KW_END);
		bw.write(SStringUtil.END_LINE_CARACTER);
		bw.write(SStringUtil.END_LINE_CARACTER);
	}

	/**
	 * Méthode pour écrire les paramètres associés à la classe SParticlesSystem et ses paramètres hérités.
	 * 
	 * @param bw Le BufferedWriter écrivant l'information dans un fichier txt.
	 * @throws IOException Si une erreur I/O s'est produite.
	 * @see IOException
	 */
	protected void writeSParticlesSystemParameter(BufferedWriter bw) throws IOException
	{
		// Faire l'écriture de toutes les particules.
		for(SParticle p : particles_list)
			p.write(bw);
	}

	@Override
	public String getReadableName() 
	{
		return SKeyWordDecoder.KW_PARTICLES_SYSTEM;
	}

	@Override
	public String[] getReadableParameterName()
	{
		String[] other_parameters = super.getReadableParameterName();

		return SStringUtil.merge(other_parameters, KEYWORD_PARAMETER);
	}

	@Override
	protected void readingInitialization() throws SInitializationException 
	{

	}

	@Override
	protected boolean read(SBufferedReader sbr, int code, String remaining_line) throws SReadingException, IOException
	{
		switch(code)
		{
		case SKeyWordDecoder.CODE_PARTICLE : particles_list.add(new SParticle(sbr)); return true;

		default : return false;
		}
	}

	@Override
	public String toString() 
	{
		StringBuffer bString = new StringBuffer();

		for(int i = 0; i < particles_list.size(); i++)
		{
			bString.append((i+1) + "- ").append(particles_list.get(i).toString()).append("\n");
		}

		return bString.toString();
	}

}// fin de la classe SParticlesSystem
