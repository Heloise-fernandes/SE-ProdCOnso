package prodcons.v1;

import junit.framework.*;



public class TestProducteurConsomateurJUnit extends TestCase 
{
	
	public TestProducteurConsomateurJUnit(String name) {
		super(name); // appel du constructeur de TestCase
	}
	
	//Tout nos tests sont là pour vérifier la bonne terminaison du programme

	/*Valeurs contenues dans le fichier Test1.xml
	 * nbCons = 20
	 * nbProd =10 
	 * TpsCons = 10
	 * TpsProd = 10
	 * Buff = 10 
	 * */
	public void testSeTermineOption1()
	{
		int i;
		for( i = 0; i <= 10; i++)
		{
			assertTrue("Fichier Test1 vers : "+1+" finis", TestProdCons.begin("Test1.xml"));
		}
	}
	
	/*Valeurs contenues dans le fichier Test2.xml
	 * nbCons = 10
	 * nbProd =20 
	 * TpsCons = 10
	 * TpsProd = 10
	 * Buff = 10 */
	public void testSeTermineOption2()
	{
		int i;
		
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test2 vers : "+2+" finis", TestProdCons.begin("Test2.xml"));
		}
		
	}

	/*Valeurs contenues dans le fichier Test3.xml
	 * nbCons = 200
	 * nbProd =200 
	 * TpsCons = 10
	 * TpsProd = 10
	 * Buff = 10 */
	public void testSeTermineOption3()
	{
		int i;
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test3 vers : "+3+" finis", TestProdCons.begin("Test3.xml"));
		}
	}
	
	/*Valeurs contenues dans le fichier Test4.xml
	 * nbCons = 20
	 * nbProd =20 
	 * TpsCons = 10
	 * TpsProd = 100
	 * Buff = 10 */
	public void testSeTermineOption4()
	{
		int i;
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test4 vers : "+4+" finis", TestProdCons.begin("Test4.xml"));
		}
	}
	
	/*Valeurs contenues dans le fichier Test5.xml
	 * nbCons = 20
	 * nbProd =20 
	 * TpsCons = 100
	 * TpsProd = 10
	 * Buff = 10 */
	public void testSeTermineOption5()
	{
		int i;		
		for(i = 0 ; i <= 10 ; i++)
		{
			assertTrue("Fichier Test5 vers : "+5+" finis", TestProdCons.begin("Test5.xml"));
		}
	}
}
