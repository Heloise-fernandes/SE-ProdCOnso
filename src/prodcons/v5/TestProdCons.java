package prodcons.v5;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	/**
	 * Variables générales à l'application
	 */
	protected static int nbProd;
	protected static int nbCons;
	protected static int nbBuffer;
	protected static int tempsMoyenProduction;
	protected static int deviationTempsMoyenProduction;
	protected static int tempsMoyenConsommation;
	protected static int deviationTempsMoyenConsommation;
	protected static int nombreMoyenDeProduction;
	protected static int deviationNombreMoyenDeProduction;
	protected static int nombreMoyenNbExemplaire;
	protected static int deviationNombreMoyenNbExemplaire;
	
	/**
	 * Liste des producteurs du système
	 */
	public Producteur[] listProducteur;
	/**
	 * Liste des consommateurs du système
	 */
	public Consommateur[] listConsommateur;
	
	
	public TestProdCons(Observateur observateur, ProdCons buffer) {
		super(observateur);
		
		try {
			observateur.init(nbProd, nbCons, nbBuffer);
		} catch (ControlException e1) {
			e1.printStackTrace();
		}
		
		/**
		 * Creation des producteurs
		 */
		listProducteur = new Producteur[nbProd];
		for(int i =0; i < nbProd; i++)
		{
			try {
				listProducteur[i] = new Producteur(observateur, tempsMoyenProduction, 
													deviationNombreMoyenDeProduction, nombreMoyenDeProduction, 
													deviationNombreMoyenDeProduction, buffer);
			} catch (ControlException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Création des consommateurs
		 */
		listConsommateur = new Consommateur[nbCons];
		for(int i =0; i < nbCons; i++)
		{
			try {
				listConsommateur[i] = new Consommateur(observateur, tempsMoyenConsommation, 
														deviationTempsMoyenConsommation, buffer);
			} catch (ControlException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	protected void run() throws Exception {
		for(int i =0; i < nbProd; i++)
		{
			listProducteur[i].start();
		}
		for(int i =0; i < nbCons; i++)
		{
			listConsommateur[i].start();
		}
		
	}
	
	public static void main(String[] args){
		if (args.length == 2)
		{
			init(args[1]);
		}
		else
		{
			init("options.xml");
		}
		
		new TestProdCons(new Observateur(),new ProdCons(nbBuffer,nbProd)).start();
	}
	

	
	/**
	* Retreave the parameters of the application.
	* @param file the final name of the file containing the options.
	*/
	protected static void init(String file) {
		// retreave the parameters of the application
		final class Properties extends java.util.Properties {
			private static final long serialVersionUID = 1L;
			public int get(String key){return Integer.parseInt(getProperty(key));}
			public Properties(String file) {
				String path = System.getProperty("user.dir" );
				try{
					InputStream a = new FileInputStream(path+File.separatorChar+file);
					loadFromXML(a);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		Properties optionApp = new Properties( "options"+ File.separatorChar+file);
		
		nbProd = optionApp.get("nbProd");
		nbCons = optionApp.get("nbCons");
		nbBuffer = optionApp.get("nbBuffer");
		tempsMoyenProduction = optionApp.get("tempsMoyenProduction");
		deviationTempsMoyenProduction = optionApp.get("deviationTempsMoyenProduction");
		tempsMoyenConsommation = optionApp.get("tempsMoyenConsommation");
		deviationTempsMoyenConsommation = optionApp.get("deviationTempsMoyenConsommation");
		nombreMoyenDeProduction = optionApp.get("nombreMoyenDeProduction");
		deviationNombreMoyenDeProduction = optionApp.get("deviationNombreMoyenDeProduction");
		nombreMoyenNbExemplaire = optionApp.get("nombreMoyenNbExemplaire");
		deviationNombreMoyenNbExemplaire = optionApp.get("deviationNombreMoyenNbExemplaire");
	}

}
