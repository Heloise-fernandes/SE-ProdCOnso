package prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;

public class Consommateur extends Acteur implements _Consommateur {
	

	int nbMessage;
	ProdCons buffer;
	
	protected Consommateur( Observateur observateur, int moyenneTempsDeTraitement,	int deviationTempsDeTraitement,ProdCons b) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.buffer = b;
		this.nbMessage = 0;
	}

	@Override
	public int nombreDeMessages() {
		return nbMessage;
	}

	public void incrementer()
	{
		this.nbMessage++;
	}
	
	@Override
	public void run() {
		while(true) 
		{
			Message m = this.consommer();
			if(m==null){break;}
			this.traiter(m);
		}
		//System.out.println("Fin consomateur"+identification());
	}
	
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
	
	public void traiter(Message m)
	{
		try 
		{
			sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement));
			System.out.println(m);
			
		}
		catch (Exception e) {e.printStackTrace();}
	}
	

}
