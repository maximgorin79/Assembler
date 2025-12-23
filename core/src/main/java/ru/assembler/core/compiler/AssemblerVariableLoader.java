package ru.assembler.core.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import ru.assembler.core.error.AssemblerException;
import ru.assembler.core.util.Converter;
import ru.assembler.core.util.Validator;

/**
 * AssemblerVariableLoader.
 *
 * @author Maxim Gorin
 */
@Slf4j
class AssemblerVariableLoader {
  Map<String, BigInteger> load(final String vTemplatePath, Charset encoding) throws IOException {
    InputStream is = Compiler.class.getResourceAsStream(vTemplatePath);
    if (is == null) {
      return Collections.emptyMap();
    }
    BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));
    final Map<String, BigInteger> varMap = new HashMap<>();
    String line;
    int lineNumber = 1;
    try {
      while ((line = reader.readLine()) != null) {
        line = removeComment(line);
        if (!line.isEmpty()) {
          putVariable(line, varMap);
        }
        lineNumber++;
      }
    } catch (IOException | RuntimeException e) {
      log.error(e.getMessage(), e);
      throw new AssemblerException(String.format("%s[%d]: %s",vTemplatePath, lineNumber, e.getMessage()));
    }
    return Collections.synchronizedMap(varMap);
  }

  private static String removeComment(String line) {
    if (line == null) {
      return line;
    }
    line = line.trim();
    if (line.isEmpty()) {
      return line;
    }
    int index = line.indexOf(';');
    if (index == -1) {
      return line;
    }
    line = line.substring(0, index);
    return line.trim();
  }

  static void putVariable(final String line, final Map<String, BigInteger> varMap) {
    String [] splitted = line.split("[ \t]{1,}");
    if (splitted.length < 2) {
      throw new AssemblerException("key-value required");
    }
    final String name = splitted[0].trim();
    if (name.isEmpty()) {
      throw new AssemblerException("key is empty");
    }
    String value = splitted[1].trim();
    if (value.isEmpty()) {
      throw new AssemblerException("value is empty");
    }
    if (!Validator.isIdentifier(name)) {
      throw new AssemblerException("identifier is required for variable name");
    }
    value = value.toLowerCase();
    if (value.startsWith("0b")) {
      value = value.substring(2, value.length() - 1);
      if (!Validator.isBinary(value)) {
        throw new AssemblerException("bad binary format");
      }
      addVariable(name, Converter.binaryToBigInteger(value), varMap);
      return;
    }
    if (value.startsWith("0x")) {
      value = value.substring(2, value.length() - 1);
      if (!Validator.isHexDecimal(value)) {
        throw new AssemblerException("bad hex format");
      }
      addVariable(name, Converter.hexadecimalToBiginteger(value), varMap);
      return;
    }
    if (value.startsWith("0")) {
      value = value.substring(1, value.length() - 1);
      if (!Validator.isOctal(value)) {
        throw new AssemblerException("bad octal format");
      }
      addVariable(name, Converter.octalToBigInteger(value), varMap);
      return;
    }
    addVariable(name, Converter.decimalToBigInteger(value), varMap);
  }

  static void addVariable(final String name, final BigInteger value, Map<String, BigInteger> varMap) {
    varMap.put(name, value);
  }
}

