grammar Expr;

@header {
import java.util.HashMap;
import java.lang.Math;
}

@members {
/** Map variable name to Integer object holding value */
HashMap memory = new HashMap();
}

prog:   stat+ ;
                
stat:   expr NEWLINE {System.out.println($expr.value);}
    |   ID '=' expr NEWLINE
        {memory.put($ID.text, new Integer($expr.value));}
    |   NEWLINE
    ;

expr returns [int value]
    :   e=multExpr {$value = $e.value;}
        (   '+' e=multExpr {$value += $e.value;}
        |   '-' e=multExpr {$value -= $e.value;}
        )*
    ;

multExpr returns [int value] 
    :   e=atom1 {$value = $e.value;} ( '*' e=atom1 {$value *= $e.value;} |  '/' e=atom1 { 
        if(!($e.value<=0)) $value /= $e.value;else System.out.println("erreur") ;})* 
    ;

atom1 returns [int value] 
    :   e=atom  c=atom2 {$value = (int)Math.pow($e.value, $c.value);} 
    ; 

atom2 returns [int value] 
     :  ('^' e=atom ) c=atom2    {$value = (int) Math.pow($e.value,$c.value);} |  {$value = 1 ;}  
     ; 
  
atom returns [int value]
    :   INT {$value = Integer.parseInt($INT.text);}
    |   ID
        {
        Integer v = (Integer)memory.get($ID.text);
        if ( v!=null ) $value = v.intValue();
        else System.err.println("undefined variable "+$ID.text);
        }
    |   '(' expr ')' {$value = $expr.value;}
    ;

ID  :   ('a'..'z'|'A'..'Z')+ ;
INT :   '0'..'9'+ ;
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;
