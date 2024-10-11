package ic.doc;

import java.util.EmptyStackException;
import java.util.Stack;

public class Model {

  private final Updatable view;

  private final Stack<Integer> numberStack = new Stack<>();

  private enum Inputs {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    NUMBER,
    INVALID
  }

  public Model(Updatable view) {
    this.view = view;
  }

  private Inputs inputToEnum(String input) {
    return switch (input) {
      case "+" -> Inputs.PLUS;
      case "-" -> Inputs.MINUS;
      case "/" -> Inputs.DIVIDE;
      case "*" -> Inputs.MULTIPLY;
      case "1", "2", "3", "4" -> Inputs.NUMBER;
      default -> Inputs.INVALID;
    };
  }

  public void updateModel(String actionInput) {

    Inputs inputEnum = inputToEnum(actionInput);

    if (inputEnum.equals(Inputs.NUMBER)) {
      pushValue(actionInput);

    } else if (inputEnum.equals(Inputs.INVALID)) {
      throw new IllegalArgumentException("Invalid input provided");
    } else {
      if (numberStack.size() < 2) {
        throw new EmptyStackException();
      }

      Integer a = numberStack.pop();
      Integer b = numberStack.pop();

      performOperation(inputEnum, b, a);
    }

    view.update(this);
  }

  private void pushValue(String actionInput) {

    numberStack.push(Integer.valueOf(actionInput));
  }

  private void performOperation(Inputs operation, Integer b, Integer a) {
    switch (operation) {
      case PLUS:
        numberStack.push(b + a);
        break;
      case MINUS:
        numberStack.push(b - a);
        break;
      case DIVIDE:
        Integer value = (int) (b / a);
        numberStack.push(value);
        break;
      case MULTIPLY:
        numberStack.push(b * a);
        break;
      default:
        throw new ArithmeticException();
    }
  }

  public Integer getValue() {
    return numberStack.peek();
  }
}
