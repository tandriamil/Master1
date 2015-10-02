grammar Expr;



doc:   bloc+ ;

bloc: '<' ID '>' predicat '.' ;

predicat: '<' ID '>' objet   ( ';' '<' ID '>' objet)* ;

objet: entite (',' entite )* ;

entite: '<' ID '>' | '"' TEXT '"' ;



ID  :   ('a'..'z'|'A'..'Z'|'0'..'9'|'-')+ ;
TEXT  :   ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|' ')+ ;
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;