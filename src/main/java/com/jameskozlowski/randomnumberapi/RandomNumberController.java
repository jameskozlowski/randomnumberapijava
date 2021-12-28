package com.jameskozlowski.randomnumberapi;

import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.apache.commons.text.CharacterPredicates.ASCII_LETTERS;

@RestController
public class RandomNumberController {

    Logger logger = LoggerFactory.getLogger(RandomNumberController.class);

    @GetMapping(value = { "/api/v1.0/randomnumber", "/api/v1.0/random" })
    public List<Integer> randomNumber(@RequestParam("min") Optional<Integer> minParam,
                                      @RequestParam("max") Optional<Integer> maxParam,
                                      @RequestParam("count") Optional<Integer> countParam) {

        int min = minParam.orElse(0);
        int max = maxParam.orElse(100) + 1;
        int count = countParam.orElse(1);

        Random rand = new Random(); //instance of random class
        List<Integer> list = new ArrayList<Integer>();

        for( int i = 0; i < count; i++)
            list.add(rand.nextInt(min, max));

        return list;
    }

    @GetMapping(value = { "/api/v1.0/randomuuid", "/api/v1.0/uuid" })
    public List<String> randomUUID(@RequestParam("count") Optional<Integer> countParam) {

        int count = countParam.orElse(1);
        List<String> list = new ArrayList<String>();

        for( int i = 0; i < count; i++)
            list.add(UUID.randomUUID().toString());

        return list;
    }

    @GetMapping(value = { "/api/v1.0/randomstring", "/api/v1.0/string" })
    public List<String> randomString(@RequestParam("min") Optional<Integer> minParam,
                                     @RequestParam("max") Optional<Integer> maxParam,
                                     @RequestParam("count") Optional<Integer> countParam,
                                     @RequestParam("all") Optional<Boolean> allParam) {

        int min = minParam.orElse(10);
        int max = maxParam.orElse(20);
        int count = countParam.orElse(1);
        Boolean all = allParam.orElse(false);

        List<String> list = new ArrayList<String>();
        RandomStringGenerator generator = null;

        if (!all)
            generator = new RandomStringGenerator.Builder().filteredBy(ASCII_LETTERS).build();
        else
            generator = new RandomStringGenerator.Builder().withinRange('!', '~').build();

        for( int i = 0; i < count; i++)
            list.add(generator.generate(min, max));

        return list;
    }

    @GetMapping(value = { "api/v1.0/randomredditnumber"})
    public List<Integer> randomRedditNumber(@RequestParam("min") Optional<Integer> minParam,
                                            @RequestParam("max") Optional<Integer> maxParam,
                                            @RequestParam("count") Optional<Integer> countParam) {
        int min = minParam.orElse(0);
        int max = maxParam.orElse(100) + 1;
        int count = countParam.orElse(1);

        Random rand = new Random(); //instance of random class
        List<Integer> list = new ArrayList<Integer>();

        for( int i = 0; i < count; i++) {
            rand.setSeed(RedditSeeds.get());
            list.add(rand.nextInt(min, max));
        }

        return list;
    }



}

