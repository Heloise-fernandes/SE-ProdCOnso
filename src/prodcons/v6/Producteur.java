package prodcons.v6;

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
	/**Nombre de message �crit*/
	private int nbMessageEcrit;
	/**Observateur*/
	private ObservateurV6 observateurv6;
	
	/**Construteur*/
	protected Producteur( Observateur observateur,ObservateurV6 o, int moyenneTempsDeTraitement,int deviationTempsDeTraitement, int nbMoyenProducteur, int derivationProd, ProdCons b) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		
		Aleatoire alea = new Aleatoire(nbMoyenProducteur, derivationProd);
		this.nbMessage = alea.next()+1;
		this.buffer = b;
		this.idMsg = 0;
		this.observateurv6 = o;
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
			int aleatoire =Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement); 
			sleep(aleatoire);
			Message m = new MessageX(super.identification(), this.idMsg);
			
			
			
			//On d�pose dans le buffer
			this.buffer.put(this, m);
			observateurv6.productionMessage(this,m,aleatoire);
			
			//on incr�mente le nombre de message ajout� au buffer
			this.idMsg++;
			return m;
			
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}	
}



