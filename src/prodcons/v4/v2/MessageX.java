package prodcons.v4.v2;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Message;

public class MessageX implements Message{
	
	/**Num�ro du producteur qui creer le message*/
	private int numeroProd;
	/**Numero du message cr�er par le producteur*/
	private int numeroMessage;
	/**Nombre de fois que le message � �t� lu*/	
	private int multiple;
	/**Nombre de fois que le message doit �tre lu*/
	private final int nombreDeMessageTotal;
	/**S�maphore qui prot�ge les diff�rents exemplaire du message*/
	private Semaphore mutex;
	
	/**Constructeur*/
	public MessageX(int numeroProd, int numeroMessage)
	{
		this.numeroProd = numeroProd;
		this.numeroMessage = numeroMessage;
		this.multiple = Aleatoire.valeur(TestProdCons.nombreMoyenNbExemplaire, TestProdCons.deviationNombreMoyenNbExemplaire);
		this.nombreDeMessageTotal = this.multiple;
		this.mutex = new Semaphore(0);
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
	
	/**Ajouter une ressource au mutex*/
	public void p()
	{
		this.mutex.p();
	}
	
	/**Enlever une ressource au mutext*/
	public void v()
	{
		this.mutex.v();
	}
	
	/**Nombre de message au total qui doivent �tre consom�*/
	public int getNbMsg()
	{
		return this.nombreDeMessageTotal;
	}
}
