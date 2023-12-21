import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;


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
        JPanel grid = new JPanel(new GridLayout(5,4));
        label = new JLabel(" ");
        label.setFont(new Font("Serif", Font.PLAIN, 25));
        historic = new JLabel(" ");
        pnl.add(historic);
        pnl.add(label);
        String[] digitsText = {"CE", "C", "X", "%",
                                "7", "8", "9", "<-",
                                "4", "5", "6", "-",
                                "1", "2","3","+",
                                "+/-", "0", ",", "="};
        
        for (String digits : digitsText){
            JButton btn = new JButton(digits);
            grid.add(btn);
            btn.setForeground(Color.white);
            btn.setBackground(Color.black);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.addActionListener(e -> EventListener(digits));
            btn.addMouseListener(new java.awt.event.MouseAdapter(){
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(15, 15,15));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(Color.BLACK);
                }
                
                @Override
                public void mousePressed(MouseEvent e){
                    btn.setBackground(new Color(15,15,15));
                    btn.setContentAreaFilled(false);
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    btn.setContentAreaFilled(true);
                }
            });
            
        }
        pnl.setBackground(new Color(15, 15,15));
        grid.setBackground(Color.BLACK);
        label.setForeground(Color.white);
        historic.setForeground(new Color(147, 149, 151));
        frame.add(pnl);
        pnl.add(grid);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public String opeStockMethod(){
        if (Objects.equals(opeStock, "+")) {
           return String.valueOf(ope.addition(Double.parseDouble(stockage)));
        } else if (Objects.equals(opeStock, "-")) {
            return String.valueOf(ope.subtraction(Double.parseDouble(stockage)));
        } else if (Objects.equals(opeStock, "X")){
            return String.valueOf(ope.multiplication(Double.parseDouble(stockage)));
        } else if (Objects.equals(opeStock, "%")){
            return String.valueOf(ope.division(Double.parseDouble(stockage)));
        } else {
            ope.setResult(Double.parseDouble(stockage));
            return stockage;
        }
        
    }
    
    public void reset()
    {
        history = " ";
        opeStock = null;
        ope.setResult(0);
        stockage = " ";
        lastOpe = null;
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
                if (Objects.equals(opeStock, "=")) reset();
                stockage += digits;
                label.setText(stockage);
                historic.setText(history);
                break;
            case "+":
                if (Objects.equals(stockage, " "))
                {
                    opeStock = digits;
                    history = history.substring(0, history.length() - 1) + digits;
                    historic.setText(history);
                    return;
                }

                result = opeStockMethod();
                history += stockage+digits;
                historic.setText(history);
                label.setText(result);
                lastDigit = stockage;
                stockage = " ";
                opeStock = digits;
                break;
            case "-":
                if (Objects.equals(stockage, " "))
                {
                    opeStock = digits;
                    history = history.substring(0, history.length() - 1) + digits;
                    historic.setText(history);
                    return;
                }

                result = opeStockMethod();
                history += stockage+digits;
                historic.setText(history);
                label.setText(result);
                lastDigit = stockage;
                stockage = " ";
                opeStock = digits;
                break;
            case "X":
                if (Objects.equals(stockage, " "))
                {
                    opeStock = digits;
                    history = history.substring(0, history.length() - 1) + digits;
                    historic.setText(history);
                    return;
                }

                history += stockage+digits;
                historic.setText(history);
                result = opeStockMethod();
                label.setText(result);
                lastDigit = stockage;
                stockage = " ";
                opeStock = digits;
                break;
            case "%":
                if (Objects.equals(stockage, " "))
                {
                    opeStock = digits;
                    history = history.substring(0, history.length() - 1) + digits;
                    historic.setText(history);
                    return;
                }

                history += stockage+digits;
                historic.setText(history);
                result = opeStockMethod();
                label.setText(result);
                lastDigit = stockage;
                stockage = " ";
                opeStock = digits;
                break;
            case "=":
                if (Objects.equals(stockage, " "))
                {
                    stockage = String.valueOf(ope.result);
                    history += stockage + "=";
                    historic.setText(history);
                    result = opeStockMethod();
                    label.setText(result);
                    lastOpe = opeStock;
                    lastDigit = stockage;
                    opeStock = "=";
                    stockage = result;
                    return;
                }
                
                if (Objects.equals(opeStock, "="))
                {
                    if (lastOpe == null) return;
                    String lastResult = stockage;
                    opeStock = lastOpe;
                    stockage = lastDigit;
                    history = lastResult + lastOpe + lastDigit + "=";
                    historic.setText(history);
                    result = opeStockMethod();
                    label.setText(result);
                    opeStock = "=";
                    stockage = result;
                    return;
                }
                if (opeStock == null)
                {
                    System.out.println("empty");
                    history += stockage + "=";
                    historic.setText(history);
                    lastOpe = opeStock;
                    lastDigit = stockage;
                    opeStock = "=";
                    return;
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