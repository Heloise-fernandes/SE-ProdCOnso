package prodcons.v1;

import java.util.Random;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur{
	
	int nbMessage;

	protected Producteur( Observateur observateur, int moyenneTempsDeTraitement,int deviationTempsDeTraitement, int nbMoyenProducteur, int derivationProd) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		//TODO : Generer nbMessage à produire
		Aleatoire alea = new Aleatoire(nbMoyenProducteur, derivationProd);
		this.nbMessage = alea.next();
		
	}

	@Override
	public int nombreDeMessages() {
		
		return this.nbMessage;
	}

}
