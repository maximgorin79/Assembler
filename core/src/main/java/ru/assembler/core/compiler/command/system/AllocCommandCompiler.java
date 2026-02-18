package ru.assembler.core.compiler.command.system;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import lombok.NonNull;
import ru.assembler.core.compiler.CommandCompiler;
import ru.assembler.core.compiler.CompilerApi;
import ru.assembler.core.error.CompilerException;
import ru.assembler.core.error.text.Messages;
import ru.assembler.core.lexem.Lexem;
import ru.assembler.core.lexem.LexemType;
import ru.assembler.core.ns.NamespaceApi;
import ru.assembler.core.settings.SettingsApi;
import ru.assembler.core.syntax.Expression;
import ru.assembler.core.syntax.LexemSequence;
import ru.assembler.core.util.RepeatableIterator;
import ru.assembler.core.util.RepeatableIteratorImpl;

/**
 * AllocCommandCompiler.
 *
 * @author Maxim Gorin
 */
public class AllocCommandCompiler implements CommandCompiler {

  protected static final String[] NAMES = {"malloc", "alloc"};

  protected CompilerApi compilerApi;

  protected NamespaceApi namespaceApi;

  public AllocCommandCompiler(@NonNull NamespaceApi namespaceApi
      , @NonNull CompilerApi compilerApi) {
    this.compilerApi = compilerApi;
    this.namespaceApi = namespaceApi;
  }

  @Override
  public String[] names() {
    return NAMES;
  }

  @Override
  public byte[] compile(LexemSequence lexemSequence) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    RepeatableIterator<Lexem> iterator = new RepeatableIteratorImpl<>(
        lexemSequence.get().iterator());
    Lexem nextLexem;
    if (!iterator.hasNext() || !contains(names(), (iterator.next()).getValue())) {
      return null;
    }
    nextLexem = iterator.hasNext() ? iterator.next() : null;
    while (true) {
      if (nextLexem == null) {
        throw new CompilerException(compilerApi.getFd(), compilerApi.getLineNumber(),
            Messages.getMessage(Messages.VALUE_EXCEPTED));
      }
      if (nextLexem.getType() == LexemType.CHAR || nextLexem.getType() == LexemType.DECIMAL ||
          nextLexem.getType() == LexemType.OCTAL || nextLexem.getType() == LexemType.HEXADECIMAL ||
          nextLexem.getType() == LexemType.BINARY || nextLexem.getType() == LexemType.VARIABLE) {
        final Expression expression = new Expression(compilerApi.getFd(), iterator,
            namespaceApi);
        final Expression.Result result = expression.evaluate(nextLexem);
        if (result.isUndefined()) {
          throw new CompilerException(nextLexem.getFd(), nextLexem.getLineNumber()
              , Messages.getMessage(Messages.CONSTANT_VALUE_REQUIRED));
        }
        if (expression.getLastLexem() != null) {
          nextLexem = expression.getLastLexem();
        }
        BigInteger value = result.getValue();
        int size = value.intValue();
        if (size < 0) {
          throw new CompilerException(nextLexem.getFd(), nextLexem.getLineNumber(), Messages
              .getMessage(Messages.POSITIVE_VALUE_REQUIRED));
        }
        if (size >  1024 * 1024) { //1Mb
          throw new CompilerException(nextLexem.getFd(), nextLexem.getLineNumber(), Messages
              .getMessage(Messages.DATA_TOO_LONG), value.toString());
        }
        byte []buffer = new byte[size];
        try {
          baos.write(buffer);
        } catch (IOException e) {}
        nextLexem = expression.getLastLexem();
      } else {
        throw new CompilerException(nextLexem.getFd(), nextLexem.getLineNumber(), Messages
            .getMessage(Messages.UNEXPECTED_SYMBOL), nextLexem.getValue());
      }
      if (nextLexem == null) {
        break;
      }
      if (nextLexem.getType() == LexemType.COMMA) {
        nextLexem = iterator.hasNext() ? iterator.next() : null;
      } else {
        throw new CompilerException(nextLexem.getFd(), nextLexem.getLineNumber(), Messages
            .getMessage(Messages.EXPECTED_SYMBOL), ",");
      }
    }
    return baos.toByteArray();
  }
}
