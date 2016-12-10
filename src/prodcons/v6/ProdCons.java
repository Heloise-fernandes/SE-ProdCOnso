package prodcons.v6;

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
	//public TestProdCons test;
	
	private Message[] buffer;
	
	public ProdCons(int tailleBuffer, int producteur) 
	{
		this.buffer = new Message[tailleBuffer];
		this.ecriture = 0;
		this.lecture = 0;
		this.nbProd = producteur;
		this.notFull = new Semaphore(1);
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
			throw new PlusDeProdException();
		}
		mutex.v();
		
		
		System.out.println("Consommateur : "+ arg0.identification()+ " tente notEmpty");
		notEmpty.p();
		
		if(this.nbProd==0)
		{
			notEmpty.v();
			if(this.buffer[lecture]==null)
				throw new PlusDeProdException();
		}
		
		//notFull.p();
		System.out.println("Consommateur : "+ arg0.identification()+ " passe le notEmpty");
		System.out.println("Consommateur : "+ arg0.identification()+ " tente mutex");
		
		mutex.p();
		if((this.nbProd==0)&&(this.buffer[lecture]==null))
		{
			mutex.v();
			throw new PlusDeProdException();
		}
		System.out.println("Consommateur : "+ arg0.identification()+ " passe le mutex");
		
		Message m = this.buffer[lecture];
		this.buffer[lecture] = null;
		this.lecture = (lecture + 1) %buffer.length;
		
		mutex.v();
		System.out.println("Consommateur : "+ arg0.identification()+ " libère le mutex");
		notFull.v();
		
		System.out.println("Consommateur : "+ arg0.identification()+ " libere le notFull");
		return m;

	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception,InterruptedException {
		
		System.out.println("Producteur : "+ arg0.identification()+ " tente notFull");
		notFull.p();
		System.out.println("Producteur : "+ arg0.identification()+ " tente un mutex");
		mutex.p();
		this.buffer[ecriture]  = arg1;
		this.ecriture = (ecriture + 1) %buffer.length;
		mutex.v();
		notEmpty.v();
		//notFull.v();
		
		System.out.println("Producteur : "+ arg0.identification()+ " fini");
		
	}

	@Override
	public int taille() {
		return this.buffer.length;
	}
	
	
	public void decrementeNbProducteur()
	{
		mutex.p();
		this.nbProd--;
		if(this.nbProd==0)
		{
			//notifyAll();
			notEmpty.v();
		}
		mutex.v();
		
	}
	
	
	
	public String toString()
	{
		return "ProdCons : nombre de lecture("+this.lecture+"), nombre d'écriture("+this.ecriture+")";
	}
}
