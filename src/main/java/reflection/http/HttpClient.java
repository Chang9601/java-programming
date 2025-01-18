package reflection.http;

public class HttpClient {
	private String serverAddress;

	public HttpClient(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	public boolean doRequest(String data) {
		System.out.println(String.format("요청 데이터 %s % 서버로 전송 성공", data, serverAddress));

		return true;
	}
}