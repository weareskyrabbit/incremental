package front_end.ast;

public interface Statement extends Node {
    @Override
    String build();
    @Override
    String toS(int tab);
}
