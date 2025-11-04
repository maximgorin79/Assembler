package ru.assembler.core.lexem;

import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import ru.assembler.core.error.BadCharsetEncodingException;
import ru.assembler.core.error.CompilerException;
import ru.assembler.core.error.InvalidFormatNumberException;
import ru.assembler.core.error.LexemException;
import ru.assembler.core.error.text.Messages;
import ru.assembler.core.io.FileDescriptor;
import ru.assembler.core.lang.Encoding;
import ru.assembler.core.util.AnalyzerIterator;
import ru.assembler.core.util.LexemUtil;
import ru.assembler.core.util.SymbolUtil;
import ru.assembler.core.util.Validator;

import java.awt.image.Kernel;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author Maxim Gorin
 */
public class LexemAnalyzer implements Iterable<Lexem> {

	private final static Encoding DEFAULT_ENCODING = Encoding.UTF_8;

	private final static Encoding DEFAULT_PLATFORM_ENCODING = Encoding.ASCII;

	private PushbackReader pbReader;

	private int lineNumber = 1;

	@NonNull
	private FileDescriptor fd;

	private final LexemInternalIterator1 lexemInternalIterator1 = new LexemInternalIterator1();

	private final LexemInternalIterator2 lexemInternalIterator2 = new LexemInternalIterator2();

	@Getter
	private Encoding encoding;

	private Encoding platformEncoding;

	@Getter
	@Setter
	private boolean trimEof = true;

	@Getter
	private int maxIdentifierLength = 31;

	private LexemAnalyzer() {

	}

	public LexemAnalyzer(@NonNull InputStream is) {
		this(is, DEFAULT_PLATFORM_ENCODING, DEFAULT_ENCODING);
	}

	public LexemAnalyzer(FileDescriptor fd, @NonNull InputStream is) {
		this(fd, is, DEFAULT_PLATFORM_ENCODING, DEFAULT_ENCODING);
	}

	public LexemAnalyzer(FileDescriptor fd, @NonNull InputStream is, Encoding platformEncoding) {
		this(fd, is, platformEncoding, DEFAULT_ENCODING);
	}

	public LexemAnalyzer(@NonNull FileDescriptor fd, @NonNull InputStream is, Encoding platformEncoding,
			Encoding encoding) {
		this(is, platformEncoding, encoding);
		this.fd = fd;
	}

	public LexemAnalyzer(@NonNull InputStream is, Encoding platformEncoding, Encoding encoding) {
		if (platformEncoding == null) {
			platformEncoding = DEFAULT_PLATFORM_ENCODING;
		}
		this.platformEncoding = platformEncoding;
		if (encoding == null) {
			encoding = DEFAULT_ENCODING;
		}
		this.encoding = encoding;
		pbReader = new PushbackReader(new InputStreamReader(is, Charset.forName(encoding.getName())));
	}

	private int skipSpace() throws IOException {
		int ch;
		while (SymbolUtil.isSpace(ch = pbReader.read()))
			;
		return ch;
	}

	private Lexem getEOF() {
		return new Lexem(fd, lineNumber, LexemType.EOF);
	}

	private Lexem getEOL(int ch) {
		return new Lexem(fd, lineNumber++, LexemType.EOL);
	}

	private Lexem getComment(int ch) throws IOException {
		StringBuilder sb = new StringBuilder();
		while (!SymbolUtil.isEOF(ch = pbReader.read())) {
			if (SymbolUtil.isEOL(ch)) {
				pbReader.unread(ch);
				break;
			}
			sb.append((char) ch);
		}
		return new Lexem(fd, lineNumber, LexemType.COMMENT, sb.toString());
	}

	private Lexem getDottedIdentifier(int ch) throws IOException {
		StringBuilder sb = new StringBuilder();
		ch = pbReader.read();
		if (!SymbolUtil.isLetter(ch) && !SymbolUtil.isUnderline(ch)) {
			throw new CompilerException(fd, lineNumber, Messages.getMessage(Messages.IDENTIFIER_EXPECTED));
		}
		sb.append((char) ch);
		while (!SymbolUtil.isEOF(ch)) {
			ch = pbReader.read();
			if (SymbolUtil.isIdentifier(ch)) {
				sb.append((char) ch);
			} else {
				if (!SymbolUtil.isEOF(ch)) {
					pbReader.unread(ch);
				}
				break;
			}
		}
		return new Lexem(fd, lineNumber, LexemType.IDENTIFIER, sb.toString());
	}

