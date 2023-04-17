package com.talkeasy.server.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    // 로그인이 필요한 서비스에서 로그인 되어있지 않은 경우 예외 처리
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> resolveException(UnAuthorizedException exception) {
        ErrorResponse errResponse = exception.getErrResponse();
        return new ResponseEntity<>(errResponse, HttpStatus.UNAUTHORIZED);
    }

    // 데이터 조작 시 해당 데이터를 조작할 권한이 없는 경우
    @ExceptionHandler(ResourceForbiddenException.class)
    public ResponseEntity<ErrorResponse> resolveException(ResourceForbiddenException exception) {
        ErrorResponse errResponse = exception.getErrResponse();
        return new ResponseEntity<>(errResponse, HttpStatus.FORBIDDEN);
    }

    // 데이터가 존재하지 않는 경우 예외 처리
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resolveException(ResourceNotFoundException exception) {
        ErrorResponse errResponse = exception.getErrResponse();
        return new ResponseEntity<>(errResponse, HttpStatus.NOT_FOUND);
    }

    // 데이터 추가 시도 중 이미 존재하는 resource 일 경우 예외 처리
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> resolveException(ResourceAlreadyExistsException exception) {
        ErrorResponse errResponse = exception.getErrResponse();
        return new ResponseEntity<>(errResponse, HttpStatus.CONFLICT);
    }

    //type mismatch exception, 잘못된 인자
    @ExceptionHandler(ArgumentMismatchException.class)
    public ResponseEntity<ErrorResponse> resolveException(ArgumentMismatchException exception) {
        ErrorResponse errResponse =  exception.getErrResponse();
        return new ResponseEntity<>(errResponse, HttpStatus.BAD_REQUEST);
    }

    // @Valid 로 유효성 검사했을 때 발생한 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationExceptions(BindingResult bindingResult) {
        List<ErrorResponse> list = new ArrayList<>();
        bindingResult.getAllErrors().forEach(c ->list.add(new ErrorResponse(c.getDefaultMessage(), ((FieldError)c).getField())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list);
    }

    //UserRequestFile validation에서는 MethodArgumentNotValidException이 아닌 BindException가 났다..
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ErrorResponse>> handleBindException(BindingResult bindingResult) {
        List<ErrorResponse> list = new ArrayList<>();
        bindingResult.getAllErrors().forEach(c ->list.add(new ErrorResponse( c.getDefaultMessage(), ((FieldError)c).getField())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list);
    }


    //파일 용량 제한
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ResponseEntity<?> handleMaxUploadSizeExceptions(MaxUploadSizeExceededException exception) {
         ErrorResponse errResponse =  new ErrorResponse( "파일 용량이 너무 큽니다.");
        return new ResponseEntity<>(errResponse, HttpStatus.PAYLOAD_TOO_LARGE);
    }
}