package prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

//BUFFER
public class ProdCons implements Tampon{

	private int pointeurEcriture;
	private int pointeurLecture;
	private int ecriture;
	private int lecture;
	private int nbProd;
	//public TestProdCons test;
	
	private Message[] buffer;
	
	public ProdCons(int tailleBuffer, int producteur) 
	{
		this.buffer = new Message[tailleBuffer];
		this.pointeurEcriture = 0;
		this.pointeurLecture = 0;
		this.ecriture = 0;
		this.lecture = 0;
		this.nbProd = producteur;
		//this.test = new TestProdCons(observateur);
	}
	
	@Override
	public int enAttente() 
	{
		if(this.buffer[this.pointeurEcriture] != null)
		{
			return 1;
		}
		else if(this.buffer[this.pointeurLecture] == null)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception, InterruptedException,PlusDeProdException {
		
		System.out.println("Consommateur : "+ arg0.identification()+ " tente un get");
		
		while((this.pointeurEcriture == this.pointeurLecture)||(this.buffer[this.pointeurLecture]==null))
		{
			System.out.println("Consommateur : "+ arg0.identification()+ " attend, (ecriture = consomateur) : "+(this.pointeurEcriture == this.pointeurLecture)+" null? : "+(this.buffer[this.pointeurLecture]==null));
			if((this.nbProd==0)&&(this.buffer[this.pointeurLecture]!=null))
			{
				System.out.println("Consommateur : "+ arg0.identification()+ " crève");
				throw new PlusDeProdException();
			}
			wait();
		}
		System.out.println("Consommateur : "+ arg0.identification()+ " passe le get");
		
		this.pointeurLecture++;
		Message m = this.buffer[this.pointeurLecture];
		this.buffer[this.pointeurLecture] = null;
		this.incrementerLecture();
		this.lecture++;
		
		this.notifyAll();
		
		return m;

	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception,InterruptedException {
		
		System.out.println("Producteur : "+ arg0.identification()+ " tente un put");
		
		while(this.buffer[this.pointeurEcriture]!=null)//this.pointeurEcriture==this.buffer.length
		{
			System.out.println("Producteur : "+ arg0.identification() + " attend, null? : "+(this.buffer[this.pointeurEcriture]==null));
			wait();
		}
		
		System.out.println("Producteur : "+ arg0.identification()+ " fait un put");
		this.ecriture++;
		this.buffer[this.pointeurEcriture]  = arg1;
		this.incrementerEcriture();
		
		this.notifyAll();
			
	}

	@Override
	public int taille() {
		return this.buffer.length;
	}
	
	
	//Modification des pointeurs
	public void incrementerEcriture()
	{
		if(this.pointeurEcriture + 1 == this.taille())
		{
			this.pointeurEcriture = 0;
		}
		else{this.pointeurEcriture++;}
	}
	
	public void decrementerEcriture()
	{
		if(this.pointeurEcriture == 0)
		{
			this.pointeurEcriture = this.taille()-1;
		}
		else{this.pointeurEcriture--;}
	}
	
	public void incrementerLecture()
	{
		if(this.pointeurLecture + 1 == this.taille())
		{
			this.pointeurLecture = 0;
		}
		else{this.pointeurLecture++;}
	}
	
	public void decrementerLecture()
	{
		if(this.pointeurLecture == 0)
		{
			this.pointeurLecture = this.taille()-1;
		}
		else{this.pointeurLecture--;}
	}

	
	public void decrementeNbProducteur()
	{
		this.nbProd--;
		/*if(this.nbProd==0)
		{
			notifyAll();
		}*/
	}
	
	
	
	public String toString()
	{
		return "ProdCons : nombre de lecture("+this.lecture+"), nombre d'écriture("+this.ecriture+")";
	}
}
