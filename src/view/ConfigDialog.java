package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.ConfigBag;
import tool.ContralUtil;

import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class ConfigDialog extends JDialog implements ActionListener {
	/**
	 * 鬼知道是什么，这是配置界面
	 */
	private static final long serialVersionUID = -1748804611563472326L;
	private final JPanel contentPanel = new JPanel();
	private JFrame frame;
	private JButton okButton;
	private JButton cancelButton;
	private JRadioButton allSelect;
	private JRadioButton onlySelect;
	private JCheckBox checkBox;
	private JTextField qqText;
	private JTextField savePathText;
	private JButton pathSelectButton;
	
	public ConfigDialog(JFrame owner) {
		frame = owner;
		initConfigDialogView();
		setConfig();
	}

	private void initConfigDialogView() {
		setTitle("配置");
		setBounds(frame.getX()+100, frame.getY()+100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//　底部按钮
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("保存");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("取消");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		
		//　中间部分
		{
			JPanel centerPane = new JPanel();
			centerPane.setLayout(new GridLayout(4,1));
			getContentPane().add(centerPane, BorderLayout.CENTER);
			JLabel qqNumber = new JLabel("  请输入QQ号码：");
			JLabel savePathLabel = new JLabel("保存位置：");
			qqText = new JTextField(20);
			savePathText = new JTextField(20);
			checkBox = new JCheckBox("保存号码");
			allSelect = new JRadioButton("监控所有帐号");
			onlySelect = new JRadioButton("仅监控当前帐号");
			pathSelectButton = new JButton("选择");
			ButtonGroup btGroup = new ButtonGroup();
			JPanel secondLine = new JPanel();
			JPanel thirdLine = new JPanel();
			JPanel fourthLine = new JPanel();
			
			checkBox.addActionListener(this);
			allSelect.addActionListener(this);
			onlySelect.addActionListener(this);
			pathSelectButton.addActionListener(this);
			savePathText.setEditable(false);
			savePathText.setBackground(Color.WHITE);
			qqNumber.setFont(MyFont.getBigFont());
			qqText.setFont(MyFont.getBigFont());
			checkBox.setFont(MyFont.getSmallFont());
			allSelect.setFont(MyFont.getSmallFont());
			onlySelect.setFont(MyFont.getSmallFont());
			secondLine.add(qqText);
			secondLine.add(checkBox);
			btGroup.add(allSelect);
			btGroup.add(onlySelect);
			thirdLine.add(allSelect);
			thirdLine.add(onlySelect);
			fourthLine.add(savePathLabel);
			fourthLine.add(savePathText);
			fourthLine.add(pathSelectButton);
			centerPane.add(qqNumber);
			centerPane.add(secondLine);
			centerPane.add(thirdLine);
			centerPane.add(fourthLine);
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private void setConfig() {
		qqText.setText(ConfigBag.getQQNumber());
		checkBox.setSelected(ConfigBag.isRememberNum());
		if(ConfigBag.isSelectAll()) {
			allSelect.setSelected(true);
		} else {
			onlySelect.setSelected(true);
		}
		savePathText.setText(ConfigBag.getSavePath());
	}

	private void showPathSelectDialog() {
		String filePath = savePathText.getText();
		if(filePath.equals(""))
			filePath = "D://";
		JFileChooser fileChooser = new JFileChooser(filePath);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getAbsolutePath();
			savePathText.setText(filePath);
		}
	}
	
	public String getSavePath() {
		return savePathText.getText();
	}
	
	public String getQQNumber() {
		String s = qqText.getText();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			if(c>='0' && c<='9')
				sb.append(c);
		}
		return sb.toString();
	}
	
	public boolean isSelectAll() {
		return allSelect.isSelected();
	}
	
	public boolean isRememberNum() {
		return checkBox.isSelected();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==okButton) {
			System.out.println("okBtton is clicked!");
			ContralUtil.saveConfig();
			dispose();
		} else if(e.getSource()==cancelButton) {
			System.out.println("cancelButton is clicked!");
			dispose();
		} else if(e.getSource()==allSelect) {
			System.out.println("allSelect is clicked!");
		} else if(e.getSource()==onlySelect) {
			System.out.println("onlySelect is clicked!");
		} else if(e.getSource()==checkBox) {
			System.out.println("checkBox is clicked!");
		} else if(e.getSource()==pathSelectButton) {
			System.out.println("pathSelectButton is clicked!");
			showPathSelectDialog();
		}
	}

}
