grammar Expr;



doc:   bloc+ ;


bloc: '<' ID '>' predicat '.'
    { $predicat.subject = $ID.text; }
;


predicat: '<' i2=ID '>' objet  { $objet.subject = $predicat.subject; $objet.pred = i1.text; } ( ';' '<' i1=ID '>' objet  { $objet.subject = $predicat.subject; $objet.pred = i2.text; }  )*
;


objet: entite { $entite.subject = $objet.subject; $entite.pred = $objet.pred; }  (',' entite  { $entite.subject = $objet.subject; $entite.pred = $objet.pred; } )* ;


entite:
    '<' ID '>'  { System.out.println($entite.subject + " " + $entite.pred + " " + $ID.text); }
    | '"' TEXT '"'  { System.out.println($entite.subject + " " + $entite.pred + " " + $TEXT.text); }
;



ID  :   ('a'..'z'|'A'..'Z'|'0'..'9'|'-')+ ;
TEXT  :   ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|' '|'&')+ ;
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;