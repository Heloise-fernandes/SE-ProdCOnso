package prodcons.v6;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;

public class Consommateur extends Acteur implements _Consommateur {
	

	int nbMessage;
	ProdCons buffer;
	
	protected Consommateur( Observateur observateur, int moyenneTempsDeTraitement,	int deviationTempsDeTraitement,ProdCons b) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.buffer = b;
		this.nbMessage = 0;
		observateur.newConsommateur(this);
	}

	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}

	public void incrementer()
	{
		this.nbMessage++;
	}
	
	@Override
	public void run() {
		int aleatoire;
		while(true) //TODO changer ca
		{
			try 
			{
				aleatoire = Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement);
				Message m = this.buffer.get(this);
				observateur.retraitMessage(this, m);
				//TODO : imprimer message + faire genre temps de traitement
				sleep(aleatoire);
				observateur.consommationMessage(this, m, aleatoire);
				
				System.out.println(m);
				this.incrementer();
				
			}
			catch (PlusDeProdException e) {break;}
			catch (Exception e) {e.printStackTrace();}
		}
		System.out.println("Fin consomateur"+identification());
	}
	
	

}
