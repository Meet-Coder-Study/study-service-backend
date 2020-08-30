package com.study.service.security;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.study.service.annotation.SocialUser;
import com.study.service.user.SocialType;
import com.study.service.user.User;
import com.study.service.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterAnnotation(SocialUser.class) != null && parameter.getParameterType()
                .equals(User.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpSession session = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest()
                .getSession();
        final User user = (User)session.getAttribute("user");
        return getUser(user, session);
    }

    private User getUser(User user, final HttpSession httpSession) {
        if (user == null) {
            try {
                final OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken)SecurityContextHolder.getContext()
                        .getAuthentication();
                final Map<String, Object> map = authentication.getPrincipal().getAttributes();
                final User convertUser = getModernUser(map);

                user = userRepository.findByEmail(convertUser.getEmail());

                if (user == null) {
                    user = userRepository.save(convertUser);
                }

                httpSession.setAttribute("user", user);
            } catch (final ClassCastException e) {
                return user;
            }
        }
        return user;
    }

    private User getModernUser(final Map<String, Object> map) {
        return User.builder()
                .name(String.valueOf(map.get("name")))
                .email(String.valueOf(map.get("email")))
                .principal(String.valueOf(map.get("id")))
                .socialType(SocialType.GITHUB)
                .build();
    }
}
