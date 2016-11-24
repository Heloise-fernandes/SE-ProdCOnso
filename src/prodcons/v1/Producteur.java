package prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur{
	
	int nbMessage;

	protected Producteur( Observateur observateur, int moyenneTempsDeTraitement,int deviationTempsDeTraitement) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
	}

	@Override
	public int nombreDeMessages() {
		
		return this.nbMessage;
	}

}
