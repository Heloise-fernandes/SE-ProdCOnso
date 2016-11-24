package prodcons.v1;

import java.util.ArrayList;
import java.util.List;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	public Producteur[] listProducteur;
	public Consommateur[] listConsommateur;
	
	
	public TestProdCons(Observateur observateur) {
		super(observateur);
		this.listConsommateur = new Consommateur[/*Conso*/];
		this.listProducteur = new Producteur[/*Prod*/];
	}

	@Override
	protected void run() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		//new TestProdCons(new Observateur()).start();
	}

}
