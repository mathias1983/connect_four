/*
Aufgabe 1c) 
"Vier Gewinnt" auf einem 4x4 Spielfeld
Implementierung des Alpha-Beta-Algorithmus' und Berechnung des optimalen ersten Zuges für Rot
*/
import java.util.*;

public class AlphaBeta
{
	
	public int alphabeta (Zustand a,int Spieler, int alpha, int beta, int Tiefe)
		
	{
	       int w[]=new int[4];
	       int   m=0;
	       LinkedList nf = a.nachf(Spieler);
	       
	       if(nf.size()==0 || Tiefe<1)
		       return a.wert();
	       
	       if (Spieler == Zustand.R)                       // Rot sei Maximierer
	       {
			for (int i=0; i<nf.size(); i++)
	               {
				w[i]=alphabeta((Zustand)nf.get(i),Zustand.G, alpha, beta, Tiefe-1);
			       if(w[i]>=beta)
				       return beta;
			       alpha=(alpha>w[i])?alpha:w[i];
	                }
			return alpha;
		}
		else                                                 // Gelb sei Minimierer
		{
			for (int i=0; i<nf.size(); i++)
	               {
				w[i]=alphabeta((Zustand)nf.get(i),Zustand.R, alpha, beta, Tiefe-1);
			        if(w[i]<=alpha)
					return alpha;
				beta=(beta<w[i])?beta:w[i];
	                }
			return beta;
		}
		
	}
	
	
       public Zustand ALPHABETA(Zustand a1, int Spieler,int Tiefe)
	       
        {
		LinkedList nf1 = a1.nachf(Spieler);  
		int w[] = new int[4];
		Zustand z = null;
		int n=0;
		int alpha = -50000;
		int beta = 50000;
		
		if(Spieler==Zustand.R)                                    // wenn Rot am Zug ist
		{
		       for (int i=0; i<nf1.size(); i++)
	               {
				((Zustand)nf1.get(i)).print();
			       
			       w[i]=alphabeta((Zustand)nf1.get(i),Zustand.G, alpha, beta,Tiefe-1);
			       System.out.print(w[i]);
			       System.out.println();
			       System.out.println();
			       
				n=w[0];
			        z=(Zustand)nf1.get(0);
				for(int k=1; k<nf1.size(); k++)         // findet das Maximum der Bewertungen aus den Nachfolgezustände des Roten Spielers und gibt den korrespondierenden Zustand zurück
					{
						if(w[k]>n)
						  {
						    n=w[k];
						    z=(Zustand)nf1.get(k);
						  }
					}
	                }
		}
		else                                                             // wenn Gelb am Zug ist
		{
			for (int i=0; i<nf1.size(); i++)
	                {
				((Zustand)nf1.get(i)).print();
				
				w[i]=alphabeta((Zustand)nf1.get(i),Zustand.R, alpha, beta,Tiefe-1);
				System.out.print(w[i]);
				System.out.println();
			        System.out.println();
				
				n=w[0];
				z=(Zustand)nf1.get(0);
				for(int k=1; k<nf1.size(); k++)         // findet das Minimum der Bewertungen aus den Nachfolgezustände des Roten Spielers und gibt den korrespondierenden Zustand zurück
					{
						if(w[k]<n)
						  {
						    n=w[k];
						    z=(Zustand)nf1.get(k);
						  }
					}
	                }
	        }
		
		return z;
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}