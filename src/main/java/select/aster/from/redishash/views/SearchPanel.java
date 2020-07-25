package select.aster.from.redishash.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField textQuery;
	
	public SearchPanel(MyTabPanel tabPanel, String query) {
		JLabel labelQuery = new JLabel("query:");
		labelQuery.setToolTipText("from [hashkey] where field1=value1");
		this.textQuery = new JTextField(50);
		textQuery.setText(query);
		textQuery.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					// Press enter to search
					tabPanel.searchButtonClicked(textQuery.getText());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// Do nothing
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// Do nothing
			}
		});
		JButton buttonQuery = new JButton("Query");
		buttonQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabPanel.searchButtonClicked(textQuery.getText());
			}
		});
		
		this.add(labelQuery);
		this.add(textQuery);
		this.add(buttonQuery);
	}
	
	String getQuery() {
		return this.textQuery.getText();
	}
}
