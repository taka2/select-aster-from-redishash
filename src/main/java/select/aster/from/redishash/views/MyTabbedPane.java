package select.aster.from.redishash.views;

import javax.swing.JTabbedPane;

public class MyTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	private int tabCount;
	
	private MainFrame mainFrame;
	
	public MyTabbedPane(MainFrame mainFrame) {
		super();
		
		this.mainFrame = mainFrame;

		// TODO �Ƃ肠����1�^�u�ǉ��B���ƂŃt�@�C�����畜������悤�ɂ���B
		MyTabPanel tabPanel = new MyTabPanel(mainFrame);
		this.addTab("tab1", tabPanel);
		
		tabCount = 1;
	}
	
	public void addTab() {
		tabCount++;
		MyTabPanel tabPanel = new MyTabPanel(mainFrame);
		this.addTab("tab" + tabCount, tabPanel);
	}
	
	public void removeTab(int index) {
		this.remove(index);
	}
}
