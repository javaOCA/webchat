package ua.univerpulse.webchat.mvc.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import ua.univerpulse.webchat.mvc.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AspectExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public RedirectView handleServiceException(ServiceException exception, HttpServletRequest request) {
        RedirectView rw = new RedirectView("./registration");
        rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null){
            outputFlashMap.put("error", exception.getMessage());
        }
        return rw;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex){
        ex.printStackTrace();
        return "/index";
    }

}
