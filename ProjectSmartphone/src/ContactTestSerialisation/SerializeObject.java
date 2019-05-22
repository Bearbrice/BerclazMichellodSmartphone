package ContactTestSerialisation;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SerializeObject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContactData contacts[]=new ContactData[10];
		
		contacts[0]=new ContactData(0, "Brice Berclaz");
		contacts[1]=new ContactData(1, "Pieror Rodriguez");
		contacts[2]=new ContactData(2, "Jean-Michel Zufferey");
		
		/*ContactData brice = new ContactData(1, "Brice Berclaz");
		ContactData pierro = new ContactData(2, "Pierro Rodriguez");
		ContactData jm = new ContactData(6, "Jean-Michel Zufferey");*/
		
		System.out.println(contacts[0]);
		/*System.out.println(pierro);
		System.out.println(jm);*/
		
		
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("contacts.ser");
				
				ObjectOutputStream oos = new ObjectOutputStream(fos); //permet d'écrire au niveau du flux de sortie
				
				for(int i=0; i<contacts.length;i++) {
					oos.writeObject(contacts[i]);
				}
				
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
	
}
