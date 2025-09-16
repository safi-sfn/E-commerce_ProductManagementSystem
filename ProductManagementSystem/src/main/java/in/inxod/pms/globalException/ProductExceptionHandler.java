package in.inxod.pms.globalException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ProductExceptionHandler {

	
	 @ExceptionHandler(ProductNotFoundException.class)
     public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ProductNotFoundException ex) {
		 ErrorResponse errorResponse = new ErrorResponse(
	                HttpStatus.NOT_FOUND.value(),
	                ex.getMessage(),
	                LocalDateTime.now()
	                
	        );
		 return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
     }
	 
}
