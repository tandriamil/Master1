// Fichier grammaire 2 du binôme
// ANDRIAMILANTO Tompoariniaina
// IHSINE Azzeddine



// Name of our grammer
grammar Partie2;



// Some options to produce the AST
options {
    output=AST;
    ASTLabelType=CommonTree;
}



// Syntaxic analysis
doc:
	( bloc { System.out.println($bloc.tree.toStringTree()); } )+
;

bloc:
	ID predicat '.'  -> ^(ID predicat)
;

predicat:
	ID objet predicat_prime  -> ^(ID objet predicat_prime)
;

predicat_prime:
	';' predicat  -> predicat
	|
;

objet:
	entite    -> entite
	| entite ',' objet  -> entite objet
;

entite:
    ID  -> ID
    |  TEXT  -> TEXT
;



// Lexical analysys
WS  :   (' '|'\t'|'\n'|'\r')+ { skip(); } ;
ID  :  '<' ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|' '|'&')+ '>' ;
TEXT  :  '"' ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|' '|'&')* '"' ;