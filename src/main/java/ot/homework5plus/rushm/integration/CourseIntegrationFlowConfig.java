package ot.homework5plus.rushm.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ot.homework5plus.rushm.service.EducationService;

@Configuration
@RequiredArgsConstructor
public class CourseIntegrationFlowConfig {
    private final EducationService educationService;

    @Bean
    public QueueChannel studentChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel courseChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get() ;
    }

    @Bean
    public IntegrationFlow workFlow() {
        return IntegrationFlows.from("studentChannel")
                .handle(educationService,"gradeStudent")
                .channel("courseChannel")
                .get();
    }
}