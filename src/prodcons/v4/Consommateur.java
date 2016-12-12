package prodcons.v4;

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
	 * @param observateur Observateur du système
	 * @param moyenneTempsDeTraitement temps de traitement moyen d'un message
	 * @param deviationTempsDeTraitement
	 * @param b le buffer lié
	 * @throws ControlException
	 */
	protected Consommateur( Observateur observateur, int moyenneTempsDeTraitement,	
			int deviationTempsDeTraitement,ProdCons b) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.buffer = b;
		this.nbMessage = 0;
	}

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
	
	@Override
	public void run() {
		while(true)
		{
			try 
			{
				Message m = this.buffer.get(this);
				sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
				System.out.println(m);
				this.incrementer();
			}
			catch (PlusDeProdException e) {break;}
			catch (Exception e) {e.printStackTrace();}
		}
		
	}
	
	

}
