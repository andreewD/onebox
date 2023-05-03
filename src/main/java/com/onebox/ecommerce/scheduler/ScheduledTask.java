package com.onebox.ecommerce.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTask {
    @Autowired
    private CartCleanupService cartCleanupService;

    @Scheduled(fixedDelay = 10000)
    public void deleteInactiveCarts() {
        cartCleanupService.deleteInactiveCarts();
    }

}
