package prodcons.v6;

import jus.poc.prodcons.Message;

public class MessageX implements Message{
	
	/**Num�ro du producteur qui creer le message*/
	private int numeroProd;
	/**Numero du message cr�er par le producteur*/
	private int numeroMessage;

	/**Constructeur*/
	public MessageX(int numeroProd, int numeroMessage)
	{
		this.numeroProd = numeroProd;
		this.numeroMessage = numeroMessage;
	}
	
	/**Repr�sentation textuelle d'un message*/
	public String toString(){
		return "Message : Producteur " + numeroProd +"-" + numeroMessage;
	}
}
