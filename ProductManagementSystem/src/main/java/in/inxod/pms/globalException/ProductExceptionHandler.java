package in.inxod.pms.globalException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
	 
	 @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
	        ErrorResponse errorResponse = new ErrorResponse(
	                HttpStatus.BAD_REQUEST.value(),
	                "Invalid JSON format in request body",
	                LocalDateTime.now()
	                
	        );
	        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	    }


		@ExceptionHandler(BrandNameNotValidException.class)
		public ResponseEntity<ErrorResponse> handleBrandNameException(BrandNameNotValidException e){
		 	ErrorResponse errorResponse = new ErrorResponse(
				 HttpStatus.BAD_REQUEST.value(),
				 e.getMessage(),
				 LocalDateTime.now()
		 	);
		 	return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		 ErrorResponse errorResponse = new ErrorResponse(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(),
	                "Internal Server Error",
	                LocalDateTime.now()
	                
	        );
	        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); 
	    }

	 
}
