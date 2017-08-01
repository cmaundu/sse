package co.ke.sart.site.controller;

import co.ke.sart.config.annotation.WebController;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.form.LabTestForm;
import co.ke.sart.site.form.RoleForm;
import co.ke.sart.site.form.UserForm;
import co.ke.sart.site.model.Patient;
import co.ke.sart.site.service.UserService;
import co.ke.sart.site.utils.FormAction;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@WebController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping("administration")
public class AdministrationController {

    @Autowired
    UserService userService;

    @RequestMapping(value = {"**","user*","user**", "user/list"}, method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("users", this.userService.getAllUsers());

        return "user/list";
    }
    
    @RequestMapping(value = {"role*", "role/list"}, method = RequestMethod.GET)
    public String roleList(Model model) {
        model.addAttribute("roles", this.userService.getAllRoles());

        return "role/list";
    }    

    @RequestMapping(value = {"user/add"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADD_USER')")
    public String userAdd(Model model) {
        model.addAttribute("userForm", this.userService.prepareUserForm(0, FormAction.NEW));

        return "user/add";
    }

    @RequestMapping(value = {"role/edit/{rowID}"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_EDIT_ROLE')")
    public String roleEdit(Model model, @PathVariable("rowID") int rowID) {
        model.addAttribute("roleForm", this.userService.prepareRoleForm(rowID, FormAction.EDIT));

        return "role/add";
    }    
    
    @RequestMapping(value = {"role/add"}, method = RequestMethod.GET)
    public String roleAdd(Model model) {
        model.addAttribute("roleForm", this.userService.prepareRoleForm(0, FormAction.NEW));

        return "role/add";
    }
  
        @RequestMapping(value = {"role/add", "role/edit"}, method = RequestMethod.POST)
        @PreAuthorize("hasRole('ROLE_EDIT_ROLE')")
    public ModelAndView roleSave(Principal principal, @Valid @ModelAttribute("roleForm") RoleForm roleForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/user/add");
        }  else if (roleForm.getRole().getRoleName().isEmpty()) {

            ObjectError error = new ObjectError("roleForm.role.roleName", "Rolename cannot be empty. Please provide a name");
            result.addError(error);

            ModelAndView rxView = new ModelAndView("/user/role");
            rxView.addObject("validationErrors", result.getAllErrors());

            return rxView;
        }
        
        this.userService.saveRole((UserPrincipal) principal, roleForm);

        return new ModelAndView("redirect:/administration/role/");
    }

    @RequestMapping(value = {"user/passwordedit/{rowID}"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_EDIT_PASSWD')")
    public String userPasswordReset(Model model, @PathVariable("rowID") int rowID, HttpServletRequest request) {
        model.addAttribute("userForm", this.userService.prepareUserForm(rowID, FormAction.PASSWORDCHANGE));
        System.out.println("Has Role "+ request.isUserInRole("EDIT_PASSW"));
        System.out.println("Has Role "+ request.isUserInRole("ADD_USER"));

        
        return "user/passwordedit";
    }

    @RequestMapping(value = {"user/edit/{rowID}"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_EDIT_USER')")
    public String userEdit(Model model, @PathVariable("rowID") int rowID) {
        model.addAttribute("userForm", this.userService.prepareUserForm(rowID, FormAction.EDIT));

        return "user/edit";
    }

    @RequestMapping(value = {"user/add", "user/edit"}, method = RequestMethod.POST)
    public ModelAndView userSave(Principal principal, @Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/user/add");
        } else if (userForm.getFormAction() == FormAction.NEW && this.userService.validateUserNameExists(userForm.getUser().getUsername())) {

            ObjectError error = new ObjectError("userForm.username", "User Name already exists, specify another one");
            result.addError(error);

            ModelAndView rxView = new ModelAndView("/user/add");
            rxView.addObject("validationErrors", result.getAllErrors());

            return rxView;
        }
        this.userService.saveUser((UserPrincipal) principal, userForm);

        return new ModelAndView("redirect:/administration/user/");
    }

    @RequestMapping(value = {"user/passwordedit"}, method = RequestMethod.POST)
    @PreAuthorize("hasRole('PASSWD_ROLE')")
    public ModelAndView userPasswordResetSave(Principal principal, @Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/user/add");
        } else if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {

            ObjectError error = new ObjectError("Passwords do not Match", "Passwords do not match, please ensure they match.");
            result.addError(error);

            ModelAndView rxView = new ModelAndView("/user/passwordedit");
            rxView.addObject("validationErrors", result.getAllErrors());

            return rxView;
        } else if (userForm.getPassword().length() < 4) {

            ObjectError error = new ObjectError("Passwords is not complex", "Ensure Password is more than 4 characters.");
            result.addError(error);

            ModelAndView rxView = new ModelAndView("/user/passwordedit");
            rxView.addObject("validationErrors", result.getAllErrors());

            return rxView;
        }
        this.userService.saveUser((UserPrincipal) principal, userForm);

        return new ModelAndView("redirect:/administration/user/");
    }

}
