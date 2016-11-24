package prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;

public class Consommateur extends Acteur implements _Consommateur {
	

	int nbMessage;
	ProdCons buffer;
	
	protected Consommateur( Observateur observateur, int moyenneTempsDeTraitement,	int deviationTempsDeTraitement,ProdCons b) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.buffer = b;
		this.nbMessage = 0;
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
		
		try 
		{
			Message m = this.buffer.get(this);
			//TODO : imprimer message
			this.incrementer();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	

}
