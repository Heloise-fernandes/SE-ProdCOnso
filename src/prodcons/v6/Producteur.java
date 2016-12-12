package prodcons.v6;

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
	private ObservateurV6 observateurv6;
	
	protected Producteur( Observateur observateur, ObservateurV6 observateurv6, int moyenneTempsDeTraitement,int deviationTempsDeTraitement, int nbMoyenProducteur, int derivationProd, ProdCons b) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		//TODO : Generer nbMessage à produire
		Aleatoire alea = new Aleatoire(nbMoyenProducteur, derivationProd);
		this.nbMessage = alea.next()+1;
		this.buffer = b;
		this.idMsg = 0;	
		this.observateurv6 = observateurv6;
		observateurv6.newProducteur(this);
	}

	@Override
	public int nombreDeMessages() {
		
		return this.nbMessage;
	}
	
	
	@Override
	public void run() {
		
		int aleatoire;
		Message m;
		while(nbMessage - idMsg>0){
			try 
			{
				m = new MessageX(super.identification(), this.idMsg);
				aleatoire = Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement);
				observateurv6.productionMessage(this, m, aleatoire);
				//Temps construction message
				sleep(1000);
				
				//On dépose dans le buffer
				this.buffer.put(this, m);
				observateurv6.depotMessage(this, m,System.currentTimeMillis());
				
				this.idMsg++;
			} catch (Exception e) {e.printStackTrace();}
		}
		this.buffer.decrementeNbProducteur();
		System.out.println("Fin producteur"+identification());
	}

}
