package select.aster.from.redishash.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import select.aster.from.redishash.model.ApplicationConfig;
import select.aster.from.redishash.model.TabConfig;

public class MyTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_QUERY = "from ";
	
	private List<MyTabPanel> tabPanels;
	
	private MainFrame mainFrame;
		
	public MyTabbedPane(MainFrame mainFrame) {
		super();

		this.tabPanels = new ArrayList<>();
		this.mainFrame = mainFrame;

		addTab(DEFAULT_QUERY);
	}
	
	public void addTab() {
		addTab(DEFAULT_QUERY);
	}
	
	public void addTab(String query) {
		MyTabPanel tabPanel = new MyTabPanel(mainFrame, query);
		this.addTab(getNewTabName(), tabPanel);
		this.tabPanels.add(tabPanel);
	}
	
	public void removeTab(int index) {
		this.remove(index);
		this.tabPanels.remove(index);
	}
	
	private String getNewTabName() {
		return "tab" + (this.tabPanels.size() + 1);
	}
	
	List<MyTabPanel> getTabPanels() {
		return this.tabPanels;
	}
	
	void setTabConfigs(ApplicationConfig applicationConfig) {
		this.removeAll();

		List<TabConfig> tabConfigs = applicationConfig.getTabs();
		for(TabConfig tabConfig : tabConfigs) {
			addTab(tabConfig.getQuery());
		}
	}
}
