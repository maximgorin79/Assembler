package ru.assembler.zxspectrum.core.compiler.command.system;

import lombok.NonNull;
import ru.assembler.core.compiler.CommandCompiler;
import ru.assembler.core.compiler.CompilerApi;
import ru.assembler.core.compiler.option.Option;
import ru.assembler.core.error.CompilerException;
import ru.assembler.core.error.text.Messages;
import ru.assembler.core.lexem.Lexem;
import ru.assembler.core.lexem.LexemType;
import ru.assembler.core.syntax.LexemSequence;
import ru.assembler.zxspectrum.core.compiler.option.OptionTypes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Maxim Gorin
 */

public class TapCommandCompiler implements CommandCompiler {

  protected static final String[] NAMES = {"saveTap"};

  private final CompilerApi compilerApi;

  public TapCommandCompiler(@NonNull CompilerApi compilerApi) {
    this.compilerApi = compilerApi;
  }

  @Override
  public String[] names() {
    return NAMES;
  }

  @Override
  public byte[] compile(@NonNull LexemSequence lexemSequence) {
    Iterator<Lexem> iterator = lexemSequence.get().iterator();
    Lexem nextLexem;
    if (!iterator.hasNext() || !contains(NAMES, (nextLexem = iterator.next()).getValue())) {
      return null;
    }
    nextLexem = iterator.hasNext() ? iterator.next() : null;
    if (nextLexem == null) {
      throw new CompilerException(compilerApi.getFd(), compilerApi.getLineNumber(), Messages
          .getMessage(Messages.FILE_PATH_EXCEPTED));
    }
    final List<String> paths = new LinkedList<>();
    while (true) {
      if (nextLexem.getType() == LexemType.STRING) {
        final String path = nextLexem.getValue();
        paths.add(path);
        nextLexem = iterator.hasNext() ? iterator.next() : null;
      } else {
        throw new CompilerException(compilerApi.getFd(), nextLexem.getLineNumber(), Messages
            .getMessage(Messages.UNEXPECTED_SYMBOL), nextLexem.getValue());
      }
      if (nextLexem == null) {
        break;
      }
    }
    compilerApi.addOption(new Option(OptionTypes.PRODUCE_TAP, paths));
    return new byte[0];
  }
}
