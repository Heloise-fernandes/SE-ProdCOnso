package prodcons.v4.v2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;

public class Consommateur extends Acteur implements _Consommateur {
	
	/**
	 * Le nombre de message consommé
	 */
	int nbMessage;
	
	/**
	 * Le buffer où l'on doit consommer
	 */
	ProdCons buffer;
	
	/**
	 * Constructeur
	 * @param observateur 
	 * @param moyenneTempsDeTraitement
	 * @param deviationTempsDeTraitement
	 * @param b buffer lié au consomateur
	 * @throws ControlException
	 */
	protected Consommateur( Observateur observateur, int moyenneTempsDeTraitement,	int deviationTempsDeTraitement,ProdCons b) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.buffer = b;
		this.nbMessage = 0;
	}

	/**
	 * Renvoie le nombre de messages lu par le consommateur
	 */
	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}

	/**
	 * permet d'incrementer le nombre de message lu
	 */
	public void incrementer()
	{
		this.nbMessage++;
	}
	
	/**
	 * Coeur de metier du consommateur
	 */
	@Override
	public void run() {
		while(true) 
		{
			Message m = this.consommer();
			if(m==null){break;}
			this.traiter(m);
		}
	}
	/**
	 * Demande au buffer un message
	 * @return le message récupéré du buffer
	 */
	public Message consommer()
	{
		try 
		{
			Message m = this.buffer.get(this);
			this.incrementer();
			return m;
			
		}
		catch (PlusDeProdException e) {return null;}
		catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	/**
	 * Traite le message préalablement récupéré
	 * @param m le message récupéré du buffer
	 */
	public void traiter(Message m)
	{
		try 
		{
			sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
			System.out.println(m);
			
		}
		catch (Exception e) {e.printStackTrace();}
	}
	

}
