package select.aster.from.redishash.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import select.aster.from.redishash.exception.ApplicationException;
import select.aster.from.redishash.model.ApplicationConfig;
import select.aster.from.redishash.model.TabConfig;
import select.aster.from.redishash.redis.service.RedisService;
import select.aster.from.redishash.util.PropertyUtil;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private MyTabbedPane tabbedPane;
	private RedisService redisService;
	private PropertyUtil propertyUtil;
	private ApplicationConfig applicationConfig;

	public MainFrame() {
		super("select * from redishash");
		
		MainFrame mainFrame = this;
		
		// Set exception handler
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                JOptionPane.showMessageDialog(mainFrame, e.getMessage());
                
                if(e instanceof ApplicationException) {
                	// continue process
                } else {
                	System.exit(1);
                }
            }
        });

		// Initialize service
		propertyUtil = new PropertyUtil();
		redisService = new RedisService(propertyUtil.getRedisConnectionString());
		applicationConfig = new ApplicationConfig();
		
		// Build GUI
		JMenuBar menuBar = createMenuBar(this);
		this.setJMenuBar(menuBar);
		
		this.tabbedPane = new MyTabbedPane(this);
		tabbedPane.addMouseListener(new MouseListener() {
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
		
		tabbedPane.setToolTipText("To add tab left double click, to remove tab select tab and right double click");
		
		this.add(tabbedPane);
		
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private JMenuBar createMenuBar(MainFrame mainFrame) {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("file");
		JMenuItem menuItemFileSave = new JMenuItem("save");
		menuItemFileSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<TabConfig> tabConfigs = new ArrayList<>();
				List<MyTabPanel> tabPanels = tabbedPane.getTabPanels();
				for(MyTabPanel tabPanel : tabPanels) {
					TabConfig tabConfig = new TabConfig();
					tabConfig.setQuery(tabPanel.getQuery());
					tabConfigs.add(tabConfig);
				}
				applicationConfig.setTabs(tabConfigs);
				applicationConfig.save();
			}
		});
		JMenuItem menuItemFileLoad = new JMenuItem("load");
		menuItemFileLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				applicationConfig.load();
				mainFrame.refreshTabs();
			}
		});
		JMenuItem menuItemFileExit = new JMenuItem("exit");
		menuItemFileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuBar.add(menuFile);
		menuFile.add(menuItemFileSave);
		menuFile.add(menuItemFileLoad);
		menuFile.add(menuItemFileExit);
		
		return menuBar;
	}
	
	RedisService getRedisService() {
		return this.redisService;
	}
	
	void refreshTabs() {
		tabbedPane.setTabConfigs(this.applicationConfig);
	}
}

