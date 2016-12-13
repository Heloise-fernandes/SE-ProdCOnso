package prodcons.v4;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Message;

public class MessageX implements Message{


	/**Num�ro du producteur qui creer le message*/
	private int numeroProd;
	/**Numero du message cr�er par le producteur*/
	private int numeroMessage;
	/**Nombre de fois que le message doit �tre mis*/	
	private int multiple;
	
	/**Constructeur*/
	public MessageX(int numeroProd, int numeroMessage)
	{
		this.numeroProd = numeroProd;
		this.numeroMessage = numeroMessage;
		this.multiple = Aleatoire.valeur(TestProdCons.nombreMoyenNbExemplaire, TestProdCons.deviationNombreMoyenNbExemplaire);
	}
	
	/**Decrementer le nombre d'exemplaire du message restant � lire*/
	public void moinsUnMessage()
	{
		this.multiple--;
	}
	
	/**Nombre d'exemplaire du message restant � lire*/
	public int getNbRestant()
	{
		return this.multiple;
	}
	
	
	/**Repr�sentation textuelle d'un message*/
	public String toString(){
		return "Message : Producteur " + numeroProd +"-" + numeroMessage;
	}
}
