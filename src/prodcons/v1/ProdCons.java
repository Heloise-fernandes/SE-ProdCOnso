package prodcons.v1;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

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
		
		while(this.buffer[this.lecture]==null)
		{
			if((this.nbProd==0)&&(this.buffer[this.lecture]==null))
			{
				notifyAll();
				throw new PlusDeProdException();
			}
			wait();
		}
		
		Message m = this.buffer[this.lecture];
		this.buffer[this.lecture] = null;
		this.incrementerLecture();
		
		this.notifyAll();
		
		return m;

	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception,InterruptedException {
		
		
		while(this.buffer[this.ecriture]!=null)//this.pointeurEcriture==this.buffer.length
		{
			wait();
		}
		
		this.buffer[this.ecriture]  = arg1;
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
		this.ecriture = ((this.ecriture + 1) %buffer.length);
	}
	
	public void incrementerLecture()
	{
		this.lecture = ((this.lecture + 1)%buffer.length);
	}
	
		
	public synchronized void decrementeNbProducteur()
	{
		this.nbProd--;
	}
	
	public String toString()
	{
		return "ProdCons : nombre de lecture("+this.lecture+"), nombre d'écriture("+this.ecriture+")";
	}
}
