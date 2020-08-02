package select.aster.from.redishash.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import select.aster.from.redishash.model.ApplicationConfig;
import select.aster.from.redishash.model.TabConfig;

public class MyTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_QUERY = "select * from ";
	
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
				
				// Right single click
				if(e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
					JPopupMenu popupMenu = new JPopupMenu();
					
					JMenuItem menuItemEditSelectedTabname = new JMenuItem("edit selected tabname");
					menuItemEditSelectedTabname.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String oldTabName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
							String newTabName = JOptionPane.showInputDialog("New tabname", oldTabName);
							
							tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), newTabName);
						}
					});
					
					JMenuItem menuItemDeleteSelectedTab = new JMenuItem("delete selected tab");
					menuItemDeleteSelectedTab.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
						}
					});
										
					popupMenu.add(menuItemEditSelectedTabname);
					popupMenu.add(menuItemDeleteSelectedTab);
					popupMenu.show(tabbedPane, e.getX()+5, e.getY()+5);
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
		
		this.setToolTipText("To add tab left double click");
		
		addTab(getNewTabName(), DEFAULT_QUERY);
	}
	
	public void addTab() {
		addTab(getNewTabName(), DEFAULT_QUERY);
	}
	
	public void addTab(String tabName, String query) {
		MyTabPanel tabPanel = new MyTabPanel(mainFrame, query);
		this.addTab(tabName, tabPanel);
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
		this.tabPanels = new ArrayList<>();

		List<TabConfig> tabConfigs = applicationConfig.getTabs();
		for(TabConfig tabConfig : tabConfigs) {
			addTab(tabConfig.getTabName(), tabConfig.getQuery());
		}
	}
}
