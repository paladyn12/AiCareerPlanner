package com.careerservice.ai_career_planner.config.auth;

import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
@Slf4j
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        // 세션 유지 시간 = 3600초
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);

        User loginUser = userRepository.findByLoginId(auth.getName()).get();
        session.setAttribute("user", loginUser);

        // 성공 시 메세지 출력 후 홈 화면으로 redirect
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();

        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if (prevPage != null) {
            pw.println("<script>alert('" + loginUser.getName() + "님 반갑습니다!'); " +
                    "location.href='" + prevPage + "';</script>");
        } else {
            pw.println("<script>alert('" + loginUser.getName() + "님 반갑습니다!'); " +
                    "location.href='/';</script>");
        }
        pw.flush();
    }
}
