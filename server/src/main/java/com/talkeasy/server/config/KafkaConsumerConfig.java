//package com.talkeasy.server.config;
//
//
//import com.talkeasy.server.dto.LocationDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//
//import java.util.Map;
//
//@EnableKafka
////@Configuration
//@RequiredArgsConstructor
//public class KafkaConsumerConfig {
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    //    @Bean
////    public ConsumerFactory<String, LocationDto> consumerFactory() {
////        Map<String, Object> properties = new HashMap<>();
////        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
////        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group"); // 카프카에서 토픽에 쌓여져있는 메시지를 가져가는 컨슈머 그룹화 명
//////        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//////        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//////
//////        return new DefaultKafkaConsumerFactory<>(properties);
////        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(LocationDto.class));
////
////    }
////    @Bean
////    public ConsumerFactory<String, LocationDto> consumerFactory() {
////        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
////    }
//
//    public Map<String, Object> consumerConfigs() {
//        return CommonJsonDeserializer.getStringObjectMap(bootstrapServers);
//    }
//
////    @Bean
////    public ConcurrentKafkaListenerContainerFactory<String, LocationDto> kafkaListenerContainerFactory() {
////        ConcurrentKafkaListenerContainerFactory<String, LocationDto> kafkaListenerContainerFactory
////                = new ConcurrentKafkaListenerContainerFactory<>();
////
////        /* 위 컨슈머 등록 */
////        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
////
////        return kafkaListenerContainerFactory;
////    }
//
//}