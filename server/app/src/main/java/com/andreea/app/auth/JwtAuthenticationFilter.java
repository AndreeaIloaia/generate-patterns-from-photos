package com.andreea.app.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Clasa folosita pentru a verifica si extrage informatii din token-ul din header
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    /**
     * Extrage informatiile din token si le retine in SecurityContext
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @param filterChain - FilterChain
     * @throws ServletException - doFilter arunca aceasta exceptie
     * @throws IOException - doFilter arunca aceasta exceptie
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //authenticationToken - pentru user-ul curent contine informatii precum daca e autentificat si rolurile pe care le contine
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            logger.error("Could not be done user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Functie folosita pentru a extrage token-ul din header-ul request-ului
     * @param request - HttpServletRequest, request-ul venit de la endpoint
     * @return token - daca header-ul asociat se numeste Authorization si e de tip Bearer
     *         null - altfel
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
