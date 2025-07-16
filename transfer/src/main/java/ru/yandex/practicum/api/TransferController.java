package ru.yandex.practicum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.TransferDto;
import ru.yandex.practicum.service.TransferService;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferDto transferDto) {
        transferService.transfer(transferDto);
    }
}
