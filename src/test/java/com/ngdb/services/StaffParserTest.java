package com.ngdb.services;

import com.ngdb.entities.Participation;
import com.ngdb.entities.article.Game;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.google.common.io.ByteStreams.toByteArray;
import static org.fest.assertions.Assertions.assertThat;

public class StaffParserTest {

    private StaffParser staffParser = new StaffParser();

    @Test
    public void should_create_staff_from_file() throws IOException {
        InputStream stream = StaffParserTest.class.getClassLoader().getResourceAsStream("com/ngdb/services/staff.txt");
        String content = new String(toByteArray(stream));

        Game game = Mockito.mock(Game.class);
        List<Participation> participations = staffParser.createFrom(content, game);

        assertThat(participations).hasSize(52);
    }

}
