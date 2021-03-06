package prodcons.v6;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;

public class Consommateur extends Acteur implements _Consommateur {
	
	/**Observateur*/
	ObservateurV6 observateurv6;
	
	/**
	 * Le nombre de message consomm�
	 */
	int nbMessage;
	
	/**
	 * Le buffer o� l'on doit consommer
	 */
	ProdCons buffer;
	
	/**
	 * Constructeur
	 * @param observateur 
	 * @param moyenneTempsDeTraitement
	 * @param deviationTempsDeTraitement
	 * @param b buffer li� au consomateur
	 * @throws ControlException
	 */
	protected Consommateur( Observateur observateur,ObservateurV6 o, int moyenneTempsDeTraitement,	int deviationTempsDeTraitement,ProdCons b) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.buffer = b;
		this.nbMessage = 0;
		this.observateurv6 = o;
	}

	/**
	 * Renvoie le nombre de messages lu par le consommateur
	 */
	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}

	/**
	 * permet d'incrementer le nombre de message lu
	 */
	public void incrementer()
	{
		this.nbMessage++;
	}
	
	/**
	 * Coeur de metier du consommateur
	 */
	@Override
	public void run() {
		while(true) 
		{
			Message m = this.consommer();
			if(m==null){break;}
			this.traiter(m);
			//System.out.println("Consommateur "+identification()+" toujour en vie");
		}
	}
	/**
	 * Demande au buffer un message
	 * @return le message r�cup�r� du buffer
	 */
	public Message consommer()
	{
		try 
		{
			Message m = this.buffer.get(this);
			this.incrementer();
			return m;
			
		}
		catch (PlusDeProdException e) {return null;}
		catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	/**
	 * Traite le message pr�alablement r�cup�r�
	 * @param m le message r�cup�r� du buffer
	 */
	public void traiter(Message m)
	{
		try 
		{
			int aleatoire = Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement);
			sleep(aleatoire);
			observateurv6.consommationMessage(this,m,aleatoire);
			System.out.println(m);
			
		}
		catch (Exception e) {e.printStackTrace();}
	}
	

}

