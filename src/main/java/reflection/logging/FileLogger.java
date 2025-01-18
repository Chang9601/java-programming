package reflection.logging;

import java.io.IOException;

public class FileLogger {
	public void doRequest(String data) throws IOException {
		//throw new IOException("데이터 파일 시스템에 로깅 실패");
		System.out.println(String.format("데이터 %s 파일 시스템에 로깅 성공", data));
	}
}