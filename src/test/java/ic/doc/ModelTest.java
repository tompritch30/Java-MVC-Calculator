package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ModelTest {

  @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

  //  GuiApp.Controller dummyController = context.mock(GuiApp.Controller.class);
  Updatable dummyView = context.mock(Updatable.class);

  Model calculatorModel = new Model(dummyView);

  private void viewNotified() {
    context.checking(
        new Expectations() {
          {
            ignoring(dummyView).update(calculatorModel);
          }
        });
  }

  @Test
  public void numberInputWillBeInsertedToStackAndViewWillBeNotified() {
    context.checking(
        new Expectations() {
          {
            oneOf(dummyView).update(calculatorModel);
          }
        });

    calculatorModel.updateModel("1");
    assertThat(calculatorModel.getValue(), is(1));
  }

  //  @Test
  //  public void numberInputWhenTwoNumberAlreadyExistsInStackThrowsException() {
  //
  //    viewNotified();
  //
  //    calculatorModel.updateModel("1");
  //    calculatorModel.updateModel("1");
  //    try {
  //      calculatorModel.updateModel("1");
  //      fail("Should have thrown exception");
  //    } catch (RuntimeException rte) {
  //      assertThat(rte.getMessage(), containsString("Too Many Numbers"));
  //    }
  //  }

  @Test
  public void invalidInputThrowsException() {

    viewNotified();

    try {
      calculatorModel.updateModel("s");
      fail("Should have thrown exception");
    } catch (IllegalArgumentException iae) {
      assertThat(iae.getMessage(), containsString("Invalid input provided"));
    }
  }

  @Test(expected = EmptyStackException.class)
  public void operatorInputWhenLessThanTwoNumberInStackThrowsError() {
    calculatorModel.updateModel("+");
  }

  @Test
  public void operatorInputWillPerformOperatorOnNumbersInStack() {

    viewNotified();

    calculatorModel.updateModel("1");
    calculatorModel.updateModel("1");
    calculatorModel.updateModel("+");
    assertThat(calculatorModel.getValue(), equalTo(2));
    calculatorModel.updateModel("1");
    calculatorModel.updateModel("-");
    assertThat(calculatorModel.getValue(), equalTo(1));
    calculatorModel.updateModel("3");
    calculatorModel.updateModel("*");
    assertThat(calculatorModel.getValue(), equalTo(3));
    calculatorModel.updateModel("2");
    calculatorModel.updateModel("/");
    assertThat(calculatorModel.getValue(), equalTo(1));
  }
}
