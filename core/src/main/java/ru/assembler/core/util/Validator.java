package ru.assembler.core.util;

public final class Validator {
	private Validator() {
		
	}
	
	public static final boolean isBinary(String s) {
		if (s == null || s.trim().isEmpty()) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!SymbolUtil.isBinaryDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static final boolean isOctal(String s) {
		if (s == null || s.trim().isEmpty()) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!SymbolUtil.isOctalDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static final boolean isDecimal(String s) {
		if (s == null || s.trim().isEmpty()) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!SymbolUtil.isDecDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static final boolean isHexDecimal(String s) {
		if (s == null || s.trim().isEmpty()) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!SymbolUtil.isHexDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static final boolean isIdentifier(String s) {
		if (s == null || s.trim().isEmpty()) {
			return false;
		}
		int ch = s.charAt(0);//first symbol
		if (SymbolUtil.isUnderline(ch) || SymbolUtil.isLetter(ch)) {
			for (int i = 1; i < s.length(); i++) {
				ch = s.charAt(i);
				if (!SymbolUtil.isUnderline(ch) && !SymbolUtil.isLetter(ch) && !SymbolUtil.isDecDigit(ch)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
