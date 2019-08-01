package ast;

public interface Node {
    // translate AST into
    String build();
    // display AST in S-Expr
    String toS(int tab);
}
