/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.asteroids.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author stefh
 */
@Configuration
public class AsteroidsFactoryConfig {
    
    @Bean
    public IAsteroidsFactory asteroidsFactory() {
        return new AsteroidFactory();
    }
    
}
