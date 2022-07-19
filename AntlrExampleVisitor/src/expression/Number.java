package expression;

public class Number extends Expression {
    int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
