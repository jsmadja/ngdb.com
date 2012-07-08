package com.ngdb;

import static org.fest.assertions.Assertions.assertThat;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.ngdb.entities.MuseumFilter;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;

@RunWith(MockitoJUnitRunner.class)
public class MuseumFilterTest {

	// (all games from Japan, published by Snk on NeoGeoCD)

	@InjectMocks
	private MuseumFilter museumFilter;

	@Before
	public void init() {
		ReflectionTestUtils.setField(museumFilter, "filteredByGames", true);
	}

	@Test
	public void should_create_empty_query_label() {
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all games");
	}

	@Test
	public void should_create_query_label_with_origin() {
		museumFilter.filterByOrigin(new Origin("Japan"));
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all games from Japan");
	}

	@Test
	public void should_create_query_label_with_publisher() {
		museumFilter.filterByPublisher(new Publisher("SNK"));
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all games published by SNK");
	}

	@Test
	public void should_create_query_label_with_platform() {
		museumFilter.filterByPlatform(new Platform("NeoGeoCD"));
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all games on NeoGeoCD");
	}

	@Test
	public void should_create_query_label_with_ngh() {
		museumFilter.filterByNgh("nghX");
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all games with ngh 'nghX'");
	}

	@Test
	public void should_create_query_label_with_release_date() {
		museumFilter.filterByReleaseDate(new DateTime().withYear(1995).withMonthOfYear(12).withDayOfMonth(19).toDate());
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all games with release date '12/19/1995'");
	}

	@Test
	public void should_create_query_label_with_tag() {
		museumFilter.filterByTag(new Tag("tagX", null));
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all games with tag <span class=\"orange\">tagX</span>");
	}

}
