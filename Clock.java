import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Clock extends JComponent implements ActionListener {
    private int count = 0;
    private Timer time;
    public Clock(){
        time = new Timer(1000, this);
        time.start();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        count++;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawString("Count: " + count, 175,20);
    }
}

