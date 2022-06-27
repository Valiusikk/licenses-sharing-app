package com.endava.license.config;


import com.endava.license.converter.LicenseDtoToLicenseEntityConverter;
import com.endava.license.converter.LicenseEntityToLicenseDtoConverter;
import com.endava.license.converter.LicenseEntityToLicenseGetDtoConverter;
import com.endava.license.converter.OrderDtoToOrderEntityConverter;
import com.endava.license.converter.OrderEntityToOrderDtoConverter;
import com.endava.license.converter.OrderEntityToReadOrderDtoConverter;
import com.endava.license.converter.ProductDtoToProductEntityConverter;
import com.endava.license.converter.ProductEntityToProductDtoConverter;
import com.endava.license.converter.RegisterRequestToUserEntityConverter;
import com.endava.license.converter.UserDtoToUserEntityConverter;
import com.endava.license.converter.UserEntityToUserDtoConverter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@RequiredArgsConstructor
public class ConversionServiceConfig implements WebMvcConfigurer {

    private static final String ALL_PATHS = "/**";
    private static final String ALL_ORIGINS = "*";
    private final ProductEntityToProductDtoConverter toProductDtoConverter;
    private final ProductDtoToProductEntityConverter toProductEntityConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserDtoToUserEntityConverter());
        registry.addConverter(new UserEntityToUserDtoConverter());
        registry.addConverter(new ProductDtoToProductEntityConverter());
        registry.addConverter(new ProductEntityToProductDtoConverter());
        registry.addConverter(new LicenseDtoToLicenseEntityConverter(toProductEntityConverter));
        registry.addConverter(new LicenseEntityToLicenseDtoConverter(toProductDtoConverter));
        registry.addConverter(new LicenseEntityToLicenseGetDtoConverter());
        registry.addConverter(new OrderDtoToOrderEntityConverter());
        registry.addConverter(new OrderEntityToOrderDtoConverter());
        registry.addConverter(new OrderEntityToReadOrderDtoConverter());
        registry.addConverter(new RegisterRequestToUserEntityConverter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping(ALL_PATHS)
                .allowedMethods(GET.name(), HEAD.name(), POST.name(), PUT.name(), DELETE.name(), PATCH.name(), OPTIONS.name())
                .allowedOrigins(ALL_ORIGINS);
    }
}
