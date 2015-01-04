//STILL IN DEVLOPEMENT
public class PointObject {

	String user;
	int xp;
	int timer;
	
	
	public PointObject(String user, int xp){
		this.user = user;
		this.xp = xp;
		timer = (int) (System.currentTimeMillis() * 60000);
	}
	
}
