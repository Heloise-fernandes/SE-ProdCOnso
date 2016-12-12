package prodcons.v5;

import jus.poc.prodcons.Message;

public class MessageX implements Message{
	/**
	 * L'identifiant du producteur qui créer le message
	 */
	private int numeroProd;
	/**
	 * Le numéro du message créé
	 */
	private int numeroMessage;

	/**
	 * Constructeur
	 * @param numeroProd L'identifiant du producteur qui créer le message
	 * @param numeroMessage Le numéro du message créé
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
