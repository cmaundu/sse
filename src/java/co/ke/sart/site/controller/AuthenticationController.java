package co.ke.sart.site.controller;

import co.ke.sart.config.annotation.WebController;
import co.ke.sart.site.entity.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebController
public class AuthenticationController {

    @RequestMapping(value = {"login"}, method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof UserPrincipal) {
            return new ModelAndView(new RedirectView("/patient/", true, false));
        }

        model.put("loginForm", new LoginForm());
        return new ModelAndView("login");
    }
    
    //@RequestMapping(value = {"*"}, method = RequestMethod.GET)
    //public String defaultPage(Map<String, Object> model) {
    //    return "redirect:/patient/" ;
    //}

    @RequestMapping(value = "accessdenied", method = RequestMethod.GET)
    public ModelAndView accessdenied(Map<String, Object> model) {
        return new ModelAndView("accessdenied");
    }

    @RequestMapping(value = {"logout"}, method = RequestMethod.GET)
    public ModelAndView logout(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("redirect:/login?loggedOut");
    }

    public static class LoginForm {

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
