package org.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.core.error.ErrorMessage;
import org.core.error.Result;
import org.payment.repository.BalanceRepository;
import org.springframework.stereotype.Service;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public Result<Void> chargeMoney(String username, Long amount) {
        try {
            if (!isEnoughMoney(username, amount)) {
                return new Result<>(ErrorMessage.NOT_ENOUGH_MONEY);
            }

            balanceRepository.updateBalanceSubtractAmount(amount, username);
            return Result.SUCCESS;
        } catch (Exception e) {
            log.error("something error when subtract balance", e);
            return new Result<>(ErrorMessage.UPDATE_BALANCE_FAILED);
        }

    }

    public Result<Void> refund(String username, Long amount) {
        try {
            balanceRepository.updateBalanceSubtractAmount(-amount, username);
            return Result.SUCCESS;
        } catch (Exception e) {
            log.error("something error when refund", e);
            return new Result<>(ErrorMessage.UPDATE_BALANCE_FAILED);
        }
    }

    private boolean isEnoughMoney(String username, Long amount) {
        return balanceRepository.existsByBalanceAndUsername(amount, username);
    }
}
