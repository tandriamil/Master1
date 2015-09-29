/**
 * @(#) Etudiant2.java
 */

package fr.istic.m1.aco.tp1;

public class Etudiant2
{
	private Cours2 cours;
	
	public Cours2 getCours(){return cours;}
	
	public void setCours(Cours2 c){
		if(this.cours != null){
			this.cours.removeEtudiant(this);
		}
			 
		this.cours = c;	
		
		if(this.cours != null){
			c.addEtudiant(this);
		}
	}
}
