package prodcons.v6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;


public class ObservateurV6 {
	
	private boolean coherant = true;
	
	private HashMap<Message, Long> messageDepot; 
	private HashMap<Message, Long> messageRetrait; 
	
	private Semaphore s;
	
	public void init(int nbCons, int nbProd, int tailleBuffer) throws ControlException{
		if(nbCons<=0||nbProd<=0||tailleBuffer<=0){ throw new ControlException(getClass(), "consommationMessage");}
		
		this.messageDepot = new HashMap<Message, Long>();
		this.messageRetrait = new HashMap<Message, Long>();
		
		this.s = new Semaphore(1);
	}
	
	/**Savoir si un message est retir� avant les messages mis avant lui*/
	public boolean estRetireDansBonOrdre(Message m, long tps) throws ControlException
	{
		long valueDepot = 0;
				
		for ( HashMap.Entry <Message, Long> entry : this.messageDepot.entrySet()) {
			if(entry.getKey()==m){valueDepot = entry.getValue();}
		}
		
		this.messageDepot.remove(m);
		if(valueDepot==0){throw new ControlException(getClass(), "Pas dans la liste depot"); }
		
		for ( HashMap.Entry <Message, Long> entry : this.messageDepot.entrySet()) {
			if(entry.getValue()<valueDepot){throw new ControlException(getClass(), "retir� trop tot");}
		}
		
		
		for ( HashMap.Entry <Message, Long> entry : this.messageRetrait.entrySet()) {
			if(tps < entry.getValue()){/*System.out.println("value : "+valueDepot+" other : "+entry.getValue());*/ return false;}
		}
		this.messageRetrait.put(m, tps);
		
		return true;
	}
	

	public String toString()
	{
		String s = "";
		s+= "==============>Retrait\n";
		for ( HashMap.Entry <Message, Long> entry : this.messageRetrait.entrySet()) {
			s+= entry.getKey().toString() + " - tps = "+entry.getValue()+"\n";
		}
		s+= "==============>Depot\n";
		for ( HashMap.Entry <Message, Long> entry : this.messageDepot.entrySet()) {
			s+= entry.getKey().toString() + " - tps = "+entry.getValue()+"\n";
		}
		return s;
	}
	
	public boolean estAjouterDansBonOrdre(Message m,long tps)
	{
		for ( HashMap.Entry <Message, Long> entry : this.messageDepot.entrySet()) {
			if(entry.getValue()>tps){return false;}
		}
		this.messageDepot.put(m, tps);
		return true;
	}
	
    /**
     * Evenement correspondant � la consommation d'un message 
     * @param c
     * @param m
     * @param tempsDeTraitement
     * @throws ControlException 
     */
	 public void consommationMessage(_Consommateur c, Message m, int tempsDeTraitement) throws ControlException{
		if(c==null||m==null||tempsDeTraitement<=0){ throw new ControlException(getClass(), "consommationMessage");}
	 }
	 /**
	  * Evenement correspondant au d�pot d'un message dans le tampon  
	  * @param p
	  * @param m
	 * @throws ControlException 
	  */
	 public synchronized void depotMessage(_Producteur p, Message m,long l) throws ControlException{
		 if(p==null||m==null){ throw new ControlException(getClass(), "depotMessage");}
		 s.p();
		 if(!estAjouterDansBonOrdre(m,l))
		 {   s.v();
			 throw new ControlException(getClass(), "depotMessage");}
		 s.v();
	 }
	 
	 /**
	  *  Evenement correspondant � la cr�ation d'un nouveau consommateur 
	  * @param c
	 * @throws ControlException 
	  */
	 public synchronized void newConsommateur(_Consommateur c) throws ControlException{
		 if(c==null)
		 { throw new ControlException(getClass(), "newConsommateur");}
	 }
	 /**
	  *  Evenement correspondant � la cr�ation d'un nouveau producteur 
	  * @param p
	 * @throws ControlException 
	  */
	 public void newProducteur(_Producteur p) throws ControlException
	 {
		 if(p==null)
		 { throw new ControlException(getClass(), "newProducteur");}
	 }
	 /**
	  * Evenement correspondant � la production d'un message         
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
	 public  void retraitMessage(_Consommateur c, Message m, long l) throws ControlException{
		 if(c==null||m==null||l<=0){throw new ControlException(getClass(), "retraitMessage");} 	
		 s.p();
		 if(estRetireDansBonOrdre(m,l)==false)
		 {  
			 //System.out.println(this.toString());
			 s.v();
			 throw new ControlException(getClass(), "consommationMessage-estretirerdanslebonordre");
		 }
		 s.v();
	 }
	           

	
}
