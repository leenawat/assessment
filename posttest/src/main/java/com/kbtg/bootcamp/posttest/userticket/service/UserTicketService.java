package com.kbtg.bootcamp.posttest.userticket.service;

import com.kbtg.bootcamp.posttest.lottery.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.lottery.exception.LotteryUnavailableException;
import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTickerSummaryDto;
import com.kbtg.bootcamp.posttest.userticket.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.userticket.exception.InvalidUserTicketException;
import com.kbtg.bootcamp.posttest.userticket.model.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.repository.UserTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTicketService {

    private final UserTicketRepository userTicketRepository;
    private final LotteryRepository lotteryRepository;

    public UserTicketService(UserTicketRepository userTicketRepository, LotteryRepository lotteryRepository) {
        this.userTicketRepository = userTicketRepository;
        this.lotteryRepository = lotteryRepository;
    }

    public UserTicketDto buyLotteries(String userId, String ticketId) {
        UserTicket userTicket = new UserTicket();

        Optional<Lottery> optional = lotteryRepository.findById(ticketId);
        Lottery lottery;

        if (optional.isEmpty()) {
            throw new LotteryUnavailableException("Lottery Unavailable Exception");
        } else {
            lottery = optional.get();
            if (lottery.getAmount() <= 0) {
                throw new LotteryUnavailableException("Lottery Unavailable Exception");
            }

        }
        lottery.setTicket(ticketId);

        userTicket.setUserId(userId);
        userTicket.setLottery(lottery);

        UserTicket saved = userTicketRepository.save(userTicket);
        lottery.setAmount(0);
        lotteryRepository.save(lottery);
        return new UserTicketDto(saved.getId());
    }

    public UserTickerSummaryDto getLotteriesByUserId(String userId) {
        UserTickerSummaryDto userTickerSummaryDto = new UserTickerSummaryDto();


        List<UserTicket> byUser = userTicketRepository.findByUserId(userId);
        List<String> tickets = byUser.stream().map(b -> b.getLottery().getTicket()).collect(Collectors.toList());

        userTickerSummaryDto.setTickets(tickets);
        userTickerSummaryDto.setCount(tickets.size());
        userTickerSummaryDto.setCost(calculateTotalPrice(byUser));

        return userTickerSummaryDto;
    }

    private static Integer calculateTotalPrice(List<UserTicket> byUser) {
        Integer sum = 0;
        for (UserTicket ticket : byUser) {
            sum += ticket.getLottery().getPrice();
        }
        return sum;
    }

    @Transactional
    public TicketResponseDto deleteLotteriesByUserId(String userId, String ticketId) {
        List<UserTicket> byUser = userTicketRepository.findByUserIdAndTicketId(userId, ticketId);
        if (!byUser.isEmpty()) {
            userTicketRepository.delete(byUser.get(0));
            Optional<Lottery> optional = lotteryRepository.findById(ticketId);
            if(optional.isPresent()) {
                Lottery lottery = optional.get();
                lottery.setAmount(1);
                lotteryRepository.save(lottery);
                return new TicketResponseDto(ticketId);
            }else{
                throw new InvalidUserTicketException("Invalid userId or ticketId");
            }
        } else {
            throw new InvalidUserTicketException("Invalid userId or ticketId");
        }
    }
}
