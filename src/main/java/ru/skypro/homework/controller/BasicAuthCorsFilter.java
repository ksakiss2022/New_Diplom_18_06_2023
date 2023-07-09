package ru.skypro.homework.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 Класс BasicAuthCorsFilter является фильтром, который применяется один раз для каждого HTTP-запроса.
 Он отвечает за добавление заголовка Access-Control-Allow-Credentials в ответ, чтобы разрешить
 передачу учетных данных (cookies, authorization headers и т.д.) через кросс-доменные запросы.
 В данном случае, фильтр просто добавляет заголовок без какой-либо проверки или обработки запроса.
 */
@Component
public class BasicAuthCorsFilter extends OncePerRequestFilter {
    /**
     * doFilterInternal: Метод, который предоставляется для подклассов с целью выполнения
     * фактической работы фильтра. В данном случае, метод добавляет заголовок
     * Access-Control-Allow-Credentials со значением "true" в ответе и передает
     * управление следующему фильтру в цепочке, используя объект FilterChain.
     * @param httpServletRequest объект HttpServletRequest, представляющий HTTP-запрос.
     * @param httpServletResponse объект HttpServletResponse, представляющий HTTP-ответ.
     * @param filterChain объект FilterChain, который используется для передачи управления следующему фильтру в цепочке.
     * @throws ServletException /ServletException, IOException: может вызывать исключение ServletException,
     * IOException при обработке запроса.
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}