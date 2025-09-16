package in.inxod.pms.globalException;

import java.time.LocalDateTime;

public class ErrorResponse {

	private int status;
    private String message;
    private LocalDateTime timestamp;
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public ErrorResponse(int status, String message, LocalDateTime timestamp) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}
	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
