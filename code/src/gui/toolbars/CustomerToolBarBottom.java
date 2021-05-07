
package gui.toolbars;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class CustomerToolBarBottom extends JToolBar {
        private ActionListener filterListener;
    public CustomerToolBarBottom() {
        this.setFloatable(false);
        this.setMargin(new Insets(5,25,5,5));
        initialization();
    }

    private void initialization() {
        ActionListener al = new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            String ch = ((JButton)(e.getSource())).getText();
            if(ch.equalsIgnoreCase("all"))
                ch="";
            filterListener.actionPerformed(new ActionEvent(e.getSource() , 0 , ch.trim()));
        }
    };
           for(int i = 'A' ; i<='Z' ; i++){
               JButton b = new JButton(new Character((char)i).toString());
               b.setActionCommand(" "+new Character((char)i).toString()+" ");
               b.addActionListener(al);
               this.add(b);
               this.addSeparator();
               b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                       BorderFactory.createEmptyBorder(2,5,2,5)));
           }
           JButton b = new JButton("All");
           b.addActionListener(al);
           this.add(b);
    }

    public void setFilterListener(ActionListener filterListener) {
        this.filterListener = filterListener;
    }
    
     
    
    
    
}
