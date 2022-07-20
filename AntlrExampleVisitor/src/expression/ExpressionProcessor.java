package expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionProcessor {
    List<Expression> list;
    public Map<String, Integer> values; // Symbol table for storing values of variables

    public ExpressionProcessor(List<Expression> list) {
        this.list = list;
        values = new HashMap<>();
    }

    public List<String> getEvaluationResults() {
        List<String> evaluations = new ArrayList<>();

        for (Expression e : list) {
            if (e instanceof VariableDeclaration) {
                VariableDeclaration declaration = (VariableDeclaration) e;
                values.put(declaration.id, declaration.value);
            } else { // e instanceof Number / Variable / addition / Multiplication
                String input = e.toString();
                int result = getEvalResult(e);
                evaluations.add(input + " is " + result);
            }
        }
        return evaluations;
    }

    private int getEvalResult(Expression e) {
        int result = 0;

        if (e instanceof Number) {
            Number number = (Number) e;
            result = number.number;
        }

        else if (e instanceof Variable) {
            Variable variable = (Variable) e;
            result = values.get(variable.id);
        }

       else if (e instanceof Addition) {
            Addition addition = (Addition) e;
            int left = getEvalResult(addition.left);
            int right = getEvalResult(addition.right);
            result = left + right;
        }

        else if (e instanceof Multiplication) {
            Multiplication multiplication = (Multiplication) e;
            int left = getEvalResult(multiplication.left);
            int right = getEvalResult(multiplication.right);
            result = left * right;
        }

        return result;
    }
}
