package prodcons.v6;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;


public class ObservateurV6 {
	private boolean coherant = true;
	private int nbCons;
	private int nbProd;
	private int trailleBuf;
	
	private long tpsAjout;
	private long tpsDepot;
	
	private Semaphore s;
	
	public void init(int nbCons, int nbProd, int tailleBuffer) throws ControlException{
		if(nbCons<=0||nbProd<=0||tailleBuffer<=0){ throw new ControlException(getClass(), "consommationMessage");}
		this.nbCons = nbCons;
		this.nbProd = nbProd;
		this.trailleBuf = tailleBuffer;
		
		this.tpsAjout = 0;
		this.tpsDepot = 0;
		this.s = new Semaphore(1);
	}
	
	
    /**
     * Evenement correspondant à la consommation d'un message 
     * @param c
     * @param m
     * @param tempsDeTraitement
     * @throws ControlException 
     */
	 public void consommationMessage(_Consommateur c, Message m, int tempsDeTraitement, long l) throws ControlException{
		if(c==null||m==null||tempsDeTraitement<=0||l<=0){ throw new ControlException(getClass(), "consommationMessage");}
		s.p();
		if(this.tpsAjout < l){this.tpsAjout = l;}
		else
		{ throw new ControlException(getClass(), "consommationMessage");}
		s.v();
	 }
	 /**
	  * Evenement correspondant au dépot d'un message dans le tampon  
	  * @param p
	  * @param m
	 * @throws ControlException 
	  */
	 public synchronized void depotMessage(_Producteur p, Message m, long l) throws ControlException{
		 if(p==null||m==null||l<=0){ throw new ControlException(getClass(), "depotMessage");}
		 s.p();
		 if(this.tpsAjout< l){this.tpsAjout = l;}
		 else
		 { throw new ControlException(getClass(), "depotMessage");}
		 s.v();
	 }
	 
	 /**
	  *  Evenement correspondant à la création d'un nouveau consommateur 
	  * @param c
	 * @throws ControlException 
	  */
	 public synchronized void newConsommateur(_Consommateur c) throws ControlException{
		 if(c==null)
		 { throw new ControlException(getClass(), "newConsommateur");}
	 }
	 /**
	  *  Evenement correspondant à la création d'un nouveau producteur 
	  * @param p
	 * @throws ControlException 
	  */
	 public void newProducteur(_Producteur p) throws ControlException
	 {
		 if(p==null)
		 { throw new ControlException(getClass(), "newProducteur");}
	 }
	 /**
	  * Evenement correspondant à la production d'un message         
	  * @param p
	  * @param m
	  * @param tempsDeTraitement
	 * @throws ControlException 
	  */
	 public void productionMessage(_Producteur p, Message m, int tempsDeTraitement) throws ControlException{
	       if(p==null||m==null||tempsDeTraitement<=0){throw new ControlException(getClass(), "productionMessage");}
	 }
	 /**
	  * Evenement correspondant au retrait d'un message du tampon  
	  * @param c
	  * @param m
	 * @throws ControlException 
	  */
	 public  void retraitMessage(_Consommateur c, Message m) throws ControlException{
		 if(c==null||m==null){throw new ControlException(getClass(), "retraitMessage");} 	   
	 }
	           

	
}
