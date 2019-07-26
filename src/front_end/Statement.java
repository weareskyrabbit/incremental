package front_end;

public interface Statement extends Node {
    @Override
    String build();
    @Override
    String toS(int tab);
}
