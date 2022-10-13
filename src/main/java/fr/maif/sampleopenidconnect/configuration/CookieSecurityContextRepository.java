package fr.maif.sampleopenidconnect.configuration;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CookieSecurityContextRepository implements SecurityContextRepository {

    public static final String COOKIE_NAME = "FDI-ACCESS-TOKEN";

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        return null;
    }

    @Override
    public Supplier<SecurityContext> loadContext(HttpServletRequest request) {
        return SecurityContextRepository.super.loadContext(request);
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return false;
    }

    private Optional<UserInfo> readUserInfoFromCookie(HttpServletRequest request) {
        return readCookieFromRequest(request)
                .map(this::createUserInfo);
    }

    private Optional<Cookie> readCookieFromRequest(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }

        Optional<Cookie> maybeCookie = Stream.of(request.getCookies())
                .filter(c -> SignedUserInfoCookie.NAME.equals(c.getName()))
                .findFirst();

        return maybeCookie;
    }
}
