package guru.springframework.reactivebeerclient.client;

import guru.springframework.reactivebeerclient.config.WebClientConfig;
import guru.springframework.reactivebeerclient.model.BeerDto;
import guru.springframework.reactivebeerclient.model.BeerPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeerClientImplTest {

    BeerClientImpl beerClient;

    @BeforeEach
    void setUp() {
        beerClient = new BeerClientImpl(new WebClientConfig().webClient());
    }
    @Test
    void listBeers() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null, null, null,
                null);

        BeerPagedList pagedList = beerPagedListMono.block();

        assertThat(pagedList).isNotNull();
        assertThat(pagedList.getContent().size()).isGreaterThan(0);
        System.out.println(pagedList.toList());
    }

    @Test
    void listBeersPageSize10() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10, null, null,
                null);

        BeerPagedList pagedList = beerPagedListMono.block();

        assertThat(pagedList).isNotNull();
        assertThat(pagedList.getContent().size()).isEqualTo(10);
    }

    @Test
    void listBeersNoRecords() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(10, 20, null, null,
                null);

        BeerPagedList pagedList = beerPagedListMono.block();

        assertThat(pagedList).isNotNull();
        assertThat(pagedList.getContent().size()).isEqualTo(0);
    }

    @Test
    void getBeerById() {
        Mono<BeerPagedList> pagedListMono = beerClient.listBeers(null, null, null, null,
                false);
        BeerPagedList pagedList = pagedListMono.block();
        BeerDto firstBeer =pagedList.getContent().get(0);

        UUID firstBeerUUID = firstBeer.getId();
        Mono<BeerDto> beerDtoMono = beerClient.getBeerById(firstBeerUUID,false);
        BeerDto beerDto = beerDtoMono.block();
        assertTrue(beerDto.getId().equals(firstBeerUUID));
     }

    @Test
    void createBeer() {
    }

    @Test
    void updateBeer() {
    }

    @Test
    void deleteBeerById() {
    }

    @Test
    void getBeerByUPC() {

        Mono<BeerPagedList> pagedListMono = beerClient.listBeers(null, null, null, null,
                null);
        BeerPagedList pagedList = pagedListMono.block();
        List<BeerDto> beers =pagedList.toList();
        BeerDto firstBeer = beers.get(0);
        String firstBeerUpc = firstBeer.getUpc();
        Mono<BeerDto> beerDtoMono = beerClient.getBeerByUPC(firstBeerUpc, false);
        BeerDto beerDto = beerDtoMono.block();
        assertTrue(beerDto.getUpc().equals(firstBeerUpc));

    }
}