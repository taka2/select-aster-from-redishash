package select.aster.from.redishash.views;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import select.aster.from.redishash.redis.model.RedisHashData;

public class ResultPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel tableModel;
	
	List<RedisHashData> redishashDataList;

	public ResultPanel(MyTabPanel tabPanel) {
		redishashDataList = new ArrayList<>();

		this.tableModel = new DefaultTableModel();
		JTable jTable = new JTable(tableModel);
		jTable.setAutoCreateRowSorter(true);

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(jTable), BorderLayout.CENTER);
	}

	public void updateTableData(List<RedisHashData> redishashDataList) {
		this.redishashDataList = redishashDataList;

		Vector<Vector<String>> dataVector = new Vector<>();
		Vector<String> columnIdentifiers = null;

		boolean firstRowFlag = true;
		for (RedisHashData data : redishashDataList) {
			if(firstRowFlag) {
				columnIdentifiers = getColumnIdentifiers(data.getMap());
				firstRowFlag = false;
			}
			dataVector.add(getRowData(data.getMap(), columnIdentifiers));
		}

		tableModel.setDataVector(dataVector, columnIdentifiers);
	}
	
	private Vector<String> getColumnIdentifiers(Map<String, String> row) {
		Vector<String> columnIdentifiers = new Vector<>();
		for(Map.Entry<String, String> entry : row.entrySet()) {
			columnIdentifiers.add(entry.getKey());
		}
		
		return columnIdentifiers;
	}
	
	private Vector<String> getRowData(Map<String, String> row, Vector<String> columnIdentifiers) {
		Vector<String> result = new Vector<>();
		for(String columnIdentifier : columnIdentifiers) {
			String value = row.get(columnIdentifier);
			if(value == null) {
				// add blank if value is nil
				result.add("");
			} else {
				result.add(value);
			}
		}
		
		return result;
	}
	
	List<RedisHashData> getRedishashDataList() {
		return this.redishashDataList;
	}
}
