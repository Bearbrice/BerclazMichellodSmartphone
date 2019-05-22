package Contacts;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DeserializeObject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FileInputStream fis;
		try {
			fis = new FileInputStream("serialization/allcontacts.ser");
			
			ObjectInputStream ois = new ObjectInputStream(fis); //permet d'écrire au niveau du flux de sortie
			
			
			ArrayList<ContactData> allcontact = new ArrayList<ContactData>();
			
			allcontact.add((ContactData)ois.readObject());
			allcontact.add((ContactData)ois.readObject());
			allcontact.add((ContactData)ois.readObject());
			allcontact.add((ContactData)ois.readObject());
			
			for(int i=0; i<allcontact.size(); i++) {
				System.out.println(allcontact.get(i));
			}
			
			System.out.println(allcontact.get(0).getPrenom());
			
			/*System.out.println(allcontact.get(0));
			System.out.println(student2);
			System.out.println(student3);*/
			
			/*ContactData contact0 = (ContactData)ois.readObject();
			ContactData contact0 = (ContactData)ois.readObject();
			ContactData contact0 = (ContactData)ois.readObject();*/
			
			/*Student student1 = (Student)ois.readObject();
			Student student2 = (Student)ois.readObject();
			Student student3 = (Student)ois.readObject();
			
			System.out.println(student1);
			System.out.println(student2);
			System.out.println(student3);*/
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
