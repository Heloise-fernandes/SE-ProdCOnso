package prodcons.v5;

import jus.poc.prodcons.Message;

public class MessageX implements Message{
	/**
	 * L'identifiant du producteur qui cr�er le message
	 */
	private int numeroProd;
	/**
	 * Le num�ro du message cr��
	 */
	private int numeroMessage;

	/**
	 * Constructeur
	 * @param numeroProd L'identifiant du producteur qui cr�er le message
	 * @param numeroMessage Le num�ro du message cr��
	 */
	public MessageX(int numeroProd, int numeroMessage)
	{
		this.numeroProd = numeroProd;
		this.numeroMessage = numeroMessage;
	}
	
	public String toString(){
		return "Message : Producteur " + numeroProd +"-" + numeroMessage;
	}
}
