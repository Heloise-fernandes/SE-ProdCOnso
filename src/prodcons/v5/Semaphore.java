package prodcons.v5;

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
