package com.example;

import com.example.services.ConverterService;
import com.example.services.GifEncoderService;
import com.example.services.VideoDecoderService;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.inject.Inject;
import java.io.File;

@Configuration
@ConditionalOnClass({FFmpegFrameGrabber.class, AnimatedGifEncoder.class})
public class JustGifItAutoConfiguration {

    @Inject
    private JustGifItProperties properties;

    @Bean
    @ConditionalOnProperty(prefix = "com.example", name = "create-result-directory")
    public boolean createResultDirectory() {
        File gifFolder = new File(properties.getGifLocation());
        if (!gifFolder.exists()) {
            gifFolder.mkdir();
        }

        return true;
    }

    @Bean
    @ConditionalOnMissingBean
    public VideoDecoderService videoDecoderService() {
        return new VideoDecoderService();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConverterService converterService() {
        return new ConverterService();
    }

    @Bean
    @ConditionalOnMissingBean
    public GifEncoderService gifEncoderService() {
        return new GifEncoderService();
    }

    @Configuration
    @ConditionalOnWebApplication
    @EnableConfigurationProperties(JustGifItProperties.class)
    public static class WebConfiguration {

        @Inject
        private JustGifItProperties properties;

        @Bean
        @ConditionalOnProperty(prefix = "com.example", name = "optimize")
        public FilterRegistrationBean deRegisterHiddenHttpMethodFilter(HiddenHttpMethodFilter hiddenHttpMethodFilter) {
            FilterRegistrationBean bean = new FilterRegistrationBean(hiddenHttpMethodFilter);
            bean.setEnabled(false);
            return bean;
        }

        @Bean
        @ConditionalOnProperty(prefix = "com.example", name = "optimize")
        public FilterRegistrationBean deRegisterHttpPutFormContentFilter(HttpPutFormContentFilter filter) {
            FilterRegistrationBean bean = new FilterRegistrationBean(filter);
            bean.setEnabled(false);
            return bean;
        }

        @Bean
        @ConditionalOnProperty(prefix = "com.example", name = "optimize")
        public FilterRegistrationBean deRegisterRequestContextFilter(RequestContextFilter filter) {
            FilterRegistrationBean bean = new FilterRegistrationBean(filter);
            bean.setEnabled(false);
            return bean;
        }

        @Bean
        public WebMvcConfigurer webMvcConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addResourceHandlers(ResourceHandlerRegistry registry) {
                    registry.addResourceHandler("/gif/**")
                            .addResourceLocations("file:" + properties.getGifLocation());
                    super.addResourceHandlers(registry);
                }
            };
        }

    }

}
