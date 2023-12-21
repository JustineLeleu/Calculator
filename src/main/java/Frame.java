import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class Frame {
    static JLabel label;
    static JLabel historic;
    
    Operator ope;
    Frame(){
        ApplicationContext context = new AnnotationConfigApplicationContext(Operator.class);
        this.ope = context.getBean(Operator.class);
    }
    public void calculator(){
        JFrame frame = new JFrame("Calculator");
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
//        pnl.setBorder(new EmptyBorder(4,4,4,4));
        JPanel grid = new JPanel(new GridLayout(5,4));
//        JTextArea screen = new JTextArea(2,25);
//        pnl.add(screen, BorderLayout.NORTH);
        label = new JLabel(" ");
        historic = new JLabel(" ");
//        label.setBounds(50,50, 150,20);
        pnl.add(historic);
//        screen.add(historic);
        pnl.add(label);
        String digitsText[] = {"CE", "C", "X", "%",
                                "7", "8", "9", "<-",
                                "4", "5", "6", "-",
                "1", "2","3","+",
                "+/-", "0", ",", "="};
        
        for (String digits : digitsText){
            JButton btn = new JButton(digits);
            grid.add(btn);
            btn.setForeground(Color.white);
            btn.setBackground(Color.black);
            btn.addActionListener(e -> EventListener(digits));
        }
        pnl.setBackground(new Color(15, 15,15));
        label.setForeground(Color.white);
        historic.setForeground(new Color(147, 149, 151));
        frame.add(pnl);
        pnl.add(grid);
        frame.setSize(300, 400);
//        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public String opeStockMethod(){
        if (opeStock == "+") {
           return String.valueOf(ope.addition(Double.parseDouble(stockage)));
        } else if (opeStock == "-") {
            return String.valueOf(ope.subtraction(Double.parseDouble(stockage)));
        } else if (opeStock == "*"){
            return String.valueOf(ope.multiplication(Double.parseDouble(stockage)));
        } else if (opeStock == "/"){
            return String.valueOf(ope.division(Double.parseDouble(stockage)));
        } else {
            ope.setResult(Double.parseDouble(stockage));
            return stockage;
        }
        
    }
    
    public void reset()
    {
        history = " ";
        opeStock = "";
        ope.setResult(0);
        stockage = " ";
    }
    
    String history = " ";
    String stockage = " ";
    String opeStock;
    String lastOpe;
    String lastDigit;
    public void EventListener(String digits){
        String result;
        switch (digits) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if (opeStock == "=") reset();
                stockage += digits;
                label.setText(stockage);
                historic.setText(history);
                break;
            case "+":
                result = opeStockMethod();
                history += stockage+digits;
                historic.setText(history);
                label.setText(result);
                stockage = " ";
                opeStock = digits;
                break;
            case "-":
                result = opeStockMethod();
                history += stockage+digits;
                historic.setText(history);
                label.setText(result);
                stockage = " ";
                opeStock = digits;
                break;
            case "*":
                history += stockage+digits;
                historic.setText(history);
                result = opeStockMethod();
                label.setText(result);
                stockage = " ";
                opeStock = digits;
                break;
            case "/":
                history += stockage+digits;
                historic.setText(history);
                result = opeStockMethod();
                label.setText(result);
                stockage = " ";
                opeStock = digits;
                break;
            case "=":
                
                if (opeStock == "=")
                {
                    String lastResult = stockage;
                    opeStock = lastOpe;
                    stockage = lastDigit;
                    history = lastResult + lastOpe + lastDigit + "=";
                    historic.setText(history);
                    result = opeStockMethod();
                    label.setText(result);
                    opeStock = "=";
                    stockage = result;
                }
                else
                {
                    history += stockage + digits;
                    historic.setText(history);
                    result = opeStockMethod();
                    label.setText(result);
                    lastOpe = opeStock;
                    lastDigit = stockage;
                    opeStock = "=";
                    stockage = result;
                    System.out.println(lastOpe);
                }
                
            break;
        }
    }
}