package ir;

public interface Code {
    String build();
    Code reduce();
    int toWC();
    String toAssembly();
}
