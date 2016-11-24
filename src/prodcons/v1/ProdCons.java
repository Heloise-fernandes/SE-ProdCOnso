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
	
	private Message[] buffer;
	
	public ProdCons(int N) 
	{
		this.buffer = new Message[N];
		this.pointeurEcriture = 0;
		this.pointeurLecture = 0;
		this.ecriture = 0;
		this.lecture = 0;
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
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		
		while((this.lecture!=0)||(this.pointeurEcriture == this.pointeurLecture))
		{
			wait();
		}
		synchronized (arg0) {
			this.pointeurLecture++;
			Message m = this.buffer[this.pointeurLecture];
			this.incrementerLecture();
			this.lecture--;
			return m;
		}
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception,InterruptedException {
		
		while((this.ecriture!=0)||(this.pointeurEcriture == this.pointeurLecture))
		{
			wait();
		}
		synchronized (arg0) {
			this.ecriture++;
			this.buffer[this.pointeurEcriture]  = arg1;
			this.incrementerEcriture();
			this.ecriture--;
		}
				
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

}
