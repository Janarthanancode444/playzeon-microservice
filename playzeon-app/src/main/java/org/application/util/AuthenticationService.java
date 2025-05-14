package org.application.util;

import org.application.dto.UserInfoUserDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.user.util.Constants;

@Service
public class AuthenticationService {
    public String getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserInfoUserDetailsDTO userDetails) {
            return userDetails.getId();
        }
        return Constants.NOT_FOUND;
    }
}