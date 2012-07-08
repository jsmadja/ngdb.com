package com.ngdb.web.pages;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
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
public class MuseumTest {

	// (all games from Japan, published by Snk on NeoGeoCD)

	@InjectMocks
	private Museum museum;

	@Mock
	private ReferenceService referenceService;

	@Before
	public void init() {
		ReflectionTestUtils.setField(museum, "filteredByGames", true);
	}

	@Test
	public void should_create_empty_query_label() {
		assertThat(museum.getQueryLabel()).isEqualTo("all games");
	}

	@Test
	public void should_create_query_label_with_origin() {
		ReflectionTestUtils.setField(museum, "filterOrigin", 0L);
		Mockito.when(referenceService.findOriginById(Mockito.anyLong())).thenReturn(new Origin("Japan"));
		assertThat(museum.getQueryLabel()).isEqualTo("all games from Japan");
	}

	@Test
	public void should_create_query_label_with_publisher() {
		ReflectionTestUtils.setField(museum, "filterPublisher", 0L);
		Mockito.when(referenceService.findPublisherBy(Mockito.anyLong())).thenReturn(new Publisher("SNK"));
		assertThat(museum.getQueryLabel()).isEqualTo("all games published by SNK");
	}

	@Test
	public void should_create_query_label_with_platform() {
		ReflectionTestUtils.setField(museum, "filterPlatform", 0L);
		Mockito.when(referenceService.findPlatformById(Mockito.anyLong())).thenReturn(new Platform("NeoGeoCD"));
		assertThat(museum.getQueryLabel()).isEqualTo("all games on NeoGeoCD");
	}

	@Test
	public void should_create_query_label_with_ngh() {
		ReflectionTestUtils.setField(museum, "filterValue", "nghX");
		ReflectionTestUtils.setField(museum, "filter", Filter.byNgh);
		assertThat(museum.getQueryLabel()).isEqualTo("all games with ngh 'nghX'");
	}

	@Test
	public void should_create_query_label_with_release_date() {
		ReflectionTestUtils.setField(museum, "filterValue", "1995-12-19");
		ReflectionTestUtils.setField(museum, "filter", Filter.byReleaseDate);
		assertThat(museum.getQueryLabel()).isEqualTo("all games with release date '12/19/1995'");
	}

	@Test
	public void should_create_query_label_with_tag() {
		ReflectionTestUtils.setField(museum, "filterValue", "tagX");
		ReflectionTestUtils.setField(museum, "filter", Filter.byTag);
		assertThat(museum.getQueryLabel()).isEqualTo("all games with tag 'tagX'");
	}

}
