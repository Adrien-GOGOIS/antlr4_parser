grammar Expr;

/* The grammar name and file name must match */

@header {
    package antlr;
}

// Start Symbol
prog: (decl | expr)+ EOF
    ;

decl: ID ':' INT_TYPE '=' NUM
    ;

/* ANTLR resolve ambiguities in favor of alternative given first */
expr: expr '*' expr
    | expr '+' expr
    | ID
    | NUM
    ;

/* TOKEN */
ID : [a-z][a-zA-Z0-9]*; // Identifiers
NUM : '0' | '-'?[1-9][0-9]*;
INT_TYPE : 'INT';
COMMENT : '--' ~[\r\n]* -> skip;
WS : [ \t\n]+ -> skip; // White space