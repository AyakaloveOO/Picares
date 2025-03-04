package com.example.picares.config;

import com.example.picares.common.BusinessException;
import org.springframework.beans.PropertyAccessException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class CustomBinderConfig {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setBindingErrorProcessor(new DefaultBindingErrorProcessor() {
            @Override
            public void processPropertyAccessException(@NonNull PropertyAccessException ex, @NonNull BindingResult bindingResult) {
                Throwable rootCause = ex.getRootCause();
                if (rootCause instanceof BusinessException) {
                    throw (BusinessException) rootCause;
                }
                super.processPropertyAccessException(ex, bindingResult);
            }
        });
    }
}