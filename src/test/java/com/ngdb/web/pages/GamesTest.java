package com.ngdb.web.pages;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.Filter;

@RunWith(MockitoJUnitRunner.class)
public class GamesTest {

	// (all games from Japan, published by Snk on NeoGeoCD)

	@InjectMocks
	private Games games;

	@Mock
	private ReferenceService referenceService;

	@Test
	public void should_create_empty_query_label() {
		assertThat(games.getQueryLabel()).isEqualTo("all games");
	}

	@Test
	public void should_create_query_label_with_origin() {
		ReflectionTestUtils.setField(games, "filterOrigin", 0L);
		Mockito.when(referenceService.findOriginById(Mockito.anyLong())).thenReturn(new Origin("Japan"));
		assertThat(games.getQueryLabel()).isEqualTo("all games from Japan");
	}

	@Test
	public void should_create_query_label_with_publisher() {
		ReflectionTestUtils.setField(games, "filterPublisher", 0L);
		Mockito.when(referenceService.findPublisherBy(Mockito.anyLong())).thenReturn(new Publisher("SNK"));
		assertThat(games.getQueryLabel()).isEqualTo("all games published by SNK");
	}

	@Test
	public void should_create_query_label_with_platform() {
		ReflectionTestUtils.setField(games, "filterPlatform", 0L);
		Mockito.when(referenceService.findPlatformById(Mockito.anyLong())).thenReturn(new Platform("NeoGeoCD"));
		assertThat(games.getQueryLabel()).isEqualTo("all games on NeoGeoCD");
	}

	@Test
	public void should_create_query_label_with_ngh() {
		ReflectionTestUtils.setField(games, "filterValue", "nghX");
		ReflectionTestUtils.setField(games, "filter", Filter.byNgh);
		assertThat(games.getQueryLabel()).isEqualTo("all games with ngh 'nghX'");
	}

	@Test
	public void should_create_query_label_with_release_date() {
		ReflectionTestUtils.setField(games, "filterValue", "1995-12-19");
		ReflectionTestUtils.setField(games, "filter", Filter.byReleaseDate);
		assertThat(games.getQueryLabel()).isEqualTo("all games with release date '12/19/1995'");
	}

	@Test
	public void should_create_query_label_with_tag() {
		ReflectionTestUtils.setField(games, "filterValue", "tagX");
		ReflectionTestUtils.setField(games, "filter", Filter.byTag);
		assertThat(games.getQueryLabel()).isEqualTo("all games with tag 'tagX'");
	}

}
