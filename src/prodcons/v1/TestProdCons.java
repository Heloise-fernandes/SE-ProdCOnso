package prodcons.v1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

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
	
	public Producteur[] listProducteur;
	public Consommateur[] listConsommateur;
	
	/**Constructeur*/
	public TestProdCons(Observateur observateur, ProdCons buffer) throws ControlException {
		super(observateur);
		
		listProducteur = new Producteur[nbProd];
		for(int i =0; i < nbProd; i++)
		{
			listProducteur[i] = new Producteur(observateur, tempsMoyenProduction, deviationNombreMoyenDeProduction, nombreMoyenDeProduction,deviationNombreMoyenDeProduction, buffer);
		}
		listConsommateur = new Consommateur[nbCons];
		for(int i =0; i < nbCons; i++)
		{
			listConsommateur[i] = new Consommateur(observateur, tempsMoyenConsommation,deviationTempsMoyenConsommation, buffer);
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
		
		//On attend la fin de tout les threads lancer pour terminer l'exécution
		for(int i =0; i < nbProd; i++)
		{
			listProducteur[i].join();
		}
		for(int i =0; i < nbCons; i++)
		{
			listConsommateur[i].join();
		}
			
	}
	
	public static void main(String[] args){
		String s;
		if (args.length == 2)
		{
			s = args[1];//fichier passé en argument
		}
		else
		{
			s = "options.xml";//default
		}
		
		begin(s);
	}
	
	/**
	* Retreave the parameters of the application.
	* @param file the final name of the file containing the options.
	*/
	protected static void init(String file) 
	{
		// retreave the parameters of the application
		final class Properties extends java.util.Properties 
		{
			private static final long serialVersionUID = 1L;
			
			public int get(String key){return Integer.parseInt(getProperty(key));}
			
			public Properties(String file) 
			{
				String path = System.getProperty("user.dir" );
				try{
					//InputStream a = ClassLoader.getSystemResourceAsStream(file);
					InputStream a = new FileInputStream(path+File.separatorChar+file);
					loadFromXML(a);
				}catch(Exception e){e.printStackTrace();}
			}
		}
		//optionApp = new Properties("jus/poc/prodcons/options/"+file);
		Properties optionApp = new Properties( "options"+ File.separatorChar+file);
		
		nbProd = optionApp.get("nbProd");
		nbCons = optionApp.get("nbCons");
		//TODO C'est quoi nbBuffer ? La taille du buffer ou autre chose ?
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
	
	/**Fonction qui permet de creer un nouveau testProdcons*/
	public synchronized static boolean begin(String s)
	{
		init(s);
		try {
			new TestProdCons(new Observateur(),new ProdCons(nbBuffer,nbProd)).start();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
