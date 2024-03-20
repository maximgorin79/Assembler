/* The following code was generated by JFlex 1.4.1 on 3/20/24, 1:20 AM */

/*
 * 26/02/2024
 *
 * AssemblerI8080TokenMaker.java - An object that can take a chunk of text and
 * return a linked list of tokens representing i8080 assembler.
 * 
 * This library is distributed under a modified BSD license.  See the included
 * LICENSE file for details.
 */
package org.fife.ui.rsyntaxtextarea.modes;

import java.io.*;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


/**
 * This class takes plain text and returns tokens representing i8080
 * assembler.<p>
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
 *   <li>The generated <code>AssemblerI8080TokenMaker.java</code> file will contain two
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
 * @author Robert Futrell & Maxim Gorin
 * @version 0.2
 *
 */

public class AssemblerI8080TokenMaker extends AbstractJFlexTokenMaker {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\21\1\20\1\0\1\21\23\0\1\21\1\25\1\15\1\3"+
    "\1\3\1\24\1\25\1\16\2\0\1\23\1\23\1\14\1\23\1\50"+
    "\1\23\1\6\1\11\6\12\1\51\1\2\1\22\1\17\1\25\1\25"+
    "\1\25\1\52\1\0\1\4\1\10\1\30\1\33\1\34\1\35\1\13"+
    "\1\5\1\26\1\53\1\1\1\31\1\45\1\27\1\46\1\42\1\47"+
    "\1\44\1\36\1\41\1\32\1\37\1\40\1\7\1\1\1\43\1\14"+
    "\1\0\1\14\1\25\1\1\1\0\1\4\1\10\1\30\1\33\1\34"+
    "\1\35\1\13\1\5\1\26\1\53\1\1\1\31\1\45\1\27\1\46"+
    "\1\42\1\47\1\44\1\36\1\41\1\32\1\37\1\40\1\7\1\1"+
    "\1\43\1\0\1\25\1\0\1\25\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\2\1\1\2\1\1\2\3\1\2\1\1\1\3"+
    "\2\2\1\4\1\5\1\6\1\7\1\10\1\11\2\1"+
    "\4\3\4\1\1\3\3\1\1\12\1\1\1\13\2\2"+
    "\5\1\1\2\3\1\1\2\1\14\1\15\1\16\4\1"+
    "\4\16\4\1\1\12\1\1\1\12\1\1\1\12\14\1"+
    "\1\16\3\1\11\0\1\16\1\2\5\1\1\16\2\13"+
    "\1\2\5\1\2\0\2\12\12\0\11\1\2\0\2\13"+
    "\5\0\5\1\1\13\2\1\12\0\5\1\5\0\1\13"+
    "\7\0";

