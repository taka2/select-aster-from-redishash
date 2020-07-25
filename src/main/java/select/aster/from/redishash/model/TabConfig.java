package select.aster.from.redishash.model;

import java.io.Serializable;

public class TabConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String tabName;
	private String query;

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
