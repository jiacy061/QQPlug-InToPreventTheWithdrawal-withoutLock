package view;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import model.ImageBag;

public class MyTableModel implements TableModel {
	private String[] columnName = {"图片名", "创建日期", "是否已撤回"};
	private final int columnNum = 3;
	private ArrayList<ImageBag> listImage;
	
	public MyTableModel(ArrayList<ImageBag> listImage) {
		this.listImage = listImage;
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return ImageBag.class;
	}

	@Override
	public int getColumnCount() {
		return columnNum;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnName[columnIndex];
	}

	@Override
	public int getRowCount() {
		return listImage.size();
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex==-1)
			return null;
		
		ImageBag imageBag = listImage.get(rowIndex);
		if(columnIndex==0) {
			return imageBag.getImageName();
		} else if(columnIndex==1) {
			return ""+imageBag.getDate();
		} else if(columnIndex==2) {
			return imageBag.isWithdrawal()?"是":"否";
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
	}
	
	public ImageBag getSelectItem(int index) {
		if(index==-1) {
			return null;
		} else {
			return listImage.get(index);
		}
	}

}
