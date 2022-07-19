grammar Expr;

/* The grammar name and file name must match */

@header {
    package antlr;
}

// Start Symbol
prog: (decl | expr)+ EOF        # Program
    ;

decl: ID ':' INT_TYPE '=' NUM   # Declaration
    ;

/* ANTLR resolve ambiguities in favor of alternative given first */
expr: expr '*' expr             # Multiplication
    | expr '+' expr             # Addition
    | ID                        # Variable
    | NUM                       # Number
    ;

/* TOKEN */
ID : [a-z][a-zA-Z0-9]*; // Identifiers
NUM : '0' | '-'?[1-9][0-9]*;
INT_TYPE : 'INT';
COMMENT : '--' ~[\r\n]* -> skip;
WS : [ \t\n]+ -> skip; // White space