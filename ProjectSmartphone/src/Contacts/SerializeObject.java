package Contacts;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class SerializeObject {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<ContactData> allcontact = new ArrayList<ContactData>();
		
		allcontact.add(new ContactData(0, "brice", "berclaz", "0798668993", "0274559604", "Ch. des Anémones 6", "HES-SO Valais-Wallis"));
		allcontact.add(new ContactData(1, "brice2", "berclaz2", "0798668993", "0274559604", "Ch. des Anémones 6", "HES-SO Valais-Wallis"));
		//allcontact.add(new ContactData(2, "brice3", "berclaz3", "0798668993", "0274559604", "Ch. des Anémones 6", "HES-SO Valais-Wallis"));
		
		int id = 0;
		
		/*allcontact.get(0).setPrenom("Jean-mich");
		allcontact.get(0).setNom("Jean-mich");
		allcontact.get(0).setAdresse("Jean-mich");
		allcontact.get(0).setOrganisation("Jean-mich");
		allcontact.get(0).setTelephoneFixe("Jean-mich");*/
		
		/*ContactData contacts[]=new ContactData[10];
		
		int id=0;
		
		contacts[0]=new ContactData("brice", "berclaz", "0798668993", "0274559604", "Ch. des Anémones 6", "HES-SO Valais-Wallis");
		contacts[1]=new ContactData("brice2", "berclaz2", "0798668993", "0274559604", "Ch. des Anémones 6", "HES-SO Valais-Wallis");
		contacts[2]=new ContactData("brice3", "berclaz3", "0798668993", "0274559604", "Ch. des Anémones 6", "HES-SO Valais-Wallis");*/
		
		/*ContactData brice = new ContactData(1, "Brice Berclaz");
		ContactData pierro = new ContactData(2, "Pierro Rodriguez");
		ContactData jm = new ContactData(6, "Jean-Michel Zufferey");*/
		
		for(int i=0; i<allcontact.size(); i++) {
			System.out.println(allcontact.get(i));
		}
		
		/*System.out.println(pierro);
		System.out.println(jm);*/
		
		
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("serialization/allcontacts.ser");
				
				ObjectOutputStream oos = new ObjectOutputStream(fos); //permet d'écrire au niveau du flux de sortie
				
				//for(int i=0; i<allcontact.size();i++) {
					oos.writeObject(allcontact.get(1));
				//}
				
				/*for(int i=0; i<contacts.length;i++) {
					oos.writeObject(contacts[i]);
				}*/
				
				/*oos.writeObject(brice);
				oos.writeObject(pierro);
				oos.writeObject(jm);*/
				
				oos.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void readID() {
		//allcontact.size();
	}
	
}
