package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.lottery.exception.LotteryDuplicateException;
import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotteryService {
    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public TicketResponseDto save(LotteryRequestDto lotteryRequestDto) {
        Optional<Lottery> optional = lotteryRepository.findById(lotteryRequestDto.getTicket());
        if (optional.isPresent()) {
            throw new LotteryDuplicateException("Duplicate lottery");
        } else {
            return new TicketResponseDto(lotteryRepository.save(Lottery.builder()
                    .amount(lotteryRequestDto.getAmount())
                    .price(lotteryRequestDto.getPrice())
                    .ticket(lotteryRequestDto.getTicket())
                    .build()
            ).getTicket());
        }
    }

    public TicketListResponseDto findAll() {
        return new TicketListResponseDto(lotteryRepository.findAllAvailableLottery()
                .stream()
                .map(Lottery::getTicket)
                .collect(Collectors.toList()));
    }
}
