package select.aster.from.redishash.views;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;

import select.aster.from.redishash.model.RedisHashData;
import select.aster.from.redishash.service.RedisService;
import select.aster.from.redishash.util.PropertyUtil;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private SearchPanel searchPanel;
	private ResultPanel resultPanel;
	private RedisService redisService;
	private PropertyUtil propertyUtil;

	public MainFrame() {
		super("select * from redishash");
		
		// ロジックの初期化
		propertyUtil = new PropertyUtil();
		redisService = new RedisService(propertyUtil.getRedisConnectionString());
		
		// GUIの構築
		this.searchPanel = new SearchPanel(this);
		this.resultPanel = new ResultPanel(this);
		
		this.setLayout(new BorderLayout());
		this.add(this.searchPanel, BorderLayout.NORTH);
		this.add(this.resultPanel, BorderLayout.CENTER);
		
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void searchButtonClicked(String query) {
		List<RedisHashData> redishashDataList = redisService.query(query);
		resultPanel.updateTableData(redishashDataList);
	}
}

