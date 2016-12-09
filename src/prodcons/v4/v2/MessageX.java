package prodcons.v4.v2;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Message;

public class MessageX implements Message{
	
	private int numeroProd;
	private int numeroMessage;
	private int multiple;
	private final int nombreDeMessageTotal;
	private Semaphore mutex;
	
	public MessageX(int numeroProd, int numeroMessage, int nbConsommationAvantErase)
	{
		this.numeroProd = numeroProd;
		this.numeroMessage = numeroMessage;
		//TODO Passer en argument constructeur ?
		this.multiple = Aleatoire.valeur(TestProdCons.nombreMoyenNbExemplaire, TestProdCons.deviationNombreMoyenNbExemplaire);
		this.nombreDeMessageTotal = this.multiple;
		this.mutex = new Semaphore(0);
	}
	
	public void moinsUnMessage()
	{
		this.multiple--;
	}
	
	public int getNbRestant()
	{
		return this.multiple;
	}
	
	public String toString(){
		return "Message : Producteur " + numeroProd +"-" + numeroMessage+"-"+this.multiple;
	}
	
	public void p()
	{
		this.mutex.p();
	}
	
	public void v()
	{
		this.mutex.v();
	}
	
	public int getNbMsg()
	{
		return this.nombreDeMessageTotal;
	}
}
