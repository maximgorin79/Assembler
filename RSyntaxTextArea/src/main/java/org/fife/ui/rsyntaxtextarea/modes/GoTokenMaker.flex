/*
 * This library is distributed under a modified BSD license.  See the included
 * LICENSE file for details.
 */
package org.fife.ui.rsyntaxtextarea.modes;

import java.io.*;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


/**
 * Scanner for the Go programming language.
 *
 * This implementation was created using
 * <a href="https://www.jflex.de/">JFlex</a> 1.4.1; however, the generated file
 * was modified for performance.  Memory allocation needs to be almost
 * completely removed to be competitive with the handwritten lexers (subclasses
 * of <code>AbstractTokenMaker</code>), so this class has been modified so that
 * Strings are never allocated (via yytext()), and the scanner never has to
 * worry about refilling its buffer (needlessly copying chars around).
 * We can achieve this because RText always scans exactly 1 line of tokens at a
 * time, and hands the scanner this line as an array of characters (a Segment
 * really).  Since tokens contain pointers to char arrays instead of Strings
 * holding their contents, there is no need for allocating new memory for
 * Strings.<p>
 *
 * The actual algorithm generated for scanning has, of course, not been
 * modified.<p>
 *
 * If you wish to regenerate this file yourself, keep in mind the following:
 * <ul>
 *   <li>The generated <code>GoTokenMaker.java</code> file will contain two
 *       definitions of both <code>zzRefill</code> and <code>yyreset</code>.
 *       You should hand-delete the second of each definition (the ones
 *       generated by the lexer), as these generated methods modify the input
 *       buffer, which we'll never have to do.</li>
 *   <li>You should also change the declaration/definition of zzBuffer to NOT
 *       be initialized.  This is a needless memory allocation for us since we
 *       will be pointing the array somewhere else anyway.</li>
 *   <li>You should NOT call <code>yylex()</code> on the generated scanner
 *       directly; rather, you should use <code>getTokenList</code> as you would
 *       with any other <code>TokenMaker</code> instance.</li>
 * </ul>
 *
 * @author Robert Futrell
 * @version 0.6
 *
 */
%%

%public
%class GoTokenMaker
%extends AbstractJFlexCTokenMaker
%unicode
%type org.fife.ui.rsyntaxtextarea.Token


%{


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public GoTokenMaker() {
		super();
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addToken(int, int, int)
	 */
	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, true);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array The character array.
	 * @param start The starting offset in the array.
	 * @param end The ending offset in the array.
	 * @param tokenType The token's type.
	 * @param startOffset The offset in the document at which this token
	 *                    occurs.
	 */
	@Override
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset) {
		super.addToken(array, start,end, tokenType, startOffset);
		zzStartRead = zzMarkedPos;
	}


	@Override
	public String[] getLineCommentStartAndEnd(int languageIndex) {
		return new String[] { "//", null };
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *                    <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = YYINITIAL;
		switch (initialTokenType) {
			case Token.COMMENT_MULTILINE:
				state = MLC;
				start = text.offset;
				break;
			default:
				state = Token.NULL;
		}

		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new TokenImpl();
		}

	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 */
	private boolean zzRefill() {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
	public final void yyreset(Reader reader) {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtBOL  = true;
		zzAtEOF  = false;
	}


%}

Letter				= [A-Za-z]
LetterOrUnderscore	= ({Letter}|[_])
Digit				= [0-9]
HexDigit			= {Digit}|[A-Fa-f]
OctalDigit			= [0-7]
Exponent			= [eE][+-]?{Digit}+

Trigraph				= ("??="|"??("|"??)"|"??/"|"??'"|"??<"|"??>"|"??!"|"??-")

OctEscape1			= ([\\]{OctalDigit})
OctEscape2			= ([\\]{OctalDigit}{OctalDigit})
OctEscape3			= ([\\][0-3]{OctalDigit}{OctalDigit})
OctEscape				= ({OctEscape1}|{OctEscape2}|{OctEscape3})
HexEscape				= ([\\][xX]{HexDigit}{HexDigit})

