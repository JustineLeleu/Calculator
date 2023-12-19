import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Calculator {
    public static void main(String[] args){
        ApplicationContext ctxt = new AnnotationConfigApplicationContext(Operator.class);
        Operator ope = ctxt.getBean(Operator.class);
        Frame frame = new Frame();
        frame.calculator();
    }
}
