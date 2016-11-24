package prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;

public class Consommateur extends Acteur implements _Consommateur {
	

	int nbMessage;
	int numConso;
	ProdCons buffer;
	
	protected Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,	int deviationTempsDeTraitement,ProdCons b) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.buffer = b;
		this.nbMessage = 0;
	}

	@Override
	public int deviationTempsDeTraitement() {
		return super.deviationTempsDeTraitement();
	}

	@Override
	public int identification() {
		return numConso;
	}

	@Override
	public int moyenneTempsDeTraitement() {
		return super.moyenneTempsDeTraitement();
	}

	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}

	public void incrementer()
	{
		this.nbMessage = 0;
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