	private Lexem getIdentifier(int ch) throws IOException {
		final StringBuilder sb = new StringBuilder();
		sb.append((char) ch);
		int count = 1;
		while (!SymbolUtil.isEOF(ch = pbReader.read())) {
			if (SymbolUtil.isLetter(ch) || SymbolUtil.isDecDigit(ch) || SymbolUtil.isUnderline(ch)) {
				if (count < maxIdentifierLength) {
					sb.append((char) ch);
				}
				count++;
			} else {
				break;
			}
		}
		if (SymbolUtil.isColon(ch)) {
			return new Lexem(fd, lineNumber, LexemType.LABEL, sb.toString());
		} else if (SymbolUtil.isApostrophe(ch)) {
			sb.append((char) ch);
		} else {
			if (!SymbolUtil.isEOF(ch)) {
				pbReader.unread(ch);
			}
		}
		final String name = sb.toString();
		if (SymbolUtil.isHexOldStylePostfix(name.charAt(name.length() - 1))) {
			final String number = StringUtils.chop(name);
			if (LexemUtil.isHexNumber(number)) {
				return new Lexem(fd, lineNumber, LexemType.HEXADECIMAL, number);
			}
		}
		return new Lexem(fd, lineNumber, LexemType.IDENTIFIER, name);
	}

	private Lexem getAsmStyleHexadecimalNumber(int ch) throws IOException {
		String value = getToken();
		if (!Validator.isHexDecimal(value)) {
			throw new InvalidFormatNumberException(fd, lineNumber,
					Messages.getMessage(Messages.INVALID_HEXADECIMAL_NUMBER_FORMAT), value);
		}
		return new Lexem(fd, lineNumber, LexemType.HEXADECIMAL, value);
	}

	private Lexem getCStyleHexDecimalNumber() throws IOException {
		String token = getToken();
		if (!Validator.isHexDecimal(token)) {
			throw new InvalidFormatNumberException(fd, lineNumber, Messages.getMessage(Messages.INVALID_NUMBER_FORMAT),
					token);
		}
		return new Lexem(fd, lineNumber, LexemType.HEXADECIMAL, token);
	}

	private Lexem getCStyleBinaryNumber() throws IOException {
		String token = getToken();
		if (!Validator.isBinary(token)) {
			throw new InvalidFormatNumberException(fd, lineNumber, Messages.getMessage(Messages.INVALID_NUMBER_FORMAT),
					token);
		}
		return new Lexem(fd, lineNumber, LexemType.BINARY, token);
	}

	private Lexem getClassicStyleNumber(int ch) throws IOException {
		LexemType type = LexemType.DECIMAL;
		if (ch == '0') {
			type = LexemType.OCTAL;
		}
		StringBuilder sb = new StringBuilder();
		sb.append((char)ch);
		sb.append(getToken());
		ch = sb.charAt(sb.length() - 1); // last letter
		if (SymbolUtil.isHexOldStylePostfix(ch)) {
			sb.setLength(sb.length() - 1);
			type = LexemType.HEXADECIMAL;
		} else {
			if (SymbolUtil.isBinaryOldStylePostfix(ch)) {
				sb.setLength(sb.length() - 1);
				type = LexemType.BINARY;
			} else {
				if (SymbolUtil.isOctalOldStylePostfix(ch)) {
					sb.setLength(sb.length() - 1);
					type = LexemType.OCTAL;
				}
			}
		}
		String value = sb.toString();
		if (type == LexemType.BINARY) {
			if (!Validator.isBinary(value)) {
				throw new InvalidFormatNumberException(fd, lineNumber,
						Messages.getMessage(Messages.INVALID_BINARY_NUMBER_FORMAT), value);
			}
		} else {
			if (type == LexemType.OCTAL) {
				if (!Validator.isOctal(value)) {
					throw new InvalidFormatNumberException(fd, lineNumber,
							Messages.getMessage(Messages.INVALID_OCTAL_NUMBER_FORMAT), value);
				}
			} else {
				if (type == LexemType.HEXADECIMAL) {
					if (!Validator.isHexDecimal(value)) {
						throw new InvalidFormatNumberException(fd, lineNumber,
								Messages.getMessage(Messages.INVALID_HEXADECIMAL_NUMBER_FORMAT), value);
					}
				} else {
					if (!Validator.isDecimal(value)) {
						throw new InvalidFormatNumberException(fd, lineNumber,
								Messages.getMessage(Messages.INVALID_NUMBER_FORMAT), value);
					}
				}
			}
		}
		return new Lexem(fd, lineNumber, type, value);
	}

