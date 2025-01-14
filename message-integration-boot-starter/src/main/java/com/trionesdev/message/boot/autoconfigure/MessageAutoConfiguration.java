package com.trionesdev.message.boot.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


@AutoConfiguration
@EnableConfigurationProperties(value = {
        MessageProperties.class
})
@Import({MessageAutoConfiguration.MessageConfigurationImportSelector.class})
public class MessageAutoConfiguration {


    static class MessageConfigurationImportSelector implements ImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            MessageType[] types = MessageType.values();
            String[] imports = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                imports[i] = MessageConfigurations.getConfigurationClass(types[i]);
            }
            return imports;
        }

    }

}
