package ru.assembler.core.compiler.command.system;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import lombok.NonNull;
import ru.assembler.core.compiler.CompilerApi;
import ru.assembler.core.lang.Type;
import ru.assembler.core.ns.NamespaceApi;
import ru.assembler.core.settings.SettingsApi;
import ru.assembler.core.util.IOUtils;

public class DdwCommandCompiler extends DwCommandCompiler {

  protected static final String[] NAMES = {"ddw", "defdw", "dd"};

  public DdwCommandCompiler(@NonNull NamespaceApi namespaceApi,
      @NonNull SettingsApi settingsApi, @NonNull CompilerApi compilerApi) {
    super(namespaceApi, settingsApi, compilerApi);
  }

  @NonNull
  @Override
  public String[] names() {
    return NAMES;
  }

  @Override
  protected Type getDestType() {
    return Type.UInt32;
  }

  @Override
  protected void writeValue(@NonNull ByteArrayOutputStream baos, @NonNull BigInteger value)
      throws IOException {
    IOUtils.writeInt(baos, value.intValue(), settingsApi.getByteOrder());
  }
}
