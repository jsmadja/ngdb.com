package com.ngdb.services;

import com.ngdb.entities.Employee;
import com.ngdb.entities.Staff;
import com.ngdb.entities.article.Game;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.google.common.io.ByteStreams.toByteArray;
import static org.fest.assertions.Assertions.assertThat;

public class StaffParserTest {

    private StaffParser staffParser = new StaffParser();

    @Test
    public void should_create_staff_from_file() throws IOException {
        InputStream stream = StaffParserTest.class.getClassLoader().getResourceAsStream("com/ngdb/services/staff.txt");
        String content = new String(toByteArray(stream));

        Game game = Mockito.mock(Game.class);
        Staff staff = staffParser.createFrom(content, game, new ArrayList<Employee>());

        assertThat(staff.employees()).hasSize(52);
    }

}
