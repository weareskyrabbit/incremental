package front_end.ast;

public interface Expression extends Statement {
    @Override
    String build();
    @Override
    String toS(int tab);
}
