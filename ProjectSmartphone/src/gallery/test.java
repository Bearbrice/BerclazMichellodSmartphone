package gallery;

public class test {
	
	static String x ="C:\\C:\\test\\xsd";
	
	public static void main(String[] args) {
        
		System.out.print(findPath(x));
        //System.out.println(getRelativePath(x));
    }
	
	
	//Permet de retrouver le chemin relatif
	private static String getRelativePath(String x) {
		
		
		
		
		return null;
	}
	
	//Methode qui renvoie un fichier jusqu'à un caractère donné
	private static String findPath(String x) {
		String newPath = null;
		int z=0;
		int profondeur=0;
		
		x = x.replace("\\", "/");
		System.out.println(x);
		
		for(int i=x.length()-1; i>0; i--) {
			if(x.charAt(i)=='/') {
				//x.charAt(i).
				newPath=x.substring(x.length()-z, x.length());
				profondeur++;
				
				if(profondeur==2) {
					break;
				}
				
			}
			z++;
		}
		
		return newPath;
	}
}
