package com.ngdb.web.pages;

import com.ngdb.entities.Registry;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResultTest {

    @InjectMocks
    private Result result;

    @Mock
    private Registry registry;

    @Mock
    private CurrentUser currentUser;

    @Mock
    private ReferenceService referenceService;

    @Before
    public void setup() {
        Mockito.when(currentUser.getUsername()).thenReturn("test");
    }

    @Test
    public void should_pass_query_without_filter() {
        result.setSearch("search");
        result.setup();
        Mockito.verify(registry).completeWithRelatedGames("search");
    }

    @Test
    public void should_pass_query_without_platform_filter() {
        result.setSearch("search platform:aes");
        result.setup();
        Mockito.verify(registry).completeWithRelatedGames("search");
        Mockito.verify(referenceService).findPlatformByName("aes");
    }

    @Test
    public void should_pass_query_without_p_filter() {
        result.setSearch("search p:aes");
        result.setup();
        Mockito.verify(registry).completeWithRelatedGames("search");
        Mockito.verify(referenceService).findPlatformByName("aes");
    }

    @Test
    public void should_pass_query_without_origin_filter() {
        result.setSearch("search origin:japan");
        result.setup();
        Mockito.verify(registry).completeWithRelatedGames("search");
        Mockito.verify(referenceService).findOriginByTitle("japan");
    }

    @Test
    public void should_pass_query_without_o_filter() {
        result.setSearch("search o:japan");
        result.setup();
        Mockito.verify(registry).completeWithRelatedGames("search");
        Mockito.verify(referenceService).findOriginByTitle("japan");
    }

    @Test
    public void should_pass_query_without_o_filter_and_p_filter() {
        result.setSearch("search p:aes o:japan");
        result.setup();
        Mockito.verify(registry).completeWithRelatedGames("search");
        Mockito.verify(referenceService).findPlatformByName("aes");
        Mockito.verify(referenceService).findOriginByTitle("japan");
    }

    @Test
    public void should_pass_query_without_o_filter_and_p_filter_different_order() {
        result.setSearch("p:aes search o:japan");
        result.setup();
        Mockito.verify(registry).completeWithRelatedGames("search");
        Mockito.verify(referenceService).findPlatformByName("aes");
        Mockito.verify(referenceService).findOriginByTitle("japan");
    }
}
