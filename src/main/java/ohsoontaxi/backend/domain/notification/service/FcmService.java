package ohsoontaxi.backend.domain.notification.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FcmService {

    public ApiFuture<BatchResponse> sendGroupMessageAsync(
            List<String> tokenList, String title, String content) {
        MulticastMessage multicast =
                MulticastMessage.builder()
                        .addAllTokens(tokenList)
                        .putData("title", title)
                        .putData("content", content)
                        .setApnsConfig(
                                ApnsConfig.builder()
                                        .setAps(Aps.builder().setSound("default").build())
                                        .build())
                        .build();

        return FirebaseMessaging.getInstance().sendMulticastAsync(multicast);
    }

}