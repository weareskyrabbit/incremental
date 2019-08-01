package lsp;

public interface LanguageServerProtocol {
    void warn();
    void emit_error();
    void complete();
    void emit_help();
    void jump_to_definition();
    void search_reference();
    void highlight();
    void codelens();
    void format_document();
    void rename();
    void link_document();
    void execute_command();
}
