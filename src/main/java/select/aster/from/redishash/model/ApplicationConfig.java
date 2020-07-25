package select.aster.from.redishash.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import select.aster.from.redishash.exception.ApplicationException;

public class ApplicationConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String CONFIG_PATH = "./safr-config.ser";

	List<TabConfig> tabs;

	public List<TabConfig> getTabs() {
		return tabs;
	}

	public void setTabs(List<TabConfig> tabs) {
		this.tabs = tabs;
	}

	public void save() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_PATH))) {
			oos.writeObject(this);
		} catch(IOException e) {
			throw new ApplicationException("Cannot save. " + CONFIG_PATH);
		}
	}
	
	public void load() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG_PATH))) {
			ApplicationConfig applicationConfig = (ApplicationConfig)ois.readObject();
			this.tabs = applicationConfig.getTabs();
		} catch(IOException e) {
			throw new ApplicationException("Cannot load. " + CONFIG_PATH);
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
