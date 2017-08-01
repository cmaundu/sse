/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.service;

import co.ke.sart.site.entity.UserPrincipal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;


@Validated
public interface UserServiceInterface extends AuthenticationProvider
{
    @Override
    UserPrincipal authenticate(Authentication authentication);

    void saveUser(
            @NotNull(message = "{validate.authenticate.saveUser}") @Valid
                UserPrincipal principal,
            String newPassword
    );
}
