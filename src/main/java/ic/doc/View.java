package ic.doc;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;

public class View implements Updatable {

  private final JPanel panel;
  private final JTextField textField;

  public View(ActionListener controller) {
    JFrame frame = new JFrame("Calculator");
    frame.setSize(400, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new JPanel();

    textField = new JTextField(5);
    panel.add(textField);
    setNumberButtonListener(controller);
    setOperatorButtonListener(controller);
    frame.getContentPane().add(panel);
    frame.setVisible(true);
  }

  public void setNumberButtonListener(ActionListener listener) {
    for (int i = 1; i <= 4; i++) {
      configureButton(listener, new JButton(String.valueOf(i)));
    }
  }

  private void configureButton(ActionListener listener, JButton button) {
    button.addActionListener(listener);
    panel.add(button);
  }

  public void setOperatorButtonListener(ActionListener listener) {
    String[] operators = {"+", "-", "/", "*"};
    for (String operator : operators) {
      configureButton(listener, new JButton(operator));
    }
  }

  @Override
  public void update(Model calculatorModel) {
    textField.setText(String.valueOf(calculatorModel.getValue()));
  }
}
