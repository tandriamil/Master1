/**
 * @(#) Cours3.java
 */

package fr.istic.m1.aco.tp1;

import java.util.Collection;
import java.util.Iterator;

public class Cours3 implements Iterable<Etudiant3>
{
	private Collection<Etudiant3> etudiants;
	
	public boolean contains(Etudiant3 e) {
		return etudiants.contains(e);
	}
	
	public void addEtudiant(Etudiant3 e) {
		if (e == null) {
			throw new IllegalArgumentException("Etudiant non valide.");
		} else {
			etudiants.add(e);
			e.addCours(this);
		}
	}
	
	public void removeEtudiant(Etudiant3 e) {
		if ((e == null) || (this.etudiants.contains(e))) {
			throw new IllegalArgumentException("Etudiant non valide ou deja contenu dans la liste.");
		} else {
			etudiants.remove(e);
			e.removeCours(this);
		}
	}
	
	public Iterator<Etudiant3> iterator() {
		return etudiants.iterator();
	}
	
}