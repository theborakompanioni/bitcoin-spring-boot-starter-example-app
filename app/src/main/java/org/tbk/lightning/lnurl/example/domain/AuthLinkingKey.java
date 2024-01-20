package org.tbk.lightning.lnurl.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.acinq.bitcoin.PublicKey;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.tbk.lnurl.auth.K1;
import org.tbk.lnurl.auth.LinkingKey;

import java.time.Instant;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "lnurl_auth_linking_key")
class AuthLinkingKey implements Entity<WalletUser, AuthLinkingKey.LinkingKeyId> {

    private final LinkingKeyId id;

    private final long createdAt;

    @JsonIgnore
    @Version
    private Long version;

    private final String linkingKey;

    @Column(name = "least_recently_used_k1")
    private String leastRecentlyUsedK1;

    AuthLinkingKey(LinkingKey linkingKey) {
        this.id = LinkingKeyId.create();
        this.createdAt = Instant.now().toEpochMilli();
        this.linkingKey = linkingKey.toHex();

        checkArgument(this.toPublicKey().isValid(), "Linking key must be a valid public key");
    }

    public PublicKey toPublicKey() {
        return PublicKey.fromHex(linkingKey);
    }

    public void markUsedK1(K1 k1) {
        this.leastRecentlyUsedK1 = k1.toHex();
    }

    @Value(staticConstructor = "of")
    public static class LinkingKeyId implements Identifier {
        public static LinkingKeyId create() {
            return LinkingKeyId.of(UUID.randomUUID().toString());
        }

        @NonNull
        String id;
    }
}
