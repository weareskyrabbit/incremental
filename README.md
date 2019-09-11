A small Compiler

# Memo
Source Code  
-> front_end  
AST  
-> middle_end  
IR  
-> back_end  
Assembly  
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
