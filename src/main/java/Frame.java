import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Objects;


public class Frame {
    static JLabel label;        // label for result/given digits
    static JLabel historic;     // label for operations historic
    Operator ope;               // bean for the operators methods

    Frame(){
        ApplicationContext context = new AnnotationConfigApplicationContext(Operator.class);
        this.ope = context.getBean(Operator.class);
    }

    // Method to set the frame with labels and buttons
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

    // Method to call the correct operator method
    public String opeStockMethod(){
        DecimalFormat format = new DecimalFormat("0.#");
        return switch (opeStock) {
            case "+" -> format.format(ope.addition(Double.parseDouble(stockage)));
            case "-" -> format.format(ope.subtraction(Double.parseDouble(stockage)));
            case "X" -> format.format(ope.multiplication(Double.parseDouble(stockage)));
            case "%" -> format.format(ope.division(Double.parseDouble(stockage)));
            case null, default -> {
                ope.setResult(Double.parseDouble(stockage));
                yield stockage;
            }
        };
        
    }

    // Method to reset data
    public void reset()
    {
        history = " ";
        opeStock = null;
        ope.setResult(0);
        stockage = " ";
    }

    // Method to deal with an operator button pressed
    public void operations(String button)
    {
        if (Objects.equals(opeStock, "=")){
            history = " ";
            stockage = ope.getResult();
        }

        if (Objects.equals(stockage, " ") && !Objects.equals(history, " "))
        {
            opeStock = button;
            history = history.substring(0, history.length() - 1) + button;
            historic.setText(history);
            return;
        }

        if (Objects.equals(history, " ") && Objects.equals(stockage, " ")) stockage = "0";

        history += stockage+button;
        historic.setText(history);
        String result = opeStockMethod();
        label.setText(result);
        lastDigit = stockage;
        stockage = " ";
        opeStock = button;
    }
    
    String history = " ";           // variable to stock historic
    String stockage = " ";          // variable to stock result/digits
    String opeStock;                // variable to stock the last operator called
    String lastOpe;                 // variable to stock previous operator
    String lastDigit;               // variable to stock the last digits entered

    // Method dealing with button pressed event listener
    public void EventListener(String button){
        String result;
        switch (button) {
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
                if (stockage.equals(" ")) stockage = "";
                stockage += button;
                label.setText(stockage);
                historic.setText(history);
                break;
            case "+":
            case "-":
            case "X":
            case "%":
                operations(button);
                break;
            case "=":
                if (Objects.equals(stockage, " "))
                {
                    stockage = ope.getResult();
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
                    history += stockage + "=";
                    historic.setText(history);
                    lastOpe = opeStock;
                    lastDigit = stockage;
                    opeStock = "=";
                    return;
                }
                else
                {
                    history += stockage + button;
                    historic.setText(history);
                    result = opeStockMethod();
                    label.setText(result);
                    lastOpe = opeStock;
                    lastDigit = stockage;
                    opeStock = "=";
                    stockage = result;
                }
                
            break;

            case "<-":
                if (Objects.equals(stockage, " ")) return;
                stockage = stockage.substring(0, stockage.length() - 1);
                if (stockage.isEmpty()) stockage = " ";
                label.setText(stockage);
                break;

            case ",":
                if (Objects.equals(stockage, " ")){
                    stockage = "0.";
                    label.setText(stockage);
                    return;
                }
                if (!stockage.contains(".")){
                    stockage += ".";
                    label.setText(stockage);
                }
                break;

            case "+/-":
                if (Objects.equals(stockage, " ")) return;
                if (stockage.contains("-")){
                    stockage = stockage.substring(1);
                    label.setText(stockage);
                }
                else{
                    stockage = "-" + stockage;
                    label.setText(stockage);
                }
                break;

            case "C":
                reset();
                label.setText(stockage);
                historic.setText(history);
                break;

            case "CE":
                stockage = " ";
                label.setText(stockage);
                break;
        }
    }
}