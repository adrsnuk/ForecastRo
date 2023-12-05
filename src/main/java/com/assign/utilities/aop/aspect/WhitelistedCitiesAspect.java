package com.assign.utilities.aop.aspect;

import com.assign.utilities.aop.annotation.WhitelistedCities;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
@Order(1)
public class WhitelistedCitiesAspect {

    @Value("${whitelisted_cities}")
    private Set<String> whitelistedCities;

    @Pointcut("@annotation(whitelistedCities) || @within(whitelistedCities)")
    public void whitelistedCitiesPointcut(WhitelistedCities whitelistedCities) {
    }

    @Before("execution(* *..computeWeatherByCities(..)) && args(city)")
    public void filterInvalidCityNames(Set<String> city) {
        city.retainAll(whitelistedCities);
    }
}
