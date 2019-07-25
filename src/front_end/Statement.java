package front_end;

public interface Statement extends Node {
    String toS(int tab);
    @Override
    String build();
}
