/**
 * @(#) Cours2.java
 */

package fr.istic.m1.aco.tp1;

import java.util.Collection;
import java.util.Iterator;

public class Cours2 implements Iterable<Etudiant2>
{
	private Collection<Etudiant2> etudiants;

	public boolean contains(Etudiant2 e) {
		return etudiants.contains(e);
	}

	public void addEtudiant(Etudiant2 e) {
		if (e == null) {
			throw new IllegalArgumentException("Etudiant non valide.");
		} else {
			etudiants.add(e);
			e.setCours(this);
		}
	}

	public void removeEtudiant(Etudiant2 e) {
		if ((e == null) || (this.etudiants.contains(e))) {
			throw new IllegalArgumentException("Etudiant non valide ou deja contenu dans la liste.");
		} else {
			etudiants.remove(e);
			e.setCours(null);
		}
	}

	public Iterator<Etudiant2> iterator() {
		return etudiants.iterator();
	}

}
