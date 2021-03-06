package com.ngdb;

import com.ngdb.entities.MuseumFilter;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.fest.assertions.Assertions.assertThat;

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
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all <span class=\"orange\">games</span>");
	}

	@Test
	public void should_create_query_label_with_origin() {
		museumFilter.filterByOrigin(new Origin("Japan"));
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all <span class=\"orange\">games</span> from <span class=\"orange\">Japan</span>");
	}

	@Test
	public void should_create_query_label_with_publisher() {
		museumFilter.filterByPublisher(new Publisher("SNK"));
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all <span class=\"orange\">games</span> published by <span class=\"orange\">SNK</span>");
	}

	@Test
	public void should_create_query_label_with_platform() {
		museumFilter.filterByPlatform(new Platform("NeoGeoCD"));
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all <span class=\"orange\">games</span> on <span class=\"orange\">NeoGeoCD</span>");
	}

	@Test
	public void should_create_query_label_with_ngh() {
		museumFilter.filterByNgh("nghX");
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all <span class=\"orange\">games</span> with ngh <span class=\"orange\">nghX</span>");
	}

	@Test
	public void should_create_query_label_with_release_date() {
		museumFilter.filterByReleaseDate(new DateTime().withYear(1995).withMonthOfYear(12).withDayOfMonth(19).toDate());
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all <span class=\"orange\">games</span> released at <span class=\"orange\">12/19/1995</span>");
	}

	@Test
	public void should_create_query_label_with_tag() {
		museumFilter.filterByTag("tagX");
		assertThat(museumFilter.getQueryLabel()).isEqualTo("all <span class=\"orange\">games</span> with tag <span class=\"orange\">tagX</span>");
	}

}
