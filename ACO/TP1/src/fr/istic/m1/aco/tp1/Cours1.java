/**
 * @(#) Cours1.java
 */

package fr.istic.m1.aco.tp1;

public class Cours1
{
	private Etudiant1 etudiant;
	
	private boolean maj;
	   
	public Etudiant1 getEtudiant(){return etudiant;}
	
	public void setEtudiant(Etudiant1 e){
		if(!maj){
			if(this.etudiant != null){
				this.etudiant.setCours(null);
			}
			 
			this.etudiant = e;	
		
			if(this.etudiant != null){
				this.etudiant.setCours(this);
			}
			maj=false;
		}	
		
	}
	
}