AnyChrChr					= ([^\'\n\\])
Escape					= ([\\]([abfnrtv\'\"\?\\0e]))
UnclosedCharLiteral			= ([\']({Escape}|{OctEscape}|{HexEscape}|{Trigraph}|{AnyChrChr}))
CharLiteral				= ({UnclosedCharLiteral}[\'])
ErrorUnclosedCharLiteral		= ([\'][^\'\n]*)
ErrorCharLiteral			= (([\'][\'])|{ErrorUnclosedCharLiteral}[\'])
AnyStrChr					= ([^\"\n\\])
FalseTrigraph				= (("?"(("?")*)[^\=\(\)\/\'\<\>\!\-\\\?\"\n])|("?"[\=\(\)\/\'\<\>\!\-]))
StringLiteral				= ([\"]((((("?")*)({Escape}|{OctEscape}|{HexEscape}|{Trigraph}))|{FalseTrigraph}|{AnyStrChr})*)(("?")*)[\"])
UnclosedStringLiteral		= ([\"]([\\].|[^\\\"])*[^\"]?)
ErrorStringLiteral			= ({UnclosedStringLiteral}[\"])


LineTerminator		= \n
WhiteSpace		= [ \t\f]

MLCBegin			= "/*"
MLCEnd			= "*/"
LineCommentBegin	= "//"

DecimalLit          = ([1-9]{Digit}*)
OctalLit            = ("0"{OctalDigit}*)
IntegerLit		    = ({DecimalLit}|{OctalLit})
HexLit      		= ("0"[xX]{HexDigit}+)
FloatLit    		= ((({Digit}*[\.]{Digit}+)|({Digit}+[\.]{Digit}*)){Exponent}?)
ImaginaryLit        = (({Digit}+|{FloatLit})"i")
ErrorNumberFormat	= (({IntegerLit}|{HexLit}|{FloatLit}){NonSeparator}+)

NonSeparator		= ([^\t\f\r\n\ \(\)\{\}\[\]\;\,\.\=\>\<\!\~\?\:\+\-\*\/\&\|\^\%\"\']|"#")
Identifier		= ({LetterOrUnderscore}({LetterOrUnderscore}|{Digit}|[$])*)
ErrorIdentifier	= ({NonSeparator}+)


URLGenDelim				= ([:\/\?#\[\]@])
URLSubDelim				= ([\!\$&'\(\)\*\+,;=])
URLUnreserved			= ({LetterOrUnderscore}|{Digit}|[\-\.\~])
URLCharacter			= ({URLGenDelim}|{URLSubDelim}|{URLUnreserved}|[%])
URLCharacters			= ({URLCharacter}*)
URLEndCharacter			= ([\/\$]|{Letter}|{Digit})
URL						= (((https?|f(tp|ile))"://"|"www.")({URLCharacters}{URLEndCharacter})?)

%state MLC
%state EOL_COMMENT

%%

<YYINITIAL> {

	/* Keywords */
	"break" |
	"case" |
	"chan" |
	"const" |
	"continue" |
	"default" |
	"defer" |
	"else" |
	"fallthrough" |
	"for" |
	"func" |
	"go" |
	"goto" |
	"if" |
	"import" |
	"interface" |
	"map" |
	"package" |
	"range" |
	"select" |
	"struct" |
	"switch" |
	"type" |
	"var" 					{ addToken(Token.RESERVED_WORD); }

	"return"				{ addToken(Token.RESERVED_WORD_2); }

	/* Data types. */
	"bool" |
	"string" |
	"int" |
	"int8" |
	"int16" |
	"int32" |
	"int64" |
	"uint" |
	"uint8" |
	"uint16" |
	"uint32" |
	"uint64" |
	"uintptr" |
	"byte" |
	"rune" |
	"float32" |
	"float64" |
	"complex64" |
	"complex128"			{ addToken(Token.DATA_TYPE); }

	/* Standard functions */
	"append" |
	"cap" |
	"close" |
	"complex" |
	"copy" |
	"delete" |
	"imag" |
	"len" |
	"make" |
	"new" |
	"panic" |
	"print" |
	"println" |
	"real" |
	"recover"				{ addToken(Token.FUNCTION); }

    /* Strings package */
    "Compare" |
    "Contains" |
    "ContainsAny" |
    "ContainsRune" |
    "Count" |
    "EqualsFold" |
    "Fields" |
    "FieldsFunc" |
    "HasPrefix" |
    "HasSuffix" |
    "Index" |
    "IndexAny" |
    "IndexByte" |
    "IndexFunc" |
    "IndexRune" |
    "Join" |
    "LastIndex" |
    "LastIndexAny" |
    "LastIndexByte" |
    "LastIndexFunc" |
    "Map" |
    "Repeat" |
    "Replace" |
    "ReplaceAll" |
    "Split" |
    "SplitAfter" |
    "SplitAfterN" |
    "SplitN" |
    "Title" |
    "ToLower" |
    "ToLowerSpecial" |
    "ToTitle" |
    "ToTitleSpecial" |
    "ToUpper" |
    "ToUppserSpecial" |
    "Trim" |
    "TrimFunc" |
    "TrimLeft" |
    "TrimLeftFunc" |
    "TrimPrefix" |
    "TrimRight" |
    "TrimRightFunc" |
    "TrimSpace" |
    "TrimSuffix"				{ addToken(Token.FUNCTION); }

    /* Boolean literals */
    ("true"|"false"|"nil")      { addToken(Token.LITERAL_BOOLEAN); }

	{LineTerminator}				{ addNullToken(); return firstToken; }

	{Identifier}					{ addToken(Token.IDENTIFIER); }

	{WhiteSpace}+					{ addToken(Token.WHITESPACE); }

	/* String/Character Literals. */
	{CharLiteral}					{ addToken(Token.LITERAL_CHAR); }
	{UnclosedCharLiteral}			{ addToken(Token.ERROR_CHAR); /*addNullToken(); return firstToken;*/ }
	{ErrorUnclosedCharLiteral}		{ addToken(Token.ERROR_CHAR); addNullToken(); return firstToken; }
	{ErrorCharLiteral}				{ addToken(Token.ERROR_CHAR); }
	{StringLiteral}				{ addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }
	{UnclosedStringLiteral}			{ addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken; }
	{ErrorStringLiteral}			{ addToken(Token.ERROR_STRING_DOUBLE); }

	/* Comment Literals. */
	{MLCBegin}					{ start = zzMarkedPos-2; yybegin(MLC); }
	{LineCommentBegin}			{ start = zzMarkedPos-2; yybegin(EOL_COMMENT); }

	/* Separators. */
	"(" |
	")" |
	"[" |
	"]" |
	"{" |
	"}"							{ addToken(Token.SEPARATOR); }

	/* Operators. */
	{Trigraph} |
	"=" |
	":=" |
	"+" |
	"-" |
	"*" |
	"/" |
	"%" |
	"~" |
	"<" |
	"<-" |
	">" |
	"<<" |
	">>" |
	"==" |
	"+=" |
	"-=" |
	"*=" |
	"/=" |
	"%=" |
	"&=" |
	"|=" |
	"^=" |
	">=" |
	"<=" |
	"!=" |
	">>=" |
	"<<=" |
	"^" |
	"&" |
	"&&" |
	"&^" |
	"&^=" |
	"..." |
	"|" |
	"||" |
	"?" |
	":" |
	"," |
	"!" |
	"++" |
	"--" |
	"." |
	","							{ addToken(Token.OPERATOR); }

	/* Numbers */
	{IntegerLit}	    			{ addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }
	{HexLit}	    				{ addToken(Token.LITERAL_NUMBER_HEXADECIMAL); }
	{FloatLit}  					{ addToken(Token.LITERAL_NUMBER_FLOAT); }
	{ImaginaryLit} 					{ addToken(Token.LITERAL_NUMBER_FLOAT); }
	{ErrorNumberFormat}				{ addToken(Token.ERROR_NUMBER_FORMAT); }

	/* Some lines will end in '\' to wrap an expression. */
	"\\"							{ addToken(Token.IDENTIFIER); }

	{ErrorIdentifier}				{ addToken(Token.ERROR_IDENTIFIER); }

	/* Other punctuation, we'll highlight it as "identifiers." */
	";"							{ addToken(Token.IDENTIFIER); }

	/* Ended with a line not in a string or comment. */
	<<EOF>>						{ addNullToken(); return firstToken; }

	/* Catch any other (unhandled) characters and flag them as bad. */
	.							{ addToken(Token.ERROR_IDENTIFIER); }

}

<MLC> {

	[^hwf\n\*]+					{}
	{URL}						{ int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_MULTILINE); start = zzMarkedPos; }
	[hwf]						{}

	\n							{ addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken; }
	{MLCEnd}						{ yybegin(YYINITIAL); addToken(start,zzStartRead+1, Token.COMMENT_MULTILINE); }
	\*							{}
	<<EOF>>						{ addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken; }

}


<EOL_COMMENT> {
	[^hwf\n]+				{}
	{URL}					{ int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_EOL); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_EOL); start = zzMarkedPos; }
	[hwf]					{}
	\n						{ addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken; }
	<<EOF>>					{ addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken; }
}
