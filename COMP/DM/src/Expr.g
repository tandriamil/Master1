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
        |   '/' e=puissance 
            {
            if ($e.value > 0) $value = (int)($value / $e.value);
            else { System.err.println("Can't divide by 0"); System.exit(1); }
            }
        )*
    ; 

puissance returns [int value]
    :   e=atom d=puissance_prime {$value = $e.value + $d.value;}
    ;

puissance_prime returns [int value]
    :   (  '^' e=puissance {$value = (int)Math.pow((double)$value, (double)$e.value);}  )?
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