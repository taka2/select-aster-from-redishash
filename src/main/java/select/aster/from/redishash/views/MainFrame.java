package select.aster.from.redishash.views;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import select.aster.from.redishash.exception.ApplicationException;
import select.aster.from.redishash.model.ApplicationConfig;
import select.aster.from.redishash.redis.service.RedisService;
import select.aster.from.redishash.util.PropertyUtil;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private MyTabbedPane tabbedPane;
	private RedisService redisService;
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

		// Initialize services
        PropertyUtil propertyUtil = new PropertyUtil();
		redisService = new RedisService(propertyUtil.getRedisConnectionString());
		applicationConfig = new ApplicationConfig();
		
		// Build GUI
		MyMenuBar menuBar = new MyMenuBar(this);
		this.setJMenuBar(menuBar);
		
		this.tabbedPane = new MyTabbedPane(this);
		this.add(tabbedPane);
		
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	RedisService getRedisService() {
		return this.redisService;
	}
	
	ApplicationConfig getApplicationConfig() {
		return this.applicationConfig;
	}
	
	MyTabbedPane getTabbedPane() {
		return this.tabbedPane;
	}
	
	void refreshTabs() {
		tabbedPane.setTabConfigs(this.applicationConfig);
	}
}

