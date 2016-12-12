package prodcons.v5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	 * Condition protégant l'écriture dans le buffer
	 */
	private Condition notFull;
	/**
	 * Condition protégant la lecture dans le buffer
	 */
	private Condition notEmpty;
	/**
	 * Verrou protégant l'accès aux variables de la classe
	 */
	private Lock mutex;
	
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
		this.mutex = new ReentrantLock();
		this.notFull = mutex.newCondition();
		this.notEmpty = mutex.newCondition();
		
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
		mutex.lock();
		try{
			/**
			 * On se bloque si pas de messages à lire
			 */
			if((this.buffer[lecture]==null)&&(this.nbProd!=0)){
				notEmpty.await();
			}
			/**
			 * Pour la terminaison
			 */
			if((this.nbProd==0)&&(this.buffer[lecture]==null))
			{
				notEmpty.signalAll();
				throw new PlusDeProdException();
			}
			
			Message m = this.buffer[lecture];
			this.buffer[lecture] = null;
			this.lecture = (lecture + 1) %buffer.length;
			notFull.signal();
			return m;
		}finally{
			mutex.unlock();
		}	
		

	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception,InterruptedException {
		mutex.lock();
		try{
			/**
			 * On se bloque si le buffer est plein
			 */
			if(this.buffer[ecriture]!=null){
				notFull.await();
		}
			this.buffer[ecriture]  = arg1;
			this.ecriture = (ecriture + 1) %buffer.length;
			notEmpty.signal();
		}finally{
			mutex.unlock();
		}
		
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
		mutex.lock();
		try{
			this.nbProd--;
			if(this.nbProd==0)
			{
				notEmpty.signalAll();
			}
		}finally{
			mutex.unlock();
		}
	}
	
	
	
	public String toString()
	{
		return "ProdCons : nombre de lecture("+this.lecture+"), nombre d'écriture("+this.ecriture+")";
	}
}
