package ohsoontaxi.backend.domain.email.service;

import ohsoontaxi.backend.domain.email.domain.EmailMessage;

public interface EmailUtils {

    EmailMessage findEmailMessageOauthAndEmail(String oauthProvider, String schEmail);
}
