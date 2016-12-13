package prodcons.v1;

import junit.framework.*;



public class TestProducteurConsomateurJUnit extends TestCase 
{
	
	public TestProducteurConsomateurJUnit(String name) {
		super(name); // appel du constructeur de TestCase
	}
	
	//Tout nos tests sont là pour vérifier la bonne terminaison du programme

	/**Valeurs contenues dans le fichier Test1.xml
	 * nbCons = 20
	 * nbProd =10 
	 * TpsCons = 10
	 * TpsProd = 10
	 * Buff = 10 
	 * 
	 * Ici on test la terminaison pour un nombre légerement supérieur
	 * de consommateur par rapport au nombre de producteur
	 * */
	public void testSeTermineOption1()
	{
		int i;
		for( i = 0; i <= 10; i++)
		{
			assertTrue("Fichier Test1 vers : "+1+" finis", TestProdCons.begin("Test1.xml"));
		}
	}
	
	/**Valeurs contenues dans le fichier Test2.xml
	 * nbCons = 10
	 * nbProd =20 
	 * TpsCons = 10
	 * TpsProd = 10
	 * Buff = 10 
	 * 
	 * Ici on test la terminaison pour un nombre légerement supérieur
	 * de Producteur par rapport au nombre de consommateur
	 */
	public void testSeTermineOption2()
	{
		int i;
		
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test2 vers : "+2+" finis", TestProdCons.begin("Test2.xml"));
		}
		
	}

	/**Valeurs contenues dans le fichier Test3.xml
	 * nbCons = 200
	 * nbProd =200 
	 * TpsCons = 10
	 * TpsProd = 10
	 * Buff = 10 
	 * 
	 * Ici on test la terminaison pour un nombre légerement supérieur
	 * de consommateur par rapport au nombre de producteur
	 */
	public void testSeTermineOption3()
	{
		int i;
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test3 vers : "+3+" finis", TestProdCons.begin("Test3.xml"));
		}
	}
	
	/**Valeurs contenues dans le fichier Test4.xml
	 * nbCons = 20
	 * nbProd =20 
	 * TpsCons = 10
	 * TpsProd = 100
	 * Buff = 10 
	 * 
	 * Ici on test la terminaison pour un nombre égal
	 * de consommateur et de producteur avec un temps de
	 * production 10 fois plus important que le temps de consommation
	 */
	public void testSeTermineOption4()
	{
		int i;
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test4 vers : "+4+" finis", TestProdCons.begin("Test4.xml"));
		}
	}
	
	/**Valeurs contenues dans le fichier Test5.xml
	 * nbCons = 20
	 * nbProd =20 
	 * TpsCons = 100
	 * TpsProd = 10
	 * Buff = 10 
	 * 
	 * Ici on test la terminaison pour un nombre légerement supérieur
	 * de consommateur par rapport au nombre de producteur avec un temps de 
	 * consommation 10 fois supérieur au temps de production
	 * */
	public void testSeTermineOption5()
	{
		int i;		
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test5 vers : "+5+" finis", TestProdCons.begin("Test5.xml"));
		}
	}
	
	/**Valeurs contenues dans le fichier Test6.xml
	 * nbCons = 50
	 * nbProd =200 
	 * TpsCons = 10
	 * TpsProd = 10
	 * Buff = 10 
	 * 
	 * Ici on test la terminaison pour un nombre très supérieur
	 * de producteur par rapport au nombre de consomateur
	 * C'est un stress test
	 * */
	public void testSeTermineOption6()
	{
		int i;		
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test6 vers : "+6+" finis", TestProdCons.begin("Test6.xml"));
		}
	}
	
	/**Valeurs contenues dans le fichier Test7.xml
	 * nbCons = 200
	 * nbProd =20 
	 * TpsCons = 10
	 * TpsProd = 10
	 * Buff = 10 
	 * 
	 * Ici on test la terminaison pour un nombre très supérieur
	 * de consommateur par rapport au nombre de producteur
	 * C'est un stress test
	 * */
	public void testSeTermineOption7()
	{
		int i;		
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test6 vers : "+7+" finis", TestProdCons.begin("Test7.xml"));
		}
	}
}
