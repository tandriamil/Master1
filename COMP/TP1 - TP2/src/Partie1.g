// Fichier grammaire 1 du binôme
// ANDRIAMILANTO Tompoariniaina
// IHSINE Azzeddine



// Name of our grammer
grammar Partie1;



// Syntaxic analysis
doc: bloc+ ;


bloc: ID  predicat[$ID.text] '.' ;


predicat[String subject]:
	ID objet[$subject, $ID.text] predicat_prime[$subject]
;


predicat_prime[String subject]:
	';' predicat[$subject]
	|  // Empty word
;


objet[String subject, String pred]:
	entite[$subject, $pred]
	| entite[$subject, $pred] ',' objet[$subject, $pred]
;


entite[String subject, String pred]:
    ID  { System.out.println($subject + " " + $pred + " " + $ID.text); }
    |  TEXT  { System.out.println($subject + " " + $pred + " " + $TEXT.text); }
;



// Lexical analysys
WS  :   (' '|'\t'|'\n'|'\r')+ { skip(); } ;
ID  :  '<' ('a'..'z'|'A'..'Z'|'0'..'9'|'-')+ '>' ;
TEXT  :  '"' ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|' '|'&')* '"' ;