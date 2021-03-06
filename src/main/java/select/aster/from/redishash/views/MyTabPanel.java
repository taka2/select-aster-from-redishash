package select.aster.from.redishash.views;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import select.aster.from.redishash.redis.service.QueryResult;
import select.aster.from.redishash.redis.service.RedisService;

public class MyTabPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private SearchPanel searchPanel;
	private ResultPanel resultPanel;
	
	private RedisService redisService;
	
	public MyTabPanel(MainFrame mainFrame, String query) {
		super();
		
		this.redisService = mainFrame.getRedisService();

		this.searchPanel = new SearchPanel(this, query);
		this.resultPanel = new ResultPanel(this);
		
		this.setLayout(new BorderLayout());
		this.add(this.searchPanel, BorderLayout.NORTH);
		this.add(this.resultPanel, BorderLayout.CENTER);
	}
	
	public void searchButtonClicked(String query) {
		QueryResult queryResult = redisService.query(query);
		resultPanel.updateTableData(queryResult);
		if(queryResult.isQueryResultCountExceeded()) {
			JOptionPane.showMessageDialog(this, "Query result count exceeded limit. limit = "
					+ queryResult.getQueryResultLimitCount() + ", keysCount = " + queryResult.getKeysCount());
		}
	}
	
	ResultPanel getResultPanel() {
		return this.resultPanel;
	}
	
	String getQuery() {
		return this.searchPanel.getQuery();
	}
}
