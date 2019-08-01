package back_end;

public interface JITCompiler {
    void compile(final Instruction[] instructions);
}