package reflection.database;

public class DatabaseClient {
	public boolean insert(String data) {
		System.out.println(String.format("데이터 %s 저장 성공", data));
		
		return true;
	}
}