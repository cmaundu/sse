package co.ke.sart.site.service;

import co.ke.sart.site.entity.Permission;
import co.ke.sart.site.entity.Role;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.form.RoleForm;
import co.ke.sart.site.form.UserForm;
import co.ke.sart.site.model.Record;
import co.ke.sart.site.repository.PermissionRepository;
import co.ke.sart.site.repository.RoleRepository;
import co.ke.sart.site.repository.UserRepository;
import co.ke.sart.site.utils.FormAction;
import co.ke.sart.site.utils.RecordStatus;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import sun.security.provider.PolicyParser;

@Service
public class UserService implements UserServiceInterface {

    private static final SecureRandom RANDOM;
    private static final int HASHING_ROUNDS = 10;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    static {
        try {
            RANDOM = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    @Transactional
    public UserPrincipal authenticate(Authentication authentication) {
        UsernamePasswordAuthenticationToken credentials
                = (UsernamePasswordAuthenticationToken) authentication;
        String username = credentials.getPrincipal().toString();
        String password = credentials.getCredentials().toString();
        credentials.eraseCredentials();

        UserPrincipal principal = this.userRepository.getByUsername(username);
        if (principal == null) {
            return null;
        }

        if (!BCrypt.checkpw(
                password,
                new String(principal.getPassword(), StandardCharsets.UTF_8)
        )) {
            return null;
        }

        principal.setAuthenticated(true);

        principal.setAuthorities(this.getAuthorities(principal.getRole()));

        principal.setLastLoginTime(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(principal);
        return principal;
    }

    @Override
    public boolean supports(Class<?> c) {
        return c == UsernamePasswordAuthenticationToken.class;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        List<Permission> permissions = this.toList(this.permissionRepository.findAll());
        permissions.forEach(p -> {
            if (((long) Math.pow(2, p.getRowID()) & role.getAddPermissions()) > 1) {
                list.add(new SimpleGrantedAuthority("ROLE_ADD_" + p.getCode()));
            }
            if (((long) Math.pow(2, p.getRowID()) & role.getEditPermissions()) > 1) {
                list.add(new SimpleGrantedAuthority("ROLE_EDIT_" + p.getCode()));
            }
            if (((long) Math.pow(2, p.getRowID()) & role.getDeletePermissions()) > 1) {
                list.add(new SimpleGrantedAuthority("ROLE_DEL_" + p.getCode()));
            }
            if (((long) Math.pow(2, p.getRowID()) & role.getViewPermissions()) > 1) {
                list.add(new SimpleGrantedAuthority("ROLE_VIEW_" + p.getCode()));
            }
        });
        return list;
    }



    @Transactional
    public void saveUser(UserPrincipal principal, String newPassword) {
        if (newPassword != null && newPassword.length() > 0) {
            String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
            principal.setPassword(BCrypt.hashpw(newPassword, salt).getBytes());
        }

        this.userRepository.save(principal);
    }

    public List<UserPrincipal> getAllUsers() {
        return this.toList(this.userRepository.findAll());
    }

    public List<Role> getAllRoles() {
        return this.toList(this.roleRepository.findAll());
    }

    private <E> List<E> toList(Iterable<E> i) {
        List<E> list = new ArrayList<>();
        i.forEach(list::add);
        return list;
    }

    public RoleForm prepareRoleForm(int rowID, FormAction formAction) {
        RoleForm roleForm = new RoleForm();
        roleForm.setFormAction(formAction);
        roleForm.setPermissions(this.toList(this.permissionRepository.findAll()));
        if (rowID > 0) {
            roleForm.setRole(this.roleRepository.findOne(rowID));
            roleForm.setAddPermissions(new ArrayList<>());
            roleForm.setEditPermissions(new ArrayList<>());
            roleForm.setDeletePermissions(new ArrayList<>());
            roleForm.setViewPermissions(new ArrayList<>());

            for (Permission p : roleForm.getPermissions()) {
                if (((long) Math.pow(2, p.getRowID()) & roleForm.getRole().getAddPermissions()) > 0) {
                    roleForm.getAddPermissions().add(p.getRowID());
                }
                if (((long) Math.pow(2, p.getRowID()) & roleForm.getRole().getEditPermissions()) > 0) {
                    roleForm.getEditPermissions().add(p.getRowID());
                }
                if (((long) Math.pow(2, p.getRowID()) & roleForm.getRole().getDeletePermissions()) > 0) {
                    roleForm.getDeletePermissions().add(p.getRowID());
                }
                if (((long) Math.pow(2, p.getRowID()) & roleForm.getRole().getViewPermissions()) > 0) {
                    roleForm.getViewPermissions().add(p.getRowID());
                }
            }

        } else {
            roleForm.setRole(new Role());
        }

        return roleForm;
    }

    public UserForm prepareUserForm(int rowID, FormAction formAction) {
        UserForm userForm = new UserForm();
        userForm.setFormAction(formAction);

        if (rowID > 0) {
            userForm.setUser(this.userRepository.findOne(rowID));

        } else {
            userForm.setUser(new UserPrincipal());
        }

        userForm.setRoles(this.toList(roleRepository.findAll()));

        return userForm;
    }

    public boolean validateUserNameExists(String userName) {
        UserPrincipal user = this.userRepository.getByUsername(userName.trim().toLowerCase());
        return (user != null && user.getRowID() > 0);
    }

    public boolean saveRole(UserPrincipal principal, RoleForm roleForm) {
        Role role = new Role();
        if (roleForm.getFormAction() == FormAction.EDIT) {
            role = this.roleRepository.findOne(roleForm.getRole().getRowID());
        }
        role.setRoleDesc(roleForm.getRole().getRoleDesc());
        role.setRoleName(roleForm.getRole().getRoleName());
        role.setAddPermissions(roleForm.getAddPermissions().stream().mapToLong(p -> (long) Math.pow(2, p)).sum());
        role.setEditPermissions(roleForm.getEditPermissions().stream().mapToLong(p -> (long) Math.pow(2, p)).sum());
        role.setDeletePermissions(roleForm.getDeletePermissions().stream().mapToLong(p -> (long) Math.pow(2, p)).sum());
        role.setViewPermissions(roleForm.getViewPermissions().stream().mapToLong(p -> (long) Math.pow(2, p)).sum());
        if (roleForm.getFormAction() == FormAction.NEW) {
            role.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            role.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            role.setCreatedBy(principal.getRowID());
            role.setUpdatedBy(principal.getRowID());
        } else if (roleForm.getFormAction() == FormAction.EDIT) {
            role.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            role.setUpdatedBy(principal.getRowID());
        }
        this.roleRepository.save(role);
        return true;
    }

    public boolean saveUser(UserPrincipal principal, UserForm userForm) {
        if (userForm.getFormAction() == FormAction.NEW) {
            UserPrincipal user = userForm.getUser();
            user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            user.setCreatedBy(principal.getRowID());
            user.setUpdatedBy(principal.getRowID());
            this.userRepository.save(user);
            user.setEkNumber(String.format("EK-%04d", user.getRowID()));
            this.userRepository.save(user);
        } else if (userForm.getFormAction() == FormAction.EDIT) {
            UserPrincipal user = this.userRepository.findOne(userForm.getUser().getRowID());
            UserPrincipal newDetail = userForm.getUser();
            user.setDocNumber(newDetail.getDocNumber());
            user.setDocType(newDetail.getDocType());
            user.setForename(newDetail.getForename());
            user.setGender(newDetail.getGender());
            user.setMiddleNames(newDetail.getMiddleNames());
            user.setRole(newDetail.getRole());
            user.setSurname(newDetail.getSurname());
            user.setRecordStatus(newDetail.getRecordStatus());
            this.userRepository.save(user);
        } else if (userForm.getFormAction() == FormAction.PASSWORDCHANGE) {
            UserPrincipal user = this.userRepository.findOne(userForm.getUser().getRowID());
            this.saveUser(user, userForm.getPassword());
        }

        return true;
    }
}
