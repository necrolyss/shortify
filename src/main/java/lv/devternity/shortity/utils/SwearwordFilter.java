package lv.devternity.shortity.utils;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import lv.devternity.shortity.model.Swearword;
import lv.devternity.shortity.model.SwearwordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * Author: jurijsgrabovskis
 * Created at: 01/08/2017.
 */
@Component
public class SwearwordFilter {

    @Autowired
    private SwearwordRepository swearwordRepository;

    private BloomFilter<CharSequence> swearwordFilter;

    @PostConstruct
    private void load() {

        Iterable<Swearword> repositoryEntryPoint = swearwordRepository.findAll();
        Funnel<CharSequence> funnel = Funnels.stringFunnel(Charset.forName("utf-8"));
        swearwordFilter = BloomFilter.create(funnel, swearwordRepository.count());

        for (Swearword swearword : repositoryEntryPoint) {
            swearwordFilter.put(swearword.asText());
        }
    }

    public boolean test(String userInput) {
        return !swearwordFilter.mightContain(userInput);
    }

}
