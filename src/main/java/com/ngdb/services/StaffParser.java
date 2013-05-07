package com.ngdb.services;

import com.ngdb.entities.Participation;
import com.ngdb.entities.article.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaffParser {

    private List<Participation> participations = new ArrayList<Participation>();

    public List<Participation> createFrom(String content, Game game) {
        Scanner scanner = new Scanner(content);
        while (scanner.hasNextLine()) {
            String role = scanner.nextLine();
            insertParticipation(game, scanner, role);
        }
        return participations;
    }

    private void insertParticipation(Game game, Scanner scanner, String role) {
        while (scanner.hasNextLine()) {
            String employeeName = scanner.nextLine();
            if (employeeName.isEmpty()) {
                return;
            } else {
                Participation participation = new Participation(employeeName, role, game);
                participations.add(participation);
            }
        }
    }

}
