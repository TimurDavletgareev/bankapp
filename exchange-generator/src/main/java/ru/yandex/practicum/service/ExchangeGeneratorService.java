package ru.yandex.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Currency;
import ru.yandex.practicum.model.Rate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

@Service
@Slf4j
public class ExchangeGeneratorService {

    public List<Rate> getRates() {
        log.info("generating rates");
        List<Rate> rates = new ArrayList<>();
        Arrays.stream(Currency.values())
                .forEach(currency -> {
                    Rate rate = new Rate();
                    rate.setName(currency.name());
                    rate.setTitle(currency.getTitle());
                    if (currency == Currency.RUB) {
                        rate.setValue(1.0);
                    } else {
                        RandomGenerator randomGenerator = RandomGenerator.getDefault();
                        rate.setValue(randomGenerator.nextDouble(89, 99));
                    }
                    log.info("generated rate {}", rate);
                    rates.add(rate);
                });
        return rates;
    }
}
