package select.aster.from.redishash.views;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import select.aster.from.redishash.service.RedisService;
import select.aster.from.redishash.util.PropertyUtil;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private MyTabbedPane tabbedPane;
	private RedisService redisService;
	private PropertyUtil propertyUtil;

	public MainFrame() {
		super("select * from redishash");
		
		// ���W�b�N�̏�����
		propertyUtil = new PropertyUtil();
		redisService = new RedisService(propertyUtil.getRedisConnectionString());
		
		// GUI�̍\�z
		this.tabbedPane = new MyTabbedPane(this);
		tabbedPane.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// �^�u�����_�u���N���b�N���ꂽ��^�u��ǉ�
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					tabbedPane.addTab();
				}
				
				// �E�_�u���N���b�N���ꂽ��A�I�𒆂̃^�u���폜
				if(e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 2) {
					tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Do nothing.
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// Do nothing.
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Do nothing.
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Do nothing.
			}
			
		});
		
		tabbedPane.setToolTipText("To add tab left double click, to remove tab select tab and right double click");
		
		this.add(tabbedPane);
		
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	RedisService getRedisService() {
		return this.redisService;
	}
}

