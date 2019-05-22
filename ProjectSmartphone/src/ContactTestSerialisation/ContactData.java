package ContactTestSerialisation;

import java.io.Serializable;

//LES TEACHERS DU NET
//COURS 86 SERIALISATION

public class ContactData implements Serializable {
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 201905011847L;
	
	
	private int id;
	private String name;
	
	public ContactData(int id, String name) {
		this.id=id;
		this.name=name;
	}
	
	@Override
	public String toString() {
		return String.format("Student ~ [id: #%d, name: %s]", id, name);
	}
}

