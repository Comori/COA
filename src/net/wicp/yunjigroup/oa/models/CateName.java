package net.wicp.yunjigroup.oa.models;

public class CateName extends BaseData{

	private static final long serialVersionUID = 1L;
	
	private String name = null;
	
	private boolean isSelected = false;

	public CateName() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		return "CateName [name=" + name + ", isSelected=" + isSelected + "]";
	}
	
	

}
