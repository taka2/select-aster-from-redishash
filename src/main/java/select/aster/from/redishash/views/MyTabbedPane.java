package select.aster.from.redishash.views;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import select.aster.from.redishash.model.ApplicationConfig;
import select.aster.from.redishash.model.TabConfig;

public class MyTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_QUERY = "from ";
	
	private MainFrame mainFrame;
	
	private List<MyTabPanel> tabPanels;
		
	public MyTabbedPane(MainFrame mainFrame) {
		super();
		
		this.mainFrame = mainFrame;
		this.tabPanels = new ArrayList<>();
		
		MyTabbedPane tabbedPane = this;

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Add tab when left double clicked
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					tabbedPane.addTab();
				}
				
				// Remove tab when right double clicked
				if(e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 2) {
					tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Do nothing
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// Do nothing
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Do nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Do nothing
			}
		});
		
		this.setToolTipText("To add tab left double click, to remove tab select tab and right double click");
		
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
