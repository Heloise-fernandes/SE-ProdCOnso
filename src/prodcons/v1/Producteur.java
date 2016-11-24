package prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur{

	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
	}

	@Override
	public int nombreDeMessages() {
		
		return 0;
	}

}
