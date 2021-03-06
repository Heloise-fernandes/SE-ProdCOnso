package prodcons.v4.v2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

//BUFFER
public class ProdCons implements Tampon{

	private int ecriture;
	private int lecture;
	private int nbProd;
	private Semaphore notFull;
	private Semaphore notEmpty;
	private Semaphore mutex;
	
	private Message[] buffer;
	
	/**Constructeur
	 * @param tailleBuffer : capacit� du buffer
	 * 	producteur : le nombre de producteur qui pevent �crire dans le buffer*/
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
		
		mutex.p();
		if((this.nbProd==0)&&(this.buffer[lecture]==null))
		{
			mutex.v();
			throw new PlusDeProdException();//on s'ar�te
		}
		mutex.v();
		
		
		notEmpty.p();
		mutex.p();
		
		if(this.nbProd==0)//si plus de producteur
		{
			notEmpty.v();//on lib�re un consommateur
			mutex.v();
			if(this.buffer[lecture]==null)//si plus de message fin
				throw new PlusDeProdException();
		}
		
		MessageX m = (MessageX) this.buffer[lecture];
		int restant = m.getNbRestant();
		if(restant==1)
		{
			m.moinsUnMessage();			
			this.buffer[lecture] = null;
			this.lecture = (lecture + 1) %buffer.length;//on avance le pointeur de lecture
			for(int i = 0 ; i < m.getNbMsg(); i++)
			{
				m.v();//lib�re le producteur et tout les consommateurs bloqu� sur le message
			}
			notFull.v();
			mutex.v();
		}
		else
		{
			m.moinsUnMessage();
			mutex.v();
			m.p();//on bloque le consommateur sur le message
		}
		return m;
		
		}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception,InterruptedException {
		
		
		notFull.p();
		mutex.p();
		
		this.buffer[ecriture]  = arg1;
		this.ecriture = (ecriture + 1) %buffer.length;
		
		MessageX m = (MessageX) arg1;
		
		for (int i = 0; i < m.getNbRestant(); i++) {
			notEmpty.v();//on lib�re autant de consommateur qu'il y d'�xemplaire � lire
		}
		mutex.v();
		
		m.p();//on bloque le producteur sur le message
			
	}

	@Override
	public int taille() {
		return this.buffer.length;
	}
	
	/**D�cr�mente le nombre de producteur actifs*/
	public void decrementeNbProducteur()
	{
		mutex.p();
		this.nbProd--;
		if(nbProd==0)
		{
			notEmpty.v();
		}
		mutex.v();
	}
	
	/**Repr�sentation textuelle du buffer*/
	public String toString()
	{
		return "ProdCons : nombre de lecture("+this.lecture+"), nombre d'�criture("+this.ecriture+")";
	}
}