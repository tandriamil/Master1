grammar Expr;



doc: bloc+ ;


bloc: '<' ID '>' predicat[$ID.text] '.' ;


predicat[String subject]: '<' i1=ID '>' objet[$subject, $i1.text] ( ';' '<' i2=ID '>' objet[$subject, $i2.text] )*
;


objet[String subject, String pred]: entite[$subject, $pred]  ( ',' entite[$subject, $pred]  )* ;


entite[String subject, String pred]:
    '<' ID '>'  { System.out.println("<" + $subject + "> <" + $pred + "> <" + $ID.text + ">"); }
    | '"' TEXT '"'  { System.out.println("<" + $subject + "> <" + $pred + "> \"" + $TEXT.text + "\""); }
;



WS  :   (' '|'\t'|'\n'|'\r')+ { skip(); } ;
ID  :   ('a'..'z'|'A'..'Z'|'0'..'9'|'-')+ ;
TEXT  :   ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|' '|'&')+ ;