package prodcons.v4;

public class Semaphore {
	
	int compteur;
	
	public Semaphore(int taille)
	{
		this.compteur = taille;
	}
	
	public synchronized void p(){
		compteur--;
		if(compteur<0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void v(){
		compteur++;
		if(compteur <=0){
			notify();
		}
	}
}