  private static int [] zzUnpackAction() {
    int [] result = new int[177];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\54\0\130\0\204\0\260\0\334\0\u0108\0\u0134"+
    "\0\u0160\0\u018c\0\u01b8\0\u01e4\0\u0210\0\u023c\0\u0268\0\u0294"+
    "\0\130\0\u02c0\0\130\0\u02ec\0\u0318\0\u0344\0\u0370\0\u039c"+
    "\0\u03c8\0\u01b8\0\u03f4\0\u0420\0\u044c\0\u0478\0\u04a4\0\u04d0"+
    "\0\u04fc\0\u0528\0\u0554\0\u0554\0\204\0\334\0\u0580\0\u05ac"+
    "\0\u05d8\0\u0604\0\u0630\0\u065c\0\u0688\0\u06b4\0\u06e0\0\u01b8"+
    "\0\130\0\130\0\u070c\0\u0738\0\u0764\0\u0790\0\u07bc\0\u01b8"+
    "\0\u07e8\0\204\0\u0814\0\u0840\0\u086c\0\u0898\0\u08c4\0\u01b8"+
    "\0\u08f0\0\u091c\0\u0948\0\204\0\u0974\0\u09a0\0\u09cc\0\u09f8"+
    "\0\u0a24\0\u0a50\0\u0a7c\0\u0aa8\0\u0ad4\0\u0b00\0\u0b2c\0\u0b58"+
    "\0\u0b84\0\u0bb0\0\u0bdc\0\u0c08\0\u0c34\0\u0c60\0\u0c8c\0\u0cb8"+
    "\0\u0ce4\0\u0d10\0\u0d3c\0\u0d68\0\u0d94\0\u0764\0\u0630\0\u0dc0"+
    "\0\u0dec\0\u0e18\0\u0e44\0\u0e70\0\u0e9c\0\u0ec8\0\204\0\u0ef4"+
    "\0\u0f20\0\u0f4c\0\u0f78\0\u0fa4\0\u0fd0\0\u0ffc\0\u1028\0\130"+
    "\0\u1054\0\u1080\0\u10ac\0\u10d8\0\u1104\0\u1130\0\u115c\0\u1188"+
    "\0\u11b4\0\u11e0\0\u120c\0\u1238\0\u1264\0\u1290\0\u091c\0\u12bc"+
    "\0\u12e8\0\u1314\0\u1340\0\u136c\0\u1398\0\u13c4\0\u13f0\0\130"+
    "\0\u141c\0\u1448\0\u1474\0\u14a0\0\u14cc\0\u14f8\0\u1524\0\u1550"+
    "\0\u157c\0\u15a8\0\u15d4\0\u1600\0\u162c\0\u1658\0\u1684\0\u16b0"+
    "\0\u1054\0\u16dc\0\u1708\0\u1734\0\u1760\0\u178c\0\u17b8\0\u17e4"+
    "\0\u1810\0\u183c\0\u1868\0\u1894\0\u18c0\0\u18ec\0\u1918\0\u1944"+
    "\0\u1970\0\u199c\0\u19c8\0\u19f4\0\u1a20\0\u1a4c\0\u1a78\0\u1aa4"+
    "\0\u1ad0";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[177];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\4\1\3\1\16\1\17\1\20"+
    "\1\21\1\22\1\3\3\23\1\24\1\25\1\26\1\27"+
    "\1\4\1\30\1\31\1\32\1\33\3\4\1\34\1\4"+
    "\1\35\1\36\1\37\1\4\1\40\1\5\1\3\1\41"+
    "\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\4\1\3\1\16\1\17\1\20"+
    "\1\21\1\22\1\3\1\23\1\42\1\23\1\24\1\25"+
    "\1\26\1\27\1\4\1\30\1\31\1\32\1\33\3\4"+
    "\1\34\1\4\1\35\1\36\1\37\1\4\1\40\1\5"+
    "\1\3\1\41\54\0\1\43\2\4\1\43\10\4\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\22\4\1\0\1\4"+
    "\1\43\1\4\1\43\1\4\1\5\1\43\1\32\1\45"+
    "\1\5\1\4\1\32\2\5\1\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\2\4\1\32\2\4\3\32\12\4"+
    "\1\0\1\5\1\43\1\4\2\0\1\46\1\0\1\46"+
    "\1\0\1\46\1\0\3\46\15\0\1\46\2\0\3\46"+
    "\13\0\1\46\2\0\1\43\1\4\1\32\1\43\1\32"+
    "\1\45\1\32\1\4\3\32\1\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\1\4\1\47\1\50\2\4\1\51"+
    "\2\32\12\4\1\0\1\32\1\43\1\4\1\43\2\4"+
    "\1\43\10\4\1\0\3\43\2\0\1\44\2\0\1\43"+
    "\3\4\1\52\16\4\1\0\1\4\1\43\1\4\1\43"+
    "\1\4\1\5\1\43\1\32\1\45\1\14\1\53\1\54"+
    "\1\14\1\15\1\45\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\2\4\1\32\2\4\3\32\12\4\1\0\1\5"+
    "\1\43\1\4\1\43\2\4\1\43\10\4\1\0\3\43"+
    "\2\0\1\44\2\0\1\43\2\4\1\55\10\4\1\56"+
    "\2\4\1\57\3\4\1\0\1\4\1\43\1\4\1\43"+
    "\1\4\1\32\1\43\1\32\1\45\1\32\1\4\3\32"+
    "\1\4\1\0\3\43\2\0\1\44\2\0\1\43\2\4"+
    "\1\32\2\4\3\32\12\4\1\0\1\32\1\43\1\4"+
    "\1\43\1\4\1\5\1\43\1\32\1\45\1\14\1\4"+
    "\1\60\1\14\1\15\1\45\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\2\4\1\32\2\4\3\32\12\4\1\0"+
    "\1\5\1\43\1\4\1\43\1\4\1\5\1\43\1\32"+
    "\1\45\1\15\1\4\1\32\2\15\1\45\1\0\3\43"+
    "\2\0\1\44\2\0\1\43\2\4\1\32\2\4\3\32"+
    "\12\4\1\0\1\5\1\43\1\4\15\16\1\61\36\16"+
    "\16\17\1\62\35\17\20\20\1\0\33\20\21\0\1\22"+
    "\32\0\1\43\2\4\1\43\10\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\1\4\1\63\15\4\1\64\2\4"+
    "\1\0\1\4\1\43\1\4\1\43\2\4\1\43\10\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\20\4\1\65"+
    "\1\4\1\0\1\4\1\43\1\4\1\43\1\4\1\32"+
    "\1\43\1\66\1\45\1\32\1\4\3\32\1\4\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\1\4\1\67\1\70"+
    "\2\4\3\32\4\4\1\71\1\72\1\4\1\73\2\4"+
    "\1\0\1\32\1\43\1\4\1\43\2\4\1\43\1\4"+
    "\1\74\1\4\1\75\4\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\5\4\1\76\14\4\1\0\1\4\1\43"+
    "\1\4\1\43\1\4\1\32\1\43\1\77\1\45\1\32"+
    "\1\4\1\100\2\32\1\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\1\72\1\4\1\101\2\4\1\102\1\103"+
    "\1\32\2\4\1\104\7\4\1\0\1\32\1\43\1\4"+
    "\1\43\1\4\1\32\1\43\1\32\1\45\1\32\1\4"+
    "\3\32\1\4\1\0\3\43\2\0\1\44\2\0\1\43"+
    "\1\72\1\105\1\106\2\4\3\32\11\4\1\107\1\0"+
    "\1\32\1\43\1\4\1\43\2\4\1\43\1\110\1\74"+
    "\2\4\1\111\3\4\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\1\112\3\4\1\111\6\4\1\113\1\56\5\4"+
    "\1\0\1\4\1\43\1\4\1\43\2\4\1\43\10\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\2\4\1\56"+
    "\1\4\1\114\11\4\1\115\1\4\1\65\1\4\1\0"+
    "\1\4\1\43\1\4\1\43\2\4\1\43\1\116\7\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\1\112\1\67"+
    "\1\72\1\117\2\4\1\120\1\4\1\52\3\4\1\121"+
    "\1\72\1\117\1\72\2\4\1\0\1\4\1\43\1\4"+
    "\1\43\2\4\1\43\10\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\6\4\1\122\2\4\1\75\6\4\1\123"+
    "\1\4\1\0\1\4\1\43\1\4\1\43\2\4\1\43"+
    "\10\4\1\0\3\43\2\0\1\44\2\0\1\43\4\4"+
    "\1\52\11\4\1\124\3\4\1\0\1\4\1\43\1\4"+
    "\26\0\1\125\4\0\1\126\1\127\1\0\1\130\3\0"+
    "\1\131\1\132\1\133\1\134\1\135\5\0\1\43\2\4"+
    "\1\43\10\4\1\0\3\43\2\0\1\44\2\0\1\43"+
    "\1\4\1\67\1\72\11\4\1\121\1\72\1\4\1\136"+
    "\2\4\1\0\1\4\1\43\1\4\1\0\2\42\1\0"+
    "\10\42\12\0\22\42\1\0\1\42\1\0\1\42\14\43"+
    "\1\0\3\43\2\0\1\43\2\0\23\43\1\0\4\43"+
    "\2\4\1\43\1\72\7\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\22\4\1\0\1\4\1\43\1\4\1\43"+
    "\1\4\1\32\1\43\1\32\1\45\1\32\1\4\3\32"+
    "\1\4\1\0\3\43\2\0\1\44\2\0\1\43\1\72"+
    "\1\4\1\32\2\4\3\32\12\4\1\0\1\32\1\43"+
    "\1\4\1\43\1\4\1\32\1\43\1\32\1\45\1\32"+
    "\1\4\3\32\1\4\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\1\72\1\4\1\70\2\4\1\70\2\32\12\4"+
    "\1\0\1\32\1\43\1\4\1\43\2\4\1\43\10\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\13\4\1\72"+
    "\6\4\1\0\1\4\1\43\1\4\1\43\1\4\1\137"+
    "\1\43\1\137\1\4\1\137\1\4\3\137\1\4\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\2\4\1\137\2\4"+
    "\3\137\12\4\1\0\1\137\1\43\1\4\1\43\1\4"+
    "\1\32\1\43\1\32\1\45\1\54\1\4\1\32\1\54"+
    "\1\32\1\4\1\0\3\43\2\0\1\44\2\0\1\43"+
    "\2\4\1\32\2\4\3\32\12\4\1\0\1\32\1\43"+
    "\1\4\1\43\2\4\1\43\1\4\1\140\6\4\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\22\4\1\0\1\4"+
    "\1\43\1\4\1\43\2\4\1\43\1\4\1\141\6\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\22\4\1\0"+
    "\1\4\1\43\1\4\1\43\2\4\1\43\1\72\7\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\1\72\21\4"+
    "\1\0\1\4\1\43\1\4\1\43\2\4\1\43\3\4"+
    "\1\72\4\4\1\0\3\43\2\0\1\44\2\0\1\43"+
    "\2\4\1\142\13\4\1\72\3\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\1\143\6\4\1\104\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\22\4\1\0\1\4"+
    "\1\43\1\4\1\43\2\4\1\43\10\4\1\0\3\43"+
    "\2\0\1\44\2\0\1\43\14\4\1\72\5\4\1\0"+
    "\1\4\1\43\1\4\1\43\1\4\1\32\1\43\1\32"+
    "\1\45\1\32\1\4\3\32\1\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\2\4\1\32\1\141\1\4\3\32"+
    "\12\4\1\0\1\32\1\43\1\4\1\43\2\4\1\43"+
    "\10\4\1\0\3\43\2\0\1\44\2\0\1\43\2\4"+
    "\1\72\12\4\1\72\4\4\1\0\1\4\1\43\1\4"+
    "\1\43\2\4\1\43\10\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\1\72\5\4\1\72\11\4\1\72\1\4"+
    "\1\0\1\4\1\43\1\4\1\43\2\4\1\43\1\72"+
    "\7\4\1\0\3\43\2\0\1\44\2\0\1\43\2\4"+
    "\1\72\11\4\1\72\5\4\1\0\1\4\1\43\1\4"+
    "\1\43\2\4\1\43\10\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\3\4\1\144\16\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\10\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\1\72\21\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\1\145\7\4\1\0\3\43"+
    "\2\0\1\44\2\0\1\43\22\4\1\0\1\4\1\43"+
    "\1\4\1\43\1\4\1\32\1\43\1\70\1\45\1\32"+
    "\1\4\3\32\1\4\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\2\4\1\32\2\4\1\70\2\32\12\4\1\0"+
    "\1\32\1\43\1\4\1\43\1\4\1\32\1\43\1\32"+
    "\1\45\1\32\1\72\3\32\1\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\2\4\1\32\2\4\3\32\6\4"+
    "\1\72\3\4\1\0\1\32\1\43\1\4\1\43\1\4"+
    "\1\32\1\43\1\32\1\45\1\32\1\4\3\32\1\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\2\4\1\32"+
    "\2\4\3\32\2\4\1\104\7\4\1\0\1\32\1\43"+
    "\1\4\1\43\1\4\1\32\1\43\1\32\1\45\1\32"+
    "\1\4\3\32\1\4\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\2\4\1\32\2\4\2\32\1\146\12\4\1\0"+
    "\1\32\1\43\1\4\1\43\2\4\1\43\10\4\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\5\4\1\147\14\4"+
    "\1\0\1\4\1\43\1\4\1\43\1\4\1\32\1\43"+
    "\1\32\1\150\1\32\1\4\3\32\1\4\1\0\3\43"+
    "\2\0\1\44\2\0\1\43\2\4\1\32\2\4\3\32"+
    "\12\4\1\0\1\32\1\43\1\4\1\43\2\4\1\43"+
    "\10\4\1\0\3\43\2\0\1\44\2\0\1\43\4\4"+
    "\1\147\15\4\1\0\1\4\1\43\1\4\1\43\2\4"+
    "\1\43\10\4\1\0\3\43\2\0\1\44\2\0\1\43"+
    "\11\4\1\151\10\4\1\0\1\4\1\43\1\4\1\43"+
    "\2\4\1\43\4\4\1\72\3\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\1\72\21\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\10\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\17\4\1\72\2\4\1\0\1\4"+
    "\1\43\1\4\1\43\2\4\1\43\1\145\7\4\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\2\4\1\72\17\4"+
    "\1\0\1\4\1\43\1\4\1\43\2\4\1\43\10\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\10\4\1\152"+
    "\11\4\1\0\1\4\1\43\1\4\1\43\2\4\1\43"+
    "\10\4\1\0\3\43\2\0\1\44\2\0\1\43\1\153"+
    "\21\4\1\0\1\4\1\43\1\4\1\43\2\4\1\43"+
    "\10\4\1\0\3\43\2\0\1\44\2\0\1\43\3\4"+
    "\1\72\12\4\1\72\3\4\1\0\1\4\1\43\1\4"+
    "\1\43\2\4\1\43\10\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\2\4\1\72\17\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\10\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\10\4\1\154\2\4\1\72\6\4"+
    "\1\0\1\4\1\43\1\4\1\43\2\4\1\43\10\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\6\4\1\72"+
    "\11\4\1\72\1\4\1\0\1\4\1\43\1\4\1\43"+
    "\2\4\1\43\10\4\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\10\4\1\155\11\4\1\0\1\4\1\43\1\4"+
    "\1\43\2\4\1\43\10\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\11\4\1\72\10\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\1\72\6\4\1\147\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\1\72\21\4\1\0"+
    "\1\4\1\43\1\4\27\0\1\156\15\0\1\157\16\0"+
    "\1\160\22\0\1\161\1\162\3\0\1\160\42\0\1\163"+
    "\1\164\16\0\1\165\10\0\1\166\113\0\1\167\60\0"+
    "\1\170\36\0\1\171\53\0\1\172\63\0\1\173\7\0"+
    "\1\43\2\4\1\43\7\4\1\72\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\22\4\1\0\1\4\1\43\1\4"+
    "\1\43\2\4\1\43\10\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\3\4\1\72\16\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\10\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\3\4\1\174\16\4\1\0\1\4"+
    "\1\43\1\4\1\43\2\4\1\43\7\4\1\175\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\22\4\1\0\1\4"+
    "\1\43\1\4\1\43\2\4\1\43\10\4\1\0\3\43"+
    "\2\0\1\44\2\0\1\43\5\4\1\72\14\4\1\0"+
    "\1\4\1\43\1\4\1\43\2\4\1\43\3\4\1\72"+
    "\4\4\1\0\3\43\2\0\1\44\2\0\1\43\22\4"+
    "\1\0\1\4\1\43\1\4\1\43\1\4\1\32\1\43"+
    "\1\32\1\45\1\32\1\4\1\100\2\32\1\4\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\1\176\1\4\1\32"+
    "\2\4\1\177\2\32\2\4\1\104\3\4\1\200\3\4"+
    "\1\0\1\32\1\43\1\4\1\43\2\4\1\43\10\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\20\4\1\147"+
    "\1\4\1\0\1\4\1\43\1\4\1\43\2\4\1\43"+
    "\10\4\1\0\3\43\2\0\1\44\2\0\1\43\6\4"+
    "\1\201\13\4\1\0\1\4\1\43\1\4\1\43\2\4"+
    "\1\43\1\4\1\72\6\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\22\4\1\0\1\4\1\43\1\4\1\43"+
    "\2\4\1\43\10\4\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\1\4\1\202\20\4\1\0\1\4\1\43\1\4"+
    "\1\43\2\4\1\43\10\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\20\4\1\203\1\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\10\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\10\4\1\204\11\4\1\0\1\4"+
    "\1\43\1\4\30\0\1\205\27\0\1\206\6\0\1\160"+
    "\100\0\1\160\50\0\1\207\51\0\1\210\25\0\1\211"+
    "\100\0\1\210\60\0\1\212\42\0\1\213\33\0\1\210"+
    "\103\0\1\214\53\0\1\215\30\0\1\210\40\0\1\43"+
    "\2\4\1\43\10\4\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\4\4\1\216\15\4\1\0\1\4\1\43\1\4"+
    "\1\43\2\4\1\43\10\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\6\4\1\104\13\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\10\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\1\4\1\217\20\4\1\0\1\4"+
    "\1\43\1\4\1\43\2\4\1\43\10\4\1\0\3\43"+
    "\2\0\1\44\2\0\1\43\6\4\1\220\13\4\1\0"+
    "\1\4\1\43\1\4\1\43\2\4\1\43\10\4\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\12\4\1\221\1\222"+
    "\6\4\1\0\1\4\1\43\1\4\1\43\2\4\1\43"+
    "\10\4\1\0\3\43\2\0\1\44\2\0\1\43\13\4"+
    "\1\223\6\4\1\0\1\4\1\43\1\4\1\43\2\4"+
    "\1\43\10\4\1\0\3\43\2\0\1\44\2\0\1\43"+
    "\4\4\1\224\15\4\1\0\1\4\1\43\1\4\1\43"+
    "\2\4\1\43\1\225\7\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\22\4\1\0\1\4\1\43\1\4\31\0"+
    "\1\226\35\0\1\227\50\0\1\160\15\0\1\230\4\0"+
    "\1\231\4\0\1\160\3\0\1\232\55\0\1\233\41\0"+
    "\1\234\46\0\1\235\72\0\1\236\43\0\1\237\15\0"+
    "\1\43\2\4\1\43\10\4\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\5\4\1\217\14\4\1\0\1\4\1\43"+
    "\1\4\1\43\2\4\1\43\10\4\1\0\3\43\2\0"+
    "\1\44\2\0\1\43\6\4\1\147\13\4\1\0\1\4"+
    "\1\43\1\4\1\43\2\4\1\43\10\4\1\0\3\43"+
    "\2\0\1\44\2\0\1\43\10\4\1\104\11\4\1\0"+
    "\1\4\1\43\1\4\1\43\2\4\1\43\1\240\7\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\22\4\1\0"+
    "\1\4\1\43\1\4\1\43\2\4\1\43\1\241\7\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\15\4\1\242"+
    "\4\4\1\0\1\4\1\43\1\4\1\43\2\4\1\43"+
    "\10\4\1\0\3\43\2\0\1\44\2\0\1\43\3\4"+
    "\1\243\16\4\1\0\1\4\1\43\1\4\1\43\2\4"+
    "\1\43\10\4\1\0\3\43\2\0\1\44\2\0\1\43"+
    "\16\4\1\244\3\4\1\0\1\4\1\43\1\4\1\43"+
    "\2\4\1\43\7\4\1\217\1\0\3\43\2\0\1\44"+
    "\2\0\1\43\22\4\1\0\1\4\1\43\1\4\32\0"+
    "\1\245\55\0\1\160\46\0\1\246\60\0\1\247\71\0"+
    "\1\210\41\0\1\250\1\251\53\0\1\252\44\0\1\253"+
    "\25\0\1\254\47\0\1\43\2\4\1\43\10\4\1\0"+
    "\3\43\2\0\1\44\2\0\1\43\11\4\1\147\10\4"+
    "\1\0\1\4\1\43\1\4\1\43\2\4\1\43\10\4"+
    "\1\0\3\43\2\0\1\44\2\0\1\43\14\4\1\147"+
    "\5\4\1\0\1\4\1\43\1\4\1\43\2\4\1\43"+
    "\3\4\1\147\4\4\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\22\4\1\0\1\4\1\43\1\4\1\43\2\4"+
    "\1\43\10\4\1\0\3\43\2\0\1\44\2\0\1\43"+
    "\1\4\1\147\20\4\1\0\1\4\1\43\1\4\1\43"+
    "\2\4\1\43\10\4\1\0\3\43\2\0\1\44\2\0"+
    "\1\43\2\4\1\175\17\4\1\0\1\4\1\43\1\4"+
    "\33\0\1\246\54\0\1\210\55\0\1\160\21\0\1\255"+
    "\53\0\1\256\36\0\1\257\41\0\1\260\66\0\1\261"+
    "\22\0\1\246\77\0\1\210\56\0\1\210\20\0\1\210"+
    "\73\0\1\210\54\0\1\227\23\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[6908];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\15\1\1\11\1\1\1\11\35\1\2\11"+
    "\42\1\11\0\20\1\2\0\1\11\1\1\12\0\11\1"+
    "\2\0\1\1\1\11\5\0\10\1\12\0\5\1\5\0"+
    "\1\1\7\0";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[177];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char [] zzBuffer;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /* user code: */


	/**
	 * Constructor.  We must have this here as JFLex does not generate a
	 * no parameter constructor.
	 */
	public AssemblerI8080TokenMaker() {
		super();
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
		return new String[] { ";", null };
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
		int state = Token.NULL;
		switch (initialTokenType) {
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




  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public AssemblerI8080TokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public AssemblerI8080TokenMaker(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 192) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }





  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public org.fife.ui.rsyntaxtextarea.Token yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      if (zzMarkedPosL > zzStartRead) {
        switch (zzBufferL[zzMarkedPosL-1]) {
        case '\n':
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          zzAtBOL = true;
          break;
        case '\r': 
          if (zzMarkedPosL < zzEndReadL)
            zzAtBOL = zzBufferL[zzMarkedPosL] != '\n';
          else if (zzAtEOF)
            zzAtBOL = false;
          else {
            boolean eof = zzRefill();
            zzMarkedPosL = zzMarkedPos;
            zzEndReadL = zzEndRead;
            zzBufferL = zzBuffer;
            if (eof) 
              zzAtBOL = false;
            else 
              zzAtBOL = zzBufferL[zzMarkedPosL] != '\n';
          }
          break;
        default:
          zzAtBOL = false;
        }
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      if (zzAtBOL)
        zzState = ZZ_LEXSTATE[zzLexicalState+1];
      else
        zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 14: 
          { addToken(Token.RESERVED_WORD);
          }
        case 15: break;
        case 1: 
          { addToken(Token.IDENTIFIER);
          }
        case 16: break;
        case 12: 
          { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE);
          }
        case 17: break;
        case 10: 
          { addToken(Token.FUNCTION);
          }
        case 18: break;
        case 6: 
          { addToken(Token.COMMENT_EOL); addNullToken(); return firstToken;
          }
        case 19: break;
        case 8: 
          { addToken(Token.WHITESPACE);
          }
        case 20: break;
        case 3: 
          { addToken(Token.VARIABLE);
          }
        case 21: break;
        case 11: 
          { addToken(Token.PREPROCESSOR);
          }
        case 22: break;
        case 5: 
          { addToken(Token.ERROR_CHAR); /*addNullToken(); return firstToken;*/
          }
        case 23: break;
        case 9: 
          { addToken(Token.OPERATOR);
          }
        case 24: break;
        case 2: 
          { addToken(Token.LITERAL_NUMBER_DECIMAL_INT);
          }
        case 25: break;
        case 13: 
          { addToken(Token.LITERAL_CHAR);
          }
        case 26: break;
        case 4: 
          { addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken;
          }
        case 27: break;
        case 7: 
          { addNullToken(); return firstToken;
          }
        case 28: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case YYINITIAL: {
              addNullToken(); return firstToken;
            }
            case 178: break;
            default:
            return null;
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
