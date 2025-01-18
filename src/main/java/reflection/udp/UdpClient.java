package reflection.udp;

public class UdpClient {
	public void send(String payload) {
		System.out.println(String.format("요청 %s UDP로 전송 성공", payload));
	}
}