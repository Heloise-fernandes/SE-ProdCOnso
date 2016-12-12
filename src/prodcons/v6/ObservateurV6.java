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
	private ArrayList<_Producteur> producteurs;
	private ArrayList<_Consommateur> consommateurs;
	private boolean coherant = true;
	private int nbProd;
	private int nbCons;
	private int tailleB;
	private Hashtable<_Producteur,Message> depot;
	private Hashtable<_Consommateur, Message> retrait;
	
	
	public void init(int nbCons, int nbProd, int tailleBuffer){
		this.producteurs = new ArrayList<_Producteur>();
		this.consommateurs = new ArrayList<_Consommateur>();
		this.nbProd = nbProd;
		this.nbCons = nbCons;
		this.tailleB = tailleBuffer;
		this.depot = new Hashtable<_Producteur,Message>();
		this.retrait = new Hashtable<_Consommateur, Message>();
	}
	
	public synchronized boolean coherant() {
		return this.coherant;
	}
	
    /**
     * Evenement correspondant à la consommation d'un message 
     * @param c
     * @param m
     * @param tempsDeTraitement
     */
	 public void consommationMessage(_Consommateur c, Message m, int tempsDeTraitement){
		 
	 }
	 /**
	  * Evenement correspondant au dépot d'un message dans le tampon  
	  * @param p
	  * @param m
	 * @throws ControlException 
	  */
	 public synchronized void depotMessage(_Producteur p, Message m) throws ControlException{
		 
		 //On regarde les arguments
		 if(p!=null && m != null && coherant()){
			 if(!producteurs.contains(p)){
				 throw new ControlException(getClass(), "depotMessage");
			 }
			 depot.put(p, m);
		 }
		 else
			 throw new ControlException(getClass(), "depotMessage");
		 
		 
		 
		 
	 }
	 
	 /**
	  *  Evenement correspondant à la création d'un nouveau consommateur 
	  * @param c
	  */
	 public synchronized void newConsommateur(_Consommateur c){
		 
	 }
	 /**
	  *  Evenement correspondant à la création d'un nouveau producteur 
	  * @param p
	  */
	 public void newProducteur(_Producteur p)
	 {
		 
	 }
	 /**
	  * Evenement correspondant à la production d'un message         
	  * @param p
	  * @param m
	  * @param tempsDeTraitement
	  */
	 public void productionMessage(_Producteur p, Message m, int tempsDeTraitement){
	        	   
	 }
	 /**
	  * Evenement correspondant au retrait d'un message du tampon  
	  * @param c
	  * @param m
	  */
	 public  void retraitMessage(_Consommateur c, Message m){
	        	   
	 }
	           

	
}
