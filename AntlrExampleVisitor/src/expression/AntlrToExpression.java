package expression;

import antlr.ExprBaseVisitor;
import antlr.ExprParser;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class AntlrToExpression extends ExprBaseVisitor<Expression> {

    private final List<String> variables; // Stock des variables entrées dans le programme
    /* Les noeuds sont visités dans l'ordre d'écriture dans le document */
    private final List<String> semanticErrors; // 1. Duplicate 2. Reference to undeclared variable

    public AntlrToExpression(List<String> semanticErrors) {
        variables = new ArrayList<>();
        this.semanticErrors = semanticErrors;
    }

    @Override
    public Expression visitDeclaration(ExprParser.DeclarationContext ctx) {
        Token idToken = ctx.ID().getSymbol(); // equivalent : ctx.getChild(0).getSymbol(0);
        int line = idToken.getLine();
        int character = idToken.getCharPositionInLine() + 1;
        String id = ctx.getChild(0).getText();

        if (variables.contains((id))) {
            semanticErrors.add("Error : variable " + id + " already declared at " + line + ":" + character);
        } else {
            variables.add(id);
        }

        String type = ctx.getChild(2).getText();
        int value = Integer.parseInt(ctx.NUM().getText());

        return new VariableDeclaration(id, type, value);
    }

    @Override
    public Expression visitMultiplication(ExprParser.MultiplicationContext ctx) {
        Expression left = visit(ctx.getChild(0)); // Recursively visit the left subtree of current expression : multiplication
        Expression right = visit(ctx.getChild(2));
        return new Multiplication(left, right);
    }

    @Override
    public Expression visitAddition(ExprParser.AdditionContext ctx) {
        Expression left = visit(ctx.getChild(0)); // Recursively visit the left subtree of current expression : addition
        Expression right = visit(ctx.getChild(2));
        return new Addition(left, right);
    }

    @Override
    public Expression visitVariable(ExprParser.VariableContext ctx) {
        Token idToken = ctx.ID().getSymbol();
        int line = idToken.getLine();
        int character = idToken.getCharPositionInLine() + 1;
        String id = ctx.getChild(0).getText();

        if (!variables.contains(id)) {
            semanticErrors.add("Error: variable " + id + " not declared at : " + line + ":" + character);
        }

        return new Variable(id);
    }

    @Override
    public Expression visitNumber(ExprParser.NumberContext ctx) {
        String numberText = ctx.getChild(0).getText();
        int number = Integer.parseInt(numberText);
        return new Number(number);
    }
}
