package select.aster.from.redishash.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import select.aster.from.redishash.exception.ApplicationException;
import select.aster.from.redishash.redis.service.RedisService;
import select.aster.from.redishash.util.PropertyUtil;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private MyTabbedPane tabbedPane;
	private RedisService redisService;
	private PropertyUtil propertyUtil;

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
		
		// Build GUI
		JMenuBar menuBar = createMenuBar();
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

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("file");
		JMenuItem menuItemFileExit = new JMenuItem("exit");
		menuItemFileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuBar.add(menuFile);
		menuFile.add(menuItemFileExit);
		
		return menuBar;
	}
	
	RedisService getRedisService() {
		return this.redisService;
	}
}

