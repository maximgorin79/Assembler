package ru.assembler.microsha.core.compiler.command.system;

import lombok.NonNull;
import ru.assembler.core.compiler.CommandCompiler;
import ru.assembler.core.compiler.CompilerApi;
import ru.assembler.core.compiler.option.Option;
import ru.assembler.core.compiler.option.OptionType;
import ru.assembler.core.error.CompilerException;
import ru.assembler.core.error.text.MessageList;
import ru.assembler.core.lexem.Lexem;
import ru.assembler.core.lexem.LexemType;
import ru.assembler.core.syntax.LexemSequence;
import ru.assembler.microsha.core.compiler.option.OptionTypes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Maxim Gorin
 */

public class RkmCommandCompiler implements CommandCompiler {
    protected static final String[] NAMES = {"saveRkm"};

    private final CompilerApi compilerApi;

    public RkmCommandCompiler(@NonNull CompilerApi compilerApi) {
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
        if (!iterator.hasNext() || !contains(names(), (nextLexem = iterator.next()).getValue())) {
            return null;
        }
        nextLexem = iterator.hasNext() ? iterator.next() : null;
        if (nextLexem == null) {
            throw new CompilerException(compilerApi.getFd(), compilerApi.getLineNumber(), MessageList
                    .getMessage(MessageList.FILE_PATH_EXCEPTED));
        }
        final List<String> paths = new LinkedList<>();
        while (true) {
            if (nextLexem.getType() == LexemType.STRING) {
                final String path = nextLexem.getValue();
                paths.add(path);
            } else {
                throw new CompilerException(compilerApi.getFd(), nextLexem.getLineNumber(), MessageList
                        .getMessage(MessageList.UNEXPECTED_SYMBOL), nextLexem.getValue());
            }
            nextLexem = iterator.hasNext() ? iterator.next() : null;
            if (nextLexem == null) {
                break;
            }
        }
        compilerApi.addOption(new Option(OptionTypes.PRODUCE_RKM, paths));
        return new byte[0];
    }
}
