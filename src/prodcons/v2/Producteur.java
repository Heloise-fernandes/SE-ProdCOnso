package prodcons.v2;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur{
	
	private int nbMessage;
	private int idMsg;
	private ProdCons buffer;
	private int nbMessageEcrit;
	
	protected Producteur( Observateur observateur, int moyenneTempsDeTraitement,int deviationTempsDeTraitement, int nbMoyenProducteur, int derivationProd, ProdCons b) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		//TODO : Generer nbMessage à produire
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
			try 
			{
				//Temps construction message
				sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
				//On dépose dans le buffer
				Message m = new MessageX(super.identification(), this.idMsg);
				this.buffer.put(this, m);
				
				this.idMsg++;
			} catch (Exception e) {e.printStackTrace();}
		}
		this.buffer.decrementeNbProducteur();
		System.out.println("Fin producteur"+identification());
	}

}
