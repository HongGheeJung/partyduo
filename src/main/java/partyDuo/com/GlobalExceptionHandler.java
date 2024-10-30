package partyDuo.com;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Page Not Found
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorMessage", "페이지를 찾을 수 없습니다. 입력한 주소를 확인해주세요.");
        return "error/404";
    }

    // 500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    public String handleServerError(Exception ex, Model model, HttpServletRequest request) {
        // 정적 리소스 요청인지 확인
        String uri = request.getRequestURI();
        if (uri.startsWith("/static/") || uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/images/")) {
            // 정적 리소스라면 예외 처리를 무시
            return null;
        }

        model.addAttribute("errorMessage", "서버 내부 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
        return "error/500";
    }
}
