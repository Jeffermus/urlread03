import javax.swing.*;
import java.awt.*;

public class WorkbenchOld {

    public void createAndShowGui() {
        final JFrame frame = new JFrame("Stack Overflow");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        //PANEL TOP
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.PAGE_AXIS));
        JButton pbConnect = new JButton("Connect");
        JLabel lblConnected = new JLabel("Not Connected");
        JButton pbExecute = new JButton("Execute");
        JButton pbDelete = new JButton("Delete");
        JButton pbInsert = new JButton("Insert");
        pbConnect.addActionListener(event -> {
            if (4>3) {  //(resToTab.setConnection()) {
                lblConnected.setText("Connected");
            } else {
                pbExecute.setEnabled(false);
            }
        });
        //pbExecute.addActionListener(event -> executeQuery());
        panelTop.add(pbConnect);
        panelTop.add(pbExecute);
        panelTop.add(lblConnected);
        JTextField txtQuery = new JTextField("", 50);
        panelTop.add(txtQuery);
        frame.add(panelTop);

        //MAIN WINDOW
        frame.pack();
        frame.setBounds(100, 100, 800, 800);
        frame.setVisible(true);

    }

}
