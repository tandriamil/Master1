/**
 * @(#) Etudiant3.java
 */

package fr.istic.m1.aco.tp1;

import java.util.Collection;
import java.util.Iterator;

public class Etudiant3 implements Iterable<Cours3>
{
	private Collection<Cours3> cours;

	public boolean contains(Cours3 c) {
		return cours.contains(c);
	}

	public void addCours(Cours3 c) {
		if (c == null) {
			throw new IllegalArgumentException("Cours non valide.");
		} else {
			cours.add(c);
			c.addEtudiant(this);
		}
	}

	public void removeCours(Cours3 c) {
		if ((c == null) || (this.cours.contains(c))) {
			throw new IllegalArgumentException("Cours non valide ou deja contenu dans la liste.");
		} else {
			cours.remove(c);
			c.removeEtudiant(this);
		}
	}

	public Iterator<Cours3> iterator() {
		return cours.iterator();
	}

}
