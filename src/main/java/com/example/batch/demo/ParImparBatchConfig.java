package com.example.batch.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class ParImparBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job imprimeParOuImparJob(){
        return jobBuilderFactory
                .get("imprimeParOuImparJob")
                .start(imprimeParOuImparStep())
                .build();
    }

    public Step imprimeParOuImparStep() {
        return stepBuilderFactory
                .get("imprimeParOuImparStep")
                .<Integer, String> chunk(1)
                .reader(contaAte10Reader())
                .processor(parOuImparProcessor())
                .writer(imprimeWriter())
                .build();
    }

    public ItemWriter<? super String> imprimeWriter() {
        return itens -> itens.forEach(System.out::println);
    }

    public FunctionItemProcessor<? super Integer, String> parOuImparProcessor() {
        return new FunctionItemProcessor<>(
                item -> item % 2 == 0? String.format("%s é par", item): String.format("%s é impar", item));
    }

    public IteratorItemReader<Integer> contaAte10Reader() {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        return new IteratorItemReader<>(list);
    }
}
