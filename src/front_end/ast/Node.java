package front_end.ast;

public interface Node {
    String toIR();
    String build();
    String toS(int tab);
}
