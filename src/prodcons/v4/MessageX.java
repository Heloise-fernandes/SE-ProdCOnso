package prodcons.v4;

import jus.poc.prodcons.Message;

public class MessageX implements Message{
	
	private int numeroProd;
	private int numeroMessage;
	private int multiple;
	
	public MessageX(int numeroProd, int numeroMessage, int nbConsommationAvantErase)
	{
		this.numeroProd = numeroProd;
		this.numeroMessage = numeroMessage;
		this.multiple = nbConsommationAvantErase;
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
		return "Message : Producteur " + numeroProd +"-" + numeroMessage;
	}
}
