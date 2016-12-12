package prodcons.v2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon{

	/**
	 * Pointeur sur la prochaine case où ecrire
	 */
	private int ecriture;
	
	/**
	 * Poiteur sur la prochaine case où lire
	 */
	private int lecture;
	
	/**
	 * Compteur du nombre de producteurs courant
	 */
	private int nbProd;
	
	/**
	 * Semaphore protégant l'écriture dans le buffer
	 */
	private Semaphore notFull;
	
	/**
	 * Semaphore protégant la lecture dans le buffer
	 */
	private Semaphore notEmpty;
	
	/**
	 * Semaphore protégant l'accès aux variables de la classe
	 */
	private Semaphore mutex;
	
	/**
	 * Tableau représentant le buffer
	 */
	private Message[] buffer;
	
	/**
	 * Constructeur d'un buffer cyclique producteur consommateur
	 * @param tailleBuffer taille du buffer à creer
	 * @param producteur Nombre de producteur à l'initialisation du système
	 */
	public ProdCons(int tailleBuffer, int producteur) 
	{
		this.buffer = new Message[tailleBuffer];
		this.ecriture = 0;
		this.lecture = 0;
		this.nbProd = producteur;
		this.notFull = new Semaphore(this.taille());
		this.notEmpty = new Semaphore(0);
		this.mutex = new Semaphore(1);
	}
	
	@Override
	public int enAttente() 
	{
		if(this.buffer[ecriture%buffer.length] != null)
		{
			return 1;
		}
		else if(this.buffer[lecture%buffer.length] == null)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException,PlusDeProdException {
		
		//Permet la terminaison du systeme
		mutex.p();
		if((this.nbProd==0)&&(this.buffer[lecture]==null))
		{
			mutex.v();
			throw new PlusDeProdException();
		}
		mutex.v();
		
		
		notEmpty.p();
		//Permet la terminaison du systeme
		if((this.nbProd==0))
		{
			notEmpty.v();
			if(this.buffer[lecture]==null)
				throw new PlusDeProdException();
		}
		mutex.p();
		
		Message m = this.buffer[lecture];
		this.buffer[lecture] = null;
		this.lecture = (lecture + 1) %buffer.length;
		
		mutex.v();
		notFull.v();
		return m;

	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception,InterruptedException {
		notFull.p();
		mutex.p();
		this.buffer[ecriture]  = arg1;
		this.ecriture = (ecriture + 1) %buffer.length;
		mutex.v();
		notEmpty.v();
	}

	@Override
	public int taille() {
		return this.buffer.length;
	}
	
	/**
	 * Decrémente le nombre de producteurs actif restant
	 */
	public void decrementeNbProducteur()
	{
		mutex.p();
		this.nbProd--;
		if(this.nbProd==0)
		{
			notEmpty.v();
		}
		mutex.v();
	}
	
	
	
	public String toString()
	{
		return "ProdCons : nombre de lecture("+this.lecture+"), nombre d'écriture("+this.ecriture+")";
	}
}
