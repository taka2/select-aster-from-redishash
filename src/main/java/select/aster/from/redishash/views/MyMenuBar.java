package select.aster.from.redishash.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import select.aster.from.redishash.exception.ApplicationException;
import select.aster.from.redishash.model.TabConfig;
import select.aster.from.redishash.redis.model.RedisHashData;

public class MyMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public MyMenuBar(MainFrame mainFrame) {
		JMenu menuFile = new JMenu("file");
		JMenuItem menuItemFileSave = new JMenuItem("save");
		menuItemFileSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<TabConfig> tabConfigs = new ArrayList<>();
				List<MyTabPanel> tabPanels = mainFrame.getTabbedPane().getTabPanels();
				for(int i=0; i<tabPanels.size(); i++) {
					TabConfig tabConfig = new TabConfig();
					tabConfig.setTabName(mainFrame.getTabbedPane().getTitleAt(i));
					tabConfig.setQuery(tabPanels.get(i).getQuery());
					tabConfigs.add(tabConfig);
				}
				mainFrame.getApplicationConfig().setTabs(tabConfigs);
				mainFrame.getApplicationConfig().save();
			}
		});
		JMenuItem menuItemFileLoad = new JMenuItem("load");
		menuItemFileLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.getApplicationConfig().load();
				mainFrame.refreshTabs();
			}
		});
		JMenuItem menuItemFileExportCsv = new JMenuItem("export-csv");
		menuItemFileExportCsv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyTabPanel tabPanel = mainFrame.getTabbedPane().getTabPanels().get(mainFrame.getTabbedPane().getSelectedIndex());
				String tabName = mainFrame.getTabbedPane().getTitleAt(mainFrame.getTabbedPane().getSelectedIndex());
				List<RedisHashData> redisHashDataList = tabPanel.getResultPanel().getRedishashDataList();
				
				if(redisHashDataList.size() == 0) {
					throw new ApplicationException("No data.");
				}

				try (PrintWriter pw = new PrintWriter(new FileWriter("export_" + tabName + ".csv"))) {
					pw.println(redisHashDataList.get(0).toHeaderCsvString());
					for(RedisHashData redisHashData : redisHashDataList) {
						pw.println(redisHashData.toCsvString());
					}
				} catch(IOException ex) {
					throw new ApplicationException("Cannot export file." + ex.getMessage());
				}
			}
		});
		JMenuItem menuItemFileExit = new JMenuItem("exit");
		menuItemFileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuFile.add(menuItemFileSave);
		menuFile.add(menuItemFileLoad);
		menuFile.add(menuItemFileExportCsv);
		menuFile.add(menuItemFileExit);
		add(menuFile);
	}
}
