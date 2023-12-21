import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
@Scope("singleton")
public class Operator {
  private double result;
  Operator(){
    this.result = 0;
  }
  public void setResult(double result)
  {
    this.result = result;
  }
  public double addition(double x){
    result += x;
    return result;
  }
  public double subtraction(double x){
    result -= x;
    return result;
  }
  public double multiplication(double x){
    result *= x;
    return result;
  }
  public double division(double x){
    result /= x;
    return result;
  }

  public String getResult()
  {
    DecimalFormat format = new DecimalFormat("0.#");
    return format.format(result);
  }
}
