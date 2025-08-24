package ru.yandex.practicum.configuration;

import lombok.Data;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.*;

@Data
public class CustomSleepingBackOffPolicy implements SleepingBackOffPolicy<CustomSleepingBackOffPolicy> {

    private int attemptCount = 0;

    private Sleeper sleeper = new ThreadWaitSleeper();

    @Override
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        // Логика для расчета задержки
        long delay = CustomBackOffContext.calculateCustomDelay(attemptCount++);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new BackOffInterruptedException("Thread was interrupted", e);
        }
    }



    @Override
    public BackOffContext start(RetryContext retryContext) {
        return new CustomBackOffContext();
    }

    @Override
    public CustomSleepingBackOffPolicy withSleeper(Sleeper sleeper) {
        // Переопределение Sleeper
        return this;
    }

    @Data
    static class CustomBackOffContext implements BackOffContext {
        private static long calculateCustomDelay(int attempt) {
            // Адаптивная задержка на основе номера попытки
            if (attempt < 3) {
                // Первые 3 попытки: 1 секунда
                return 1000;
            } else if (attempt < 5) {
                // Следующие 2 попытки: 3 секунды
                return 3000;
            } else {
                // Все остальные попытки: 5 секунд
                return 5000;
            }
        }
    }
}