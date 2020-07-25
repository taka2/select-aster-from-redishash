package select.aster.from.redishash.views;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import select.aster.from.redishash.model.RedisHashData;
import select.aster.from.redishash.service.RedisService;

public class MyTabPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private SearchPanel searchPanel;
	private ResultPanel resultPanel;
	
	private RedisService redisService;
	
	public MyTabPanel(MainFrame mainFrame) {
		super();
		
		this.redisService = mainFrame.getRedisService();

		this.searchPanel = new SearchPanel(this);
		this.resultPanel = new ResultPanel(this);
		
		this.setLayout(new BorderLayout());
		this.add(this.searchPanel, BorderLayout.NORTH);
		this.add(this.resultPanel, BorderLayout.CENTER);
	}
	
	public void searchButtonClicked(String query) {
		List<RedisHashData> redishashDataList = redisService.query(query);
		resultPanel.updateTableData(redishashDataList);
	}
}
