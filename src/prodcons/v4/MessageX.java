package prodcons.v4;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Message;

public class MessageX implements Message{


	/**Numéro du producteur qui creer le message*/
	private int numeroProd;
	/**Numero du message créer par le producteur*/
	private int numeroMessage;
	/**Nombre de fois que le message doit être mis*/	
	private int multiple;
	
	/**Constructeur*/
	public MessageX(int numeroProd, int numeroMessage)
	{
		this.numeroProd = numeroProd;
		this.numeroMessage = numeroMessage;
		this.multiple = Aleatoire.valeur(TestProdCons.nombreMoyenNbExemplaire, TestProdCons.deviationNombreMoyenNbExemplaire);
	}
	
	/**Decrementer le nombre d'exemplaire du message restant à lire*/
	public void moinsUnMessage()
	{
		this.multiple--;
	}
	
	/**Nombre d'exemplaire du message restant à lire*/
	public int getNbRestant()
	{
		return this.multiple;
	}
	
	
	/**Représentation textuelle d'un message*/
	public String toString(){
		return "Message : Producteur " + numeroProd +"-" + numeroMessage;
	}
}