	private Lexem getNumber(int ch) throws IOException {
		if (ch == '0') {
			ch = pbReader.read();
			if (SymbolUtil.isHexNewStylePrefix(ch)) {
				return getCStyleHexDecimalNumber();
			}

			if (SymbolUtil.isBinaryNewStylePrefix(ch)) {
				return getCStyleBinaryNumber();
			}
			if (!SymbolUtil.isEOF(ch)) {
				pbReader.unread(ch);
			}
			ch = '0';
		}
		return getClassicStyleNumber(ch);
	}

	private Lexem getHexadecimalNumberOrVariable(int ch) throws IOException {
		String value = getToken();
		if (Validator.isIdentifier(value)) {
			return new Lexem(fd, lineNumber, LexemType.VARIABLE, value);
		}
		if (!Validator.isHexDecimal(value)) {
			throw new InvalidFormatNumberException(fd, lineNumber,
					Messages.getMessage(Messages.INVALID_HEXADECIMAL_NUMBER_FORMAT), value);
		}
		return new Lexem(fd, lineNumber, LexemType.HEXADECIMAL, value);
	}

	private Lexem getDelimiter(int ch) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append((char) ch);
		LexemType type = LexemType.UNKNOWN;
		switch (ch) {
		case '+':
			type = LexemType.PLUS;
			break;
		case '-':
			type = LexemType.MINUS;
			break;
		case '/':
			type = LexemType.SLASH;
			break;
		case '*':
			type = LexemType.STAR;
			break;
		case '(':
			type = LexemType.OPEN_BRACE;
			break;
		case ')':
			type = LexemType.CLOSED_BRACE;
			break;
		case ',':
			type = LexemType.COMMA;
			break;
		case '&':
			type = LexemType.AMPERSAND;
			break;
		case '|':
			type = LexemType.PIPE;
			break;
		case '%':
			type = LexemType.PERCENT;
			break;
		case '~':
			type = LexemType.TILDE;
			break;
		case '^':
			type = LexemType.CARET;
			break;
		case '<':
			ch = pbReader.read();
			switch (ch) {
			case '<':
				sb.append((char) ch);
				type = LexemType.LSHIFT;
				break;
			default:
				if (!SymbolUtil.isEOF(ch)) {
					pbReader.unread(ch);
					break;
				}
			}
			break;
		case '>':
			ch = pbReader.read();
			switch (ch) {
			case '>':
				sb.append((char) ch);
				type = LexemType.RSHIFT;
				break;
			default:
				if (!SymbolUtil.isEOF(ch)) {
					pbReader.unread(ch);
					break;
				}
			}
			break;

		}
		return new Lexem(fd, lineNumber, type, sb.toString());
	}

	private Lexem getChar(int ch) throws IOException {
		StringBuilder sb = new StringBuilder();
		while (!SymbolUtil.isApostrophe(ch = pbReader.read())) {
			if (SymbolUtil.isEOF(ch) || SymbolUtil.isEOL(ch)) {
				throw new LexemException(fd, lineNumber, Messages.getMessage(Messages.EXPECTED_SYMBOL), "'");
			}
			if (ch == '\\') {
				sb.append((char) ch);
				ch = pbReader.read();
				if (SymbolUtil.isEOF(ch) || SymbolUtil.isEOL(ch)) {
					throw new LexemException(fd, lineNumber, Messages.getMessage(Messages.EXPECTED_SYMBOL), "'");
				}
			}
			sb.append((char) ch);
		}
		String charValue = sb.toString();
		if (!charValue.isEmpty()) {
			charValue = StringEscapeUtils.unescapeJava(charValue);
		}
		if (!Checker.isValidEncoding(charValue, platformEncoding)) {
			throw new BadCharsetEncodingException(fd, lineNumber, Messages.getMessage(Messages.BAD_CHARSET_ENCODING),
					platformEncoding.getName());
		}
		if (charValue.length() > 1) {
			throw new LexemException(fd, lineNumber, Messages.getMessage(Messages.CHAR_TOO_LONG), charValue);
		}
		return new Lexem(fd, lineNumber, LexemType.CHAR, charValue);
	}

	private Lexem getString(int ch) throws IOException {
		StringBuilder sb = new StringBuilder();
		do {
			while (!SymbolUtil.isQuote(ch = pbReader.read())) {
				if (SymbolUtil.isEOF(ch) || SymbolUtil.isEOL(ch)) {
					throw new LexemException(fd, lineNumber, Messages.getMessage(Messages.EXPECTED_SYMBOL), "\"");
				}
				if (ch == '\\') {
					sb.append((char) ch);
					ch = pbReader.read();
					if (SymbolUtil.isEOF(ch) || SymbolUtil.isEOL(ch)) {
						throw new LexemException(fd, lineNumber, Messages.getMessage(Messages.EXPECTED_SYMBOL), "\"");
					}
				}
				sb.append((char) ch);
			}
			ch = pbReader.read();
			if (!SymbolUtil.isQuote(ch)) {
				if (!SymbolUtil.isEOF(ch)) {
					pbReader.unread(ch);
				}
				break;
			}
		} while (true);
		String value = sb.toString();
		if (!value.isEmpty()) {
			value = StringEscapeUtils.unescapeJava(value);
		}
		if (!Checker.isValidEncoding(value, platformEncoding)) {
			throw new BadCharsetEncodingException(fd, lineNumber, Messages.getMessage(Messages.BAD_CHARSET_ENCODING),
					platformEncoding.getName());
		}
		return new Lexem(fd, lineNumber, LexemType.STRING, value);
	}

	protected Lexem getNext() throws IOException {
		int ch = pbReader.read();
		if (SymbolUtil.isSpace(ch)) {
			ch = skipSpace();
		}
		if (SymbolUtil.isEOF(ch)) {
			return getEOF();
		}
		if (SymbolUtil.isEOL(ch)) {
			return getEOL(ch);
		}
		if (SymbolUtil.isComment(ch)) {
			return getComment(ch);
		}
		if (SymbolUtil.isLetter(ch) || SymbolUtil.isUnderline(ch)) {
			return getIdentifier(ch);
		}
		if (SymbolUtil.isDot(ch)) {
			return getDottedIdentifier(ch);
		}
		if (SymbolUtil.isDecDigit(ch)) {
			return getNumber(ch);
		}
		if (SymbolUtil.isDelimiter(ch)) {
			return getDelimiter(ch);
		}
		if (SymbolUtil.isApostrophe(ch)) {
			return getChar(ch);
		}
		if (SymbolUtil.isQuote(ch)) {
			return getString(ch);
		}
		if (SymbolUtil.isDollar(ch)) {
			return getHexadecimalNumberOrVariable(ch);
		}
		if (SymbolUtil.isHash(ch)) {
			return getAsmStyleHexadecimalNumber(ch);
		}
		throw new LexemException(fd, lineNumber, Messages.getMessage(Messages.UNEXPECTED_SYMBOL),
				String.valueOf((char) ch));
	}

	public void setMaxIdentifierLength(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("Length is zero or negative");
		}
		this.maxIdentifierLength = length;
	}

	@Override
	public Iterator<Lexem> iterator() {
		return trimEof ? lexemInternalIterator2 : lexemInternalIterator1;
	}

	private String getToken() throws IOException {
		StringBuilder sb = new StringBuilder();
		int ch;
		while (true) {
			ch = pbReader.read();
			if (SymbolUtil.isDelimiter(ch) || SymbolUtil.isSpace(ch) || SymbolUtil.isEOL(ch) || SymbolUtil.isEOF(ch)) {
				break;
			}
			sb.append((char) ch);
		}
		if (!SymbolUtil.isEOF(ch)) {
			pbReader.unread(ch);
		}
		String value = sb.toString();
		return value;
	}

	// Retrieves the EOF lexeme
	private class LexemInternalIterator1 implements Iterator<Lexem> {

		private Lexem readLexem;

		private boolean eof;

		@Override
		public boolean hasNext() {
			if (eof) {
				return false;
			}
			if (readLexem != null) {
				return true;
			}
			try {
				readLexem = getNext();
				return true;
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		@Override
		public Lexem next() {
			if (hasNext()) {
				if (readLexem.getType() == LexemType.EOF) {
					if (!eof) {
						eof = true;
					} else {
						throw new NoSuchElementException();
					}
				}
				final Lexem lexem = readLexem;
				readLexem = null;
				return lexem;
			}
			throw new NoSuchElementException();
		}
	}

	// Does not retrieve EOF lexeme
	private class LexemInternalIterator2 extends AnalyzerIterator<Lexem> {

		@Override
		protected Lexem externalNext() throws IOException {
			return getNext();
		}

		@Override
		protected boolean externalHasNext() {
			return lastItem.getType() != LexemType.EOF;
		}
	}
}
