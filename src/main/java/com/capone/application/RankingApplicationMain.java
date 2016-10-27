package com.capone.application;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.capone.datasource.AnalyticDatasource;
import com.capone.datasource.AnalyticDatasourceImpl;
import com.capone.service.RankService;

@SpringBootApplication
@ComponentScan({"com.capone"})
public class RankingApplicationMain {

	public static void main(String[] args) {
		ApplicationContext appCtx = SpringApplication.run(RankingApplicationMain.class,args);
        System.out.println("Starting Ranking Service");
	}
	
	
//	@Bean
//	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//	@Lazy(value=true)
//	public RankService getRankingService() {
//		return new RankService();
//	}
//
//
//	@Bean
//	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//	@Lazy(value=true)
//	public AnalyticDatasource getAnalyticDatasource() {
//		return new AnalyticDatasourceImpl();
//	}

}
