/* The following code was generated by JFlex 1.4.1 on 6/20/22, 7:10 PM */

/*
 * This library is distributed under a modified BSD license.  See the included
 * LICENSE file for details.
 */
package org.fife.ui.rsyntaxtextarea.modes;

import java.io.*;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


/**
 * Scanner for the Proto files. See
 * https://developers.google.com/protocol-buffers/docs/reference/proto3-spec.<p>
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
 *   <li>The generated <code>ProtoTokenMaker.java</code> file will contain two
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
 * @version 1.0
 *
 */

public class ProtoTokenMaker extends AbstractJFlexCTokenMaker {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** lexical states */
  public static final int EOL_COMMENT = 2;
  public static final int YYINITIAL = 0;
  public static final int MLC = 1;

  /**
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED =
    "\1\33\10\0\1\36\1\34\1\0\1\36\1\6\22\0\1\36\1\50"+
    "\1\32\1\7\1\55\1\50\1\52\1\35\2\54\1\40\1\43\1\42"+
    "\1\14\1\15\1\37\1\3\1\11\1\73\1\72\1\75\1\11\1\74"+
    "\1\11\2\2\1\53\1\42\1\44\1\45\1\47\1\51\1\7\4\4"+
    "\1\13\1\4\21\1\1\12\2\1\1\54\1\10\1\54\1\46\1\5"+
    "\1\0\1\21\1\31\1\66\1\63\1\25\1\20\1\65\1\56\1\16"+
    "\1\1\1\67\1\26\1\61\1\17\1\64\1\57\1\70\1\23\1\27"+
    "\1\22\1\24\1\30\1\60\1\62\1\71\1\1\1\41\1\46\1\41"+
    "\1\51\uff81\0";

  /**
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\1\2\2\3\1\2\1\4\12\2\1\5"+
    "\1\6\1\7\1\10\2\4\1\11\5\4\5\2\2\12"+
    "\1\13\5\12\1\14\2\12\2\15\2\16\1\3\1\15"+
    "\7\2\1\17\13\2\1\5\1\20\1\5\2\7\1\20"+
    "\1\21\1\22\1\0\1\4\10\2\2\0\1\23\6\0"+
    "\1\16\1\0\1\24\1\16\22\2\2\5\2\0\1\5"+
    "\1\25\2\7\2\26\2\0\7\2\10\0\5\2\1\27"+
    "\10\2\1\30\1\2\2\5\2\0\2\7\2\0\5\2"+
    "\2\0\1\31\2\0\1\32\10\2\4\0\3\2\4\0"+
    "\3\2\1\17\1\2";

  private static int [] zzUnpackAction() {
    int [] result = new int[207];
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
    "\0\0\0\76\0\174\0\272\0\370\0\u0136\0\u0174\0\u01b2"+
    "\0\u01f0\0\u022e\0\u026c\0\u02aa\0\u02e8\0\u0326\0\u0364\0\u03a2"+
    "\0\u03e0\0\u041e\0\u045c\0\u049a\0\u01b2\0\u04d8\0\u0516\0\u0554"+
    "\0\u0592\0\u01b2\0\u05d0\0\u060e\0\u064c\0\u01b2\0\u068a\0\u06c8"+
    "\0\u0706\0\u0744\0\u0782\0\u07c0\0\u07fe\0\u083c\0\u01b2\0\u087a"+
    "\0\u08b8\0\u08f6\0\u0934\0\u0972\0\u01b2\0\u09b0\0\u09ee\0\u0a2c"+
    "\0\u0a6a\0\u0aa8\0\u0ae6\0\u0b24\0\u0b62\0\u0ba0\0\u0bde\0\u0c1c"+
    "\0\u0c5a\0\u0c98\0\u0cd6\0\u0d14\0\370\0\u0d52\0\u0d90\0\u0dce"+
    "\0\u0e0c\0\u0e4a\0\u0e88\0\u0ec6\0\u0f04\0\u0f42\0\u0f80\0\u0fbe"+
    "\0\u0ffc\0\u103a\0\u1078\0\u10b6\0\u10f4\0\u1132\0\u01b2\0\u01b2"+
    "\0\u0592\0\u1170\0\u11ae\0\u11ec\0\u122a\0\u1268\0\u12a6\0\u12e4"+
    "\0\u1322\0\u1360\0\u139e\0\u13dc\0\u01b2\0\u141a\0\u1458\0\u1496"+
    "\0\u14d4\0\u1512\0\u1550\0\u158e\0\u15cc\0\u0b62\0\u0a2c\0\u160a"+
    "\0\u1648\0\u1686\0\u16c4\0\u1702\0\u1740\0\u177e\0\u17bc\0\u17fa"+
    "\0\u1838\0\u1876\0\u18b4\0\u18f2\0\u1930\0\u196e\0\u19ac\0\u19ea"+
    "\0\u1a28\0\u1a66\0\u1aa4\0\u103a\0\u1ae2\0\u1b20\0\u01b2\0\u1b5e"+
    "\0\u1b9c\0\u1132\0\u01b2\0\u1132\0\u1bda\0\u1c18\0\u1c56\0\u1c94"+
    "\0\u1cd2\0\u1d10\0\u1d4e\0\u1d8c\0\u1dca\0\u1e08\0\u1e46\0\u1e84"+
    "\0\u1ec2\0\u1f00\0\u1f3e\0\u1f7c\0\u1fba\0\u1ff8\0\u2036\0\u2074"+
    "\0\u20b2\0\370\0\u20f0\0\u212e\0\u216c\0\u21aa\0\u21e8\0\u2226"+
    "\0\u2264\0\u22a2\0\370\0\u22e0\0\u231e\0\u235c\0\u239a\0\u23d8"+
    "\0\u2416\0\u2454\0\u2492\0\u24d0\0\u250e\0\u254c\0\u258a\0\u25c8"+
    "\0\u2606\0\u2644\0\u2682\0\u26c0\0\u26fe\0\u273c\0\u277a\0\u27b8"+
    "\0\u27f6\0\u2834\0\u2872\0\u28b0\0\u28ee\0\u292c\0\u296a\0\u29a8"+
    "\0\u29e6\0\u2a24\0\u2a62\0\u2aa0\0\u2ade\0\u2b1c\0\u2b5a\0\u26c0"+
    "\0\u2b98\0\u277a\0\u2bd6\0\u2c14\0\u2c52\0\u2c90\0\u2cce";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[207];
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
    "\1\4\1\5\1\6\1\7\1\5\1\4\1\10\2\4"+
    "\1\6\2\5\1\11\1\12\1\13\1\14\1\15\1\5"+
    "\1\16\1\17\1\20\1\21\1\5\1\22\1\5\1\23"+
    "\1\24\1\4\1\25\1\26\1\27\1\30\1\31\1\32"+
    "\1\10\1\33\1\34\2\31\1\35\1\31\1\36\1\37"+
    "\1\36\1\32\1\4\1\5\1\40\1\41\1\42\1\5"+
    "\1\43\1\44\5\5\4\6\20\45\1\46\13\45\1\47"+
    "\3\45\1\50\15\45\1\51\1\45\1\52\15\45\20\53"+
    "\1\54\13\53\1\55\21\53\1\56\1\53\1\57\15\53"+
    "\6\4\1\0\5\4\2\0\14\4\1\0\1\4\21\0"+
    "\22\4\5\5\1\0\2\4\3\5\2\0\14\5\1\0"+
    "\1\4\21\0\1\4\20\5\2\60\2\6\2\60\1\0"+
    "\2\60\1\6\1\60\1\61\1\0\1\62\7\60\1\61"+
    "\4\60\1\0\1\60\21\0\15\60\4\6\2\60\1\63"+
    "\1\64\2\60\1\0\2\60\1\64\1\65\1\61\1\0"+
    "\1\62\7\60\1\61\4\60\1\0\1\60\21\0\5\60"+
    "\1\65\7\60\4\64\112\0\1\36\30\0\1\36\32\0"+
    "\2\62\5\0\1\62\60\0\4\62\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\1\5\1\66\12\5\1\0\1\4"+
    "\21\0\1\4\3\5\1\67\14\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\3\5\1\70\10\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\1\71\2\5\1\72\4\5\1\73\3\5\1\0"+
    "\1\4\21\0\1\4\20\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\5\5\1\74\6\5\1\0\1\4\21\0"+
    "\1\4\6\5\1\75\11\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\7\5\1\76\4\5\1\0\1\4\21\0"+
    "\1\4\1\5\1\77\16\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\1\100\13\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\1\5"+
    "\1\101\12\5\1\0\1\4\21\0\1\4\4\5\1\102"+
    "\13\5\1\4\5\5\1\0\2\4\3\5\2\0\1\100"+
    "\1\5\1\103\1\5\1\104\2\5\1\105\4\5\1\0"+
    "\1\4\21\0\1\4\13\5\1\106\4\5\1\4\5\5"+
    "\1\0\2\4\3\5\2\0\14\5\1\0\1\4\21\0"+
    "\1\4\6\5\1\107\4\5\1\110\4\5\10\24\1\111"+
    "\21\24\1\112\2\113\41\24\10\26\1\114\22\26\1\115"+
    "\1\0\1\116\40\26\36\0\1\27\76\0\1\117\1\120"+
    "\4\0\1\36\75\0\1\36\73\0\1\36\1\0\1\36"+
    "\74\0\1\121\1\36\75\0\1\36\1\0\1\122\73\0"+
    "\1\36\4\0\1\36\23\0\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\3\5\1\123\2\5\1\124\5\5\1\0"+
    "\1\4\21\0\1\4\20\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\7\5\1\125\4\5\1\0\1\4\21\0"+
    "\1\4\20\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\3\5\1\126\3\5\1\127\4\5\1\0\1\4\21\0"+
    "\1\4\20\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\14\5\1\0\1\4\21\0\1\4\6\5\1\130\11\5"+
    "\1\4\5\5\1\0\2\4\3\5\2\0\1\5\1\131"+
    "\12\5\1\0\1\4\21\0\1\4\1\5\1\132\16\5"+
    "\20\45\1\0\13\45\1\0\3\45\1\0\15\45\1\0"+
    "\1\45\1\0\15\45\16\0\1\133\3\0\1\134\112\0"+
    "\1\135\60\0\1\136\133\0\1\137\15\0\20\53\1\0"+
    "\13\53\1\0\21\53\1\0\1\53\1\0\15\53\16\0"+
    "\1\140\3\0\1\141\75\0\1\142\133\0\1\143\15\0"+
    "\6\60\1\0\5\60\2\0\14\60\1\0\1\60\21\0"+
    "\23\60\2\144\2\60\1\0\2\60\1\144\2\60\1\145"+
    "\1\0\14\60\1\0\1\60\7\0\1\145\11\0\15\60"+
    "\4\144\2\60\2\62\2\60\1\0\2\60\1\62\1\60"+
    "\1\61\2\0\7\60\1\61\4\60\1\0\1\60\21\0"+
    "\15\60\4\62\2\60\2\63\2\60\1\0\2\60\1\63"+
    "\1\60\1\61\1\0\1\62\7\60\1\61\4\60\1\0"+
    "\1\60\21\0\15\60\4\63\2\60\1\63\1\64\2\60"+
    "\1\0\2\60\1\64\1\60\1\61\1\0\1\62\7\60"+
    "\1\61\4\60\1\0\1\60\21\0\15\60\4\64\2\60"+
    "\3\146\1\60\1\0\2\60\1\146\1\60\1\146\2\0"+
    "\2\60\2\146\3\60\1\146\3\60\1\146\1\0\1\60"+
    "\21\0\6\60\1\146\2\60\1\146\3\60\4\146\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\2\5\1\147\1\5"+
    "\1\150\7\5\1\0\1\4\21\0\1\4\20\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\14\5\1\0\1\4"+
    "\21\0\1\4\1\5\1\151\16\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\1\5\1\147\12\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\14\5\1\0\1\4\21\0\1\4\4\5\1\152"+
    "\13\5\1\4\5\5\1\0\2\4\3\5\2\0\10\5"+
    "\1\153\3\5\1\0\1\4\21\0\1\4\20\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\14\5\1\0\1\4"+
    "\21\0\1\4\6\5\1\154\11\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\6\5\1\155\5\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\4\5\1\156\4\5\1\157\2\5\1\0\1\4"+
    "\21\0\1\4\1\5\1\160\10\5\1\161\5\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\14\5\1\0\1\4"+
    "\21\0\1\4\10\5\1\75\7\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\1\5\1\162\12\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\6\5\1\163\5\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\4\5"+
    "\1\164\7\5\1\0\1\4\21\0\1\4\20\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\1\71\13\5\1\0"+
    "\1\4\21\0\1\4\20\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\5\5\1\165\6\5\1\0\1\4\21\0"+
    "\1\4\20\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\5\5\1\166\6\5\1\0\1\4\21\0\1\4\20\5"+
    "\1\4\5\5\1\0\2\4\3\5\2\0\1\5\1\167"+
    "\12\5\1\0\1\4\21\0\1\4\20\5\1\4\5\5"+
    "\1\0\2\4\3\5\2\0\14\5\1\0\1\4\21\0"+
    "\1\4\6\5\1\170\11\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\4\5\1\171\7\5\1\0\1\4\21\0"+
    "\1\4\20\5\3\113\1\172\4\113\1\24\1\172\1\173"+
    "\4\113\5\24\4\113\3\24\1\113\1\0\1\24\24\113"+
    "\1\173\7\113\4\172\10\174\1\175\21\174\1\112\2\0"+
    "\41\174\10\113\1\176\21\113\1\177\43\113\3\115\1\200"+
    "\4\115\1\26\1\200\1\201\4\115\5\26\4\115\3\26"+
    "\1\115\1\0\1\202\24\115\1\201\7\115\4\200\34\115"+
    "\1\0\1\203\40\115\10\204\1\205\22\204\2\0\1\116"+
    "\40\204\45\0\1\36\1\0\1\31\26\0\1\4\5\5"+
    "\1\0\2\4\3\5\2\0\14\5\1\0\1\4\21\0"+
    "\1\4\10\5\1\206\7\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\13\5\1\207\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\3\5"+
    "\1\210\10\5\1\0\1\4\21\0\1\4\20\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\14\5\1\0\1\4"+
    "\21\0\1\4\4\5\1\75\13\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\11\5\1\211\2\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\6\5\1\212\5\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\7\5"+
    "\1\213\4\5\1\0\1\4\21\0\1\4\20\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\4\5\1\214\7\5"+
    "\1\0\1\4\21\0\1\4\20\5\26\0\1\215\126\0"+
    "\1\216\40\0\1\217\133\0\1\220\43\0\1\221\126\0"+
    "\1\222\40\0\1\223\133\0\1\224\15\0\2\60\2\144"+
    "\2\60\1\0\2\60\1\144\2\60\2\0\14\60\1\0"+
    "\1\60\21\0\15\60\4\144\2\0\2\144\5\0\1\144"+
    "\60\0\4\144\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\14\5\1\0\1\4\21\0\1\4\14\5\1\225\1\5"+
    "\1\226\1\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\14\5\1\0\1\4\21\0\1\4\6\5\1\227\11\5"+
    "\1\4\5\5\1\0\2\4\3\5\2\0\7\5\1\230"+
    "\4\5\1\0\1\4\21\0\1\4\20\5\1\4\5\5"+
    "\1\0\2\4\3\5\2\0\11\5\1\155\2\5\1\0"+
    "\1\4\21\0\1\4\20\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\3\5\1\231\10\5\1\0\1\4\21\0"+
    "\1\4\20\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\7\5\1\232\4\5\1\0\1\4\21\0\1\4\20\5"+
    "\1\4\5\5\1\0\2\4\3\5\2\0\6\5\1\233"+
    "\5\5\1\0\1\4\21\0\1\4\20\5\1\4\5\5"+
    "\1\0\2\4\3\5\2\0\7\5\1\234\4\5\1\0"+
    "\1\4\21\0\1\4\20\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\7\5\1\235\4\5\1\0\1\4\21\0"+
    "\1\4\20\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\6\5\1\236\5\5\1\0\1\4\21\0\1\4\20\5"+
    "\1\4\5\5\1\0\2\4\3\5\2\0\4\5\1\150"+
    "\7\5\1\0\1\4\21\0\1\4\20\5\1\4\5\5"+
    "\1\0\2\4\3\5\2\0\14\5\1\0\1\4\21\0"+
    "\1\4\3\5\1\75\14\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\7\5\1\237\4\5\1\0\1\4\21\0"+
    "\1\4\20\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\1\240\13\5\1\0\1\4\21\0\1\4\20\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\12\5\1\241\1\5"+
    "\1\0\1\4\21\0\1\4\20\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\4\5\1\242\7\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\10\5\1\243\3\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\7\5"+
    "\1\244\4\5\1\0\1\4\21\0\1\4\20\5\3\113"+
    "\1\245\4\113\1\176\1\245\20\113\1\177\37\113\4\245"+
    "\2\113\3\246\3\113\1\176\1\246\1\113\1\246\4\113"+
    "\2\246\3\113\1\246\3\113\1\246\1\177\30\113\1\246"+
    "\2\113\1\246\3\113\4\246\3\0\1\247\4\0\1\174"+
    "\1\247\1\250\4\0\5\174\4\0\3\174\2\0\1\174"+
    "\24\0\1\250\7\0\4\247\34\113\1\0\41\113\3\115"+
    "\1\251\5\115\1\251\22\115\1\0\1\203\34\115\4\251"+
    "\2\115\3\252\4\115\1\252\1\115\1\252\4\115\2\252"+
    "\3\115\1\252\3\115\1\252\2\115\1\0\1\203\25\115"+
    "\1\252\2\115\1\252\3\115\4\252\3\0\1\253\4\0"+
    "\1\204\1\253\1\254\4\0\5\204\4\0\3\204\2\0"+
    "\1\204\24\0\1\254\7\0\4\253\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\14\5\1\0\1\4\21\0\1\4"+
    "\11\5\1\255\6\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\10\5\1\256\3\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\14\5"+
    "\1\0\1\4\21\0\1\4\11\5\1\75\6\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\11\5\1\255\2\5"+
    "\1\0\1\4\21\0\1\4\20\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\13\5\1\257\1\0\1\4\21\0"+
    "\1\4\20\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\14\5\1\0\1\4\21\0\1\4\6\5\1\260\11\5"+
    "\1\4\5\5\1\0\2\4\3\5\2\0\1\261\13\5"+
    "\1\0\1\4\21\0\1\4\20\5\25\0\1\216\123\0"+
    "\1\262\101\0\1\263\33\0\1\264\105\0\1\222\123\0"+
    "\1\265\101\0\1\266\33\0\1\267\60\0\1\4\5\5"+
    "\1\0\2\4\3\5\2\0\14\5\1\0\1\4\21\0"+
    "\1\4\15\5\1\243\2\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\14\5\1\0\1\4\21\0\1\4\17\5"+
    "\1\243\1\4\5\5\1\0\2\4\3\5\2\0\5\5"+
    "\1\270\6\5\1\0\1\4\21\0\1\4\20\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\14\5\1\0\1\4"+
    "\21\0\1\4\5\5\1\150\12\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\4\5\1\243\7\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\5\5\1\271\6\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\5\5"+
    "\1\272\6\5\1\0\1\4\21\0\1\4\20\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\3\5\1\273\10\5"+
    "\1\0\1\4\21\0\1\4\20\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\1\274\13\5\1\0\1\4\21\0"+
    "\1\4\20\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\1\5\1\275\12\5\1\0\1\4\21\0\1\4\20\5"+
    "\1\4\5\5\1\0\2\4\3\5\2\0\1\5\1\276"+
    "\12\5\1\0\1\4\21\0\1\4\20\5\1\4\5\5"+
    "\1\0\2\4\3\5\2\0\1\277\13\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\3\5\1\126\10\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\11\5"+
    "\1\243\2\5\1\0\1\4\21\0\1\4\20\5\3\113"+
    "\1\24\4\113\1\176\1\24\20\113\1\177\37\113\4\24"+
    "\2\113\3\24\3\113\1\176\1\24\1\113\1\24\4\113"+
    "\2\24\3\113\1\24\3\113\1\24\1\177\30\113\1\24"+
    "\2\113\1\24\3\113\4\24\3\0\1\300\5\0\1\300"+
    "\60\0\4\300\2\0\3\301\4\0\1\301\1\0\1\301"+
    "\4\0\2\301\3\0\1\301\3\0\1\301\31\0\1\301"+
    "\2\0\1\301\3\0\4\301\3\115\1\26\5\115\1\26"+
    "\22\115\1\0\1\203\34\115\4\26\2\115\3\26\4\115"+
    "\1\26\1\115\1\26\4\115\2\26\3\115\1\26\3\115"+
    "\1\26\2\115\1\0\1\203\25\115\1\26\2\115\1\26"+
    "\3\115\4\26\3\0\1\302\5\0\1\302\60\0\4\302"+
    "\2\0\3\303\4\0\1\303\1\0\1\303\4\0\2\303"+
    "\3\0\1\303\3\0\1\303\31\0\1\303\2\0\1\303"+
    "\3\0\4\303\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\3\5\1\304\10\5\1\0\1\4\21\0\1\4\20\5"+
    "\1\4\5\5\1\0\2\4\3\5\2\0\1\77\13\5"+
    "\1\0\1\4\21\0\1\4\20\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\10\5\1\305\3\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\2\5\1\75\11\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\14\5"+
    "\1\0\1\4\21\0\1\4\6\5\1\306\11\5\37\0"+
    "\1\307\65\0\1\216\23\0\1\262\23\0\4\264\1\310"+
    "\1\0\1\310\1\0\3\264\2\310\14\264\3\0\1\310"+
    "\1\0\1\264\1\310\1\0\2\310\1\0\1\310\2\0"+
    "\5\310\21\264\37\0\1\311\65\0\1\222\23\0\1\265"+
    "\23\0\4\267\1\312\1\0\1\312\1\0\3\267\2\312"+
    "\14\267\3\0\1\312\1\0\1\267\1\312\1\0\2\312"+
    "\1\0\1\312\2\0\5\312\21\267\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\4\5\1\75\7\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\1\5\1\313\12\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\12\5"+
    "\1\314\1\5\1\0\1\4\21\0\1\4\20\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\4\5\1\314\7\5"+
    "\1\0\1\4\21\0\1\4\20\5\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\5\5\1\314\6\5\1\0\1\4"+
    "\21\0\1\4\20\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\14\5\1\0\1\4\21\0\1\4\5\5\1\75"+
    "\12\5\1\4\5\5\1\0\2\4\3\5\2\0\14\5"+
    "\1\0\1\4\21\0\1\4\7\5\1\243\10\5\1\4"+
    "\5\5\1\0\2\4\3\5\2\0\14\5\1\0\1\4"+
    "\21\0\1\4\10\5\1\315\7\5\3\0\1\174\5\0"+
    "\1\174\60\0\4\174\2\0\3\174\4\0\1\174\1\0"+
    "\1\174\4\0\2\174\3\0\1\174\3\0\1\174\31\0"+
    "\1\174\2\0\1\174\3\0\4\174\3\0\1\204\5\0"+
    "\1\204\60\0\4\204\2\0\3\204\4\0\1\204\1\0"+
    "\1\204\4\0\2\204\3\0\1\204\3\0\1\204\31\0"+
    "\1\204\2\0\1\204\3\0\4\204\1\4\5\5\1\0"+
    "\2\4\3\5\2\0\14\5\1\0\1\4\21\0\1\4"+
    "\7\5\1\315\10\5\1\4\5\5\1\0\2\4\3\5"+
    "\2\0\7\5\1\243\4\5\1\0\1\4\21\0\1\4"+
    "\20\5\1\4\5\5\1\0\2\4\3\5\2\0\1\5"+
    "\1\316\12\5\1\0\1\4\21\0\1\4\20\5\37\0"+
    "\1\264\75\0\1\267\36\0\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\11\5\1\75\2\5\1\0\1\4\21\0"+
    "\1\4\20\5\1\4\5\5\1\0\2\4\3\5\2\0"+
    "\7\5\1\275\4\5\1\0\1\4\21\0\1\4\20\5"+
    "\1\4\5\5\1\0\2\4\3\5\2\0\7\5\1\75"+
    "\4\5\1\0\1\4\21\0\1\4\20\5\1\4\5\5"+
    "\1\0\2\4\3\5\2\0\3\5\1\317\10\5\1\0"+
    "\1\4\21\0\1\4\20\5\1\4\5\5\1\0\2\4"+
    "\3\5\2\0\10\5\1\75\3\5\1\0\1\4\21\0"+
    "\1\4\20\5";

  private static int [] zzUnpackTrans() {
    int [] result = new int[11532];
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
    "\3\0\4\1\1\11\14\1\1\11\4\1\1\11\3\1"+
    "\1\11\10\1\1\11\5\1\1\11\41\1\2\11\1\0"+
    "\11\1\2\0\1\11\6\0\1\1\1\0\26\1\2\0"+
    "\1\1\1\11\3\1\1\11\2\0\7\1\10\0\22\1"+
    "\2\0\2\1\2\0\5\1\2\0\1\1\2\0\11\1"+
    "\4\0\3\1\4\0\5\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[207];
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
  private char zzBuffer[];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /* user code: */


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public ProtoTokenMaker() {
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
	 * @see #addHyperlinkToken(int, int, int)
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, false);
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
	 * @param hyperlink Whether this token is a hyperlink.
	 */
	@Override
	public void addToken(char[] array, int start, int end, int tokenType,
						int startOffset, boolean hyperlink) {
		super.addToken(array, start,end, tokenType, startOffset, hyperlink);
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
	 *        <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state;
		switch (initialTokenType) {
			case TokenTypes.COMMENT_MULTILINE:
				state = MLC;
				start = text.offset;
				break;
			default:
				state = YYINITIAL;
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
		zzCurrentPos = zzMarkedPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtEOF  = false;
	}




  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public ProtoTokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public ProtoTokenMaker(java.io.InputStream in) {
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
    while (i < 164) {
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

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = zzLexicalState;


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
        case 26:
          { int temp=zzStartRead; addToken(start,zzStartRead-1, TokenTypes.COMMENT_EOL); addHyperlinkToken(temp,zzMarkedPos-1, TokenTypes.COMMENT_EOL); start = zzMarkedPos;
          }
        case 27: break;
        case 1:
          { addToken(TokenTypes.IDENTIFIER); /* Just to be safe */
          }
        case 28: break;
        case 6:
          { addNullToken(); return firstToken;
          }
        case 29: break;
        case 3:
          { addToken(TokenTypes.LITERAL_NUMBER_DECIMAL_INT);
          }
        case 30: break;
        case 9:
          { addToken(TokenTypes.SEPARATOR);
          }
        case 31: break;
        case 18:
          { start = zzMarkedPos-2; yybegin(MLC);
          }
        case 32: break;
        case 13:
          { addToken(TokenTypes.ERROR_NUMBER_FORMAT);
          }
        case 33: break;
        case 14:
          { addToken(TokenTypes.LITERAL_NUMBER_FLOAT);
          }
        case 34: break;
        case 24:
          { addToken(TokenTypes.DATA_TYPE);
          }
        case 35: break;
        case 23:
          { addToken(TokenTypes.LITERAL_BOOLEAN);
          }
        case 36: break;
        case 17:
          { start = zzMarkedPos-2; yybegin(EOL_COMMENT);
          }
        case 37: break;
        case 20:
          { addToken(TokenTypes.LITERAL_NUMBER_HEXADECIMAL);
          }
        case 38: break;
        case 25:
          { int temp=zzStartRead; addToken(start,zzStartRead-1, TokenTypes.COMMENT_MULTILINE); addHyperlinkToken(temp,zzMarkedPos-1, TokenTypes.COMMENT_MULTILINE); start = zzMarkedPos;
          }
        case 39: break;
        case 16:
          { addToken(TokenTypes.LITERAL_STRING_DOUBLE_QUOTE);
          }
        case 40: break;
        case 5:
          { addToken(TokenTypes.ERROR_STRING_DOUBLE); addNullToken(); return firstToken;
          }
        case 41: break;
        case 12:
          { addToken(start,zzStartRead-1, TokenTypes.COMMENT_EOL); addNullToken(); return firstToken;
          }
        case 42: break;
        case 22:
          { addToken(TokenTypes.ERROR_CHAR);
          }
        case 43: break;
        case 21:
          { addToken(TokenTypes.ERROR_STRING_DOUBLE);
          }
        case 44: break;
        case 4:
          { addToken(TokenTypes.OPERATOR);
          }
        case 45: break;
        case 11:
          { addToken(start,zzStartRead-1, TokenTypes.COMMENT_MULTILINE); return firstToken;
          }
        case 46: break;
        case 19:
          { yybegin(YYINITIAL); addToken(start,zzStartRead+1, TokenTypes.COMMENT_MULTILINE);
          }
        case 47: break;
        case 8:
          { addToken(TokenTypes.WHITESPACE);
          }
        case 48: break;
        case 7:
          { addToken(TokenTypes.ERROR_CHAR); addNullToken(); return firstToken;
          }
        case 49: break;
        case 15:
          { addToken(TokenTypes.RESERVED_WORD);
          }
        case 50: break;
        case 2:
          { addToken(TokenTypes.IDENTIFIER);
          }
        case 51: break;
        case 10:
          {
          }
        case 52: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case EOL_COMMENT: {
              addToken(start,zzStartRead-1, TokenTypes.COMMENT_EOL); addNullToken(); return firstToken;
            }
            case 208: break;
            case YYINITIAL: {
              addNullToken(); return firstToken;
            }
            case 209: break;
            case MLC: {
              addToken(start,zzStartRead-1, TokenTypes.COMMENT_MULTILINE); return firstToken;
            }
            case 210: break;
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
