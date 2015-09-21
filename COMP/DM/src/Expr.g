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
    :   e=puissance {$value = $e.value;}
        (   '*' e=puissance {$value *= $e.value;}
        |   '/' e=puissance {$value = (int)($value / $e.value);}
        )*
    ; 

puissance returns [int value]
    :   e=atom {$value = $e.value;} e=puissance_prime {$value = $e.value;}
    ;

puissance_prime returns [int value]
    :   (  '^' e=puissance {double d = new Double(Math.pow((double)$value, (double)$e.value)); $value = d.intValue(); }  )?
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