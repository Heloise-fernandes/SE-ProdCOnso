package prodcons.v6;

import jus.poc.prodcons.Message;

public class MessageX implements Message{
	
	/**Numéro du producteur qui creer le message*/
	private int numeroProd;
	/**Numero du message créer par le producteur*/
	private int numeroMessage;

	/**Constructeur*/
	public MessageX(int numeroProd, int numeroMessage)
	{
		this.numeroProd = numeroProd;
		this.numeroMessage = numeroMessage;
	}
	
	/**Représentation textuelle d'un message*/
	public String toString(){
		return "Message : Producteur " + numeroProd +"-" + numeroMessage;
	}
}
