package ru.skypro.homework.dto;
import lombok.Data;

import java.util.Collection;

/**
 * Класс ResponseWrapper представляет собой обертку для ответа с коллекцией результатов.
 *
 * @param <T> тип элементов в коллекции результатов
 */
@Data//Аннотация `@Data` автоматически генерирует методы `toString()`,
// `equals()`, `hashCode()` и геттеры/сеттеры для всех полей класса.
public class ResponseWrapper<T> {
    private final int count;//`count` типа `int`, представляющий количество элементов в коллекции `results`
    private final Collection<T> results;// `results` типа `Collection<T>`, представляющий коллекцию результатов

  //Конструктор класса `ResponseWrapper` принимает коллекцию результатов и инициализирует поля `count` и `results`.
    public ResponseWrapper(Collection<T> results) {
        this.count = results.size();
        this.results = results;
    }
}