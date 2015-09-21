// $ANTLR 3.5 Expr.g 2015-09-21 16:14:55

import java.util.HashMap;
import java.lang.Math;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ExprParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "INT", "NEWLINE", "WS", 
		"'('", "')'", "'*'", "'+'", "'-'", "'/'", "'='", "'^'"
	};
	public static final int EOF=-1;
	public static final int T__8=8;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int ID=4;
	public static final int INT=5;
	public static final int NEWLINE=6;
	public static final int WS=7;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public ExprParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public ExprParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return ExprParser.tokenNames; }
	@Override public String getGrammarFileName() { return "Expr.g"; }


	/** Map variable name to Integer object holding value */
	HashMap memory = new HashMap();



	// $ANTLR start "prog"
	// Expr.g:13:1: prog : ( stat )+ ;
	public final void prog() throws RecognitionException {
		try {
			// Expr.g:13:5: ( ( stat )+ )
			// Expr.g:13:9: ( stat )+
			{
			// Expr.g:13:9: ( stat )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= ID && LA1_0 <= NEWLINE)||LA1_0==8) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// Expr.g:13:9: stat
					{
					pushFollow(FOLLOW_stat_in_prog23);
					stat();
					state._fsp--;

					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prog"



	// $ANTLR start "stat"
	// Expr.g:15:1: stat : ( expr NEWLINE | ID '=' expr NEWLINE | NEWLINE );
	public final void stat() throws RecognitionException {
		Token ID2=null;
		int expr1 =0;
		int expr3 =0;

		try {
			// Expr.g:15:5: ( expr NEWLINE | ID '=' expr NEWLINE | NEWLINE )
			int alt2=3;
			switch ( input.LA(1) ) {
			case INT:
			case 8:
				{
				alt2=1;
				}
				break;
			case ID:
				{
				int LA2_2 = input.LA(2);
				if ( (LA2_2==14) ) {
					alt2=2;
				}
				else if ( (LA2_2==NEWLINE||(LA2_2 >= 10 && LA2_2 <= 13)||LA2_2==15) ) {
					alt2=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 2, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case NEWLINE:
				{
				alt2=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}
			switch (alt2) {
				case 1 :
					// Expr.g:15:9: expr NEWLINE
					{
					pushFollow(FOLLOW_expr_in_stat50);
					expr1=expr();
					state._fsp--;

					match(input,NEWLINE,FOLLOW_NEWLINE_in_stat52); 
					System.out.println(expr1);
					}
					break;
				case 2 :
					// Expr.g:16:9: ID '=' expr NEWLINE
					{
					ID2=(Token)match(input,ID,FOLLOW_ID_in_stat64); 
					match(input,14,FOLLOW_14_in_stat66); 
					pushFollow(FOLLOW_expr_in_stat68);
					expr3=expr();
					state._fsp--;

					match(input,NEWLINE,FOLLOW_NEWLINE_in_stat70); 
					memory.put((ID2!=null?ID2.getText():null), new Integer(expr3));
					}
					break;
				case 3 :
					// Expr.g:18:9: NEWLINE
					{
					match(input,NEWLINE,FOLLOW_NEWLINE_in_stat90); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "stat"



	// $ANTLR start "expr"
	// Expr.g:21:1: expr returns [int value] : e= multExpr ( '+' e= multExpr | '-' e= multExpr )* ;
	public final int expr() throws RecognitionException {
		int value = 0;


		int e =0;

		try {
			// Expr.g:22:5: (e= multExpr ( '+' e= multExpr | '-' e= multExpr )* )
			// Expr.g:22:9: e= multExpr ( '+' e= multExpr | '-' e= multExpr )*
			{
			pushFollow(FOLLOW_multExpr_in_expr115);
			e=multExpr();
			state._fsp--;

			value = e;
			// Expr.g:23:9: ( '+' e= multExpr | '-' e= multExpr )*
			loop3:
			while (true) {
				int alt3=3;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==11) ) {
					alt3=1;
				}
				else if ( (LA3_0==12) ) {
					alt3=2;
				}

				switch (alt3) {
				case 1 :
					// Expr.g:23:13: '+' e= multExpr
					{
					match(input,11,FOLLOW_11_in_expr131); 
					pushFollow(FOLLOW_multExpr_in_expr135);
					e=multExpr();
					state._fsp--;

					value += e;
					}
					break;
				case 2 :
					// Expr.g:24:13: '-' e= multExpr
					{
					match(input,12,FOLLOW_12_in_expr151); 
					pushFollow(FOLLOW_multExpr_in_expr155);
					e=multExpr();
					state._fsp--;

					value -= e;
					}
					break;

				default :
					break loop3;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "expr"



	// $ANTLR start "multExpr"
	// Expr.g:28:1: multExpr returns [int value] : e= puissance ( '*' e= puissance | '/' e= puissance )* ;
	public final int multExpr() throws RecognitionException {
		int value = 0;


		int e =0;

		try {
			// Expr.g:29:5: (e= puissance ( '*' e= puissance | '/' e= puissance )* )
			// Expr.g:29:9: e= puissance ( '*' e= puissance | '/' e= puissance )*
			{
			pushFollow(FOLLOW_puissance_in_multExpr193);
			e=puissance();
			state._fsp--;

			value = e;
			// Expr.g:30:9: ( '*' e= puissance | '/' e= puissance )*
			loop4:
			while (true) {
				int alt4=3;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==10) ) {
					alt4=1;
				}
				else if ( (LA4_0==13) ) {
					alt4=2;
				}

				switch (alt4) {
				case 1 :
					// Expr.g:30:13: '*' e= puissance
					{
					match(input,10,FOLLOW_10_in_multExpr209); 
					pushFollow(FOLLOW_puissance_in_multExpr213);
					e=puissance();
					state._fsp--;

					value *= e;
					}
					break;
				case 2 :
					// Expr.g:31:13: '/' e= puissance
					{
					match(input,13,FOLLOW_13_in_multExpr229); 
					pushFollow(FOLLOW_puissance_in_multExpr233);
					e=puissance();
					state._fsp--;

					value = (int)(value / e);
					}
					break;

				default :
					break loop4;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "multExpr"



	// $ANTLR start "puissance"
	// Expr.g:35:1: puissance returns [int value] : e= atom e= puissance_prime ;
	public final int puissance() throws RecognitionException {
		int value = 0;


		int e =0;

		try {
			// Expr.g:36:5: (e= atom e= puissance_prime )
			// Expr.g:36:9: e= atom e= puissance_prime
			{
			pushFollow(FOLLOW_atom_in_puissance272);
			e=atom();
			state._fsp--;

			value = e;
			pushFollow(FOLLOW_puissance_prime_in_puissance278);
			e=puissance_prime();
			state._fsp--;

			value = e;
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "puissance"



	// $ANTLR start "puissance_prime"
	// Expr.g:39:1: puissance_prime returns [int value] : ( '^' e= puissance )? ;
	public final int puissance_prime() throws RecognitionException {
		int value = 0;


		int e =0;

		try {
			// Expr.g:40:5: ( ( '^' e= puissance )? )
			// Expr.g:40:9: ( '^' e= puissance )?
			{
			// Expr.g:40:9: ( '^' e= puissance )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==15) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// Expr.g:40:12: '^' e= puissance
					{
					match(input,15,FOLLOW_15_in_puissance_prime306); 
					pushFollow(FOLLOW_puissance_in_puissance_prime310);
					e=puissance();
					state._fsp--;

					double d = new Double(Math.pow((double)value, (double)e)); value = d.intValue(); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "puissance_prime"



	// $ANTLR start "atom"
	// Expr.g:43:1: atom returns [int value] : ( INT | ID | '(' expr ')' );
	public final int atom() throws RecognitionException {
		int value = 0;


		Token INT4=null;
		Token ID5=null;
		int expr6 =0;

		try {
			// Expr.g:44:5: ( INT | ID | '(' expr ')' )
			int alt6=3;
			switch ( input.LA(1) ) {
			case INT:
				{
				alt6=1;
				}
				break;
			case ID:
				{
				alt6=2;
				}
				break;
			case 8:
				{
				alt6=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}
			switch (alt6) {
				case 1 :
					// Expr.g:44:9: INT
					{
					INT4=(Token)match(input,INT,FOLLOW_INT_in_atom339); 
					value = Integer.parseInt((INT4!=null?INT4.getText():null));
					}
					break;
				case 2 :
					// Expr.g:45:9: ID
					{
					ID5=(Token)match(input,ID,FOLLOW_ID_in_atom351); 

					        Integer v = (Integer)memory.get((ID5!=null?ID5.getText():null));
					        if ( v!=null ) value = v.intValue();
					        else System.err.println("undefined variable "+(ID5!=null?ID5.getText():null));
					        
					}
					break;
				case 3 :
					// Expr.g:51:9: '(' expr ')'
					{
					match(input,8,FOLLOW_8_in_atom371); 
					pushFollow(FOLLOW_expr_in_atom373);
					expr6=expr();
					state._fsp--;

					match(input,9,FOLLOW_9_in_atom375); 
					value = expr6;
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "atom"

	// Delegated rules



	public static final BitSet FOLLOW_stat_in_prog23 = new BitSet(new long[]{0x0000000000000172L});
	public static final BitSet FOLLOW_expr_in_stat50 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NEWLINE_in_stat52 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_stat64 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_stat66 = new BitSet(new long[]{0x0000000000000130L});
	public static final BitSet FOLLOW_expr_in_stat68 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_NEWLINE_in_stat70 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NEWLINE_in_stat90 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_multExpr_in_expr115 = new BitSet(new long[]{0x0000000000001802L});
	public static final BitSet FOLLOW_11_in_expr131 = new BitSet(new long[]{0x0000000000000130L});
	public static final BitSet FOLLOW_multExpr_in_expr135 = new BitSet(new long[]{0x0000000000001802L});
	public static final BitSet FOLLOW_12_in_expr151 = new BitSet(new long[]{0x0000000000000130L});
	public static final BitSet FOLLOW_multExpr_in_expr155 = new BitSet(new long[]{0x0000000000001802L});
	public static final BitSet FOLLOW_puissance_in_multExpr193 = new BitSet(new long[]{0x0000000000002402L});
	public static final BitSet FOLLOW_10_in_multExpr209 = new BitSet(new long[]{0x0000000000000130L});
	public static final BitSet FOLLOW_puissance_in_multExpr213 = new BitSet(new long[]{0x0000000000002402L});
	public static final BitSet FOLLOW_13_in_multExpr229 = new BitSet(new long[]{0x0000000000000130L});
	public static final BitSet FOLLOW_puissance_in_multExpr233 = new BitSet(new long[]{0x0000000000002402L});
	public static final BitSet FOLLOW_atom_in_puissance272 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_puissance_prime_in_puissance278 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_15_in_puissance_prime306 = new BitSet(new long[]{0x0000000000000130L});
	public static final BitSet FOLLOW_puissance_in_puissance_prime310 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_atom339 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_atom351 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_8_in_atom371 = new BitSet(new long[]{0x0000000000000130L});
	public static final BitSet FOLLOW_expr_in_atom373 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_atom375 = new BitSet(new long[]{0x0000000000000002L});
}
