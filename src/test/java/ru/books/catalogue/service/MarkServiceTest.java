package ru.books.catalogue.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.books.catalogue.controller.requests.MarkRequest;
import ru.books.catalogue.exceptions.MarkNotFoundException;
import ru.books.catalogue.model.Mark;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MarkServiceTest {

    @Mock
    private MarkService markService;

    @Test
    void testAddMark() throws MarkNotFoundException {
        MarkRequest mockRequest = new MarkRequest(null, null, 5);
        Mark mockMark = new Mark(1L, null, null, 5, new Date());
        when(markService.addMark(mockRequest)).thenReturn(mockMark);

        assertEquals(mockMark, markService.addMark(mockRequest));
    }

    @Test
    void testGetAllMarks() {
        List<Mark> mockMarks = Arrays.asList(
                new Mark(1L, null, null, 4, new Date()),
                new Mark(2L, null, null, 5, new Date())
        );
        when(markService.getAllMarks()).thenReturn(mockMarks);

        assertEquals(mockMarks, markService.getAllMarks());
    }

    @Test
    void testGetMarksByBook() throws MarkNotFoundException {
        long bookId = 1L;
        List<Mark> mockMarks = Collections.singletonList(
                new Mark(1L, null, null, 5, new Date())
        );
        when(markService.getMarksByBook(bookId)).thenReturn(mockMarks);

        assertEquals(mockMarks, markService.getMarksByBook(bookId));
    }

    @Test
    void testGetMarkById() {
        Long markId = 1L;
        Mark mockMark = new Mark(1L, null, null, 5, new Date());
        when(markService.getMarkById(markId)).thenReturn(Optional.of(mockMark));

        assertTrue(markService.getMarkById(markId).isPresent());
        assertEquals(mockMark, markService.getMarkById(markId).get());
    }

    @Test
    void testDeleteMarkById() {
        Long markId = 1L;
        markService.deleteMarkById(markId);

        verify(markService, times(1)).deleteMarkById(markId);
    }

    @Test
    void testPutMarkById() {
        Long markId = 1L;
        Mark updatedMark = new Mark(1L, null, null, 4, new Date());
        when(markService.putMarkById(markId, updatedMark)).thenReturn(Optional.of(updatedMark));

        assertTrue(markService.putMarkById(markId, updatedMark).isPresent());
        assertEquals(updatedMark, markService.putMarkById(markId, updatedMark).get());
    }
}