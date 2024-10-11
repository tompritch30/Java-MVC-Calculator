package ic.doc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiApp {

  private final View view = new View(new Controller());
  private final Model calculatorModel = new Model(view);

  public class Controller implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
      String buttonName = actionEvent.getActionCommand();
      calculatorModel.updateModel(buttonName);
    }
  }

  public static void main(String[] args) {
    new GuiApp();
  }
}
