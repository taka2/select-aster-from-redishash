package select.aster.from.redishash.views;

import java.awt.BorderLayout;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import select.aster.from.redishash.redis.model.RedisHashData;
import select.aster.from.redishash.redis.service.QueryResult;

public class ResultPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel tableModel;
	
	QueryResult queryResult;

	public ResultPanel(MyTabPanel tabPanel) {
		this.tableModel = new DefaultTableModel();
		JTable jTable = new JTable(tableModel);
		jTable.setAutoCreateRowSorter(true);

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(jTable), BorderLayout.CENTER);
	}

	public void updateTableData(QueryResult queryResult) {
		this.queryResult = queryResult;

		Vector<Vector<String>> dataVector = new Vector<>();
		Vector<String> columnIdentifiers = new Vector<>();
		for(String selectField : queryResult.getSelectFields()) {
			columnIdentifiers.add(selectField);
		}

		for (RedisHashData data : queryResult.getQueryResultList()) {
			dataVector.add(getRowData(data.getMap(), columnIdentifiers));
		}

		tableModel.setDataVector(dataVector, columnIdentifiers);
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
	
	QueryResult getQueryResult() {
		return this.queryResult;
	}
}
