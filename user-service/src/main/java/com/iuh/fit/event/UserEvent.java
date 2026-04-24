package com.iuh.fit.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    private String eventType;   // USER_REGISTERED
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private LocalDateTime timestamp;
}
