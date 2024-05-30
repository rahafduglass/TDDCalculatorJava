import java.util.*;

public class Calculator {
    public Stack<Object> stack = new Stack<>();
    public Deque<Object> queue = new LinkedList<>();

    public double addTwo(double num1, double num2) {
        return num1 + num2;
    }


    public double subtractTwo(double num1, double num2) {
        return num1 - num2;
    }


    public double multiplyTwo(double num1, double num2) {
        return num1 * num2;
    }

    public double divideTwo(double num1, double num2) {
        return num1 / num2;
    }

    public void readExpression(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            // place numbers

            String number = "";
            i = pushNumbersIntoQueue(expression, i, number);

            // place operators

            if (expression.charAt(i) == '(') {
                stack.push(expression.charAt(i));
            } else if (expression.charAt(i) == '+' || expression.charAt(i) == '/' || expression.charAt(i) == '*') {
                providePrecedence(expression, i);
            } else if (expression.charAt(i) == '-') {
                if (i == 0 || expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '-' || expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '/' || expression.charAt(i - 1) == '(') {// at beginning
                    i = readNegativeNumberIntoQueue(expression, i);
                } else {
                    providePrecedence(expression, i);
                }
            } else if (expression.charAt(i) == ')') {
                while ((char) stack.peek() != '(') {
                    queue.addLast(stack.pop());
                }
                stack.pop();// for the '('
            }


        }
    }

    private int readNegativeNumberIntoQueue(String expression, int i) {
        String number;
        i++;
        number = "-";
        i = pushNumbersIntoQueue(expression, i, number);
        return i;
    }

    private void providePrecedence(String expression, int i) {
        while (!stack.isEmpty() && ((char) stack.peek() == '*' || (char) stack.peek() == '/'))
            queue.addLast(stack.pop());
        stack.push(expression.charAt(i));
    }

    private int pushNumbersIntoQueue(String expression, int i, String number) {
        while (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.') {
            number += expression.charAt(i);
            if (expression.length() != i + 1 && expression.charAt(i + 1) != '*' && expression.charAt(i + 1) != '/' && expression.charAt(i + 1) != '+' && expression.charAt(i + 1) != '-' && expression.charAt(i + 1) != '(' && expression.charAt(i + 1) != ')')
                i++;
            else {
                double numberDouble = Double.parseDouble(number);
                queue.addLast(numberDouble);
                break;
            }
        }
        return i;
    }

    public void emptyStackIntoQueue() {
        while (!stack.isEmpty())
            queue.addLast(stack.pop());
    }

    public void emptyQueueIntoStack() {
        while (!queue.isEmpty()) {
            if (queue.getFirst() instanceof Double) {
                stack.push(queue.poll());

            } else if ((char) queue.getFirst() == '*' || (char) queue.getFirst() == '/' || (char) queue.getFirst() == '+' || (char) queue.getFirst() == '-') {

                double element2 = (double) stack.pop();
                double element1 = (double) stack.pop();

                switch ((char) queue.getFirst()) {
                    case '+':
                        stack.push(addTwo(element1, element2));
                        queue.poll();
                        break;
                    case '-':
                        stack.push(subtractTwo(element1, element2));
                        queue.poll();
                        break;
                    case '*':
                        stack.push(multiplyTwo(element1, element2));
                        queue.poll();
                        break;
                    case '/':
                        stack.push(divideTwo(element1, element2));
                        queue.poll();
                        break;
                }
            }


        }

    }

    public double calculate(String expression) {
        readExpression(expression);
        emptyStackIntoQueue();
        emptyQueueIntoStack();
        if ((double) stack.peek() == 0)
            return 0;
        else
            return (double) stack.peek();
    }
}
