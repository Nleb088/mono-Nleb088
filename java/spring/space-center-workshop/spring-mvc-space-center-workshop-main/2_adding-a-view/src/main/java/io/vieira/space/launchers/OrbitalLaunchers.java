package io.vieira.space.launchers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
@EnableJdbcRepositories
public class OrbitalLaunchers {

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean launchersPopulator(@Value("classpath:data-seeds/ariane-launchers.json") Resource arianeLaunchers,
                                                                     @Value("classpath:data-seeds/rkk_energia-launchers.json") Resource energiaLaunchers,
                                                                     @Value("classpath:data-seeds/spacex-launchers.json") Resource spacexLaunchers) {
        Jackson2RepositoryPopulatorFactoryBean toReturn = new Jackson2RepositoryPopulatorFactoryBean();
        toReturn.setResources(new Resource[]{arianeLaunchers, energiaLaunchers, spacexLaunchers});
        return toReturn;
    }
}
