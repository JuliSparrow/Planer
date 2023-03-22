package ru.netology.javaqa.javaqamvn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TodosTest {

    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {simpleTask, epic, meeting};
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "купить хлеб, хлеб",
            "забить гвоздь, забить",
            "сделать домашнее задание, домаш",
            "погулять с собакой, гулять",
            "взломать пентагон, взлом",
    })
    public void shouldMatchesIfSimpleTaskContainsQuery(String title, String query) {
        Task task = new SimpleTask(1, title);
        Assertions.assertTrue(task.matches(query));
    }

    @ParameterizedTest
    @CsvSource({
            "купить хлеб, молоко",
            "забить гвоздь, молоток",
            "сделать домашнее задание, зделать",
            "погулять с собакой, погулять ссобакой",
            "взломать пентагон, легко",
    })
    public void shouldNotMatchesIfSimpleTaskNotContainsQuery(String title, String query) {
        Task task = new SimpleTask(1, title);

        Assertions.assertFalse(task.matches(query));
    }

    @Test
    public void shouldMatchesIfEpicContainsQuery() {
        String[] tasks = {"масло", "хлеб", "сыр"};
        Task task = new Epic(1, tasks);

        String query1 = "масло";
        String query2 = "хлеб";
        String query3 = "сыр";
        String query4 = "Сыр";

        Assertions.assertTrue(task.matches(query1));
        Assertions.assertTrue(task.matches(query2));
        Assertions.assertTrue(task.matches(query3));
        Assertions.assertFalse(task.matches(query4));
    }

    @Test
    public void shouldMatchesIfMeetingContainsQuery() {
        Task meeting = new Meeting(3, "Обсудить покупки", "Вечеринка", "Вечер пятницы");

        Assertions.assertTrue(meeting.matches("покупки"));
        Assertions.assertTrue(meeting.matches("Вечеринка"));
        Assertions.assertFalse(meeting.matches("Вечер пятницы"));
    }

    @Test
    public void shouldSearchTasksIfSubtasksContainsQuery() {
        Task simpleTask = new SimpleTask(1, "купить хлеб");
        String[] epicTasks = {"масло", "хлеб", "сыр"};
        Task epic = new Epic(2, epicTasks);
        Task meeting = new Meeting(3, "Обсудить покупки", "Вечеринка", "Вечер пятницы");

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {simpleTask, epic};
        Task[] actual = todos.search("хлеб");

        Assertions.assertArrayEquals(actual, expected);

        actual = todos.search("кино");
        expected = new Task[0];

        Assertions.assertArrayEquals(actual, expected);
    }
}