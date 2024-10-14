package edu.jnu.config.OSU;

import edu.jnu.utils.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

/**
 * 协议参数.
 * 作用：不经意选择与更新协议OSU的系统参数.
 * @author Guo zifan
 * @date 2022年01月28日 15:47
 */
@Component
public class OsuProtocolParams {
    public static PrivateKey SK = null;
    public static PublicKey PK = null;
    public static int s;
    public static int randomSequenceSize;
    public static String key;

    @Value("${osu.base.layer}")
    private int baseLayer;

    @Value("${osu.random.sequence.size}")
    private int size;

    @Value("${osu.sk.n}")
    private String n;

    @Value("${osu.sk.g}")
    private String g;

    @Value("${osu.sk.d}")
    private String d;

    @Value("${encode.key}")
    private String myKey;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        SK = new PrivateKey(new BigInteger(d), new BigInteger(n), new BigInteger(g));
        PK = new PublicKey(new BigInteger(n), new BigInteger(g));
        for (int i = 0; i < 10; i++) {
            Action.N_[i] = SK.getN().pow(i);
        }
        s = baseLayer;
        randomSequenceSize = size;
        key = myKey;

        if (!Boolean.TRUE.equals(redisTemplate.hasKey("userInfoRecordNum"))) {
            redisTemplate.opsForValue().increment("userInfoRecordNum", 0);
        }
    }
}
