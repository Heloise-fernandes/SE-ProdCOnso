package prodcons.v5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

//BUFFER
public class ProdCons implements Tampon{

	private int ecriture;
	private int lecture;
	private int nbProd;
	private Condition notFull;
	private Condition notEmpty;
	private Lock mutex;
	//public TestProdCons test;
	
	private Message[] buffer;
	
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
		
		
		System.out.println("Consommateur : "+ arg0.identification()+ " tente lock");
		
		mutex.lock();
		try{
			
			if((this.buffer[lecture]==null)&&(this.nbProd!=0)){
				System.out.println("====>Attendre consommateur "+arg0.identification());
				notEmpty.await();
			}
			
			if((this.nbProd==0)&&(this.buffer[lecture]==null))
			{
				System.out.println("====>Plus de rien à lire et plus de producteur,consomateur "+arg0.identification());
				notEmpty.signalAll();
				throw new PlusDeProdException();
			}
			
			Message m = this.buffer[lecture];
			this.buffer[lecture] = null;
			this.lecture = (lecture + 1) %buffer.length;
			
			System.out.println("Consommateur : "+ arg0.identification()+ " signal notFull");
			notFull.signal();
			System.out.println("Consommateur : "+ arg0.identification()+ " fait unlock");
			return m;
		}finally{
			System.out.println("====>unlock,consomateur "+arg0.identification());
			mutex.unlock();
		}	
		

	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception,InterruptedException {
		mutex.lock();
		try{
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
	
	
	public void decrementeNbProducteur()
	{
		mutex.lock();
		try{
			
		this.nbProd--;
		if(this.nbProd==0)
		{
			//notifyAll();
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
