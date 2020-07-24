package select.aster.from.redishash.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public SearchPanel(MainFrame mainFrame) {
		JLabel labelQuery = new JLabel("query:");
		labelQuery.setToolTipText("from [hashkey] where field1=value1");
		JTextField textQuery = new JTextField(50);
		JButton buttonQuery = new JButton("Query");
		buttonQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.searchButtonClicked(textQuery.getText());
			}
		});
		
		this.add(labelQuery);
		this.add(textQuery);
		this.add(buttonQuery);
	}
}
