// $ANTLR 3.5 ./src/VSLParser.g 2015-10-16 17:10:37

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class VSLParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ASCII", "ASSIGN_KW", "COM", "COMMENT", 
		"DIGIT", "DIV", "DO_KW", "ELSE_KW", "FI_KW", "FUNC_KW", "IDENT", "IF_KW", 
		"INTEGER", "INT_KW", "LB", "LC", "LETTER", "LP", "MINUS", "MUL", "OD_KW", 
		"PLUS", "PRINT_KW", "PROTO_KW", "RB", "RC", "READ_KW", "RETURN_KW", "RP", 
		"TEXT", "THEN_KW", "VOID_KW", "WHILE_KW", "WS", "ARDECL", "ARELEM", "ARRAY", 
		"BLOCK", "BODY", "DECL", "FCALL", "FCALL_S", "INST", "NEGAT", "PARAM", 
		"PROG"
	};
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
	public static final int ARDECL=38;
	public static final int ARELEM=39;
	public static final int ARRAY=40;
	public static final int BLOCK=41;
	public static final int BODY=42;
	public static final int DECL=43;
	public static final int FCALL=44;
	public static final int FCALL_S=45;
	public static final int INST=46;
	public static final int NEGAT=47;
	public static final int PARAM=48;
	public static final int PROG=49;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public VSLParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public VSLParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return VSLParser.tokenNames; }
	@Override public String getGrammarFileName() { return "./src/VSLParser.g"; }


	public static class s_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "s"
	// ./src/VSLParser.g:15:1: s : program ;
	public final VSLParser.s_return s() throws RecognitionException {
		VSLParser.s_return retval = new VSLParser.s_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope program1 =null;


		try {
			// ./src/VSLParser.g:16:5: ( program )
			// ./src/VSLParser.g:16:7: program
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_program_in_s95);
			program1=program();
			state._fsp--;

			adaptor.addChild(root_0, program1.getTree());

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "s"


	public static class program_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "program"
	// ./src/VSLParser.g:19:1: program : ( unit )+ -> ^( PROG ( unit )+ ) ;
	public final VSLParser.program_return program() throws RecognitionException {
		VSLParser.program_return retval = new VSLParser.program_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope unit2 =null;

		RewriteRuleSubtreeStream stream_unit=new RewriteRuleSubtreeStream(adaptor,"rule unit");

		try {
			// ./src/VSLParser.g:20:5: ( ( unit )+ -> ^( PROG ( unit )+ ) )
			// ./src/VSLParser.g:20:7: ( unit )+
			{
			// ./src/VSLParser.g:20:7: ( unit )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==FUNC_KW||LA1_0==PROTO_KW) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// ./src/VSLParser.g:20:7: unit
					{
					pushFollow(FOLLOW_unit_in_program112);
					unit2=unit();
					state._fsp--;

					stream_unit.add(unit2.getTree());
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			// AST REWRITE
			// elements: unit
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 20:13: -> ^( PROG ( unit )+ )
			{
				// ./src/VSLParser.g:20:16: ^( PROG ( unit )+ )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PROG, "PROG"), root_1);
				if ( !(stream_unit.hasNext()) ) {
					throw new RewriteEarlyExitException();
				}
				while ( stream_unit.hasNext() ) {
					adaptor.addChild(root_1, stream_unit.nextTree());
				}
				stream_unit.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "program"


	public static class unit_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "unit"
	// ./src/VSLParser.g:23:1: unit : ( function | proto );
	public final VSLParser.unit_return unit() throws RecognitionException {
		VSLParser.unit_return retval = new VSLParser.unit_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope function3 =null;
		ParserRuleReturnScope proto4 =null;


		try {
			// ./src/VSLParser.g:24:5: ( function | proto )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==FUNC_KW) ) {
				alt2=1;
			}
			else if ( (LA2_0==PROTO_KW) ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// ./src/VSLParser.g:24:7: function
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_function_in_unit139);
					function3=function();
					state._fsp--;

					adaptor.addChild(root_0, function3.getTree());

					}
					break;
				case 2 :
					// ./src/VSLParser.g:25:7: proto
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_proto_in_unit147);
					proto4=proto();
					state._fsp--;

					adaptor.addChild(root_0, proto4.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "unit"


	public static class function_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "function"
	// ./src/VSLParser.g:28:1: function : FUNC_KW type IDENT LP param_list RP statement -> ^( FUNC_KW type IDENT param_list ^( BODY statement ) ) ;
	public final VSLParser.function_return function() throws RecognitionException {
		VSLParser.function_return retval = new VSLParser.function_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token FUNC_KW5=null;
		Token IDENT7=null;
		Token LP8=null;
		Token RP10=null;
		ParserRuleReturnScope type6 =null;
		ParserRuleReturnScope param_list9 =null;
		ParserRuleReturnScope statement11 =null;

		Object FUNC_KW5_tree=null;
		Object IDENT7_tree=null;
		Object LP8_tree=null;
		Object RP10_tree=null;
		RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
		RewriteRuleTokenStream stream_RP=new RewriteRuleTokenStream(adaptor,"token RP");
		RewriteRuleTokenStream stream_FUNC_KW=new RewriteRuleTokenStream(adaptor,"token FUNC_KW");
		RewriteRuleTokenStream stream_LP=new RewriteRuleTokenStream(adaptor,"token LP");
		RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
		RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
		RewriteRuleSubtreeStream stream_param_list=new RewriteRuleSubtreeStream(adaptor,"rule param_list");

		try {
			// ./src/VSLParser.g:29:5: ( FUNC_KW type IDENT LP param_list RP statement -> ^( FUNC_KW type IDENT param_list ^( BODY statement ) ) )
			// ./src/VSLParser.g:29:7: FUNC_KW type IDENT LP param_list RP statement
			{
			FUNC_KW5=(Token)match(input,FUNC_KW,FOLLOW_FUNC_KW_in_function164);  
			stream_FUNC_KW.add(FUNC_KW5);

			pushFollow(FOLLOW_type_in_function166);
			type6=type();
			state._fsp--;

			stream_type.add(type6.getTree());
			IDENT7=(Token)match(input,IDENT,FOLLOW_IDENT_in_function168);  
			stream_IDENT.add(IDENT7);

			LP8=(Token)match(input,LP,FOLLOW_LP_in_function170);  
			stream_LP.add(LP8);

			pushFollow(FOLLOW_param_list_in_function172);
			param_list9=param_list();
			state._fsp--;

			stream_param_list.add(param_list9.getTree());
			RP10=(Token)match(input,RP,FOLLOW_RP_in_function174);  
			stream_RP.add(RP10);

			pushFollow(FOLLOW_statement_in_function176);
			statement11=statement();
			state._fsp--;

			stream_statement.add(statement11.getTree());
			// AST REWRITE
			// elements: IDENT, statement, param_list, type, FUNC_KW
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 29:53: -> ^( FUNC_KW type IDENT param_list ^( BODY statement ) )
			{
				// ./src/VSLParser.g:29:56: ^( FUNC_KW type IDENT param_list ^( BODY statement ) )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot(stream_FUNC_KW.nextNode(), root_1);
				adaptor.addChild(root_1, stream_type.nextTree());
				adaptor.addChild(root_1, stream_IDENT.nextNode());
				adaptor.addChild(root_1, stream_param_list.nextTree());
				// ./src/VSLParser.g:29:89: ^( BODY statement )
				{
				Object root_2 = (Object)adaptor.nil();
				root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(BODY, "BODY"), root_2);
				adaptor.addChild(root_2, stream_statement.nextTree());
				adaptor.addChild(root_1, root_2);
				}

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "function"


	public static class proto_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "proto"
	// ./src/VSLParser.g:32:1: proto : PROTO_KW ^ type IDENT LP ! param_list RP !;
	public final VSLParser.proto_return proto() throws RecognitionException {
		VSLParser.proto_return retval = new VSLParser.proto_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token PROTO_KW12=null;
		Token IDENT14=null;
		Token LP15=null;
		Token RP17=null;
		ParserRuleReturnScope type13 =null;
		ParserRuleReturnScope param_list16 =null;

		Object PROTO_KW12_tree=null;
		Object IDENT14_tree=null;
		Object LP15_tree=null;
		Object RP17_tree=null;

		try {
			// ./src/VSLParser.g:33:5: ( PROTO_KW ^ type IDENT LP ! param_list RP !)
			// ./src/VSLParser.g:33:7: PROTO_KW ^ type IDENT LP ! param_list RP !
			{
			root_0 = (Object)adaptor.nil();


			PROTO_KW12=(Token)match(input,PROTO_KW,FOLLOW_PROTO_KW_in_proto212); 
			PROTO_KW12_tree = (Object)adaptor.create(PROTO_KW12);
			root_0 = (Object)adaptor.becomeRoot(PROTO_KW12_tree, root_0);

			pushFollow(FOLLOW_type_in_proto215);
			type13=type();
			state._fsp--;

			adaptor.addChild(root_0, type13.getTree());

			IDENT14=(Token)match(input,IDENT,FOLLOW_IDENT_in_proto217); 
			IDENT14_tree = (Object)adaptor.create(IDENT14);
			adaptor.addChild(root_0, IDENT14_tree);

			LP15=(Token)match(input,LP,FOLLOW_LP_in_proto219); 
			pushFollow(FOLLOW_param_list_in_proto222);
			param_list16=param_list();
			state._fsp--;

			adaptor.addChild(root_0, param_list16.getTree());

			RP17=(Token)match(input,RP,FOLLOW_RP_in_proto224); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "proto"


	public static class type_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "type"
	// ./src/VSLParser.g:36:1: type : ( INT_KW ^| VOID_KW ^);
	public final VSLParser.type_return type() throws RecognitionException {
		VSLParser.type_return retval = new VSLParser.type_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token INT_KW18=null;
		Token VOID_KW19=null;

		Object INT_KW18_tree=null;
		Object VOID_KW19_tree=null;

		try {
			// ./src/VSLParser.g:37:5: ( INT_KW ^| VOID_KW ^)
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==INT_KW) ) {
				alt3=1;
			}
			else if ( (LA3_0==VOID_KW) ) {
				alt3=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}

			switch (alt3) {
				case 1 :
					// ./src/VSLParser.g:37:7: INT_KW ^
					{
					root_0 = (Object)adaptor.nil();


					INT_KW18=(Token)match(input,INT_KW,FOLLOW_INT_KW_in_type242); 
					INT_KW18_tree = (Object)adaptor.create(INT_KW18);
					root_0 = (Object)adaptor.becomeRoot(INT_KW18_tree, root_0);

					}
					break;
				case 2 :
					// ./src/VSLParser.g:38:7: VOID_KW ^
					{
					root_0 = (Object)adaptor.nil();


					VOID_KW19=(Token)match(input,VOID_KW,FOLLOW_VOID_KW_in_type252); 
					VOID_KW19_tree = (Object)adaptor.create(VOID_KW19);
					root_0 = (Object)adaptor.becomeRoot(VOID_KW19_tree, root_0);

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "type"


	public static class param_list_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "param_list"
	// ./src/VSLParser.g:41:1: param_list : ( param ( COM param )* -> ^( PARAM ( param )* ) | -> ^( PARAM ) );
	public final VSLParser.param_list_return param_list() throws RecognitionException {
		VSLParser.param_list_return retval = new VSLParser.param_list_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COM21=null;
		ParserRuleReturnScope param20 =null;
		ParserRuleReturnScope param22 =null;

		Object COM21_tree=null;
		RewriteRuleTokenStream stream_COM=new RewriteRuleTokenStream(adaptor,"token COM");
		RewriteRuleSubtreeStream stream_param=new RewriteRuleSubtreeStream(adaptor,"rule param");

		try {
			// ./src/VSLParser.g:42:5: ( param ( COM param )* -> ^( PARAM ( param )* ) | -> ^( PARAM ) )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==IDENT) ) {
				alt5=1;
			}
			else if ( (LA5_0==RP) ) {
				alt5=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// ./src/VSLParser.g:42:7: param ( COM param )*
					{
					pushFollow(FOLLOW_param_in_param_list270);
					param20=param();
					state._fsp--;

					stream_param.add(param20.getTree());
					// ./src/VSLParser.g:42:13: ( COM param )*
					loop4:
					while (true) {
						int alt4=2;
						int LA4_0 = input.LA(1);
						if ( (LA4_0==COM) ) {
							alt4=1;
						}

						switch (alt4) {
						case 1 :
							// ./src/VSLParser.g:42:14: COM param
							{
							COM21=(Token)match(input,COM,FOLLOW_COM_in_param_list273);  
							stream_COM.add(COM21);

							pushFollow(FOLLOW_param_in_param_list275);
							param22=param();
							state._fsp--;

							stream_param.add(param22.getTree());
							}
							break;

						default :
							break loop4;
						}
					}

					// AST REWRITE
					// elements: param
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 42:26: -> ^( PARAM ( param )* )
					{
						// ./src/VSLParser.g:42:29: ^( PARAM ( param )* )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAM, "PARAM"), root_1);
						// ./src/VSLParser.g:42:37: ( param )*
						while ( stream_param.hasNext() ) {
							adaptor.addChild(root_1, stream_param.nextTree());
						}
						stream_param.reset();

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 2 :
					// ./src/VSLParser.g:43:7: 
					{
					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 43:7: -> ^( PARAM )
					{
						// ./src/VSLParser.g:43:10: ^( PARAM )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAM, "PARAM"), root_1);
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "param_list"


	public static class param_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "param"
	// ./src/VSLParser.g:46:1: param : ( IDENT | IDENT LB RB -> ^( ARRAY IDENT ) );
	public final VSLParser.param_return param() throws RecognitionException {
		VSLParser.param_return retval = new VSLParser.param_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token IDENT23=null;
		Token IDENT24=null;
		Token LB25=null;
		Token RB26=null;

		Object IDENT23_tree=null;
		Object IDENT24_tree=null;
		Object LB25_tree=null;
		Object RB26_tree=null;
		RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
		RewriteRuleTokenStream stream_LB=new RewriteRuleTokenStream(adaptor,"token LB");
		RewriteRuleTokenStream stream_RB=new RewriteRuleTokenStream(adaptor,"token RB");

		try {
			// ./src/VSLParser.g:47:5: ( IDENT | IDENT LB RB -> ^( ARRAY IDENT ) )
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==IDENT) ) {
				int LA6_1 = input.LA(2);
				if ( (LA6_1==LB) ) {
					alt6=2;
				}
				else if ( (LA6_1==COM||LA6_1==RP) ) {
					alt6=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// ./src/VSLParser.g:47:7: IDENT
					{
					root_0 = (Object)adaptor.nil();


					IDENT23=(Token)match(input,IDENT,FOLLOW_IDENT_in_param315); 
					IDENT23_tree = (Object)adaptor.create(IDENT23);
					adaptor.addChild(root_0, IDENT23_tree);

					}
					break;
				case 2 :
					// ./src/VSLParser.g:48:7: IDENT LB RB
					{
					IDENT24=(Token)match(input,IDENT,FOLLOW_IDENT_in_param323);  
					stream_IDENT.add(IDENT24);

					LB25=(Token)match(input,LB,FOLLOW_LB_in_param325);  
					stream_LB.add(LB25);

					RB26=(Token)match(input,RB,FOLLOW_RB_in_param327);  
					stream_RB.add(RB26);

					// AST REWRITE
					// elements: IDENT
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 48:19: -> ^( ARRAY IDENT )
					{
						// ./src/VSLParser.g:48:22: ^( ARRAY IDENT )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ARRAY, "ARRAY"), root_1);
						adaptor.addChild(root_1, stream_IDENT.nextNode());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "param"


	public static class statement_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "statement"
	// ./src/VSLParser.g:51:1: statement : ( IDENT ASSIGN_KW expression -> ^( ASSIGN_KW expression IDENT ) | array_elem ASSIGN_KW expression -> ^( ASSIGN_KW expression array_elem ) | RETURN_KW ^ expression | PRINT_KW ^ print_list | READ_KW ^ read_list | IF_KW ^ expression THEN_KW ! statement ( ELSE_KW ! statement )? FI_KW !| WHILE_KW ^ expression DO_KW ! statement OD_KW !| IDENT LP ( argument_list )? RP -> ^( FCALL_S IDENT ( argument_list )? ) | block );
	public final VSLParser.statement_return statement() throws RecognitionException {
		VSLParser.statement_return retval = new VSLParser.statement_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token IDENT27=null;
		Token ASSIGN_KW28=null;
		Token ASSIGN_KW31=null;
		Token RETURN_KW33=null;
		Token PRINT_KW35=null;
		Token READ_KW37=null;
		Token IF_KW39=null;
		Token THEN_KW41=null;
		Token ELSE_KW43=null;
		Token FI_KW45=null;
		Token WHILE_KW46=null;
		Token DO_KW48=null;
		Token OD_KW50=null;
		Token IDENT51=null;
		Token LP52=null;
		Token RP54=null;
		ParserRuleReturnScope expression29 =null;
		ParserRuleReturnScope array_elem30 =null;
		ParserRuleReturnScope expression32 =null;
		ParserRuleReturnScope expression34 =null;
		ParserRuleReturnScope print_list36 =null;
		ParserRuleReturnScope read_list38 =null;
		ParserRuleReturnScope expression40 =null;
		ParserRuleReturnScope statement42 =null;
		ParserRuleReturnScope statement44 =null;
		ParserRuleReturnScope expression47 =null;
		ParserRuleReturnScope statement49 =null;
		ParserRuleReturnScope argument_list53 =null;
		ParserRuleReturnScope block55 =null;

		Object IDENT27_tree=null;
		Object ASSIGN_KW28_tree=null;
		Object ASSIGN_KW31_tree=null;
		Object RETURN_KW33_tree=null;
		Object PRINT_KW35_tree=null;
		Object READ_KW37_tree=null;
		Object IF_KW39_tree=null;
		Object THEN_KW41_tree=null;
		Object ELSE_KW43_tree=null;
		Object FI_KW45_tree=null;
		Object WHILE_KW46_tree=null;
		Object DO_KW48_tree=null;
		Object OD_KW50_tree=null;
		Object IDENT51_tree=null;
		Object LP52_tree=null;
		Object RP54_tree=null;
		RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
		RewriteRuleTokenStream stream_ASSIGN_KW=new RewriteRuleTokenStream(adaptor,"token ASSIGN_KW");
		RewriteRuleTokenStream stream_RP=new RewriteRuleTokenStream(adaptor,"token RP");
		RewriteRuleTokenStream stream_LP=new RewriteRuleTokenStream(adaptor,"token LP");
		RewriteRuleSubtreeStream stream_argument_list=new RewriteRuleSubtreeStream(adaptor,"rule argument_list");
		RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
		RewriteRuleSubtreeStream stream_array_elem=new RewriteRuleSubtreeStream(adaptor,"rule array_elem");

		try {
			// ./src/VSLParser.g:52:5: ( IDENT ASSIGN_KW expression -> ^( ASSIGN_KW expression IDENT ) | array_elem ASSIGN_KW expression -> ^( ASSIGN_KW expression array_elem ) | RETURN_KW ^ expression | PRINT_KW ^ print_list | READ_KW ^ read_list | IF_KW ^ expression THEN_KW ! statement ( ELSE_KW ! statement )? FI_KW !| WHILE_KW ^ expression DO_KW ! statement OD_KW !| IDENT LP ( argument_list )? RP -> ^( FCALL_S IDENT ( argument_list )? ) | block )
			int alt9=9;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				switch ( input.LA(2) ) {
				case ASSIGN_KW:
					{
					alt9=1;
					}
					break;
				case LB:
					{
					alt9=2;
					}
					break;
				case LP:
					{
					alt9=8;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 9, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case RETURN_KW:
				{
				alt9=3;
				}
				break;
			case PRINT_KW:
				{
				alt9=4;
				}
				break;
			case READ_KW:
				{
				alt9=5;
				}
				break;
			case IF_KW:
				{
				alt9=6;
				}
				break;
			case WHILE_KW:
				{
				alt9=7;
				}
				break;
			case LC:
				{
				alt9=9;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}
			switch (alt9) {
				case 1 :
					// ./src/VSLParser.g:52:7: IDENT ASSIGN_KW expression
					{
					IDENT27=(Token)match(input,IDENT,FOLLOW_IDENT_in_statement352);  
					stream_IDENT.add(IDENT27);

					ASSIGN_KW28=(Token)match(input,ASSIGN_KW,FOLLOW_ASSIGN_KW_in_statement354);  
					stream_ASSIGN_KW.add(ASSIGN_KW28);

					pushFollow(FOLLOW_expression_in_statement356);
					expression29=expression();
					state._fsp--;

					stream_expression.add(expression29.getTree());
					// AST REWRITE
					// elements: IDENT, expression, ASSIGN_KW
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 52:34: -> ^( ASSIGN_KW expression IDENT )
					{
						// ./src/VSLParser.g:52:37: ^( ASSIGN_KW expression IDENT )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot(stream_ASSIGN_KW.nextNode(), root_1);
						adaptor.addChild(root_1, stream_expression.nextTree());
						adaptor.addChild(root_1, stream_IDENT.nextNode());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 2 :
					// ./src/VSLParser.g:53:7: array_elem ASSIGN_KW expression
					{
					pushFollow(FOLLOW_array_elem_in_statement374);
					array_elem30=array_elem();
					state._fsp--;

					stream_array_elem.add(array_elem30.getTree());
					ASSIGN_KW31=(Token)match(input,ASSIGN_KW,FOLLOW_ASSIGN_KW_in_statement376);  
					stream_ASSIGN_KW.add(ASSIGN_KW31);

					pushFollow(FOLLOW_expression_in_statement378);
					expression32=expression();
					state._fsp--;

					stream_expression.add(expression32.getTree());
					// AST REWRITE
					// elements: expression, ASSIGN_KW, array_elem
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 53:39: -> ^( ASSIGN_KW expression array_elem )
					{
						// ./src/VSLParser.g:53:42: ^( ASSIGN_KW expression array_elem )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot(stream_ASSIGN_KW.nextNode(), root_1);
						adaptor.addChild(root_1, stream_expression.nextTree());
						adaptor.addChild(root_1, stream_array_elem.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 3 :
					// ./src/VSLParser.g:54:7: RETURN_KW ^ expression
					{
					root_0 = (Object)adaptor.nil();


					RETURN_KW33=(Token)match(input,RETURN_KW,FOLLOW_RETURN_KW_in_statement396); 
					RETURN_KW33_tree = (Object)adaptor.create(RETURN_KW33);
					root_0 = (Object)adaptor.becomeRoot(RETURN_KW33_tree, root_0);

					pushFollow(FOLLOW_expression_in_statement399);
					expression34=expression();
					state._fsp--;

					adaptor.addChild(root_0, expression34.getTree());

					}
					break;
				case 4 :
					// ./src/VSLParser.g:55:7: PRINT_KW ^ print_list
					{
					root_0 = (Object)adaptor.nil();


					PRINT_KW35=(Token)match(input,PRINT_KW,FOLLOW_PRINT_KW_in_statement407); 
					PRINT_KW35_tree = (Object)adaptor.create(PRINT_KW35);
					root_0 = (Object)adaptor.becomeRoot(PRINT_KW35_tree, root_0);

					pushFollow(FOLLOW_print_list_in_statement410);
					print_list36=print_list();
					state._fsp--;

					adaptor.addChild(root_0, print_list36.getTree());

					}
					break;
				case 5 :
					// ./src/VSLParser.g:56:7: READ_KW ^ read_list
					{
					root_0 = (Object)adaptor.nil();


					READ_KW37=(Token)match(input,READ_KW,FOLLOW_READ_KW_in_statement418); 
					READ_KW37_tree = (Object)adaptor.create(READ_KW37);
					root_0 = (Object)adaptor.becomeRoot(READ_KW37_tree, root_0);

					pushFollow(FOLLOW_read_list_in_statement421);
					read_list38=read_list();
					state._fsp--;

					adaptor.addChild(root_0, read_list38.getTree());

					}
					break;
				case 6 :
					// ./src/VSLParser.g:57:7: IF_KW ^ expression THEN_KW ! statement ( ELSE_KW ! statement )? FI_KW !
					{
					root_0 = (Object)adaptor.nil();


					IF_KW39=(Token)match(input,IF_KW,FOLLOW_IF_KW_in_statement429); 
					IF_KW39_tree = (Object)adaptor.create(IF_KW39);
					root_0 = (Object)adaptor.becomeRoot(IF_KW39_tree, root_0);

					pushFollow(FOLLOW_expression_in_statement432);
					expression40=expression();
					state._fsp--;

					adaptor.addChild(root_0, expression40.getTree());

					THEN_KW41=(Token)match(input,THEN_KW,FOLLOW_THEN_KW_in_statement434); 
					pushFollow(FOLLOW_statement_in_statement437);
					statement42=statement();
					state._fsp--;

					adaptor.addChild(root_0, statement42.getTree());

					// ./src/VSLParser.g:57:44: ( ELSE_KW ! statement )?
					int alt7=2;
					int LA7_0 = input.LA(1);
					if ( (LA7_0==ELSE_KW) ) {
						alt7=1;
					}
					switch (alt7) {
						case 1 :
							// ./src/VSLParser.g:57:45: ELSE_KW ! statement
							{
							ELSE_KW43=(Token)match(input,ELSE_KW,FOLLOW_ELSE_KW_in_statement440); 
							pushFollow(FOLLOW_statement_in_statement443);
							statement44=statement();
							state._fsp--;

							adaptor.addChild(root_0, statement44.getTree());

							}
							break;

					}

					FI_KW45=(Token)match(input,FI_KW,FOLLOW_FI_KW_in_statement447); 
					}
					break;
				case 7 :
					// ./src/VSLParser.g:58:7: WHILE_KW ^ expression DO_KW ! statement OD_KW !
					{
					root_0 = (Object)adaptor.nil();


					WHILE_KW46=(Token)match(input,WHILE_KW,FOLLOW_WHILE_KW_in_statement456); 
					WHILE_KW46_tree = (Object)adaptor.create(WHILE_KW46);
					root_0 = (Object)adaptor.becomeRoot(WHILE_KW46_tree, root_0);

					pushFollow(FOLLOW_expression_in_statement459);
					expression47=expression();
					state._fsp--;

					adaptor.addChild(root_0, expression47.getTree());

					DO_KW48=(Token)match(input,DO_KW,FOLLOW_DO_KW_in_statement461); 
					pushFollow(FOLLOW_statement_in_statement464);
					statement49=statement();
					state._fsp--;

					adaptor.addChild(root_0, statement49.getTree());

					OD_KW50=(Token)match(input,OD_KW,FOLLOW_OD_KW_in_statement466); 
					}
					break;
				case 8 :
					// ./src/VSLParser.g:59:7: IDENT LP ( argument_list )? RP
					{
					IDENT51=(Token)match(input,IDENT,FOLLOW_IDENT_in_statement475);  
					stream_IDENT.add(IDENT51);

					LP52=(Token)match(input,LP,FOLLOW_LP_in_statement477);  
					stream_LP.add(LP52);

					// ./src/VSLParser.g:59:16: ( argument_list )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==IDENT||LA8_0==INTEGER||(LA8_0 >= LP && LA8_0 <= MINUS)) ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// ./src/VSLParser.g:59:16: argument_list
							{
							pushFollow(FOLLOW_argument_list_in_statement479);
							argument_list53=argument_list();
							state._fsp--;

							stream_argument_list.add(argument_list53.getTree());
							}
							break;

					}

					RP54=(Token)match(input,RP,FOLLOW_RP_in_statement482);  
					stream_RP.add(RP54);

					// AST REWRITE
					// elements: argument_list, IDENT
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 59:34: -> ^( FCALL_S IDENT ( argument_list )? )
					{
						// ./src/VSLParser.g:59:37: ^( FCALL_S IDENT ( argument_list )? )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FCALL_S, "FCALL_S"), root_1);
						adaptor.addChild(root_1, stream_IDENT.nextNode());
						// ./src/VSLParser.g:59:53: ( argument_list )?
						if ( stream_argument_list.hasNext() ) {
							adaptor.addChild(root_1, stream_argument_list.nextTree());
						}
						stream_argument_list.reset();

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 9 :
					// ./src/VSLParser.g:60:7: block
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_block_in_statement501);
					block55=block();
					state._fsp--;

					adaptor.addChild(root_0, block55.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "statement"


	public static class block_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "block"
	// ./src/VSLParser.g:63:1: block : ( LC declaration inst_list RC -> ^( BLOCK declaration inst_list ) | LC inst_list RC -> ^( BLOCK inst_list ) );
	public final VSLParser.block_return block() throws RecognitionException {
		VSLParser.block_return retval = new VSLParser.block_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token LC56=null;
		Token RC59=null;
		Token LC60=null;
		Token RC62=null;
		ParserRuleReturnScope declaration57 =null;
		ParserRuleReturnScope inst_list58 =null;
		ParserRuleReturnScope inst_list61 =null;

		Object LC56_tree=null;
		Object RC59_tree=null;
		Object LC60_tree=null;
		Object RC62_tree=null;
		RewriteRuleTokenStream stream_LC=new RewriteRuleTokenStream(adaptor,"token LC");
		RewriteRuleTokenStream stream_RC=new RewriteRuleTokenStream(adaptor,"token RC");
		RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
		RewriteRuleSubtreeStream stream_inst_list=new RewriteRuleSubtreeStream(adaptor,"rule inst_list");

		try {
			// ./src/VSLParser.g:64:5: ( LC declaration inst_list RC -> ^( BLOCK declaration inst_list ) | LC inst_list RC -> ^( BLOCK inst_list ) )
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==LC) ) {
				int LA10_1 = input.LA(2);
				if ( (LA10_1==INT_KW) ) {
					alt10=1;
				}
				else if ( ((LA10_1 >= IDENT && LA10_1 <= IF_KW)||LA10_1==LC||LA10_1==PRINT_KW||(LA10_1 >= READ_KW && LA10_1 <= RETURN_KW)||LA10_1==WHILE_KW) ) {
					alt10=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 10, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}

			switch (alt10) {
				case 1 :
					// ./src/VSLParser.g:64:7: LC declaration inst_list RC
					{
					LC56=(Token)match(input,LC,FOLLOW_LC_in_block519);  
					stream_LC.add(LC56);

					pushFollow(FOLLOW_declaration_in_block521);
					declaration57=declaration();
					state._fsp--;

					stream_declaration.add(declaration57.getTree());
					pushFollow(FOLLOW_inst_list_in_block523);
					inst_list58=inst_list();
					state._fsp--;

					stream_inst_list.add(inst_list58.getTree());
					RC59=(Token)match(input,RC,FOLLOW_RC_in_block525);  
					stream_RC.add(RC59);

					// AST REWRITE
					// elements: declaration, inst_list
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 64:35: -> ^( BLOCK declaration inst_list )
					{
						// ./src/VSLParser.g:64:38: ^( BLOCK declaration inst_list )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLOCK, "BLOCK"), root_1);
						adaptor.addChild(root_1, stream_declaration.nextTree());
						adaptor.addChild(root_1, stream_inst_list.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 2 :
					// ./src/VSLParser.g:65:7: LC inst_list RC
					{
					LC60=(Token)match(input,LC,FOLLOW_LC_in_block543);  
					stream_LC.add(LC60);

					pushFollow(FOLLOW_inst_list_in_block545);
					inst_list61=inst_list();
					state._fsp--;

					stream_inst_list.add(inst_list61.getTree());
					RC62=(Token)match(input,RC,FOLLOW_RC_in_block547);  
					stream_RC.add(RC62);

					// AST REWRITE
					// elements: inst_list
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 65:23: -> ^( BLOCK inst_list )
					{
						// ./src/VSLParser.g:65:26: ^( BLOCK inst_list )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLOCK, "BLOCK"), root_1);
						adaptor.addChild(root_1, stream_inst_list.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "block"


	public static class array_elem_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "array_elem"
	// ./src/VSLParser.g:68:1: array_elem : IDENT LB expression RB -> ^( ARELEM IDENT expression ) ;
	public final VSLParser.array_elem_return array_elem() throws RecognitionException {
		VSLParser.array_elem_return retval = new VSLParser.array_elem_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token IDENT63=null;
		Token LB64=null;
		Token RB66=null;
		ParserRuleReturnScope expression65 =null;

		Object IDENT63_tree=null;
		Object LB64_tree=null;
		Object RB66_tree=null;
		RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
		RewriteRuleTokenStream stream_LB=new RewriteRuleTokenStream(adaptor,"token LB");
		RewriteRuleTokenStream stream_RB=new RewriteRuleTokenStream(adaptor,"token RB");
		RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");

		try {
			// ./src/VSLParser.g:69:5: ( IDENT LB expression RB -> ^( ARELEM IDENT expression ) )
			// ./src/VSLParser.g:69:7: IDENT LB expression RB
			{
			IDENT63=(Token)match(input,IDENT,FOLLOW_IDENT_in_array_elem576);  
			stream_IDENT.add(IDENT63);

			LB64=(Token)match(input,LB,FOLLOW_LB_in_array_elem578);  
			stream_LB.add(LB64);

			pushFollow(FOLLOW_expression_in_array_elem580);
			expression65=expression();
			state._fsp--;

			stream_expression.add(expression65.getTree());
			RB66=(Token)match(input,RB,FOLLOW_RB_in_array_elem582);  
			stream_RB.add(RB66);

			// AST REWRITE
			// elements: expression, IDENT
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 69:30: -> ^( ARELEM IDENT expression )
			{
				// ./src/VSLParser.g:69:34: ^( ARELEM IDENT expression )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ARELEM, "ARELEM"), root_1);
				adaptor.addChild(root_1, stream_IDENT.nextNode());
				adaptor.addChild(root_1, stream_expression.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "array_elem"


	public static class inst_list_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "inst_list"
	// ./src/VSLParser.g:72:1: inst_list : ( statement )+ -> ^( INST ( statement )+ ) ;
	public final VSLParser.inst_list_return inst_list() throws RecognitionException {
		VSLParser.inst_list_return retval = new VSLParser.inst_list_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		ParserRuleReturnScope statement67 =null;

		RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");

		try {
			// ./src/VSLParser.g:73:5: ( ( statement )+ -> ^( INST ( statement )+ ) )
			// ./src/VSLParser.g:73:7: ( statement )+
			{
			// ./src/VSLParser.g:73:7: ( statement )+
			int cnt11=0;
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( ((LA11_0 >= IDENT && LA11_0 <= IF_KW)||LA11_0==LC||LA11_0==PRINT_KW||(LA11_0 >= READ_KW && LA11_0 <= RETURN_KW)||LA11_0==WHILE_KW) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// ./src/VSLParser.g:73:7: statement
					{
					pushFollow(FOLLOW_statement_in_inst_list611);
					statement67=statement();
					state._fsp--;

					stream_statement.add(statement67.getTree());
					}
					break;

				default :
					if ( cnt11 >= 1 ) break loop11;
					EarlyExitException eee = new EarlyExitException(11, input);
					throw eee;
				}
				cnt11++;
			}

			// AST REWRITE
			// elements: statement
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 73:18: -> ^( INST ( statement )+ )
			{
				// ./src/VSLParser.g:73:21: ^( INST ( statement )+ )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INST, "INST"), root_1);
				if ( !(stream_statement.hasNext()) ) {
					throw new RewriteEarlyExitException();
				}
				while ( stream_statement.hasNext() ) {
					adaptor.addChild(root_1, stream_statement.nextTree());
				}
				stream_statement.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "inst_list"


	public static class expression_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "expression"
	// ./src/VSLParser.g:76:1: expression : factor ( ( PLUS ^| MINUS ^) factor )* ;
	public final VSLParser.expression_return expression() throws RecognitionException {
		VSLParser.expression_return retval = new VSLParser.expression_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token PLUS69=null;
		Token MINUS70=null;
		ParserRuleReturnScope factor68 =null;
		ParserRuleReturnScope factor71 =null;

		Object PLUS69_tree=null;
		Object MINUS70_tree=null;

		try {
			// ./src/VSLParser.g:77:5: ( factor ( ( PLUS ^| MINUS ^) factor )* )
			// ./src/VSLParser.g:77:7: factor ( ( PLUS ^| MINUS ^) factor )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_factor_in_expression638);
			factor68=factor();
			state._fsp--;

			adaptor.addChild(root_0, factor68.getTree());

			// ./src/VSLParser.g:77:14: ( ( PLUS ^| MINUS ^) factor )*
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( (LA13_0==MINUS||LA13_0==PLUS) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// ./src/VSLParser.g:77:15: ( PLUS ^| MINUS ^) factor
					{
					// ./src/VSLParser.g:77:15: ( PLUS ^| MINUS ^)
					int alt12=2;
					int LA12_0 = input.LA(1);
					if ( (LA12_0==PLUS) ) {
						alt12=1;
					}
					else if ( (LA12_0==MINUS) ) {
						alt12=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 12, 0, input);
						throw nvae;
					}

					switch (alt12) {
						case 1 :
							// ./src/VSLParser.g:77:16: PLUS ^
							{
							PLUS69=(Token)match(input,PLUS,FOLLOW_PLUS_in_expression642); 
							PLUS69_tree = (Object)adaptor.create(PLUS69);
							root_0 = (Object)adaptor.becomeRoot(PLUS69_tree, root_0);

							}
							break;
						case 2 :
							// ./src/VSLParser.g:77:22: MINUS ^
							{
							MINUS70=(Token)match(input,MINUS,FOLLOW_MINUS_in_expression645); 
							MINUS70_tree = (Object)adaptor.create(MINUS70);
							root_0 = (Object)adaptor.becomeRoot(MINUS70_tree, root_0);

							}
							break;

					}

					pushFollow(FOLLOW_factor_in_expression649);
					factor71=factor();
					state._fsp--;

					adaptor.addChild(root_0, factor71.getTree());

					}
					break;

				default :
					break loop13;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expression"


	public static class factor_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "factor"
	// ./src/VSLParser.g:80:1: factor : primary ( ( MUL ^| DIV ^) primary )* ;
	public final VSLParser.factor_return factor() throws RecognitionException {
		VSLParser.factor_return retval = new VSLParser.factor_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token MUL73=null;
		Token DIV74=null;
		ParserRuleReturnScope primary72 =null;
		ParserRuleReturnScope primary75 =null;

		Object MUL73_tree=null;
		Object DIV74_tree=null;

		try {
			// ./src/VSLParser.g:81:5: ( primary ( ( MUL ^| DIV ^) primary )* )
			// ./src/VSLParser.g:81:7: primary ( ( MUL ^| DIV ^) primary )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_primary_in_factor668);
			primary72=primary();
			state._fsp--;

			adaptor.addChild(root_0, primary72.getTree());

			// ./src/VSLParser.g:81:15: ( ( MUL ^| DIV ^) primary )*
			loop15:
			while (true) {
				int alt15=2;
				int LA15_0 = input.LA(1);
				if ( (LA15_0==DIV||LA15_0==MUL) ) {
					alt15=1;
				}

				switch (alt15) {
				case 1 :
					// ./src/VSLParser.g:81:16: ( MUL ^| DIV ^) primary
					{
					// ./src/VSLParser.g:81:16: ( MUL ^| DIV ^)
					int alt14=2;
					int LA14_0 = input.LA(1);
					if ( (LA14_0==MUL) ) {
						alt14=1;
					}
					else if ( (LA14_0==DIV) ) {
						alt14=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 14, 0, input);
						throw nvae;
					}

					switch (alt14) {
						case 1 :
							// ./src/VSLParser.g:81:17: MUL ^
							{
							MUL73=(Token)match(input,MUL,FOLLOW_MUL_in_factor672); 
							MUL73_tree = (Object)adaptor.create(MUL73);
							root_0 = (Object)adaptor.becomeRoot(MUL73_tree, root_0);

							}
							break;
						case 2 :
							// ./src/VSLParser.g:81:22: DIV ^
							{
							DIV74=(Token)match(input,DIV,FOLLOW_DIV_in_factor675); 
							DIV74_tree = (Object)adaptor.create(DIV74);
							root_0 = (Object)adaptor.becomeRoot(DIV74_tree, root_0);

							}
							break;

					}

					pushFollow(FOLLOW_primary_in_factor679);
					primary75=primary();
					state._fsp--;

					adaptor.addChild(root_0, primary75.getTree());

					}
					break;

				default :
					break loop15;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "factor"


	public static class primary_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "primary"
	// ./src/VSLParser.g:84:1: primary : ( INTEGER | IDENT | array_elem | IDENT LP ( argument_list )? RP -> ^( FCALL IDENT ( argument_list )? ) | LP ! expression RP !| MINUS primary -> ^( NEGAT primary ) );
	public final VSLParser.primary_return primary() throws RecognitionException {
		VSLParser.primary_return retval = new VSLParser.primary_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token INTEGER76=null;
		Token IDENT77=null;
		Token IDENT79=null;
		Token LP80=null;
		Token RP82=null;
		Token LP83=null;
		Token RP85=null;
		Token MINUS86=null;
		ParserRuleReturnScope array_elem78 =null;
		ParserRuleReturnScope argument_list81 =null;
		ParserRuleReturnScope expression84 =null;
		ParserRuleReturnScope primary87 =null;

		Object INTEGER76_tree=null;
		Object IDENT77_tree=null;
		Object IDENT79_tree=null;
		Object LP80_tree=null;
		Object RP82_tree=null;
		Object LP83_tree=null;
		Object RP85_tree=null;
		Object MINUS86_tree=null;
		RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
		RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
		RewriteRuleTokenStream stream_RP=new RewriteRuleTokenStream(adaptor,"token RP");
		RewriteRuleTokenStream stream_LP=new RewriteRuleTokenStream(adaptor,"token LP");
		RewriteRuleSubtreeStream stream_argument_list=new RewriteRuleSubtreeStream(adaptor,"rule argument_list");
		RewriteRuleSubtreeStream stream_primary=new RewriteRuleSubtreeStream(adaptor,"rule primary");

		try {
			// ./src/VSLParser.g:85:5: ( INTEGER | IDENT | array_elem | IDENT LP ( argument_list )? RP -> ^( FCALL IDENT ( argument_list )? ) | LP ! expression RP !| MINUS primary -> ^( NEGAT primary ) )
			int alt17=6;
			switch ( input.LA(1) ) {
			case INTEGER:
				{
				alt17=1;
				}
				break;
			case IDENT:
				{
				switch ( input.LA(2) ) {
				case LB:
					{
					alt17=3;
					}
					break;
				case LP:
					{
					alt17=4;
					}
					break;
				case EOF:
				case COM:
				case DIV:
				case DO_KW:
				case ELSE_KW:
				case FI_KW:
				case FUNC_KW:
				case IDENT:
				case IF_KW:
				case LC:
				case MINUS:
				case MUL:
				case OD_KW:
				case PLUS:
				case PRINT_KW:
				case PROTO_KW:
				case RB:
				case RC:
				case READ_KW:
				case RETURN_KW:
				case RP:
				case THEN_KW:
				case WHILE_KW:
					{
					alt17=2;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 17, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case LP:
				{
				alt17=5;
				}
				break;
			case MINUS:
				{
				alt17=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 17, 0, input);
				throw nvae;
			}
			switch (alt17) {
				case 1 :
					// ./src/VSLParser.g:85:7: INTEGER
					{
					root_0 = (Object)adaptor.nil();


					INTEGER76=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_primary698); 
					INTEGER76_tree = (Object)adaptor.create(INTEGER76);
					adaptor.addChild(root_0, INTEGER76_tree);

					}
					break;
				case 2 :
					// ./src/VSLParser.g:86:7: IDENT
					{
					root_0 = (Object)adaptor.nil();


					IDENT77=(Token)match(input,IDENT,FOLLOW_IDENT_in_primary706); 
					IDENT77_tree = (Object)adaptor.create(IDENT77);
					adaptor.addChild(root_0, IDENT77_tree);

					}
					break;
				case 3 :
					// ./src/VSLParser.g:87:7: array_elem
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_array_elem_in_primary714);
					array_elem78=array_elem();
					state._fsp--;

					adaptor.addChild(root_0, array_elem78.getTree());

					}
					break;
				case 4 :
					// ./src/VSLParser.g:88:7: IDENT LP ( argument_list )? RP
					{
					IDENT79=(Token)match(input,IDENT,FOLLOW_IDENT_in_primary722);  
					stream_IDENT.add(IDENT79);

					LP80=(Token)match(input,LP,FOLLOW_LP_in_primary724);  
					stream_LP.add(LP80);

					// ./src/VSLParser.g:88:16: ( argument_list )?
					int alt16=2;
					int LA16_0 = input.LA(1);
					if ( (LA16_0==IDENT||LA16_0==INTEGER||(LA16_0 >= LP && LA16_0 <= MINUS)) ) {
						alt16=1;
					}
					switch (alt16) {
						case 1 :
							// ./src/VSLParser.g:88:16: argument_list
							{
							pushFollow(FOLLOW_argument_list_in_primary726);
							argument_list81=argument_list();
							state._fsp--;

							stream_argument_list.add(argument_list81.getTree());
							}
							break;

					}

					RP82=(Token)match(input,RP,FOLLOW_RP_in_primary729);  
					stream_RP.add(RP82);

					// AST REWRITE
					// elements: argument_list, IDENT
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 88:34: -> ^( FCALL IDENT ( argument_list )? )
					{
						// ./src/VSLParser.g:88:37: ^( FCALL IDENT ( argument_list )? )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FCALL, "FCALL"), root_1);
						adaptor.addChild(root_1, stream_IDENT.nextNode());
						// ./src/VSLParser.g:88:51: ( argument_list )?
						if ( stream_argument_list.hasNext() ) {
							adaptor.addChild(root_1, stream_argument_list.nextTree());
						}
						stream_argument_list.reset();

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 5 :
					// ./src/VSLParser.g:89:7: LP ! expression RP !
					{
					root_0 = (Object)adaptor.nil();


					LP83=(Token)match(input,LP,FOLLOW_LP_in_primary748); 
					pushFollow(FOLLOW_expression_in_primary751);
					expression84=expression();
					state._fsp--;

					adaptor.addChild(root_0, expression84.getTree());

					RP85=(Token)match(input,RP,FOLLOW_RP_in_primary753); 
					}
					break;
				case 6 :
					// ./src/VSLParser.g:90:7: MINUS primary
					{
					MINUS86=(Token)match(input,MINUS,FOLLOW_MINUS_in_primary762);  
					stream_MINUS.add(MINUS86);

					pushFollow(FOLLOW_primary_in_primary764);
					primary87=primary();
					state._fsp--;

					stream_primary.add(primary87.getTree());
					// AST REWRITE
					// elements: primary
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 90:21: -> ^( NEGAT primary )
					{
						// ./src/VSLParser.g:90:24: ^( NEGAT primary )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NEGAT, "NEGAT"), root_1);
						adaptor.addChild(root_1, stream_primary.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "primary"


	public static class argument_list_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "argument_list"
	// ./src/VSLParser.g:93:1: argument_list : expression ( COM ! expression )* ;
	public final VSLParser.argument_list_return argument_list() throws RecognitionException {
		VSLParser.argument_list_return retval = new VSLParser.argument_list_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COM89=null;
		ParserRuleReturnScope expression88 =null;
		ParserRuleReturnScope expression90 =null;

		Object COM89_tree=null;

		try {
			// ./src/VSLParser.g:94:5: ( expression ( COM ! expression )* )
			// ./src/VSLParser.g:94:7: expression ( COM ! expression )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_expression_in_argument_list789);
			expression88=expression();
			state._fsp--;

			adaptor.addChild(root_0, expression88.getTree());

			// ./src/VSLParser.g:94:18: ( COM ! expression )*
			loop18:
			while (true) {
				int alt18=2;
				int LA18_0 = input.LA(1);
				if ( (LA18_0==COM) ) {
					alt18=1;
				}

				switch (alt18) {
				case 1 :
					// ./src/VSLParser.g:94:19: COM ! expression
					{
					COM89=(Token)match(input,COM,FOLLOW_COM_in_argument_list792); 
					pushFollow(FOLLOW_expression_in_argument_list795);
					expression90=expression();
					state._fsp--;

					adaptor.addChild(root_0, expression90.getTree());

					}
					break;

				default :
					break loop18;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "argument_list"


	public static class print_list_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "print_list"
	// ./src/VSLParser.g:97:1: print_list : print_item ( COM ! print_item )* ;
	public final VSLParser.print_list_return print_list() throws RecognitionException {
		VSLParser.print_list_return retval = new VSLParser.print_list_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COM92=null;
		ParserRuleReturnScope print_item91 =null;
		ParserRuleReturnScope print_item93 =null;

		Object COM92_tree=null;

		try {
			// ./src/VSLParser.g:98:5: ( print_item ( COM ! print_item )* )
			// ./src/VSLParser.g:98:7: print_item ( COM ! print_item )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_print_item_in_print_list814);
			print_item91=print_item();
			state._fsp--;

			adaptor.addChild(root_0, print_item91.getTree());

			// ./src/VSLParser.g:98:18: ( COM ! print_item )*
			loop19:
			while (true) {
				int alt19=2;
				int LA19_0 = input.LA(1);
				if ( (LA19_0==COM) ) {
					alt19=1;
				}

				switch (alt19) {
				case 1 :
					// ./src/VSLParser.g:98:19: COM ! print_item
					{
					COM92=(Token)match(input,COM,FOLLOW_COM_in_print_list817); 
					pushFollow(FOLLOW_print_item_in_print_list820);
					print_item93=print_item();
					state._fsp--;

					adaptor.addChild(root_0, print_item93.getTree());

					}
					break;

				default :
					break loop19;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "print_list"


	public static class print_item_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "print_item"
	// ./src/VSLParser.g:101:1: print_item : ( TEXT | expression );
	public final VSLParser.print_item_return print_item() throws RecognitionException {
		VSLParser.print_item_return retval = new VSLParser.print_item_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token TEXT94=null;
		ParserRuleReturnScope expression95 =null;

		Object TEXT94_tree=null;

		try {
			// ./src/VSLParser.g:102:5: ( TEXT | expression )
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( (LA20_0==TEXT) ) {
				alt20=1;
			}
			else if ( (LA20_0==IDENT||LA20_0==INTEGER||(LA20_0 >= LP && LA20_0 <= MINUS)) ) {
				alt20=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 20, 0, input);
				throw nvae;
			}

			switch (alt20) {
				case 1 :
					// ./src/VSLParser.g:102:7: TEXT
					{
					root_0 = (Object)adaptor.nil();


					TEXT94=(Token)match(input,TEXT,FOLLOW_TEXT_in_print_item839); 
					TEXT94_tree = (Object)adaptor.create(TEXT94);
					adaptor.addChild(root_0, TEXT94_tree);

					}
					break;
				case 2 :
					// ./src/VSLParser.g:103:7: expression
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_expression_in_print_item847);
					expression95=expression();
					state._fsp--;

					adaptor.addChild(root_0, expression95.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "print_item"


	public static class read_list_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "read_list"
	// ./src/VSLParser.g:106:1: read_list : read_item ( COM ! read_item )* ;
	public final VSLParser.read_list_return read_list() throws RecognitionException {
		VSLParser.read_list_return retval = new VSLParser.read_list_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COM97=null;
		ParserRuleReturnScope read_item96 =null;
		ParserRuleReturnScope read_item98 =null;

		Object COM97_tree=null;

		try {
			// ./src/VSLParser.g:107:5: ( read_item ( COM ! read_item )* )
			// ./src/VSLParser.g:107:7: read_item ( COM ! read_item )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_read_item_in_read_list864);
			read_item96=read_item();
			state._fsp--;

			adaptor.addChild(root_0, read_item96.getTree());

			// ./src/VSLParser.g:107:17: ( COM ! read_item )*
			loop21:
			while (true) {
				int alt21=2;
				int LA21_0 = input.LA(1);
				if ( (LA21_0==COM) ) {
					alt21=1;
				}

				switch (alt21) {
				case 1 :
					// ./src/VSLParser.g:107:18: COM ! read_item
					{
					COM97=(Token)match(input,COM,FOLLOW_COM_in_read_list867); 
					pushFollow(FOLLOW_read_item_in_read_list870);
					read_item98=read_item();
					state._fsp--;

					adaptor.addChild(root_0, read_item98.getTree());

					}
					break;

				default :
					break loop21;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "read_list"


	public static class read_item_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "read_item"
	// ./src/VSLParser.g:110:1: read_item : ( IDENT | array_elem );
	public final VSLParser.read_item_return read_item() throws RecognitionException {
		VSLParser.read_item_return retval = new VSLParser.read_item_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token IDENT99=null;
		ParserRuleReturnScope array_elem100 =null;

		Object IDENT99_tree=null;

		try {
			// ./src/VSLParser.g:111:5: ( IDENT | array_elem )
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==IDENT) ) {
				int LA22_1 = input.LA(2);
				if ( (LA22_1==LB) ) {
					alt22=2;
				}
				else if ( (LA22_1==EOF||LA22_1==COM||(LA22_1 >= ELSE_KW && LA22_1 <= IF_KW)||LA22_1==LC||LA22_1==OD_KW||(LA22_1 >= PRINT_KW && LA22_1 <= PROTO_KW)||(LA22_1 >= RC && LA22_1 <= RETURN_KW)||LA22_1==WHILE_KW) ) {
					alt22=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 22, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 22, 0, input);
				throw nvae;
			}

			switch (alt22) {
				case 1 :
					// ./src/VSLParser.g:111:7: IDENT
					{
					root_0 = (Object)adaptor.nil();


					IDENT99=(Token)match(input,IDENT,FOLLOW_IDENT_in_read_item889); 
					IDENT99_tree = (Object)adaptor.create(IDENT99);
					adaptor.addChild(root_0, IDENT99_tree);

					}
					break;
				case 2 :
					// ./src/VSLParser.g:112:7: array_elem
					{
					root_0 = (Object)adaptor.nil();


					pushFollow(FOLLOW_array_elem_in_read_item897);
					array_elem100=array_elem();
					state._fsp--;

					adaptor.addChild(root_0, array_elem100.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "read_item"


	public static class declaration_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "declaration"
	// ./src/VSLParser.g:115:1: declaration : ( INT_KW decl_list )+ -> ^( DECL ( decl_list )+ ) ;
	public final VSLParser.declaration_return declaration() throws RecognitionException {
		VSLParser.declaration_return retval = new VSLParser.declaration_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token INT_KW101=null;
		ParserRuleReturnScope decl_list102 =null;

		Object INT_KW101_tree=null;
		RewriteRuleTokenStream stream_INT_KW=new RewriteRuleTokenStream(adaptor,"token INT_KW");
		RewriteRuleSubtreeStream stream_decl_list=new RewriteRuleSubtreeStream(adaptor,"rule decl_list");

		try {
			// ./src/VSLParser.g:116:5: ( ( INT_KW decl_list )+ -> ^( DECL ( decl_list )+ ) )
			// ./src/VSLParser.g:116:7: ( INT_KW decl_list )+
			{
			// ./src/VSLParser.g:116:7: ( INT_KW decl_list )+
			int cnt23=0;
			loop23:
			while (true) {
				int alt23=2;
				int LA23_0 = input.LA(1);
				if ( (LA23_0==INT_KW) ) {
					alt23=1;
				}

				switch (alt23) {
				case 1 :
					// ./src/VSLParser.g:116:8: INT_KW decl_list
					{
					INT_KW101=(Token)match(input,INT_KW,FOLLOW_INT_KW_in_declaration915);  
					stream_INT_KW.add(INT_KW101);

					pushFollow(FOLLOW_decl_list_in_declaration917);
					decl_list102=decl_list();
					state._fsp--;

					stream_decl_list.add(decl_list102.getTree());
					}
					break;

				default :
					if ( cnt23 >= 1 ) break loop23;
					EarlyExitException eee = new EarlyExitException(23, input);
					throw eee;
				}
				cnt23++;
			}

			// AST REWRITE
			// elements: decl_list
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (Object)adaptor.nil();
			// 116:28: -> ^( DECL ( decl_list )+ )
			{
				// ./src/VSLParser.g:116:32: ^( DECL ( decl_list )+ )
				{
				Object root_1 = (Object)adaptor.nil();
				root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DECL, "DECL"), root_1);
				if ( !(stream_decl_list.hasNext()) ) {
					throw new RewriteEarlyExitException();
				}
				while ( stream_decl_list.hasNext() ) {
					adaptor.addChild(root_1, stream_decl_list.nextTree());
				}
				stream_decl_list.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "declaration"


	public static class decl_list_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "decl_list"
	// ./src/VSLParser.g:119:1: decl_list : decl_item ( COM ! decl_item )* ;
	public final VSLParser.decl_list_return decl_list() throws RecognitionException {
		VSLParser.decl_list_return retval = new VSLParser.decl_list_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token COM104=null;
		ParserRuleReturnScope decl_item103 =null;
		ParserRuleReturnScope decl_item105 =null;

		Object COM104_tree=null;

		try {
			// ./src/VSLParser.g:120:5: ( decl_item ( COM ! decl_item )* )
			// ./src/VSLParser.g:120:7: decl_item ( COM ! decl_item )*
			{
			root_0 = (Object)adaptor.nil();


			pushFollow(FOLLOW_decl_item_in_decl_list948);
			decl_item103=decl_item();
			state._fsp--;

			adaptor.addChild(root_0, decl_item103.getTree());

			// ./src/VSLParser.g:120:17: ( COM ! decl_item )*
			loop24:
			while (true) {
				int alt24=2;
				int LA24_0 = input.LA(1);
				if ( (LA24_0==COM) ) {
					alt24=1;
				}

				switch (alt24) {
				case 1 :
					// ./src/VSLParser.g:120:18: COM ! decl_item
					{
					COM104=(Token)match(input,COM,FOLLOW_COM_in_decl_list951); 
					pushFollow(FOLLOW_decl_item_in_decl_list954);
					decl_item105=decl_item();
					state._fsp--;

					adaptor.addChild(root_0, decl_item105.getTree());

					}
					break;

				default :
					break loop24;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "decl_list"


	public static class decl_item_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "decl_item"
	// ./src/VSLParser.g:123:1: decl_item : ( IDENT | IDENT LB INTEGER RB -> ^( ARDECL IDENT INTEGER ) );
	public final VSLParser.decl_item_return decl_item() throws RecognitionException {
		VSLParser.decl_item_return retval = new VSLParser.decl_item_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token IDENT106=null;
		Token IDENT107=null;
		Token LB108=null;
		Token INTEGER109=null;
		Token RB110=null;

		Object IDENT106_tree=null;
		Object IDENT107_tree=null;
		Object LB108_tree=null;
		Object INTEGER109_tree=null;
		Object RB110_tree=null;
		RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
		RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
		RewriteRuleTokenStream stream_LB=new RewriteRuleTokenStream(adaptor,"token LB");
		RewriteRuleTokenStream stream_RB=new RewriteRuleTokenStream(adaptor,"token RB");

		try {
			// ./src/VSLParser.g:124:5: ( IDENT | IDENT LB INTEGER RB -> ^( ARDECL IDENT INTEGER ) )
			int alt25=2;
			int LA25_0 = input.LA(1);
			if ( (LA25_0==IDENT) ) {
				int LA25_1 = input.LA(2);
				if ( (LA25_1==LB) ) {
					alt25=2;
				}
				else if ( (LA25_1==COM||(LA25_1 >= IDENT && LA25_1 <= IF_KW)||LA25_1==INT_KW||LA25_1==LC||LA25_1==PRINT_KW||(LA25_1 >= READ_KW && LA25_1 <= RETURN_KW)||LA25_1==WHILE_KW) ) {
					alt25=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 25, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 25, 0, input);
				throw nvae;
			}

			switch (alt25) {
				case 1 :
					// ./src/VSLParser.g:124:7: IDENT
					{
					root_0 = (Object)adaptor.nil();


					IDENT106=(Token)match(input,IDENT,FOLLOW_IDENT_in_decl_item973); 
					IDENT106_tree = (Object)adaptor.create(IDENT106);
					adaptor.addChild(root_0, IDENT106_tree);

					}
					break;
				case 2 :
					// ./src/VSLParser.g:125:7: IDENT LB INTEGER RB
					{
					IDENT107=(Token)match(input,IDENT,FOLLOW_IDENT_in_decl_item981);  
					stream_IDENT.add(IDENT107);

					LB108=(Token)match(input,LB,FOLLOW_LB_in_decl_item983);  
					stream_LB.add(LB108);

					INTEGER109=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_decl_item985);  
					stream_INTEGER.add(INTEGER109);

					RB110=(Token)match(input,RB,FOLLOW_RB_in_decl_item987);  
					stream_RB.add(RB110);

					// AST REWRITE
					// elements: IDENT, INTEGER
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
					// 125:27: -> ^( ARDECL IDENT INTEGER )
					{
						// ./src/VSLParser.g:125:30: ^( ARDECL IDENT INTEGER )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ARDECL, "ARDECL"), root_1);
						adaptor.addChild(root_1, stream_IDENT.nextNode());
						adaptor.addChild(root_1, stream_INTEGER.nextNode());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "decl_item"

	// Delegated rules



	public static final BitSet FOLLOW_program_in_s95 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unit_in_program112 = new BitSet(new long[]{0x0000000008002002L});
	public static final BitSet FOLLOW_function_in_unit139 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_proto_in_unit147 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FUNC_KW_in_function164 = new BitSet(new long[]{0x0000000800020000L});
	public static final BitSet FOLLOW_type_in_function166 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_IDENT_in_function168 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_LP_in_function170 = new BitSet(new long[]{0x0000000100004000L});
	public static final BitSet FOLLOW_param_list_in_function172 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_RP_in_function174 = new BitSet(new long[]{0x00000010C408C000L});
	public static final BitSet FOLLOW_statement_in_function176 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PROTO_KW_in_proto212 = new BitSet(new long[]{0x0000000800020000L});
	public static final BitSet FOLLOW_type_in_proto215 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_IDENT_in_proto217 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_LP_in_proto219 = new BitSet(new long[]{0x0000000100004000L});
	public static final BitSet FOLLOW_param_list_in_proto222 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_RP_in_proto224 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_KW_in_type242 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VOID_KW_in_type252 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_param_in_param_list270 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_COM_in_param_list273 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_param_in_param_list275 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_IDENT_in_param315 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_param323 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_LB_in_param325 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_RB_in_param327 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_statement352 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ASSIGN_KW_in_statement354 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_expression_in_statement356 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_array_elem_in_statement374 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ASSIGN_KW_in_statement376 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_expression_in_statement378 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_RETURN_KW_in_statement396 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_expression_in_statement399 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PRINT_KW_in_statement407 = new BitSet(new long[]{0x0000000200614000L});
	public static final BitSet FOLLOW_print_list_in_statement410 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_READ_KW_in_statement418 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_read_list_in_statement421 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IF_KW_in_statement429 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_expression_in_statement432 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_THEN_KW_in_statement434 = new BitSet(new long[]{0x00000010C408C000L});
	public static final BitSet FOLLOW_statement_in_statement437 = new BitSet(new long[]{0x0000000000001800L});
	public static final BitSet FOLLOW_ELSE_KW_in_statement440 = new BitSet(new long[]{0x00000010C408C000L});
	public static final BitSet FOLLOW_statement_in_statement443 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_FI_KW_in_statement447 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_WHILE_KW_in_statement456 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_expression_in_statement459 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_DO_KW_in_statement461 = new BitSet(new long[]{0x00000010C408C000L});
	public static final BitSet FOLLOW_statement_in_statement464 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_OD_KW_in_statement466 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_statement475 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_LP_in_statement477 = new BitSet(new long[]{0x0000000100614000L});
	public static final BitSet FOLLOW_argument_list_in_statement479 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_RP_in_statement482 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_block_in_statement501 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LC_in_block519 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_declaration_in_block521 = new BitSet(new long[]{0x00000010C408C000L});
	public static final BitSet FOLLOW_inst_list_in_block523 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_RC_in_block525 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LC_in_block543 = new BitSet(new long[]{0x00000010C408C000L});
	public static final BitSet FOLLOW_inst_list_in_block545 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_RC_in_block547 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_array_elem576 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_LB_in_array_elem578 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_expression_in_array_elem580 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_RB_in_array_elem582 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_statement_in_inst_list611 = new BitSet(new long[]{0x00000010C408C002L});
	public static final BitSet FOLLOW_factor_in_expression638 = new BitSet(new long[]{0x0000000002400002L});
	public static final BitSet FOLLOW_PLUS_in_expression642 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_MINUS_in_expression645 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_factor_in_expression649 = new BitSet(new long[]{0x0000000002400002L});
	public static final BitSet FOLLOW_primary_in_factor668 = new BitSet(new long[]{0x0000000000800202L});
	public static final BitSet FOLLOW_MUL_in_factor672 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_DIV_in_factor675 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_primary_in_factor679 = new BitSet(new long[]{0x0000000000800202L});
	public static final BitSet FOLLOW_INTEGER_in_primary698 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_primary706 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_array_elem_in_primary714 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_primary722 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_LP_in_primary724 = new BitSet(new long[]{0x0000000100614000L});
	public static final BitSet FOLLOW_argument_list_in_primary726 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_RP_in_primary729 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LP_in_primary748 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_expression_in_primary751 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_RP_in_primary753 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_primary762 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_primary_in_primary764 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expression_in_argument_list789 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_COM_in_argument_list792 = new BitSet(new long[]{0x0000000000614000L});
	public static final BitSet FOLLOW_expression_in_argument_list795 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_print_item_in_print_list814 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_COM_in_print_list817 = new BitSet(new long[]{0x0000000200614000L});
	public static final BitSet FOLLOW_print_item_in_print_list820 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_TEXT_in_print_item839 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expression_in_print_item847 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_read_item_in_read_list864 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_COM_in_read_list867 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_read_item_in_read_list870 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_IDENT_in_read_item889 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_array_elem_in_read_item897 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_KW_in_declaration915 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_decl_list_in_declaration917 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_decl_item_in_decl_list948 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_COM_in_decl_list951 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_decl_item_in_decl_list954 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_IDENT_in_decl_item973 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_decl_item981 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_LB_in_decl_item983 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_INTEGER_in_decl_item985 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_RB_in_decl_item987 = new BitSet(new long[]{0x0000000000000002L});
}
