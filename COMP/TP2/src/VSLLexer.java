// $ANTLR 3.5 src/VSLLexer.g 2015-10-16 17:10:36

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class VSLLexer extends Lexer {
	public static final int EOF=-1;
	public static final int ASCII=4;
	public static final int ASSIGN_KW=5;
	public static final int COM=6;
	public static final int COMMENT=7;
	public static final int DIGIT=8;
	public static final int DIV=9;
	public static final int DO_KW=10;
	public static final int ELSE_KW=11;
	public static final int FI_KW=12;
	public static final int FUNC_KW=13;
	public static final int IDENT=14;
	public static final int IF_KW=15;
	public static final int INTEGER=16;
	public static final int INT_KW=17;
	public static final int LB=18;
	public static final int LC=19;
	public static final int LETTER=20;
	public static final int LP=21;
	public static final int MINUS=22;
	public static final int MUL=23;
	public static final int OD_KW=24;
	public static final int PLUS=25;
	public static final int PRINT_KW=26;
	public static final int PROTO_KW=27;
	public static final int RB=28;
	public static final int RC=29;
	public static final int READ_KW=30;
	public static final int RETURN_KW=31;
	public static final int RP=32;
	public static final int TEXT=33;
	public static final int THEN_KW=34;
	public static final int VOID_KW=35;
	public static final int WHILE_KW=36;
	public static final int WS=37;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public VSLLexer() {} 
	public VSLLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public VSLLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "src/VSLLexer.g"; }

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:9:9: ( ( ' ' | '\\n' | '\\t' ) )
			// src/VSLLexer.g:9:11: ( ' ' | '\\n' | '\\t' )
			{
			if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)==' ' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			_channel = HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:12:9: ( '//' (~ '\\n' )* )
			// src/VSLLexer.g:12:11: '//' (~ '\\n' )*
			{
			match("//"); 

			// src/VSLLexer.g:12:16: (~ '\\n' )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '\u0000' && LA1_0 <= '\t')||(LA1_0 >= '\u000B' && LA1_0 <= '\uFFFF')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// src/VSLLexer.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop1;
				}
			}

			_channel = HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "LETTER"
	public final void mLETTER() throws RecognitionException {
		try {
			// src/VSLLexer.g:15:17: ( 'a' .. 'z' )
			// src/VSLLexer.g:
			{
			if ( (input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LETTER"

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			// src/VSLLexer.g:16:17: ( '0' .. '9' )
			// src/VSLLexer.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIGIT"

	// $ANTLR start "ASCII"
	public final void mASCII() throws RecognitionException {
		try {
			// src/VSLLexer.g:17:17: (~ ( '\\n' | '\\\"' ) )
			// src/VSLLexer.g:
			{
			if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ASCII"

	// $ANTLR start "LP"
	public final void mLP() throws RecognitionException {
		try {
			int _type = LP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:20:7: ( '(' )
			// src/VSLLexer.g:20:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LP"

	// $ANTLR start "RP"
	public final void mRP() throws RecognitionException {
		try {
			int _type = RP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:21:7: ( ')' )
			// src/VSLLexer.g:21:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RP"

	// $ANTLR start "LB"
	public final void mLB() throws RecognitionException {
		try {
			int _type = LB;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:22:7: ( '[' )
			// src/VSLLexer.g:22:9: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LB"

	// $ANTLR start "RB"
	public final void mRB() throws RecognitionException {
		try {
			int _type = RB;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:23:7: ( ']' )
			// src/VSLLexer.g:23:9: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RB"

	// $ANTLR start "LC"
	public final void mLC() throws RecognitionException {
		try {
			int _type = LC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:24:7: ( '{' )
			// src/VSLLexer.g:24:9: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LC"

	// $ANTLR start "RC"
	public final void mRC() throws RecognitionException {
		try {
			int _type = RC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:25:7: ( '}' )
			// src/VSLLexer.g:25:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RC"

	// $ANTLR start "COM"
	public final void mCOM() throws RecognitionException {
		try {
			int _type = COM;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:26:7: ( ',' )
			// src/VSLLexer.g:26:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COM"

	// $ANTLR start "FUNC_KW"
	public final void mFUNC_KW() throws RecognitionException {
		try {
			int _type = FUNC_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:27:11: ( 'FUNC' )
			// src/VSLLexer.g:27:13: 'FUNC'
			{
			match("FUNC"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FUNC_KW"

	// $ANTLR start "PROTO_KW"
	public final void mPROTO_KW() throws RecognitionException {
		try {
			int _type = PROTO_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:28:11: ( 'PROTO' )
			// src/VSLLexer.g:28:13: 'PROTO'
			{
			match("PROTO"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PROTO_KW"

	// $ANTLR start "INT_KW"
	public final void mINT_KW() throws RecognitionException {
		try {
			int _type = INT_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:29:11: ( 'INT' )
			// src/VSLLexer.g:29:13: 'INT'
			{
			match("INT"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT_KW"

	// $ANTLR start "VOID_KW"
	public final void mVOID_KW() throws RecognitionException {
		try {
			int _type = VOID_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:30:11: ( 'VOID' )
			// src/VSLLexer.g:30:13: 'VOID'
			{
			match("VOID"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "VOID_KW"

	// $ANTLR start "ASSIGN_KW"
	public final void mASSIGN_KW() throws RecognitionException {
		try {
			int _type = ASSIGN_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:31:11: ( ':=' )
			// src/VSLLexer.g:31:13: ':='
			{
			match(":="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ASSIGN_KW"

	// $ANTLR start "RETURN_KW"
	public final void mRETURN_KW() throws RecognitionException {
		try {
			int _type = RETURN_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:32:11: ( 'RETURN' )
			// src/VSLLexer.g:32:13: 'RETURN'
			{
			match("RETURN"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RETURN_KW"

	// $ANTLR start "PRINT_KW"
	public final void mPRINT_KW() throws RecognitionException {
		try {
			int _type = PRINT_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:33:11: ( 'PRINT' )
			// src/VSLLexer.g:33:13: 'PRINT'
			{
			match("PRINT"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PRINT_KW"

	// $ANTLR start "READ_KW"
	public final void mREAD_KW() throws RecognitionException {
		try {
			int _type = READ_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:34:11: ( 'READ' )
			// src/VSLLexer.g:34:13: 'READ'
			{
			match("READ"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "READ_KW"

	// $ANTLR start "IF_KW"
	public final void mIF_KW() throws RecognitionException {
		try {
			int _type = IF_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:35:11: ( 'IF' )
			// src/VSLLexer.g:35:13: 'IF'
			{
			match("IF"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IF_KW"

	// $ANTLR start "THEN_KW"
	public final void mTHEN_KW() throws RecognitionException {
		try {
			int _type = THEN_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:36:11: ( 'THEN' )
			// src/VSLLexer.g:36:13: 'THEN'
			{
			match("THEN"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "THEN_KW"

	// $ANTLR start "ELSE_KW"
	public final void mELSE_KW() throws RecognitionException {
		try {
			int _type = ELSE_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:37:11: ( 'ELSE' )
			// src/VSLLexer.g:37:13: 'ELSE'
			{
			match("ELSE"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ELSE_KW"

	// $ANTLR start "FI_KW"
	public final void mFI_KW() throws RecognitionException {
		try {
			int _type = FI_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:38:11: ( 'FI' )
			// src/VSLLexer.g:38:13: 'FI'
			{
			match("FI"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FI_KW"

	// $ANTLR start "WHILE_KW"
	public final void mWHILE_KW() throws RecognitionException {
		try {
			int _type = WHILE_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:39:11: ( 'WHILE' )
			// src/VSLLexer.g:39:13: 'WHILE'
			{
			match("WHILE"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WHILE_KW"

	// $ANTLR start "DO_KW"
	public final void mDO_KW() throws RecognitionException {
		try {
			int _type = DO_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:40:11: ( 'DO' )
			// src/VSLLexer.g:40:13: 'DO'
			{
			match("DO"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DO_KW"

	// $ANTLR start "OD_KW"
	public final void mOD_KW() throws RecognitionException {
		try {
			int _type = OD_KW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:41:11: ( 'DONE' )
			// src/VSLLexer.g:41:13: 'DONE'
			{
			match("DONE"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OD_KW"

	// $ANTLR start "PLUS"
	public final void mPLUS() throws RecognitionException {
		try {
			int _type = PLUS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:42:11: ( '+' )
			// src/VSLLexer.g:42:13: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PLUS"

	// $ANTLR start "MINUS"
	public final void mMINUS() throws RecognitionException {
		try {
			int _type = MINUS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:43:11: ( '-' )
			// src/VSLLexer.g:43:13: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MINUS"

	// $ANTLR start "MUL"
	public final void mMUL() throws RecognitionException {
		try {
			int _type = MUL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:44:11: ( '*' )
			// src/VSLLexer.g:44:13: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MUL"

	// $ANTLR start "DIV"
	public final void mDIV() throws RecognitionException {
		try {
			int _type = DIV;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:45:11: ( '/' )
			// src/VSLLexer.g:45:13: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIV"

	// $ANTLR start "IDENT"
	public final void mIDENT() throws RecognitionException {
		try {
			int _type = IDENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:49:9: ( LETTER ( LETTER | DIGIT )* )
			// src/VSLLexer.g:49:11: LETTER ( LETTER | DIGIT )*
			{
			mLETTER(); 

			// src/VSLLexer.g:49:18: ( LETTER | DIGIT )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// src/VSLLexer.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop2;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IDENT"

	// $ANTLR start "TEXT"
	public final void mTEXT() throws RecognitionException {
		try {
			int _type = TEXT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:50:9: ( '\"' ( ASCII )* '\"' )
			// src/VSLLexer.g:50:11: '\"' ( ASCII )* '\"'
			{
			match('\"'); 
			// src/VSLLexer.g:50:15: ( ASCII )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '\u0000' && LA3_0 <= '\t')||(LA3_0 >= '\u000B' && LA3_0 <= '!')||(LA3_0 >= '#' && LA3_0 <= '\uFFFF')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// src/VSLLexer.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop3;
				}
			}

			match('\"'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TEXT"

	// $ANTLR start "INTEGER"
	public final void mINTEGER() throws RecognitionException {
		try {
			int _type = INTEGER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/VSLLexer.g:51:9: ( ( DIGIT )+ )
			// src/VSLLexer.g:51:11: ( DIGIT )+
			{
			// src/VSLLexer.g:51:11: ( DIGIT )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// src/VSLLexer.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INTEGER"

	@Override
	public void mTokens() throws RecognitionException {
		// src/VSLLexer.g:1:8: ( WS | COMMENT | LP | RP | LB | RB | LC | RC | COM | FUNC_KW | PROTO_KW | INT_KW | VOID_KW | ASSIGN_KW | RETURN_KW | PRINT_KW | READ_KW | IF_KW | THEN_KW | ELSE_KW | FI_KW | WHILE_KW | DO_KW | OD_KW | PLUS | MINUS | MUL | DIV | IDENT | TEXT | INTEGER )
		int alt5=31;
		switch ( input.LA(1) ) {
		case '\t':
		case '\n':
		case ' ':
			{
			alt5=1;
			}
			break;
		case '/':
			{
			int LA5_2 = input.LA(2);
			if ( (LA5_2=='/') ) {
				alt5=2;
			}

			else {
				alt5=28;
			}

			}
			break;
		case '(':
			{
			alt5=3;
			}
			break;
		case ')':
			{
			alt5=4;
			}
			break;
		case '[':
			{
			alt5=5;
			}
			break;
		case ']':
			{
			alt5=6;
			}
			break;
		case '{':
			{
			alt5=7;
			}
			break;
		case '}':
			{
			alt5=8;
			}
			break;
		case ',':
			{
			alt5=9;
			}
			break;
		case 'F':
			{
			int LA5_10 = input.LA(2);
			if ( (LA5_10=='U') ) {
				alt5=10;
			}
			else if ( (LA5_10=='I') ) {
				alt5=21;
			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 5, 10, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'P':
			{
			int LA5_11 = input.LA(2);
			if ( (LA5_11=='R') ) {
				int LA5_30 = input.LA(3);
				if ( (LA5_30=='O') ) {
					alt5=11;
				}
				else if ( (LA5_30=='I') ) {
					alt5=16;
				}

				else {
					int nvaeMark = input.mark();
					try {
						for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
							input.consume();
						}
						NoViableAltException nvae =
							new NoViableAltException("", 5, 30, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 5, 11, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'I':
			{
			int LA5_12 = input.LA(2);
			if ( (LA5_12=='N') ) {
				alt5=12;
			}
			else if ( (LA5_12=='F') ) {
				alt5=18;
			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 5, 12, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'V':
			{
			alt5=13;
			}
			break;
		case ':':
			{
			alt5=14;
			}
			break;
		case 'R':
			{
			int LA5_15 = input.LA(2);
			if ( (LA5_15=='E') ) {
				int LA5_33 = input.LA(3);
				if ( (LA5_33=='T') ) {
					alt5=15;
				}
				else if ( (LA5_33=='A') ) {
					alt5=17;
				}

				else {
					int nvaeMark = input.mark();
					try {
						for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
							input.consume();
						}
						NoViableAltException nvae =
							new NoViableAltException("", 5, 33, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 5, 15, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case 'T':
			{
			alt5=19;
			}
			break;
		case 'E':
			{
			alt5=20;
			}
			break;
		case 'W':
			{
			alt5=22;
			}
			break;
		case 'D':
			{
			int LA5_19 = input.LA(2);
			if ( (LA5_19=='O') ) {
				int LA5_34 = input.LA(3);
				if ( (LA5_34=='N') ) {
					alt5=24;
				}

				else {
					alt5=23;
				}

			}

			else {
				int nvaeMark = input.mark();
				try {
					input.consume();
					NoViableAltException nvae =
						new NoViableAltException("", 5, 19, input);
					throw nvae;
				} finally {
					input.rewind(nvaeMark);
				}
			}

			}
			break;
		case '+':
			{
			alt5=25;
			}
			break;
		case '-':
			{
			alt5=26;
			}
			break;
		case '*':
			{
			alt5=27;
			}
			break;
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt5=29;
			}
			break;
		case '\"':
			{
			alt5=30;
			}
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			{
			alt5=31;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 5, 0, input);
			throw nvae;
		}
		switch (alt5) {
			case 1 :
				// src/VSLLexer.g:1:10: WS
				{
				mWS(); 

				}
				break;
			case 2 :
				// src/VSLLexer.g:1:13: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 3 :
				// src/VSLLexer.g:1:21: LP
				{
				mLP(); 

				}
				break;
			case 4 :
				// src/VSLLexer.g:1:24: RP
				{
				mRP(); 

				}
				break;
			case 5 :
				// src/VSLLexer.g:1:27: LB
				{
				mLB(); 

				}
				break;
			case 6 :
				// src/VSLLexer.g:1:30: RB
				{
				mRB(); 

				}
				break;
			case 7 :
				// src/VSLLexer.g:1:33: LC
				{
				mLC(); 

				}
				break;
			case 8 :
				// src/VSLLexer.g:1:36: RC
				{
				mRC(); 

				}
				break;
			case 9 :
				// src/VSLLexer.g:1:39: COM
				{
				mCOM(); 

				}
				break;
			case 10 :
				// src/VSLLexer.g:1:43: FUNC_KW
				{
				mFUNC_KW(); 

				}
				break;
			case 11 :
				// src/VSLLexer.g:1:51: PROTO_KW
				{
				mPROTO_KW(); 

				}
				break;
			case 12 :
				// src/VSLLexer.g:1:60: INT_KW
				{
				mINT_KW(); 

				}
				break;
			case 13 :
				// src/VSLLexer.g:1:67: VOID_KW
				{
				mVOID_KW(); 

				}
				break;
			case 14 :
				// src/VSLLexer.g:1:75: ASSIGN_KW
				{
				mASSIGN_KW(); 

				}
				break;
			case 15 :
				// src/VSLLexer.g:1:85: RETURN_KW
				{
				mRETURN_KW(); 

				}
				break;
			case 16 :
				// src/VSLLexer.g:1:95: PRINT_KW
				{
				mPRINT_KW(); 

				}
				break;
			case 17 :
				// src/VSLLexer.g:1:104: READ_KW
				{
				mREAD_KW(); 

				}
				break;
			case 18 :
				// src/VSLLexer.g:1:112: IF_KW
				{
				mIF_KW(); 

				}
				break;
			case 19 :
				// src/VSLLexer.g:1:118: THEN_KW
				{
				mTHEN_KW(); 

				}
				break;
			case 20 :
				// src/VSLLexer.g:1:126: ELSE_KW
				{
				mELSE_KW(); 

				}
				break;
			case 21 :
				// src/VSLLexer.g:1:134: FI_KW
				{
				mFI_KW(); 

				}
				break;
			case 22 :
				// src/VSLLexer.g:1:140: WHILE_KW
				{
				mWHILE_KW(); 

				}
				break;
			case 23 :
				// src/VSLLexer.g:1:149: DO_KW
				{
				mDO_KW(); 

				}
				break;
			case 24 :
				// src/VSLLexer.g:1:155: OD_KW
				{
				mOD_KW(); 

				}
				break;
			case 25 :
				// src/VSLLexer.g:1:161: PLUS
				{
				mPLUS(); 

				}
				break;
			case 26 :
				// src/VSLLexer.g:1:166: MINUS
				{
				mMINUS(); 

				}
				break;
			case 27 :
				// src/VSLLexer.g:1:172: MUL
				{
				mMUL(); 

				}
				break;
			case 28 :
				// src/VSLLexer.g:1:176: DIV
				{
				mDIV(); 

				}
				break;
			case 29 :
				// src/VSLLexer.g:1:180: IDENT
				{
				mIDENT(); 

				}
				break;
			case 30 :
				// src/VSLLexer.g:1:186: TEXT
				{
				mTEXT(); 

				}
				break;
			case 31 :
				// src/VSLLexer.g:1:191: INTEGER
				{
				mINTEGER(); 

				}
				break;

		}
	}



}
