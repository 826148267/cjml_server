package edu.jnu.config;

import edu.jnu.domain.PublicKey;
import edu.jnu.utils.Action;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

/**
 * @author Guo zifan
 * @date 2022年01月28日 15:47
 */
@Component
public class OsuProtocolParams {
    public static PublicKey pk = null;
    public static int s = 0;

    @Value("${osu.pk.n}")
    private String n;

    @Value("${osu.pk.g}")
    private String g;

    @Value("${osu.base.layer}")
    private int baseLayer;

    @PostConstruct
    public void init() {
        pk = new PublicKey(new BigInteger(n), new BigInteger(g));
        s = baseLayer;
        for (int i = 0; i < 10; i++) {
            Action.N_[i] = pk.getN().pow(i);
        }
    }
}
