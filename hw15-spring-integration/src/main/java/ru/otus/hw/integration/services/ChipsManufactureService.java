package ru.otus.hw.integration.services;

import ru.otus.hw.integration.domain.Chips;
import ru.otus.hw.integration.domain.Potato;

public interface ChipsManufactureService {

    Chips cook (Potato potato);

}