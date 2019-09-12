A small Compiler

# Memo
```
Source Code (.grp)
AST         (.ast)
IR          (.ir)
Assembly    (.s)

WC          (.wc)
```
```
Source Code -> AST      : front_end
AST         -> IR       : middle_end
IR          -> Assembly : back_end

IR          -> WC       : middle_end
```
## Grammar
```
parse : [function_declaration]*
function_declaration : type identifier '(' [ type identifier [ ',' type identifier ]* ]? ')' closure
closure : '{' [statement]* '}'
statement : "if" '(' integer ')' closure
          | "while" '(' integer ')' closure
          | "return" integer ';'
          | identifier '=' integer ';'
type : "Number"
```
## `.wc` file layout
```
int magic;
int functions_size;
function[] functions;
int constant_pool_count;
constant[] constant_pool;
```
function
```
int instructions_size;
int[] instructions;
```
constant
```
int size;
byte[] value;
```
