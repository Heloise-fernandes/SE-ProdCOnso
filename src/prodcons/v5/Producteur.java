package prodcons.v5;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur{
	/**
	 * Nombre de message � produire
	 */
	private int nbMessage;
	
	/**
	 * ID du prochain message � produire
	 */
	private int idMsg;
	
	/**
	 * Buffer dans lequel �crire
	 */
	private ProdCons buffer;
	
	/**
	 * Nombre de messages �crit par le producteur 
	 */
	private int nbMessageEcrit;
	
	/**
	 * Constructeur
	 * @param observateur observateur du syst�me
	 * @param moyenneTempsDeTraitement temps de production moyen d'un message 
	 * @param deviationTempsDeTraitement 
	 * @param nbMoyenProducteur nombre moyen de message � produire
	 * @param derivationProd 
	 * @param b buffer
	 * @throws ControlException
	 */
	protected Producteur( Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, int nbMoyenProducteur, int derivationProd, ProdCons b) 
					throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		Aleatoire alea = new Aleatoire(nbMoyenProducteur, derivationProd);
		this.nbMessage = alea.next()+1;
		this.buffer = b;
		this.idMsg = 0;	
		observateur.newProducteur(this);
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
				/**
				 * On signal � l'observateur qu'on produit un message 
				 */
				observateur.productionMessage(this, m, aleatoire);
				//Temps construction message
				sleep(1000);
				//On d�pose dans le buffer
				this.buffer.put(this, m);
				/**
				 * On signal � l'observateur qu'on a d�pos� le message
				 */
				observateur.depotMessage(this, m);
				this.idMsg++;
			} catch (Exception e) {e.printStackTrace();}
		}
		this.buffer.decrementeNbProducteur();
	}

}
