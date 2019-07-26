A small Compiler

# Memo
Source Code  
-> front_end  
AST  
-> middle_end  
IR  
-> back_end  
Assembly  
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
