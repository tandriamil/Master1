// Fichier grammaire du binôme
// ANDRIAMILANTO Tompoariniaina
// IHSINE Azzeddine



// Name of our grammer
grammar Expr;



// Some options to produce the AST
options {
    output=AST;
    ASTLabelType=CommonTree;
}



// Analyse syntaxique
doc:
	( bloc { System.out.println($bloc.tree.toStringTree()); } )+
;

bloc:
	ID predicat '.'    -> ^(ID predicat)
;

predicat:
	ID objet  ( ';' ID objet    -> ^(ID objet) )*    -> ^(ID objet)
;

objet:
	entite  ( ',' entite     -> ^(entite) )*    -> ^(entite)
;

entite:
    ID    -> ID
    |  TEXT    -> TEXT
;



// Analyse lexicale
WS  :   (' '|'\t'|'\n'|'\r')+ { skip(); } ;
ID  :  '<' ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|' '|'&')+ '>' ;
TEXT  :  '"' ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|' '|'&')* '"' ;