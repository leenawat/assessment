package com.kbtg.bootcamp.posttest.lottery.repository;

import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {LotteryRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.kbtg.bootcamp.posttest.lottery.model"})
@DataJpaTest
class LotteryRepositoryTest {
    @Autowired
    private LotteryRepository lotteryRepository;

    /**
     * Method under test: {@link LotteryRepository#findAllAvailableLottery()}
     */
    @Test
    @Sql("classpath:createLottery.sql")
    void testFindAllAvailableLottery() {
        // Arrange

        // Act
        List<Lottery> allAvailableLottery = lotteryRepository.findAllAvailableLottery();

        // Assert
        assertEquals(2, allAvailableLottery.size());
    }
}
