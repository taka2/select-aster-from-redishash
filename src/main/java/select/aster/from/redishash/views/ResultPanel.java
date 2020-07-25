package select.aster.from.redishash.views;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import select.aster.from.redishash.model.RedisHashData;

public class ResultPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel tableModel;

	public ResultPanel(MyTabPanel tabPanel) {
		this.tableModel = new DefaultTableModel();
		JTable jTable = new JTable(tableModel);

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(jTable), BorderLayout.CENTER);
	}

	public void updateTableData(List<RedisHashData> redishashDataList) {
		Vector<Vector<String>> dataVector = new Vector<>();
		Vector<String> columnIdentifiers = new Vector<>();

		boolean firstRowFlag = true;
		for (RedisHashData data : redishashDataList) {
			Vector<String> row = new Vector<>();
			for(Map.Entry<String, String> entry : data.getMap().entrySet()) {
				if(firstRowFlag) {
					columnIdentifiers.add(entry.getKey());
				}
				row.add(entry.getValue());
			}
			firstRowFlag = false;
			dataVector.add(row);
		}

		tableModel.setDataVector(dataVector, columnIdentifiers);
	}
}
