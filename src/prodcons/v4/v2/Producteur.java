package prodcons.v4.v2;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur{
	
	/**Nombre de message � �crire*/
	private int nbMessage;
	/**Num�ro du prochain message � �crire*/
	private int idMsg;
	/**Buffer*/
	private ProdCons buffer;
	
	/**Construteur*/
	protected Producteur( Observateur observateur, int moyenneTempsDeTraitement,int deviationTempsDeTraitement, int nbMoyenProducteur, int derivationProd, ProdCons b) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		
		Aleatoire alea = new Aleatoire(nbMoyenProducteur, derivationProd);
		this.nbMessage = alea.next()+1;
		this.buffer = b;
		this.idMsg = 0;		
	}

	@Override
	public int nombreDeMessages() {
		
		return this.nbMessage;
	}
	
	
	@Override
	public void run() {
		
		while(nbMessage - idMsg>0){
			this.ecrire();
		}
		this.buffer.decrementeNbProducteur();
	}

	/**Fonction qui permet d'�crire un message dans le buffer*/
	public Message ecrire()
	{
		try 
		{
			//Temps construction message
			sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
			Message m = new MessageX(super.identification(), this.idMsg);
			
			//On d�pose dans le buffer
			this.buffer.put(this, m);
			//on incr�mente le nombre de message ajout� au buffer
			this.idMsg++;
			return m;
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}	
}
