package prodcons.v5;

import jus.poc.prodcons.Message;

public class MessageX implements Message{
	
	private int numeroProd;
	private int numeroMessage;

	public MessageX(int numeroProd, int numeroMessage)
	{
		this.numeroProd = numeroProd;
		this.numeroMessage = numeroMessage;
	}
	
	public String toString(){
		return "Message : Producteur " + numeroProd +"-" + numeroMessage;
	}
}
