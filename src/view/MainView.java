package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import model.ConfigBag;
import model.ImageBag;
import tool.ContralUtil;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainView implements ActionListener, ItemListener, MouseListener {

	private JFrame frame;
	private JMenuItem config;
	private JMenuItem decode;
	private JMenuItem about;
	private JMenuItem encrypt;
	private JButton refreshButton;
	private String[] comboBoxData = {"全选", "C2C", "Group"};
	private JComboBox<String> comboBox;
	private JTable table;
	private ArrayList<ImageBag> tableData;
	private JTextField imageNameText;
	private JTextField createDateText;
	private JTextField isWithdrawalText;
	private MyTableModel dataModel;
	private JLabel imageView;
	private JToggleButton startOrStopButton;
	private ConfigDialog cd;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainView window = new MainView();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public MainView() {
		initialize();
		frame.setVisible(true);
	}

	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame("QQ图片防撤回") {
			@Override
			public void dispose() {
//				System.out.println("程序关闭");
				ContralUtil.stopImageHandle();
				ContralUtil.endProgram();
				super.dispose();
			}
		};
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/image/QQicon.png")));
		frame.setBounds(100, 100, 740, 480);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		initMenu();
		initMainInterface();
	}

	private void initMainInterface() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel titleLine = new JPanel();
		JPanel listAndImagePanel = new JPanel();
		JPanel tableAndTitlePanel_left = new JPanel(new BorderLayout());
		JPanel imageAndTextPanel_right = new JPanel(new BorderLayout());
		JPanel textPanelInRight = new JPanel(new GridLayout(3,1));
		JPanel firstLine = new JPanel();
		JPanel secondLine = new JPanel();
		JPanel thirdLine = new JPanel();
		JPanel imageLine = new JPanel();
		JLabel fileSelectLabel = new JLabel("请选择要查看的文件夹：");
		JLabel selectImageLabel = new JLabel("请选择缓存图片");
		JLabel imageNameLabel = new JLabel("图片名称：");
		JLabel createDateLabel = new JLabel("创建时间：");
		JLabel isWithdrawalLabel = new JLabel("是否撤回：");
		imageView = new JLabel();
		refreshButton = new JButton("刷新");
		startOrStopButton = new JToggleButton("开始");
		imageNameText = new JTextField(25);
		createDateText = new JTextField(25);
		isWithdrawalText = new JTextField(25);
		comboBox = new JComboBox<String>();
		table = new JTable();
		JScrollPane jsp = new JScrollPane();
		
		initComboBox();
		initTable();
		
		imageView.setIcon(new ImageIcon(MainView.class.getResource("/image/119852-106.jpg")));
		
		imageNameLabel.setFont(MyFont.getBigFont());
		createDateLabel.setFont(MyFont.getBigFont());
		isWithdrawalLabel.setFont(MyFont.getBigFont());
		imageNameText.setFont(MyFont.getBigFont());
		createDateText.setFont(MyFont.getBigFont());
		isWithdrawalText.setFont(MyFont.getBigFont());
		refreshButton.setFont(MyFont.getBigFont());
		startOrStopButton.setFont(MyFont.getBigFont());
		selectImageLabel.setFont(MyFont.getBigFont());
		fileSelectLabel.setFont(MyFont.getBigFont());
		startOrStopButton.addActionListener(this);
		refreshButton.addActionListener(this);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		imageNameText.setEditable(false);
		createDateText.setEditable(false);
		isWithdrawalText.setEditable(false);
		imageNameText.setBackground(Color.WHITE);
		createDateText.setBackground(Color.WHITE);
		isWithdrawalText.setBackground(Color.WHITE);
		
		GridBagLayout gbl_listAndImagePanel = new GridBagLayout();
		int totleWidth = 359*2;
		int firstWidth = 220;
		gbl_listAndImagePanel.columnWidths = new int[]{firstWidth, totleWidth-firstWidth, 0};
		gbl_listAndImagePanel.rowHeights = new int[]{348, 0};
		gbl_listAndImagePanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_listAndImagePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		listAndImagePanel.setLayout(gbl_listAndImagePanel);
		
		GridBagConstraints gbc_jsp = new GridBagConstraints();
		gbc_jsp.fill = GridBagConstraints.BOTH;
		gbc_jsp.insets = new Insets(0, 0, 0, 5);
		gbc_jsp.gridx = 0;
		gbc_jsp.gridy = 0;
		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 1;
		gbc_table.gridy = 0;
		
		firstLine.add(imageNameLabel);
		firstLine.add(imageNameText);
		secondLine.add(createDateLabel);
		secondLine.add(createDateText);
		thirdLine.add(isWithdrawalLabel);
		thirdLine.add(isWithdrawalText);
		imageLine.add(imageView);
		textPanelInRight.add(firstLine);
		textPanelInRight.add(secondLine);
		textPanelInRight.add(thirdLine);
		imageAndTextPanel_right.add(textPanelInRight, BorderLayout.NORTH);
		imageAndTextPanel_right.add(imageLine);
		jsp.setViewportView(table);
		titleLine.add(fileSelectLabel);
		titleLine.add(comboBox);
		titleLine.add(refreshButton);
		titleLine.add(startOrStopButton);
		tableAndTitlePanel_left.add(jsp, BorderLayout.CENTER);
		tableAndTitlePanel_left.add(selectImageLabel, BorderLayout.NORTH);
		listAndImagePanel.add(tableAndTitlePanel_left, gbc_jsp);
		listAndImagePanel.add(imageAndTextPanel_right, gbc_table);
		mainPanel.add(titleLine, BorderLayout.NORTH);
		mainPanel.add(listAndImagePanel, BorderLayout.CENTER);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	private void initTable() {
		tableData = new ArrayList<ImageBag>();
		dataModel = new MyTableModel(tableData);
		table.setModel(dataModel);
		table.setFont(MyFont.getBigFont());
		table.setRowHeight(30);
		table.addMouseListener(this);
	}
	
	public void refreshTableData() {
		tableData = ContralUtil.getImageFilesData((String) comboBox.getSelectedItem());
		if(tableData==null)
			return;
		dataModel = new MyTableModel(tableData);
		table.setModel(dataModel);
		table.clearSelection();
		table.updateUI();
	}

	private void initComboBox() {
		for(int i=0; i<comboBoxData.length; i++)
			comboBox.addItem(comboBoxData[i]);
		comboBox.setFont(MyFont.getBigFont());
		comboBox.addItemListener(this);
	}

	private void initMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu firstMenu = new JMenu("设置");
		JMenu secondMenu = new JMenu("特权");
		JMenu thirdMenu = new JMenu("帮助");
		firstMenu.setFont(MyFont.getBigFont());
		secondMenu.setFont(MyFont.getBigFont());
		thirdMenu.setFont(MyFont.getBigFont());
		config = new JMenuItem("配置");
		decode = new JMenuItem("解锁");
		encrypt = new JMenuItem("加密");
		about = new JMenuItem("关于");
		encrypt.setIcon(new ImageIcon(MainView.class.getResource("/image/lock.png")));
		decode.setIcon(new ImageIcon(MainView.class.getResource("/image/unlock.png")));
		about.setIcon(new ImageIcon(MainView.class.getResource("/image/about.png")));
		config.setIcon(new ImageIcon(MainView.class.getResource("/image/wrench.png")));
		config.setFont(MyFont.getBigFont());
		decode.setFont(MyFont.getBigFont());
		encrypt.setFont(MyFont.getBigFont());
		about.setFont(MyFont.getBigFont());
		encrypt.addActionListener(this);
		config.addActionListener(this);
		decode.addActionListener(this);
		about.addActionListener(this);
		firstMenu.add(config);
		secondMenu.add(encrypt);
		secondMenu.add(decode);
		thirdMenu.add(about);
		menubar.add(firstMenu);
		menubar.add(secondMenu);
		menubar.add(thirdMenu);
		frame.setJMenuBar(menubar);
	}

	private void showConfigDialog() {
		if(cd==null) {
//			System.out.println("ConfigDialog is null!");
			cd = new ConfigDialog(frame);
		} else {
//			System.out.println("ConfigDialog is not null!");
			cd.dispose();
			cd = new ConfigDialog(frame);
		}
		
	}

	private void showPasswordDialog() {
		JPasswordField pwd = new JPasswordField();
		Object[] message = { "请输入密码:", pwd };
		JOptionPane.showConfirmDialog(frame, message, "解锁", JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE);
		System.out.println(pwd.getPassword());
	}

	private void showAboutUsDialog() {
		JPanel panel = new JPanel(new GridLayout(4, 1));
		JLabel label1 = new JLabel("愿你出走半生，归来仍是少年。");
		JLabel label2 = new JLabel("Version:1.1"); // 2017.8.17 updateVersion
		JLabel label3 = new JLabel("Copyright 2017 by Jiacy.");
		JLabel label4 = new JLabel("All Rights Reserved.");
		label1.setFont(MyFont.getBigFont());
		label2.setFont(MyFont.getSmallFont());
		label3.setFont(MyFont.getSmallFont());
		label4.setFont(MyFont.getSmallFont());
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.add(label4);
		JOptionPane.showMessageDialog(frame, panel, "About Us", JOptionPane.PLAIN_MESSAGE);
	}
	
	public ConfigDialog getConfigDialog() {
		if(cd==null) 
			return null;
		else
			return cd;
	}
	
	@Override
	public void itemStateChanged(ItemEvent i) {
//		System.out.println("source:\t" + i.getSource());
//		System.out.println("item:\t" + i.getItem());
//		System.out.println("ID:\t" + i.getID());
//		System.out.println("StateChange:" + i.getStateChange());
		if(i.getStateChange()==1) {
			refreshTableData();
			if(i.getItem()==comboBoxData[0]) {
				System.out.println("AllSelect is clicked!");
			} else if(i.getItem()==comboBoxData[1]) {
				System.out.println("C2C is clicked!");
			} else if(i.getItem()==comboBoxData[2]) {
				System.out.println("Group is clicked!");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == config) {
			System.out.println("config is clicked!");
			showConfigDialog();
			ContralUtil.setConfigDialog();
		} else if(e.getSource() == decode) {
			System.out.println("decode is clicked!");
			showPasswordDialog();
		} else if(e.getSource() == about) {
			System.out.println("about is clicked!");
			showAboutUsDialog();
		} else if(e.getSource() == encrypt) {
			System.out.println("encrypt is clicked!");
		} else if(e.getSource()==startOrStopButton) {
			System.out.println("startOrStopButton is clicked!");
			if(startOrStopButton.isSelected()) {
				startOrStopButton.setText("停止");
				ContralUtil.startImageHandle();
			} else {
				startOrStopButton.setText("开始");
				ContralUtil.stopImageHandle();
			}
		} else if(e.getSource()==refreshButton) {
			refreshTableData();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==table) {
			System.out.println("table is clicked!");
			ImageBag imageBag = dataModel.getSelectItem(table.getSelectedRow());
//			System.out.println(imageBag.getImageName());
//			System.out.println(imageBag.getImagePath());
			if(imageBag==null) {
				String tip = "图片未撤回，不再显示于列表";
				imageNameText.setText(tip);
				createDateText.setText(tip);
				isWithdrawalText.setText(tip);
			} else {
				imageNameText.setText(dataModel.getValueAt(table.getSelectedRow(), 0));
				createDateText.setText(dataModel.getValueAt(table.getSelectedRow(), 1));
				isWithdrawalText.setText(dataModel.getValueAt(table.getSelectedRow(), 2));
				ContralUtil.decrypImageFile(imageBag.getImagePath());
				Image image = Toolkit.getDefaultToolkit().createImage(ConfigBag.getSavePath()+"//showPic.jpg");
				imageView.setIcon(new ImageIcon(image));
				System.gc();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	public void showNewFileTip() {
		String tip = "有新截获的图片！";
		imageNameText.setText(tip);
		createDateText.setText(tip);
		isWithdrawalText.setText(tip);
	}

}
