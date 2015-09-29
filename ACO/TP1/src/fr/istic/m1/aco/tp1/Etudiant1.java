/**
 * @(#) Etudiant1.java
 */

package fr.istic.m1.aco.tp1;

public class Etudiant1
{
	private Cours1 cours;
	private boolean maj;
   
	public Cours1 getCours(){return cours;}
	
	public void setCours(Cours1 c){
		if(!maj){
			if(this.cours != null){
				this.cours.setEtudiant(null);
			}
			 
			this.cours = c;	
		
			if(this.cours != null){
				this.cours.setEtudiant(this);
			}
			maj=false;
		}	
		
	}
}
